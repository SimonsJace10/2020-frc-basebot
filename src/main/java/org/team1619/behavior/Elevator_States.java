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
 * Controls the hopper
 */

public class Elevator_States implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(Elevator_States.class);
	private static final Set<String> sSubsystems = Set.of("ss_hopper");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;

	private double mElevatorSpeed;
	private boolean mBeamSensor;

	public Elevator_States(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;

		mElevatorSpeed = 0.0;
		mBeamSensor = false;
	}

	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);

		mElevatorSpeed = config.getDouble("elevator_speed");
		mBeamSensor = config.getBoolean("beam_sensor");
	}

	@Override
	public void update() {
		fSharedOutputValues.setNumeric("opn_elevator", "percent", mElevatorSpeed);
		fSharedOutputValues.setBoolean("ipb_elevator_beam_sensor", mBeamSensor);
	}

	@Override
	public void dispose() {
		fSharedOutputValues.setNumeric("opn_elevator", "percent", 0.0);
		fSharedOutputValues.setBoolean("ipb_elevator_beam_sensor", false);
	}

	@Override
	public boolean isDone() {
		if (fSharedInputValues.getBoolean("ipb_elevator_beam_sensor")) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}