package org.team1619.behavior;

import org.uacr.models.behavior.Behavior;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.OutputValues;
import org.uacr.shared.abstractions.RobotConfiguration;
import org.uacr.utilities.Config;
import org.uacr.utilities.Timer;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

import java.util.Set;

/**
 * Controls the hopper
 */

public class Elevator_Zero implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(Elevator_Zero.class);
	private static final Set<String> sSubsystems = Set.of("ss_elevator");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;

	private boolean mBeamSensor;
	private double mElevatorSpeed;
	private double mZeroingThreshold;

//	private boolean mKicker;
//	private double mHopperSpeed;

	private Timer mTimeoutTimer;
	private long mTimeoutTime;

	public Elevator_Zero(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;

//		mKicker = false;
//		mHopperSpeed = 0.0;
		mElevatorSpeed = 0.0;
		mBeamSensor = false;
		mZeroingThreshold = 0.0;

		mTimeoutTimer = new Timer();
		mTimeoutTime = 0;
	}

	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);

//		mKicker = config.getBoolean("kicker");
//		mHopperSpeed = config.getDouble("hopper_speed");
		mBeamSensor = config.getBoolean("beam_sensor");
		mElevatorSpeed = config.getDouble("elevator_speed");
		mZeroingThreshold = config.getDouble("zeroing_threshold");

		mTimeoutTime = config.getInt("timeout_time");
		mTimeoutTimer.start(mTimeoutTime);

//		fSharedOutputValues.setBoolean("opb_hopper_kicker", mKicker);
	}

	@Override
	public void update() {
		if (Math.abs(fSharedInputValues.getNumeric("ipn_elevator_primary_position")) < mZeroingThreshold) {
			fSharedOutputValues.setOutputFlag("opn_elevator", "zero");
			fSharedOutputValues.setNumeric("opn_elevator", "percent", 0.0);
			fSharedInputValues.setBoolean("ipb_elevator_has_been_zeroed", true);
		}
		else if (mTimeoutTimer.isDone()) {
			fSharedOutputValues.setOutputFlag("opn_elevator", "zero");
			fSharedOutputValues.setNumeric("opn_elevator", "percent", 0.0);
			fSharedInputValues.setBoolean("ipb_elevator_has_been_zeroed", true);
			sLogger.debug("elevator has timed out");
		}
	}

	@Override
	public void dispose() {
//		fSharedOutputValues.setNumeric("opn_hopper", "percent", 0.0);
//		fSharedOutputValues.setBoolean("opb_hopper_kicker", mKicker);
		fSharedOutputValues.setNumeric("opn_elevator", "percent", 0.0);
	}

	@Override
	public boolean isDone() {
		return fSharedInputValues.getBoolean("ipb_elevator_has_been_zeroed");
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}