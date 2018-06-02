package bluetooth_Connection;

import wifi_connection.classes.Client;
import wifi_connection.classes.Wifi_Global;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.gamepad.gamepad_Global;

public class TouchPad_listener {
	public float moveXdown;
	public float moveYdown;
	public float PrevX;
	public float PrevY;
	public float ResultX;
	public float ResultY;
	public static Button left, right;
	public boolean pressed = false;
	
	public void OnActionDown(MotionEvent event) {
		moveXdown = event.getRawX();
		moveYdown = event.getRawY();
		PrevX = event.getRawX();
		PrevY = event.getRawY();
		ResultX = 0;
		ResultY = 0;
	}

	public void OnActionMove(MotionEvent event) {

		float movementRawX = event.getRawX() - PrevX;
		float movementRawY = event.getRawY() - PrevY;
		movementRawX = (float) ((Math.pow(Math.abs(movementRawX), 1.5f) * Math
				.signum(movementRawX))) * 0.5f;
		movementRawY = (float) ((Math.pow(Math.abs(movementRawY), 1.5f) * Math
				.signum(movementRawY))) * 0.5f;
		int movementXFinal = Math.round(movementRawX);
		int movementYFinal = Math.round(movementRawY);
		if (movementXFinal != 0 || movementYFinal != 0) {
			sendMovePacket(_Global.MESSAGE_MOVE, movementXFinal, movementYFinal);
		}
		PrevX = event.getRawX();
		PrevY = event.getRawY();

	}
	public void OnActionMove(MotionEvent event,float speed) {

		float movementRawX = event.getRawX() - PrevX;
		float movementRawY = event.getRawY() - PrevY;
		movementRawX = (float) ((Math.pow(Math.abs(movementRawX), 1.5f) * Math
				.signum(movementRawX))) * speed;
		movementRawY = (float) ((Math.pow(Math.abs(movementRawY), 1.5f) * Math
				.signum(movementRawY))) * speed;
		int movementXFinal = Math.round(movementRawX);
		int movementYFinal = Math.round(movementRawY);
		if (movementXFinal != 0 || movementYFinal != 0) {
			sendMovePacket(_Global.MESSAGE_MOVE, movementXFinal, movementYFinal);
		}
		PrevX = event.getRawX();
		PrevY = event.getRawY();

	}
	  public void scroll(MotionEvent event) {
			
			float movementRawX = event.getRawX() - PrevX;
			float movementRawY = event.getRawY() - PrevY;
			movementRawX = (float) ((Math.pow(Math.abs(movementRawX), 1.5f) * Math.signum(movementRawX))) * 0.5f;
			movementRawY = (float) ((Math.pow(Math.abs(movementRawY), 1.5f) * Math.signum(movementRawY))) * 0.5f;
			int movementXFinal = Math.round(movementRawX);
			int movementYFinal = Math.round(movementRawY);
			if (movementXFinal != 0 || movementYFinal != 0)
			{
				sendMovePacket(_Global.MESSAGE_scroll, movementXFinal, movementYFinal);
			}
			PrevX = event.getRawX();
			PrevY = event.getRawY();
		
		}

	public void touchpadOnActionClick(MotionEvent event, float accelelatorX,
			float accelelatorY) {

		float movementRawX = accelelatorX + event.getRawX() - PrevX;
		float movementRawY = accelelatorY + event.getRawY() - PrevY;
		movementRawX = (float) ((Math.pow(Math.abs(movementRawX), 1.5f) * Math
				.signum(movementRawX))) * 0.5f;
		movementRawY = (float) ((Math.pow(Math.abs(movementRawY), 1.5f) * Math
				.signum(movementRawY))) * 0.5f;
		int movementXFinal = Math.round(movementRawX);
		@SuppressWarnings("unused")
		int x = Math.round(movementRawX);
		
		int movementYFinal = Math.round(movementRawY);
		@SuppressWarnings("unused")
		int y = Math.round(movementRawY);

		if (movementXFinal != 0 || movementYFinal != 0) {
			sendMovePacket(_Global.MESSAGE_MOVE, movementXFinal, movementYFinal);
		}
		PrevX = event.getRawX();
		PrevY = event.getRawY();

	}

	public void Send_nothing(MotionEvent event) {
		sendMovePacket(_Global.EXIT_CMD, 0, 0);
	}

