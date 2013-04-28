package final_project.pacman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ChooseActivity extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	ImageView pacman_normal;
	ImageView pacman_custom;
	ImageView pacman_gri;
	Intent intent;
	String character;
	Intent toMenu;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_char);

		intent = getIntent();

		pacman_normal = (ImageView) findViewById(R.id.imageView1);
		pacman_custom = (ImageView) findViewById(R.id.imageView2);
		pacman_gri = (ImageView) findViewById(R.id.imageView3);

		pacman_normal.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				character = "";
				finish();
			}
		});

		pacman_custom.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				character = "custom";
				finish();
			}
		});

		pacman_gri.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				character = "gri";
				finish();
			}
		});

	}

	@Override
	protected void onStop() {
		super.onStop();

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		System.out.println("gata");
		editor.putString("caracter", character);
		editor.commit();
		System.out.println("pppppppppppp" + character);

	}
}
