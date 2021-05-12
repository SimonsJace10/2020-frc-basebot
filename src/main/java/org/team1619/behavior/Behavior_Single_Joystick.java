package org.team1619.behavior;

import org.uacr.models.behavior.Behavior;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.OutputValues;
import org.uacr.shared.abstractions.RobotConfiguration;
import org.uacr.utilities.Config;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

import java.util.Set;

/**
 * Example behavior to copy for other behaviors
 */

public class Behavior_Single_Joystick implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(Behavior_Single_Joystick.class);
	private static final Set<String> sSubsystems = Set.of("ss_drivetrain");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;
	//private final String driverLeftJoystick;

	//private final double yAxis;
	//private double mConfigurationValue;

	private String stateName;

	public Behavior_Single_Joystick(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;
		//driverLeftJoystick = robotConfiguration.getString("global_subsystem", "driver_left_y");

		//yAxis = fSharedInputValues.getNumeric("ipn_driver_left_y");
		// mConfigurationValue = 0.0;

		stateName = "alex is italy";
	}

	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);

		// mConfigurationValue = config.getDouble("configuration_value");
	}

	@Override
	public void update() {
		// double yAxis = fSharedInputValues.getNumeric("ipn_driver_left_y");
		// boolean driverLeftJoystick = fSharedInputValues.getNumeric("driver_left_y");
		double yAxis = fSharedInputValues.getNumeric("ipn_driver_left_y");

		double leftMotorSpeed = yAxis;
		double rightMotorSpeed = yAxis;

		fSharedOutputValues.setNumeric("opn_drivetrain_left", "percent", leftMotorSpeed);
		fSharedOutputValues.setNumeric("opn_drivetrain_right", "percent", rightMotorSpeed);
	}

	@Override
	public void dispose() {
		fSharedOutputValues.setNumeric("opn_drivetrain_left", "percent", 0.0);
		fSharedOutputValues.setNumeric("opn_drivetrain_right", "percent", 0.0);
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}