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
 * Zeros the collector by extending it and turning off the rollers
 */

public class Collector_Zero implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(Collector_Zero.class);
	private static final Set<String> sSubsystems = Set.of("ss_collector");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;

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

		mTimeoutTime = config.getInt("timeout_time");
		mTimeoutTimer.start(mTimeoutTime);
	}

	@Override
	public void update() {
		fSharedOutputValues.setBoolean("opb_collector_solenoid", true);
		fSharedOutputValues.setNumeric("opn_collector_rollers", "percent", 0.0);
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