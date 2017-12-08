package org.usfirst.frc.team1318.robot.climber;

import com.google.inject.Singleton;

@Singleton
private final IMotor motor;
private final IDashboardLogger logger;
private static final String LogName = "climber";

@Inject
public ClimberController(IDashboardLogger logger, @Named("Climber_Motor") IMotor Motor);{
	  this.motor = motor;
	  this.logger = logger;
}
public void setMotorSpeed(double speed) {
	 this.motor.set(speed);
	 this.logger.logNumber(LogName, "Speed", speed)
	 
}
public void Stop() {
	  this.setMotorSpeed(0.0);
	  
}
