package org.usfirst.frc.team1318.robot.driver.autonomous;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDigitalInput;
import org.usfirst.frc.team1318.robot.driver.IControlTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ConcurrentTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.DriveDistanceTimedTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.DriveRouteTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.GearExtendTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.IntakeExtendTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.SequentialTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ShooterFeedTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.ShooterSpinTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.TurnTimedTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.VisionCenteringTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.VisionForwardAndCenterTask;
import org.usfirst.frc.team1318.robot.driver.controltasks.WaitTask;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class AutonomousRoutineSelector
{
    private static final String LogName = "auto";
    private final IDashboardLogger logger;

    private final IDigitalInput sideOfField;
    private final IDigitalInput dipSwitchA;
    private final IDigitalInput dipSwitchB;

    /**
     * Initializes a new AutonomousDriver
     */
    @Inject
    public AutonomousRoutineSelector(
        IDashboardLogger logger,
        @Named("AUTO_SIDE_OF_FIELD") IDigitalInput sideOfField,
        @Named("AUTO_DIP_SWITCH_A") IDigitalInput dipSwitchA,
        @Named("AUTO_DIP_SWITCH_B") IDigitalInput dipSwitchB)
    {
        // initialize robot parts that are used to select autonomous routine (e.g. dipswitches) here...
        this.logger = logger;
        this.sideOfField = sideOfField;
        this.dipSwitchA = dipSwitchA;
        this.dipSwitchB = dipSwitchB;
    }

    /**
     * Check what routine we want to use and return it
     * @return autonomous routine to execute during autonomous mode
     */
    public IControlTask selectRoutine()
    {
        int routineSelection = 0;

        // add next base2 number (1, 2, 4, 8, 16, etc.) here based on number of dipswitches and which is on...
        if (this.dipSwitchA.get())
        {
            routineSelection += 1;
        }

        if (this.dipSwitchB.get())
        {
            routineSelection += 2;
        }

        boolean isOnRedSide = this.sideOfField.get();

        // print routine selection to the smartdash
        this.logger.logInteger(AutonomousRoutineSelector.LogName, "routine", routineSelection);

        switch (routineSelection)
        {
            case 0: // No switches flipped
                return AutonomousRoutineSelector.GetFillerRoutine();

            case 1: // Just A flipped
                return AutonomousRoutineSelector.GetShootCloseRoutine(isOnRedSide);

            case 2: // Just B flipped
                return AutonomousRoutineSelector.GetFarGearRoutine(isOnRedSide);

            case 3: // A and B flipped
                return AutonomousRoutineSelector.GetStraightRoutine(isOnRedSide);

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

    private static IControlTask GetFarGearRoutine(boolean isOnRedSide)
    {
        return ConcurrentTask.AllTasks(
            SequentialTask.Sequence(
                new GearExtendTask(0.5),
                new IntakeExtendTask(true, 1.0),
                new IntakeExtendTask(false, 0.5)),
            SequentialTask.Sequence(
                new DriveDistanceTimedTask(74.375, 6.0),
                new TurnTimedTask(isOnRedSide ? 60.0 : -60.0, 2.0),
                new VisionCenteringTask(true),
                new VisionForwardAndCenterTask(true),
                new DriveDistanceTimedTask(18.0, 2.0)));
    }

    private static IControlTask GetShootCloseRoutine(boolean isOnRedSide)
    {
        return ConcurrentTask.AllTasks(
            SequentialTask.Sequence(
                new GearExtendTask(0.5),
                new IntakeExtendTask(true, 1.0),
                new IntakeExtendTask(false, 0.5)),
            SequentialTask.Sequence(
                ConcurrentTask.AnyTasks(
                    new ShooterSpinTask(false, TuningConstants.SHOOTER_CLOSE_SHOT_VELOCITY),
                    SequentialTask.Sequence(
                        new WaitTask(1.0),
                        new ShooterFeedTask(3.0))),
                new DriveRouteTask(
                    percentage ->
                    {
                        if (isOnRedSide)
                        {
                            return percentage * 40.0;
                        }
                        else
                        {
                            return percentage * 20.0;
                        }
                    },
                    percentage ->
                    {
                        if (isOnRedSide)
                        {
                            return percentage * 20.0;
                        }
                        else
                        {
                            return percentage * 40.0;
                        }
                    },
                    4.0),
                new VisionCenteringTask(true),
                new VisionForwardAndCenterTask(true),
                new DriveDistanceTimedTask(18.0, 2.0)));
    }

    private static IControlTask GetStraightRoutine(boolean isOnRedSide)
    {
        return ConcurrentTask.AllTasks(
            SequentialTask.Sequence(
                new GearExtendTask(0.5),
                new IntakeExtendTask(true, 1.0),
                new IntakeExtendTask(false, 0.5)),
            SequentialTask.Sequence(
                new DriveDistanceTimedTask(48.0, 2.0),
                new VisionCenteringTask(true),
                new VisionForwardAndCenterTask(true),
                new DriveDistanceTimedTask(18.0, 2.0)));
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
