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
 * Drives the robot using joysticks.
 */

public class Drivetrain implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(Drivetrain.class);
	private static final Set<String> sSubsystems = Set.of("ss_drivetrain");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;
	private final String fX;
	private final String fY;
	private final String fGearShift;

	private String mStateName;

	public Drivetrain(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;
		fX = robotConfiguration.getString("global_drivetrain", "x");
		fY = robotConfiguration.getString("global_drivetrain", "y");
		fGearShift = robotConfiguration.getString("global_drivetrain", "gear_shift");

		mStateName = "";
	}

	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);

		mStateName = stateName;
	}

	@Override
	public void update() {
		sLogger.debug("updating");


		double X = fSharedInputValues.getNumeric(fX);
		double Y = fSharedInputValues.getNumeric(fY);
		boolean gearShiftButton = fSharedInputValues.getBoolean(fGearShift);

		// Move motors according to joysticks
		double leftMotor = Y + X;
		double rightMotor = Y - X;

		// Move the motors
		fSharedOutputValues.setNumeric("opn_drivetrain_left", "percent", leftMotor);
		fSharedOutputValues.setNumeric("opn_drivetrain_right", "percent", rightMotor);

		// Gear Shifter
		fSharedOutputValues.setBoolean("opb_drivetrain_gear_shifter", gearShiftButton);
		fSharedInputValues.setBoolean("ipb_is_low_gear", gearShiftButton);
	}

	@Override
	public void dispose() {
		sLogger.debug("disposing");
		fSharedOutputValues.setNumeric("opn_drivetrain_left", "percent", 0.0);
		fSharedOutputValues.setNumeric("opn_drivetrain_right", "percent", 0.0);
		fSharedOutputValues.setBoolean("opb_drivetrain_gear_shifter", false);
	}

	@Override
	public boolean isDone() {
		sLogger.debug("isDone = true");
		return true;
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}