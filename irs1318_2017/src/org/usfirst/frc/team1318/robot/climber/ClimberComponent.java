package org.usfirst.frc.team1318.robot.climber;

import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ClimberComponent
{
	private final IMotor motor; 
	private final IDashboardLogger logger;
	private static final String LogName = "climber"

	public ClimberComponent(IDashboardLogger Logger , @Named ("Climber_Motor") IMotor Motor) {
		this.motor = motor;
		this.logger = logger;
	}
	public void setMotorSpeed(double speed) {
		this.motor.set(speed);
		this.logger.logNumber(LogName, "Speed" , speed)
	}
	public void stop() {
		this.setMotorSpeed(O.O);
			}
		}
	}
}