package gameBad_EDIT;

import java.util.ArrayList;
import java.util.List;

import wifi_connection.classes.GAMEPAD;

import localDataBase.GLOBAL_DB;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gamepad.Gamepad;
import com.gamepad.R;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("NewApi")
public class Buttons_choice extends Activity {
	public Button bt;
	public Button btn_d, which_move;
	public static float move_x, move_y;
	public String what;
	public float x, y;
	public static int pos, Position_of_listActivity;
	int key;
	int chek,time;
	ListView list;
	static RelativeLayout.LayoutParams params;
	public List<Button> array;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_your_button);

		String[] values = new String[] { "?!?", "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z", "Esc", "Ctrl", "Alt",
				"Shift", "Tab", "start", "select" };
		Integer[] image = new Integer[] { 0, R.drawable.circle_a,
				R.drawable.circle_b, R.drawable.circle_c, R.drawable.circle_d,
				R.drawable.circle_e, R.drawable.circle_f, R.drawable.circle_g,
				R.drawable.circle_h, R.drawable.circle_i, R.drawable.circle_j,
				R.drawable.circle_k, R.drawable.circle_l, R.drawable.circle_m,
				R.drawable.circle_n, R.drawable.circle_o, R.drawable.circle_p,
				R.drawable.circle_q, R.drawable.circle_r, R.drawable.circle_s,
				R.drawable.circle_t, R.drawable.circle_u, R.drawable.circle_v,
				R.drawable.circle_w, R.drawable.circle_x, R.drawable.circle_y,
				R.drawable.circle_z, R.drawable.circle_esc,
				R.drawable.circle_ctrl, R.drawable.circle_alt,
				R.drawable.circle_shift, R.drawable.circle_tab,
				R.drawable.start_icon, R.drawable.select_icon };

		CustomListAdapter adapter = new CustomListAdapter(this, values, image);
		final ListView listview = (ListView) findViewById(R.id.btn_list);

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);

		}
		@SuppressWarnings("unused")
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		// Set The Adapter
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Bundle b = getIntent().getExtras();
				what = b.getString("what");
				x = b.getFloat("x");
				y = b.getFloat("Y");
				final String item = (String) parent.getItemAtPosition(position);
				if (position == 0) {

					Toast.makeText(getApplicationContext(),
							"Select the button you want", Toast.LENGTH_LONG)
							.show();

				} else {
					switch (what) {
					case "add":
						bt = AddButton(Gamepad.context, item, position);
						Gamepad.mydraw.addView(bt);
						pos = bt.getId();
						finish();
						break;
					case "edit":
						bt = createButton(Gamepad.context, item, position);
						bt.setX(x);
						bt.setY(y);
						Gamepad.mydraw.addView(bt);
						Delete_button(Gamepad.which_edit);
						pos = bt.getId();
						finish();
					default:
						break;
					}

				}
			}

		});
	}

	public Button createButton(Context context, String label, int position) {
		final Button bt = new Button(context);
		bt.setText(label);
		bt.setTextSize(1);
		bt.setClickable(true);
		// bt.setBackgroundColor(Color.MAGENTA);
		bt.setId(position);
		Img_Bk(bt, bt.getText().toString());

		Position_of_listActivity = position;

		return bt;
	}

	public Button AddButton(Context context, String label, int position) {
		final Button bt = new Button(context);
		bt.setText(label);
		bt.setTextSize(1);
		bt.setClickable(true);
		// bt.setBackgroundColor(Color.MAGENTA);
		bt.setId(position);
		Img_Bk(bt, bt.getText().toString());
		Position_of_listActivity = position;
		params = new RelativeLayout.LayoutParams(80, 80);
		params.setMargins(10, 60, 10, 10);
		params.addRule(RelativeLayout.END_OF, pos);
		// set the layoutParams on the button
		bt.setLayoutParams(params);
		return bt;
	}

	public void Operaion(final Button btn, String label) {
		if (Gamepad.delete == false & Gamepad.edit_btn == false
				& Gamepad.move == false) {

			switch (label) {
			case "A":
				// btn.setBackgroundResource(R.drawable.circle_a);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						GLOBAL_DB.Tlis.Click_A();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.A_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_A();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.A_stop();
						return false;
					}
				});
				break;
			case "B":
				// btn.setBackgroundResource(R.drawable.circle_b);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						GLOBAL_DB.Tlis.Click_B();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.B_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_B();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.B_stop();
						return false;
					}
				});
				break;
			case "C":
				// btn.setBackgroundResource(R.drawable.circle_c);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						GLOBAL_DB.Tlis.Click_C();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.C_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_C();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.C_stop();
						return false;
					}
				});
				break;
			case "D":
				// btn.setBackgroundResource(R.drawable.circle_d);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						GLOBAL_DB.Tlis.Click_D();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.D_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_D();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.D_stop();
						return false;
					}
				});
				break;
			case "E":
				// btn.setBackgroundResource(R.drawable.circle_e);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						GLOBAL_DB.Tlis.Click_E();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.E_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_E();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.E_stop();
						return false;
					}
				});
				break;

			case "F":
				// btn.setBackgroundResource(R.drawable.circle_f);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_F();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.F_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_F();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.F_stop();
						return false;
					}
				});
				break;
			case "G":
				// btn.setBackgroundResource(R.drawable.circle_g);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_G();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_G();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.G_stop();
						return false;
					}
				});
				break;
			case "H":
				// btn.setBackgroundResource(R.drawable.circle_h);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_H();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.H_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_H();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.H_stop();
						return false;
					}
				});
				break;
			case "I":
				// btn.setBackgroundResource(R.drawable.circle_i);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_I();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.I_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_I();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.I_stop();
						return false;
					}
				});
				break;
			case "J":
				// btn.setBackgroundResource(R.drawable.circle_j);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_J();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.J_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_J();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.J_stop();
						return false;
					}
				});
				break;
			case "K":
				// btn.setBackgroundResource(R.drawable.circle_k);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_K();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.K_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_K();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.K_stop();
						return false;
					}
				});
				break;
			case "L":
				// btn.setBackgroundResource(R.drawable.circle_l);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_L();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.L_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_L();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.L_stop();
						return false;
					}
				});
				break;
			case "M":
				// btn.setBackgroundResource(R.drawable.circle_m);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_M();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.M_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_M();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.M_stop();
						return false;
					}
				});
				break;
			case "N":
				// btn.setBackgroundResource(R.drawable.circle_n);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_N();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.N_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_N();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.N_stop();
						return false;
					}
				});
				break;
			case "O":
				// btn.setBackgroundResource(R.drawable.circle_o);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_O();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.O_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_O();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.O_stop();
						return false;
					}
				});
				break;
			case "P":
				// btn.setBackgroundResource(R.drawable.circle_p);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_P();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.P_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_P();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.P_stop();
						return false;
					}
				});
				break;
			case "Q":
				// btn.setBackgroundResource(R.drawable.circle_q);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_Q();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.Q_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_Q();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.Q_stop();
						return false;
					}
				});
				break;
			case "R":
				// btn.setBackgroundResource(R.drawable.circle_r);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_R();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.R_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_R();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.R_stop();
						return false;
					}
				});
				break;
			case "S":
				// btn.setBackgroundResource(R.drawable.circle_s);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_S();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.S_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_S();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.S_stop();
						return false;
					}
				});
				break;
			case "T":
				// btn.setBackgroundResource(R.drawable.circle_t);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_T();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.T_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_T();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.T_stop();
						return false;
					}
				});
				break;
			case "U":
				// btn.setBackgroundResource(R.drawable.circle_u);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_U();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.U_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_U();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.U_stop();
						return false;
					}
				});
				break;
			case "V":
				// btn.setBackgroundResource(R.drawable.circle_v);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_V();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.V_stop();
						return false;
						
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_V();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.V_stop();
						return false;
					}
				});
				break;
			case "W":
				// btn.setBackgroundResource(R.drawable.circle_w);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_W();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.W_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_W();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.W_stop();
						return false;
					}
				});
				break;
			case "X":
				// btn.setBackgroundResource(R.drawable.circle_x);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_X();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.X_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_X();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.X_stop();
						return false;
					}
				});
				break;
			case "Y":
				// btn.setBackgroundResource(R.drawable.circle_y);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_Y();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.Y_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_Y();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.Y_stop();
						return false;
					}
				});
				break;
			case "Z":
				// btn.setBackgroundResource(R.drawable.circle_z);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Click_Z();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.Z_stop();
						return false;
					}
				});
				btn.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN)
							GLOBAL_DB.Tlis.Click_Z();
						if (event.getAction() == MotionEvent.ACTION_UP)
							GLOBAL_DB.Tlis.Z_stop();
						return false;
					}
				});
				break;
			case "start":
				// btn.setBackgroundResource(R.drawable.start_icon);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.start();
						return false;
					}
				});
				break;
			case "select":
				// btn.setBackgroundResource(R.drawable.select_icon);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.select();
						return false;
					}
				});
				break;
			case "Esc":
				// btn.setBackgroundResource(R.drawable.select_icon);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Esc();
						return false;
					}
				});
				break;
			case "Ctrl":
				// btn.setBackgroundResource(R.drawable.select_icon);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Ctrl();
						return false;
					}
				});
				break;
			case "Alt":
				// btn.setBackgroundResource(R.drawable.select_icon);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Alt();
						return false;
					}
				});
				break;
			case "Shift":
				// btn.setBackgroundResource(R.drawable.select_icon);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Shift();
						return false;
					}
				});
				break;
			case "Tab":
				// btn.setBackgroundResource(R.drawable.select_icon);
				btn.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						GLOBAL_DB.Tlis.Tab();
						return false;
					}
				});
				break;
			}
		}
	}

	public void Img_Bk(final Button btn, String label) {
		btn.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				// Log.i("img_bk "+Gamepad.move, "img_bk "+Gamepad.delete);
				if (Gamepad.move == true) {
					Move_Button(btn);
				} else if (Gamepad.delete == true) {
					Yes_No_option(btn, Gamepad.context_this);
				} else if (Gamepad.edit_btn == true) {
					// Context cn = getApplicationContext();
					Intent editIntent = new Intent(Gamepad.context,
							Buttons_choice.class);
					Bundle b = new Bundle();
					b.putString("what", "edit");
					b.putFloat("x", btn.getX());
					b.putFloat("Y", btn.getY());
					Gamepad.which_edit = btn;
					// Gamepad.which_edit.setVisibility(View.INVISIBLE);
					editIntent.putExtras(b);
					editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Gamepad.context.startActivity(editIntent);
				}
				return false;
			}
		});
		
		/*btn.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				chek = Gamepad.chek;
				time = Gamepad.time;
				Log.i("abdp", chek+" : abdooo");
				 Log.i("abdp", time+" :abdooo");
				vibrate(chek, time);
				
			}
		});*/

		
		switch (label) {
		case "A":
			btn.setBackgroundResource(R.drawable.circle_a);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "B":
			btn.setBackgroundResource(R.drawable.circle_b);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "C":
			btn.setBackgroundResource(R.drawable.circle_c);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "D":
			btn.setBackgroundResource(R.drawable.circle_d);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "E":
			btn.setBackgroundResource(R.drawable.circle_e);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "F":
			btn.setBackgroundResource(R.drawable.circle_f);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "G":
			btn.setBackgroundResource(R.drawable.circle_g);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "H":
			btn.setBackgroundResource(R.drawable.circle_h);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "I":
			btn.setBackgroundResource(R.drawable.circle_i);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "J":
			btn.setBackgroundResource(R.drawable.circle_j);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "K":
			btn.setBackgroundResource(R.drawable.circle_k);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "L":
			btn.setBackgroundResource(R.drawable.circle_l);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "M":
			btn.setBackgroundResource(R.drawable.circle_m);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "N":
			btn.setBackgroundResource(R.drawable.circle_n);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "O":
			btn.setBackgroundResource(R.drawable.circle_o);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "P":
			btn.setBackgroundResource(R.drawable.circle_p);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "Q":
			btn.setBackgroundResource(R.drawable.circle_q);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "R":
			btn.setBackgroundResource(R.drawable.circle_r);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "S":
			btn.setBackgroundResource(R.drawable.circle_s);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "T":
			btn.setBackgroundResource(R.drawable.circle_t);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "U":
			btn.setBackgroundResource(R.drawable.circle_u);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "V":
			btn.setBackgroundResource(R.drawable.circle_v);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "W":
			btn.setBackgroundResource(R.drawable.circle_w);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "X":
			btn.setBackgroundResource(R.drawable.circle_x);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "Y":
			btn.setBackgroundResource(R.drawable.circle_y);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "Z":
			btn.setBackgroundResource(R.drawable.circle_z);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "start":
			btn.setBackgroundResource(R.drawable.start_icon);
			btn.setWidth(Gamepad.select_w);
			btn.setHeight(Gamepad.select_h);
			break;
		case "select":
			btn.setBackgroundResource(R.drawable.select_icon);
			btn.setWidth(Gamepad.select_w);
			btn.setHeight(Gamepad.select_h);
			break;
		case "Esc":
			btn.setBackgroundResource(R.drawable.circle_esc);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "Ctrl":
			btn.setBackgroundResource(R.drawable.circle_ctrl);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "Alt":
			btn.setBackgroundResource(R.drawable.circle_alt);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "Shift":
			btn.setBackgroundResource(R.drawable.circle_shift);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;
		case "Tab":
			btn.setBackgroundResource(R.drawable.circle_tab);
			btn.setWidth(Gamepad.width_btn);
			btn.setHeight(Gamepad.height_btn);
			break;

		}

	}

	public void Move_Button(Button M_bt) {
		which_move = M_bt;
		which_move.setOnTouchListener(new OnTouchListener() {

			PointF DownPT = new PointF(); // Record Mouse Position When Pressed
											// Down
			PointF StartPT = new PointF(); // Record Start Position of 'img'

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int eid = event.getAction();
				if (Gamepad.move == true) {
					switch (eid) {
					case MotionEvent.ACTION_MOVE:
						PointF mv = new PointF(event.getX() - DownPT.x, event
								.getY() - DownPT.y);
						which_move.setX((int) (StartPT.x + mv.x));
						which_move.setY((int) (StartPT.y + mv.y));
						// if(img.getX() >= 0 || img.getY() >= 0 )
						// {
						StartPT = new PointF(which_move.getX(), which_move
								.getY());
						// }
						break;
					case MotionEvent.ACTION_DOWN:
						DownPT.x = event.getX();
						DownPT.y = event.getY();
						StartPT = new PointF(which_move.getX(), which_move
								.getY());
						break;
					case MotionEvent.ACTION_UP:
						// Nothing have to do
						move_x = which_move.getX();
						move_y = which_move.getY();
						// Gamepad. popupWindow.showAsDropDown(Gamepad.editbtn);

						// }

						break;
					}
				}
				return false;
			}

		});
	}

	public void Delete_button(Button btn) {
		Gamepad.mydraw.removeView(btn);
		// btn.setVisibility(View.GONE);
	}

	public void Yes_No_option(final Button bt, Context con) {
		// Put up the Yes/No message box
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setTitle("Delete '" + bt.getText() + "' !")
				.setMessage("Are you sure?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Yes button clicked, do something
								Delete_button(bt);
							}
						}).setNegativeButton("No", null) // Do nothing on no
				.show();
	}
	
	
	public void vibrate(int check,long t) {
		
		
		if (check == 1)
		{
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
			v.vibrate(t);
		}
	}
	
	
}
