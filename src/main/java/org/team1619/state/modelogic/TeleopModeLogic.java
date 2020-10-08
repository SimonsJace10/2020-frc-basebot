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

	boolean collectorIsExtended;
	boolean rollersAreOn;

	// Controller Inputs
	boolean leftTrigger;
	boolean leftBumper;
	boolean rightTrigger;
	boolean rightBumper;

	public TeleopModeLogic(InputValues inputValues, RobotConfiguration robotConfiguration) {
		super(inputValues, robotConfiguration);
	}

	@Override
	public void initialize() {
		sLogger.info("***** TELEOP *****");
		collectorIsExtended = true;
		rollersAreOn = false;
	}

	@Override
	public void update() {
		if (fSharedInputValues.getBooleanRisingEdge("ipb_operator_left_trigger")){
			leftTrigger = true;
		}
		if (fSharedInputValues.getBooleanRisingEdge("ipb_operator_left_bumper")){
			leftBumper = true;
		}
		if (fSharedInputValues.getBooleanRisingEdge("ipb_operator_right_trigger")) {
			rightTrigger = true;
		}
		if (fSharedInputValues.getBooleanRisingEdge("ipb_operator_right_bumper")) {
			rightBumper = true;
		}
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isReady(String name) {
//		switch (name) {
//			case "st_collector_zero":
//				return !fSharedInputValues.getBoolean("ipb_collector_has_been_zeroed");
//			case "st_collector_floor_intake":
//				return collectorIsExtended && rollersAreOn;
//			case "st_collector_extend":
//				return collectorIsExtended && !rollersAreOn;
//			case "st_collector_retract":
//				return !collectorIsExtended && !rollersAreOn;
//			default:
//				return false;
//		}
		switch (name) {
			case "pl_floor_intake":
		}
	}

	@Override
	public boolean isDone(String name, State state) {
		switch (name) {
			default:
				return state.isDone();
		}
	}
}
