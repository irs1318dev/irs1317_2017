package org.usfirst.frc.team1318.robot.driver.autonomous;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDigitalInput;
import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ConcurrentTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.DriveDistanceTimedTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.DriveRouteTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.IntakeArmExtendTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.IntakeConveyorExtendTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.IntakeSpinTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.SequentialTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ShooterFeedTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ShooterSpinTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.TurnTimedTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.VisionAdvanceAndCenterTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.WaitTask;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class AutonomousRoutineSelector
{
    private static final String LogName = "auto";
    private final IDashboardLogger logger;

    private final IDigitalInput turnLeftSwitch;
    private final IDigitalInput dipSwitchA;
    private final IDigitalInput dipSwitchB;

    /**
     * Initializes a new AutonomousDriver
     */
    @Inject
    public AutonomousRoutineSelector(
        IDashboardLogger logger,
        @Named("AUTO_SIDE_OF_FIELD") IDigitalInput turnLeftSwitch,
        @Named("AUTO_DIP_SWITCH_A") IDigitalInput dipSwitchA,
        @Named("AUTO_DIP_SWITCH_B") IDigitalInput dipSwitchB)
    {
        // initialize robot parts that are used to select autonomous routine (e.g. dipswitches) here...
        this.logger = logger;
        this.turnLeftSwitch = turnLeftSwitch;
        this.dipSwitchA = dipSwitchA;
        this.dipSwitchB = dipSwitchB;
    }

    /**
     * Check what routine we want to use and return it
     * @return autonomous routine to execute during autonomous mode
     */
    public IControlTask selectRoutine()
    {
        int routineSelection = 0; // 0

        // add next base2 number (1, 2, 4, 8, 16, etc.) here based on number of dipswitches and which is on...
        if (this.dipSwitchA.get())
        {
            routineSelection += 1;
        }

        if (this.dipSwitchB.get())
        {
            routineSelection += 2;
        }

        boolean turnLeft = this.turnLeftSwitch.get();

        // print routine selection to the smartdash
        this.logger.logInteger(
            AutonomousRoutineSelector.LogName, "routine",
            routineSelection);

        switch (routineSelection)
        {
            case 0: // No switches flipped
                return AutonomousRoutineSelector.GetFillerRoutine();

            case 1: // Just A flipped
                return AutonomousRoutineSelector.GetShootCloseRoutine(turnLeft);

            case 2: // Just B flipped
                return AutonomousRoutineSelector.GetFarGearRoutine(turnLeft);

            case 3: // A and B flipped
                return AutonomousRoutineSelector.GetStraightRoutine(turnLeft);

            default: // CANNOT READ
                return AutonomousRoutineSelector.GetFillerRoutine();
        }
    }

    /**
     * Gets an autonomous routine that does nothing
     * 
     * @return very long WaitTask
     */
    private static IControlTask GetFillerRoutine()
    {
        return new WaitTask(0);
    }

    private static IControlTask GetFarGearRoutine(boolean turnLeft)
    {
        return ConcurrentTask.AllTasks(
            AutonomousRoutineSelector.GearSetUp(),
            SequentialTask.Sequence(
                new DriveDistanceTimedTask(88, 4.0), // 90 inches forwards, minus 18 from center of robot to bumper...
                new TurnTimedTask(turnLeft ? -60.0 : 60.0, 1.5),
                new VisionAdvanceAndCenterTask(true),
                new DriveDistanceTimedTask(24.0, 1.5),
                AutonomousRoutineSelector.PlaceGear()));
    }

    private static IControlTask GetShootCloseRoutine(boolean turnLeft)
    {
        return ConcurrentTask.AllTasks(
            AutonomousRoutineSelector.GearSetUp(),
            SequentialTask.Sequence(
                ConcurrentTask.AnyTasks(
                    new ShooterSpinTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY),
                    SequentialTask.Sequence(
                        new WaitTask(1.0),
                        new ShooterFeedTask(4.0))),
                new DriveRouteTask(
                    percentage ->
                    {
                        if (turnLeft)
                        {
                            return percentage * 41.0;
                        }
                        else
                        {
                            return percentage * 13.0;
                        }
                    },
                    percentage ->
                    {
                        if (turnLeft)
                        {
                            return percentage * 13.0;
                        }
                        else
                        {
                            return percentage * 41.0;
                        }
                    },
                    3.0),
                new DriveDistanceTimedTask(50.0, 1.5),
                new TurnTimedTask(turnLeft ? -68.0 : 68.0, 1.5),
                new VisionAdvanceAndCenterTask(true),
                new DriveDistanceTimedTask(24.0, 1.5),
                AutonomousRoutineSelector.PlaceGear()));
    }

    private static IControlTask GetStraightRoutine(boolean isOnRedSide)
    {
        return ConcurrentTask.AllTasks(
            AutonomousRoutineSelector.GearSetUp(),
            SequentialTask.Sequence(
                new DriveDistanceTimedTask(40.0, 2.5),
                new VisionAdvanceAndCenterTask(true),
                new DriveDistanceTimedTask(24.0, 1.5),
                AutonomousRoutineSelector.PlaceGear()));
    }

    private static IControlTask GetStraightDeadReckoningRoutine()
    {
        return ConcurrentTask.AllTasks(
            AutonomousRoutineSelector.GearSetUp(),
            SequentialTask.Sequence(
                new DriveDistanceTimedTask(TuningConstants.AIRSHIP_DISTANCE, 5.0),
                AutonomousRoutineSelector.PlaceGear()));
    }

    private static IControlTask GearSetUp()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeSpinTask(true, 0.15),
                new IntakeArmExtendTask(true, 0.15)),
            new IntakeArmExtendTask(false, 1.0),
            new IntakeConveyorExtendTask(true, 0.5));
    }

    private static IControlTask PlaceGear()
    {
        return SequentialTask.Sequence(
            ConcurrentTask.AllTasks(
                new IntakeArmExtendTask(true, 0.25),
                new IntakeConveyorExtendTask(false, 0.25)),
            new DriveDistanceTimedTask(-12.0, 1.0));
    }
}