	public void wheelUp(MotionEvent event) {
		if (event.getEventTime() - event.getDownTime() < 145
				&& checkIfMove(event) <= 11.0f) {
			/*
			 * if (lftBTN.isPressed()) { //does nothing }
			 */
			if (!this.pressed) {
				sendMovePacket(_Global.LEFT_CLICK, 0, 0);
				// this.vibrate(50);
			} else {
				this.pressed = false;
				sendMovePacket(_Global.LEFT_CLICK_UP, 0, 0);
				// this.vibrate(50);

			}
		}
	}

	public void OnActionUp(MotionEvent event) {
		if (event.getEventTime() - event.getDownTime() < 145
				&& checkIfMove(event) <= 11.0f) {
			if (left.isPressed()|| right.isPressed()) {
				// does nothing
			} else {
				
					sendMovePacket(_Global.LEFT_CLICK, 0, 0);
					// this.vibrate(50);
			
			}
		}
	}

	public double checkIfMove(MotionEvent event) {
		Log.i("moveee",""+ Math.abs((event.getRawX() - moveXdown)
				+ (event.getRawY() - moveYdown)));
		return Math.abs((event.getRawX() - moveXdown)
				+ (event.getRawY() - moveYdown));

	}

	public void sendMovePacket(int type, int x, int y) {

		Motion motion = new Motion(type, x, y);

		if (gamepad_Global.connectionType == gamepad_Global.BLEUTOOTH_CONNECTION)
			Bluetooth_activity.mCommandService.write(motion);
		if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION) {
			Client client = Wifi_Global.client;
			client.sendObject(motion);
		}
	}

	public void leftClickDown(MotionEvent event) {
		if (!right.isPressed()) {
				sendMovePacket(_Global.LEFT_CLICK_DOWN, 0, 0);

			left.setPressed(true);
			// this.vibrate(50);
		} 
	}

