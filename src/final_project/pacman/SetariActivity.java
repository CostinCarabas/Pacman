package final_project.pacman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.SeekBar;

public class SetariActivity extends Activity implements SensorEventListener {

	public static final String PREFS_NAME = "MyPrefsFile";
	CheckBox sunet;
	CheckBox vibratii;
	SeekBar dificultate;
	SeekBar sensitivitate;
	ImageView salveaza;
	ImageView pacman;
	ImageView sunet_b;
	ImageView vibratii_b;
	ImageView sensitivitate_b;
	ImageView nivel_b;
	AudioManager a;
	Vibrator v;
	MediaPlayer mediaPlayer;
	SensorManager sensorManager;
	double sensitivitate_joc;
	boolean fromPlay;

	String limba_folosita;
	int id_sensitivitate;
	int id_nivel;
	int id_sunet;
	int id_vibratii;
	int id_salveaza;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		Intent intent = getIntent();
		fromPlay = intent.getBooleanExtra("fromPlay", false);

		sunet = (CheckBox) findViewById(R.id.checkBox1);
		vibratii = (CheckBox) findViewById(R.id.checkBox2);
		dificultate = (SeekBar) findViewById(R.id.seekBar1);
		sensitivitate = (SeekBar) findViewById(R.id.seekBar2);
		salveaza = (ImageView) findViewById(R.id.imageView7);
		pacman = (ImageView) findViewById(R.id.imageView8);
		sunet_b = (ImageView) findViewById(R.id.imageView1);
		vibratii_b = (ImageView) findViewById(R.id.imageView2);
		sensitivitate_b = (ImageView) findViewById(R.id.imageView4);
		nivel_b = (ImageView) findViewById(R.id.imageView3);
		salveaza = (ImageView) findViewById(R.id.imageView7);
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		a = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

		// Restore preferences
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		sunet.setChecked(settings.getBoolean("sunet", false));
		vibratii.setChecked(settings.getBoolean("vibratii", false));
		dificultate.setProgress(settings.getInt("dificultate", 0));
		sensitivitate.setProgress(settings.getInt("sensitivitate", 0));
		limba_folosita = settings.getString("limba", "engleza");

		chooseLanguage(limba_folosita);

		sensitivitate_b.setImageResource(id_sensitivitate);
		sunet_b.setImageResource(id_sunet);
		vibratii_b.setImageResource(id_vibratii);
		nivel_b.setImageResource(id_nivel);
		salveaza.setImageResource(id_salveaza);

		sensitivitate_joc = 1.5 + sensitivitate.getProgress() * 0.3;

		// mediaPlayer = MediaPlayer.create(this, R.raw.a);
		// mediaPlayer.start();

		vibratii.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// long patern[] = { 100, 200, 200, 500, 500, 1000, 1000, 2000
				// };
				v.vibrate(500);

			}
		});

		salveaza.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Intent inapoiLaMeniu = new Intent(SetariActivity.this,
				// MeniuActivity.class);
				// startActivity(inapoiLaMeniu);
				// if (fromPlay) {
				// Intent inapoiLaMeniu = new Intent(SetariActivity.this,
				// PlayActivity.class);
				// startActivity(inapoiLaMeniu);
				// } else {
				finish();
				// }
			}
		});

	}

	@Override
	protected void onStop() {
		super.onStop();

		String string = "";

		// We need an Editor object to make preference changes.
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putBoolean("vibratii", vibratii.isChecked());
		editor.putBoolean("sunet", sunet.isChecked());
		editor.putInt("dificultate", dificultate.getProgress());
		editor.putInt("sensitivitate", sensitivitate.getProgress());

		// Commit the edits!
		editor.commit();
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		sensitivitate_joc = 1.5 + sensitivitate.getProgress() * 0.3;

		if (event.values[0] < -sensitivitate_joc) {
			pacman.setImageResource(R.drawable.pacmanfull_sus);
		} else if (event.values[0] > sensitivitate_joc) {
			pacman.setImageResource(R.drawable.pacmanfull_jos);
		} else if (event.values[1] > sensitivitate_joc) {
			pacman.setImageResource(R.drawable.pacmanfull_dreapta);
		} else if (event.values[1] < -sensitivitate_joc) {
			pacman.setImageResource(R.drawable.pacmanfull_stanga);
		}

	}

	public void chooseLanguage(String language) {
		if (language.equals("romana")) {

			id_sensitivitate = R.drawable.sensitivitate;
			id_nivel = R.drawable.nivelb;
			id_sunet = R.drawable.sunetb;
			id_vibratii = R.drawable.vibratiib;
			id_salveaza = R.drawable.salveaza;

		} else if (language.equals("franceza")) {

		} else if (language.equals("engleza")) {
			id_sensitivitate = R.drawable.sensitivity;
			id_nivel = R.drawable.speed;
			id_sunet = R.drawable.sound;
			id_vibratii = R.drawable.vibrate;
			id_salveaza = R.drawable.save;
		}
	}

}
