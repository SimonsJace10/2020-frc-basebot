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
 * Behavior that contains the logic for 'zeroing' the collector (meaning effectively calibrate it by putting the collector in and ceasing motors.)
 */

public class Collector_Zero implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(Collector_Zero.class);
	private static final Set<String> sSubsystems = Set.of("ss_collector");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;

	private boolean mSolenoid;
	private Timer mTimeoutTimer;
	private long mTimeoutTime;

	public Collector_Zero(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;

		mTimeoutTimer = new Timer();
	}



	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);

		// Get the solenoid value for the collector
		mSolenoid = config.getBoolean("solenoid");
		// Get timeout timer time
		mTimeoutTime = config.getInt("timeout_time");
		// Initialize the timer
		mTimeoutTimer.start(mTimeoutTime);
		/* Set the collector in and motors to 0
		 They can be in initialize() because zero only runs once */
		fSharedOutputValues.setBoolean("opb_collector_solenoid", false);
		fSharedOutputValues.setNumeric("obn_collector_rollers", "percent", 0.0);
	}

	@Override
	public void update() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isDone() {
		if (mTimeoutTimer.isDone()) {
			fSharedInputValues.setBoolean("ipb_collector_has_been_zeroed", true);
			return true;
		}
		return false;
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}