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
 * Example behavior to copy for other behaviors
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
		mTimeoutTimer.start(mTimeoutTime);
	}



	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);

		mSolenoid = config.getBoolean("mSolenoid");

		mTimeoutTime = config.getInt("timeout_time");
	}

	@Override
	public void update() {
		fSharedOutputValues.setBoolean("opb_collector_solenoid", false);
		fSharedOutputValues.setNumeric("obn_collector_rollers", "percent", 0.0);
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isDone() {
		if (mTimeoutTimer.isDone()) {
			return true;
		}
		return false;
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}