/*







































































































































                                      .                                                             
                                    .;+;+                                                           
                                    .+;;'   `,+'.                                                   
                                    ;';;+:..`` :+'+                                                 
                                    ,'+`    .+;;;;;+                                                
                                     ;,,, .+;;;;;'+++;                                              
                                     ;' `+;;;;;#+'+'+''#:.                                          
                                     '`+';;;'+;+;+++'''+'.                                          
                                     #';;;;#';+'+'''+''+'                                           
                                     ;;;;#;,+;;+;;;'''''':                                          
                                     ';'++'.`+;;'';;''+'',                                          
                                     :#'#+'``.'+++'#++'':`                                          
                                      `';++##```##+.''.##                                           
                                      +++#   #`#  `++++                                             
                                      +'#+ # :#: # ##'+                                             
                                      `#+#   +`+   #'#`                                             
                                       :,.+,+,`:+,+..,                                              
                                       `,:```,`,`.`;,                                               
                                        :+.;``.``;.#;                                               
                                        .'``'+'+'``'.                                               
                                         ,````````..                                                
                                          :```````:                                                 
                                          +``.:,``'                                                 
                                          :```````:                                                 
                                           +`````+                                                  
                                            ';+##                                                   
                                            '```'                                                   
                                           `'```'`                                                  
                                         .+''''''''                                                 
                                        +;;;;;;;;''#                                                
                                       :       `   `:                                               
                                      `,            '                                               
                                      +              '                                              
                                     ,;';,``.``.,,,:;#                                              
                                     +;;;;;;;;;;;;;;;'                                              
                                    ,';;;;;;;;;;;;;;;',                                             
                                    +:;;;;;;';;;;;;;;;+                                             
                                   `.   .:,;+;;:::;.``,                                             
                                   :`       #,       `.`                                            
                                   +       # ;        .;                                            
                                  .;;,`    ,         `,+                                            
                                  +;;;;;;''';;;;;;;';;';                                            
                                  +;;;;;;;';;;;;;;;;;'';;                                           
                                 `';;;;;;';;;;;;;;;;;';;+                                           
                                 + `:;;;;+;;;;;;;;';'''::                                           
                                 '     `:  ```````    ,  ,                                          
                                :       '             ;  +                                          
                                '`     ..             ,  ,                                          
                               ,;;;;;..+,`        ```.':;',                                         
                               +;;;;;;'+;;;;;;;;;;;;;;+;;;+                                         
                               ';;;;;;++;;;;;;;;;;;;;;';;;+                                         
                              `.:';;;;;#;;;;;;;;;;;;;;';;;;`                                        
                              ;    `,; ',:;;';;';;;;;:;``  +                                        
                              +      ; ;              ;    `                                        
                              ;      : +              '    `;                                       
                              ';:`` `` '              :`,:;;+                                       
                             `';;;;'+  +,..```````..:;#;;;;;;.                                      
                             `;;;;;;+  +;;;;;;;;;;;;;':';;;;;#                                      
                             .;;;;;;+  ';;;;;;;;;;;;;;,';;;;` .                                     
                             : `.;;'+  +;;;;;;;;;;;;;','.`    +                                     
                             '      ;  +.,,;:;:;;;,..`: ,     ``                                    
                             +      ,  '              : ;   .;'+                                    
                             +.`   ``  +              ;  ;:;;;;':                                   
                             ';;;';;`  +             .'  ;;;;;;;+                                   
                             ';;;;;'   :+++#++##+#+''',   +;;;;.`.                                  
                             +;;;;;'   +;;::;;;+:+;;'',   ,;;.   +                                  
                            ``:;;;;+   +;;:;;;:+;+;;++;    +     .`                                 
                             `   ``'   +;;;;;;;+;+;;'+;     ,   ;#,                                 
                            .      ;   ';;;;;;;;;;;;++'     + .+``.;                                
                            ``     ;   ';;;;;;+;';;;'+'      #`````:,                               
                             +++;,:.   ':;''++;:';:;'';      +``````,`                              
                             ,```,+    +;;';:;;+;;;;'';      +``````,+                              
                            .``````:   ;:;;++';;;;;;';,      ,``:#``+`.                             
                            ,``````'   `';;;;:;;;;;;+;`     '+``+:'`..'                             
                            ,``````'    +;;;;;;;;;;;''     ;:'``#;;.`++                             
                            ```````;    `;:;;;;;;;;;;#     ':'``++:+`+;                             
                            ```'`.`;     +;;;;;;;;;;;+    :::#``' +#`';                             
                            ,``'`:`#     `';;;;;;;;;;+    +:'.`,. ++`;;                             
                            +`.``+`'     :#;;;;;;;;;;;`   +:# ,`  +;`.'                             
                           ,.`+`.:.      ##;;;;;;;;;;;'   ,'`     ;:+#                              
                           '`;.`+`#      ##+;;;;;;;;;;+          ,::;                               
                           ,+,`:``,     :###;;;;;;;;;:'          +:;`                               
                            '`,,`+      ';##';;;;;;;;;;.         +:#                                
                             '+.+       +;;##;;;;;;;;;;'         ;:;                                
                               `       :;;;+#;;;;;;;;;;+        ;::`                                
                                       +;;;;#+;;;;;;;;;;        +:'                                 
                                       ';;;;+#;;;;;;;;;;.       ;:'                                 
                                      ,;;;;;;#;;;;;;;;;;+      +::.                                 
                                      +;;;;;;'';;;;;;;;;'      +:+                                  
                                     `;;;;;;;;#;;;;;;;;;;`    `;:+                                  
                                     ,;;;;;;;;+;;;;;;;;;;+    ':;,                                  
                                     +;;;;;;;;;+;;;;;;;;;'    +:+                                   
                                    .;;;;;;;;;+,;;;;;;;;;;`   ;;+                                   
                                    ';;;;;;;;;, ';;;;;;:;;,  +;:,                                   
                                    ';;;;;;;;'  +;;;;;;;;;'  +:+                                    
                                   ;;;;;;;;;;+  ,;;;;;;;;;+  ;:'                                    
                                   +;;;;;;;;;    ';;;;;;;;;`;:;`                                    
                                   ;;;;;;;;;+    +;;;;;;;;;+#:+                                     
                                  ';;;;;;;;;:    ;;;;;;;;;;';:'                                     
                                 `';;;;;;;:'      ';;;;;;;;;;:.                                     
                                 .;;;;;;;;;+      +;;;;;;;;;'+                                      
                                 +;;;;;;;;;       ';;;;;;;;;#+                                      
                                `;;;;;;;;;+       `;;;;;;;;;;`                                      
                                +;;;;;;;;;.        +;;;;;;;;;`                                      
                                ';;;;;;;:'         ;;;;;;;;;;;                                      
                               :;;;;;;;;;:         `;;;;;;;;;+                                      
                               +;;;;;;;;;           ';;;;;;;;;`                                     
                               ;;;;;;;;;+           ';;;;;;;;;:                                     
                              ';;;;;;;;;;           ,;;;;;;;;;+                                     
                              ':;;;;;;;'             +;;;;;;;;;                                     
                             .;:;;;;;;;'             +;;;;;;;;;:                                    
                             +;;;;;;;;;`             .;;;;;;;;;+                                    
                            `;;;;;;;;;+               ;:;;;;;;;;`                                   
                            ;;;;;;;;;;.               +;;;;;;;::.                                   
                            ';;;;;;;;'`               :;;;;;;;;:+                                   
                           :;;;;;;;;:'                ';;;;;;;;;'                                   
                           ';;;;;;;;'`                +#;;;;;;;;;`                                  
                          `;;;;;;;;;+                 '';;;;;;;;;+                                  
                          +;;;;;;;;;.                '::;;;;;;;;;+                                  
                          ;;;;;;;;;+                 #:'';;;;;;;;;`                                 
                         .#;;;;;;;;'                `;:+;;;;;;;;;;;                                 
                         ':'';;;;;;                 '::.,;;;;;;;;;+                                 
                        +::::+';;;+                 ':'  +:;;;;;;;;`                                
                       `;;;::::;#+:                `;:+  +;;;;;;;:;;      '#+,                      
                       +#::::::::;'`               +:;,  `;;;;:;;'#';;;;;::;:'`                     
                       ;:''::::::::#`              +:'    ';:;;+'::;;:;::::::''                     
                       +::;+':::::::'.            .:;+    '''+;::;:;:::;:::;':'                     
                        ';;:;'';:::::':           +::.     +:::::::::::::;#;:#                      
                         .''##;#;:;;:::'+        `+;'      ;:;::::::::;'+;:'+                       
                           ` `+:;+:;::;::+       +:;#      ';:::;:+#+';:::+.                        
                              ,+::+#';::;+       ';::      #:;;'+';'''++:`                          
                                '':::;'''#      ,:;;`      #';:;;:+                                 
                                 `:'++;;':       :++       .;;:;;#,                                 
                                       `                    '':``                                   


*/