/*	public void leftClickOnActionMove(MotionEvent event) {

		if (!this.pressed && event.getEventTime() - event.getDownTime() >= 250) {
			this.pressed = true;
			// this.vibrate(100);
			Log.i("workkkk","wooo");
		}
	}*/

	public void leftClickUp(MotionEvent event) {
		if (!right.isPressed()) {

		
			sendMovePacket(_Global.LEFT_CLICK_UP, 0, 0);
			left.setPressed(false);
		}
		
	}

	public void rightClickDown(MotionEvent event) {
		if (!left.isPressed()) {
			sendMovePacket(_Global.RIGHT_CLICK_DOWN, 0, 0);

			right.setPressed(true);
			// this.vibrate(50);
		}
	}


	public void rightClickUp(MotionEvent event) {
		if (!left.isPressed()) {

		
			sendMovePacket(_Global.RIGHT_CLICK_UP, 0, 0);
			right.setPressed(false);
		}
	}

	public void up(MotionEvent event) {
		if (event.getEventTime() - event.getDownTime() >= 100) {
				sendMovePacket(_Global.MESSAGE_Up, 0, 0);
		}
	}

	public void down(MotionEvent event) {
		if (event.getEventTime() - event.getDownTime() >= 100) {
				sendMovePacket(_Global.MESSAGE_Down, 0, 0);
		}
	}

	public void right(MotionEvent event) {
		if (event.getEventTime() - event.getDownTime() >= 100) {
				sendMovePacket(_Global.MESSAGE_right, 0, 0);
		}
	}

	public void left(MotionEvent event) {
		if (event.getEventTime() - event.getDownTime() >= 100) {
				sendMovePacket(_Global.MESSAGE_left, 0, 0);
		}
	}

	public void start(MotionEvent event) {
		if (event.getEventTime() - event.getDownTime() >= 100) {
				sendMovePacket(_Global.MESSAGE_start, 0, 0);
		}
	}

	public void select(MotionEvent event) {
		if (event.getEventTime() - event.getDownTime() >= 100) {
				sendMovePacket(_Global.MESSAGE_select, 0, 0);
		}
	}

	public void up() {
			sendMovePacket(_Global.MESSAGE_Up, 0, 0);
	}

	public void down() {
			sendMovePacket(_Global.MESSAGE_Down, 0, 0);
	}

	public void right() {
		sendMovePacket(_Global.MESSAGE_right, 0, 0);
	}

	public void left() {

			sendMovePacket(_Global.MESSAGE_left, 0, 0);
	}

	public void Click_A(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_A, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;

		}
	}

	public void Click_B(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_B, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;

		}
	}

	public void Click_C(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_C, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;

		}
	}

	public void Click_D(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_D, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_E(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_E, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_F(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_F, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_G(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_G, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_H(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_H, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_I(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_I, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_J(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_J, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_K(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_K, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_L(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_L, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_M(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_M, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_N(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_N, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_O(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_O, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_P(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_P, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_Q(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_Q, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_R(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_R, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_S(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_S, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;

		}
	}

	public void Click_T(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_T, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_U(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_U, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_V(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_V, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_W(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_W, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}
	}

	public void Click_X(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_X, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_Y(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_Y, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_Z(MotionEvent event) {
		while (gamepad_Global.send > 0) {
	 
				sendMovePacket(_Global.MESSAG_Z, 0, 0);
			gamepad_Global.send = gamepad_Global.send - 10;
		}

	}

	public void Click_A() {
 
 
			sendMovePacket(_Global.MESSAG_A, 0, 0);
	}

	public void Click_B() {
 
 
			sendMovePacket(_Global.MESSAG_B, 0, 0);

	}

	public void Click_C() {
 
 
			sendMovePacket(_Global.MESSAG_C, 0, 0);
	}

	public void Click_D() {
 
 
			sendMovePacket(_Global.MESSAG_D, 0, 0);
	}

	public void Click_E() {
 
 
			sendMovePacket(_Global.MESSAG_E, 0, 0);
	}

	public void Click_F() {
 
 
			sendMovePacket(_Global.MESSAG_F, 0, 0);
	}

	public void Click_G() {
 
 
			sendMovePacket(_Global.MESSAG_G, 0, 0);
	}

	public void Click_H() {
 
 
			sendMovePacket(_Global.MESSAG_H, 0, 0);
	}

	public void Click_I() {
 
 
			sendMovePacket(_Global.MESSAG_I, 0, 0);
	}

	public void Click_J() {
 
 
			sendMovePacket(_Global.MESSAG_J, 0, 0);
	}

	public void Click_K() {
 
 
			sendMovePacket(_Global.MESSAG_K, 0, 0);
	}

	public void Click_L() {
 
 
			sendMovePacket(_Global.MESSAG_L, 0, 0);
	}

	public void Click_M() {
 
 
			sendMovePacket(_Global.MESSAG_M, 0, 0);
	}

	public void Click_N() {
 
 
			sendMovePacket(_Global.MESSAG_N, 0, 0);
	}

	public void Click_O() {
 
 
			sendMovePacket(_Global.MESSAG_O, 0, 0);
	}

	public void Click_P() {
 
 
			sendMovePacket(_Global.MESSAG_P, 0, 0);
	}

	public void Click_Q() {
 
 
			sendMovePacket(_Global.MESSAG_Q, 0, 0);
	}

	public void Click_R() {
 
 
			sendMovePacket(_Global.MESSAG_R, 0, 0);
	}

	public void Click_S() {
 
 
			sendMovePacket(_Global.MESSAG_S, 0, 0);
	}

	public void Click_T() {
 
			sendMovePacket(_Global.MESSAG_T, 0, 0);
	}

	public void Click_U() {
 
 
			sendMovePacket(_Global.MESSAG_U, 0, 0);
	}

	public void Click_V() {
 
 
			sendMovePacket(_Global.MESSAG_V, 0, 0);
	}

	public void Click_W() {
 
			sendMovePacket(_Global.MESSAG_W, 0, 0);
	}

	public void Click_X() {
 
 
			sendMovePacket(_Global.MESSAG_X, 0, 0);
	}

	public void Click_Y() {
 
		sendMovePacket(_Global.MESSAG_Y, 0, 0);
	}

	public void Click_Z() {
 
 
			sendMovePacket(_Global.MESSAG_Z, 0, 0);
	}

	public void start() {
 
 
			sendMovePacket(_Global.MESSAGE_start, 0, 0);

	}

	public void select() {
 
 
			sendMovePacket(_Global.MESSAGE_select, 0, 0);

	}
	public void Esc() {
		 
		 
		sendMovePacket(_Global.MESSAG_Esc, 0, 0);

}
	public void Ctrl() {
		 
		 
		sendMovePacket(_Global.MESSAG_Ctrl, 0, 0);

}
	public void Alt() {
		 
		 
		sendMovePacket(_Global.MESSAG_Alt, 0, 0);

}
	public void Shift() {
		 
		 
		sendMovePacket(_Global.MESSAG_Shift, 0, 0);

}	

	public void Tab() {
		 
		 
		sendMovePacket(_Global.MESSAG_Tab, 0, 0);

}	
	public void F5() {
		 
		 
		sendMovePacket(_Global.MESSAG_F5, 0, 0);

}	

	/* stop */
	//w
	public void oneStop() {
			sendMovePacket(_Global.MessageOneStop, 0, 0);
	}


	public void threeStop() {
 
			sendMovePacket(_Global.MESSAGE_threeStop, 0, 0);
 

	}

	public void fourStop() {
 
			sendMovePacket(_Global.MESSAGE_fourStop, 0, 0);
 

	}
	
	public void A_stop(){
		sendMovePacket(_Global.MESSAG_Astop, 0, 0);

	}
	public void B_stop(){
		sendMovePacket(_Global.MESSAG_Bstop, 0, 0);

	}
	public void C_stop(){
		sendMovePacket(_Global.MESSAG_Cstop, 0, 0);

	}
	public void D_stop(){
		sendMovePacket(_Global.MESSAG_Dstop, 0, 0);

	}
	public void E_stop(){
		sendMovePacket(_Global.MESSAG_Estop, 0, 0);

	}
	public void F_stop(){
		sendMovePacket(_Global.MESSAG_Fstop, 0, 0);

	}
	public void G_stop(){
		sendMovePacket(_Global.MESSAG_Gstop, 0, 0);

	}
	public void H_stop(){
		sendMovePacket(_Global.MESSAG_Hstop, 0, 0);

	}
	public void I_stop(){
		sendMovePacket(_Global.MESSAG_Istop, 0, 0);

	}
	public void J_stop(){
		sendMovePacket(_Global.MESSAG_Jstop, 0, 0);

	}
	public void K_stop(){
		sendMovePacket(_Global.MESSAG_Kstop, 0, 0);

	}
	public void L_stop(){
		sendMovePacket(_Global.MESSAG_Lstop, 0, 0);

	}
	public void M_stop(){
		sendMovePacket(_Global.MESSAG_Mstop, 0, 0);

	}
	public void N_stop(){
		sendMovePacket(_Global.MESSAG_Nstop, 0, 0);

	}
	public void O_stop(){
		sendMovePacket(_Global.MESSAG_Ostop, 0, 0);

	}
	public void P_stop(){
		sendMovePacket(_Global.MESSAG_Pstop, 0, 0);

	}
	public void Q_stop(){
		sendMovePacket(_Global.MESSAG_Qstop, 0, 0);

	}
	public void R_stop(){
		sendMovePacket(_Global.MESSAG_Rstop, 0, 0);

	}
	public void S_stop(){
		sendMovePacket(_Global.MESSAG_Sstop, 0, 0);

	}
	public void T_stop(){
		sendMovePacket(_Global.MESSAG_Tstop, 0, 0);

	}
	public void U_stop(){
		sendMovePacket(_Global.MESSAG_Ustop, 0, 0);

	}
	public void V_stop(){
		sendMovePacket(_Global.MESSAG_Vstop, 0, 0);

	}
	public void W_stop(){
		sendMovePacket(_Global.MESSAG_Wstop, 0, 0);

	}
	public void X_stop(){
		sendMovePacket(_Global.MESSAG_Xstop, 0, 0);

	}
	public void Y_stop(){
		sendMovePacket(_Global.MESSAG_Ystop, 0, 0);

	}
	public void Z_stop(){
		sendMovePacket(_Global.MESSAG_Zstop, 0, 0);

	}
	public void Ctrl_stop() {
		 
		 
		sendMovePacket(_Global.MESSAG_Ctrlstop, 0, 0);

}
	public void Alt_Stop(){
		sendMovePacket(_Global.MESSAG_Altstop, 0, 0);
	}
	public void Shift_Stop(){
		sendMovePacket(_Global.MESSAG_Shiftstop, 0, 0);

	}
	public void Tap_Stop(){
		sendMovePacket(_Global.MESSAG_Tabstop, 0, 0);

	}
	public void F5_Stop(){
		sendMovePacket(_Global.MESSAG_F5stop, 0, 0);

	}

	/* sensor */
	public void sensor(int moveXFinal, int moveYFinal) {
 
 
			sendMovePacket(_Global.MESSAGE_sensor, moveXFinal, moveYFinal);

	}

	public void stopArrows() {
 
			sendMovePacket(_Global.MESSAGE_StopArrows, 0, 0);

	}

}
