package org.team1619.state.modelogic;

import org.uacr.models.state.State;
import org.uacr.robot.AbstractModeLogic;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.RobotConfiguration;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

/**
 * Handles the isReady and isDone logic for teleop mode on competition bot
 */

public class TeleopModeLogic extends AbstractModeLogic {

	private static final Logger sLogger = LogManager.getLogger(TeleopModeLogic.class);

	// Controller Inputs
	boolean mFloorIntake = false;
	boolean mPrime = false;
	boolean mShoot = false;
	boolean mProtect = false;

	public TeleopModeLogic(InputValues inputValues, RobotConfiguration robotConfiguration) {
		super(inputValues, robotConfiguration);
	}

	@Override
	public void initialize() {
		sLogger.info("***** TELEOP *****");
		//collectorIsExtended = true;
		//rollersAreOn = false;
	}

	@Override
	public void update() {

		if (fSharedInputValues.getBooleanRisingEdge("ipb_operator_left_trigger")) {
			mFloorIntake = !mFloorIntake;
			mPrime = false;
			mShoot = false;
			mProtect = false;
		}
		else if (fSharedInputValues.getBooleanRisingEdge("ipb_operator_left_bumper")) {
			mFloorIntake = false;
			mPrime = true;
			mShoot = false;
			mProtect = false;
		}
		else if (fSharedInputValues.getBooleanRisingEdge("ipb_operator_right_trigger")) {
			mFloorIntake = false;
			mPrime = false;
			mShoot = true;
			mProtect = false;
		}
		else if (fSharedInputValues.getBooleanFallingEdge("ipb_operator_right_trigger")) {
			mShoot = false;
		}
		else if (fSharedInputValues.getBooleanRisingEdge("ipb_operator_right_bumper")) {
			mFloorIntake = false;
			mPrime = false;
			mShoot = false;
			mProtect = true;
		}
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isReady(String name) {
		switch (name) {
			case "pl_floor_intake":
				return mFloorIntake;
			case "pl_prime":
				return mPrime;
			case "pl_shoot":
				return mShoot;
			case "pl_protect":
				return mProtect;
			default:
				return false;
		}
	}

	@Override
	public boolean isDone(String name, State state) {
		switch (name) {
			case "pl_floor_intake":
				return !mFloorIntake;
			case "pl_prime":
				return !mPrime;
			case "pl_shoot":
				return !mShoot;
			case "pl_protect":
				return !mProtect;
			default:
				return state.isDone();
		}
	}
}
