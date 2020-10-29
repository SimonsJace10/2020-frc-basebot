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

	private boolean mKicker;
	private double mHopperSpeed;

	public Elevator_States(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;

		mKicker = false;
		mHopperSpeed = 0.0;
	}

	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);

		mKicker = config.getBoolean("kicker");
		mHopperSpeed = config.getDouble("hopper_speed");
	}

	@Override
	public void update() {
		fSharedOutputValues.setNumeric("opn_hopper", "percent", mHopperSpeed);
		fSharedOutputValues.setBoolean("opb_hopper_kicker", mKicker);
	}

	@Override
	public void dispose() {
		fSharedOutputValues.setNumeric("opn_hopper", "percent", 0.0);
		fSharedOutputValues.setBoolean("opb_hopper_kicker", mKicker);
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