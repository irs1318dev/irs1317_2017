package org.usfirst.frc.team1318.robot.intake;

import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ITimer;
import org.usfirst.frc.team1318.robot.driver.Driver;
import org.usfirst.frc.team1318.robot.driver.Operation;

import com.google.inject.Inject;

public class IntakeController implements IController
{
    private final IntakeComponent intakeComponent;
    
    private Driver driver;

    private final ITimer timer;
    private double startTime = 0.0;
    
    private boolean isArmExtended;
    private boolean isConveyorExtended;

    @Inject
    public IntakeController(ITimer timer, IntakeComponent intakeComponent)
    {
        this.intakeComponent = intakeComponent;
        this.timer = timer;

        this.isArmExtended = false;
        this.isConveyorExtended = false;
    }

    @Override
    public void update()
    {
        // spin intake motor at speed if button is clicked
        if (this.driver.getDigital(Operation.IntakeIn))
        {
            this.intakeComponent.setMotorSpeed(TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        }
        else if (this.driver.getDigital(Operation.IntakeOut))
        {
            this.intakeComponent.setMotorSpeed(-TuningConstants.INTAKE_MAX_MOTOR_SPEED);
        }
        else
        {
            this.intakeComponent.setMotorSpeed(0.0);
        }

        // extend intake arm if button is clicked
        if (this.driver.getDigital(Operation.IntakeArmExtend))
        {
            this.intakeComponent.extendArm(true);
            this.isArmExtended = true;
        }
        else if (this.driver.getDigital(Operation.IntakeArmRetract))
        {
            this.intakeComponent.extendArm(false);
            this.isArmExtended = false;
        }

        // extend conveyor
        if (this.driver.getDigital(Operation.IntakeConveyorExtend))
        {
            this.intakeComponent.extendConveyor(true);
            this.isConveyorExtended = true;
        }
        else if (this.driver.getDigital(Operation.IntakeGearHolderRetract))
        {
            this.intakeComponent.extendConveyor(false);
            this.isConveyorExtended = false;
        }

        boolean throughBeamBroken = this.intakeComponent.getThroughBeamBroken();
        this.intakeComponent.setIndicator(throughBeamBroken && this.isArmExtended && !this.isConveyorExtended);
        
        // Code for automatic pickup 3/23/17 -- works :)
//        if (throughBeamBroken && !this.isConveyorExtended)
//        {
//            if (this.isArmExtended && throughBeamBroken && !this.isConveyorExtended && this.startTime == 0.0) {
//                this.startTime = timer.get();
//                System.out.println(this.startTime);
//            }
//            
//            else if ((this.timer.get() - this.startTime) < 0.8 && (this.timer.get() - this.startTime) > 0.35)
//            {
//                this.intakeComponent.extendArm(false);
//                this.isArmExtended = false;
//                
//                System.out.println("Arm Extended" + isArmExtended);
//                System.out.println("Time " + (this.timer.get() - this.startTime));
//
//                
//                this.intakeComponent.setMotorSpeed(-TuningConstants.INTAKE_MAX_MOTOR_SPEED);
//            }
//            
//            else if ((this.timer.get() - this.startTime) > 0.9 && (this.timer.get() - this.startTime) < 1.5 && !this.isArmExtended)
//            {
//                this.intakeComponent.setMotorSpeed(0.0);
//                this.intakeComponent.extendConveyor(true);
//                this.isConveyorExtended = true;
//                this.startTime = 0.0;
//                
//                System.out.println("Conveyor Extended" + this.isConveyorExtended);
//                System.out.println("Time " + (this.timer.get() - this.startTime));
//                System.out.println("Start Time " + this.startTime);
//            }
//            
//            else if ((this.timer.get() - this.startTime) > 2.0)
//            {
//                this.startTime = 0.0;
//            }
//        }
    }

    @Override
    public void stop()
    {
        this.intakeComponent.stop();
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
