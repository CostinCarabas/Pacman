package final_project.pacman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class LevelActivity extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	ImageView pacman_normal;
	ImageView pacman_custom;
	ImageView pacman_gri;
	Intent intent;
	String character;
	SharedPreferences settings;
	Intent startJoc;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);

		intent = getIntent();

		pacman_normal = (ImageView) findViewById(R.id.imageView1);
		pacman_custom = (ImageView) findViewById(R.id.imageView2);
		pacman_gri = (ImageView) findViewById(R.id.imageView3);
		
		settings = getSharedPreferences(PREFS_NAME, 0);
		final SharedPreferences.Editor editor = settings.edit();	
		startJoc = new Intent(LevelActivity.this,
				PlayActivity.class);
		
		editor.putBoolean("crazy_mode", false);
		startJoc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		
		pacman_normal.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				editor.putInt("level", 1);
				startActivity(startJoc);
				editor.commit();
			}
		});

		pacman_custom.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				editor.putInt("level", 2);
				startActivity(startJoc);
				editor.commit();
			}
		});

		pacman_gri.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				editor.putInt("level", 3);
				startActivity(startJoc);
				editor.commit();
			}
		});
		
	}

	@Override
	protected void onStop() {
		super.onStop();

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		

	}
}
