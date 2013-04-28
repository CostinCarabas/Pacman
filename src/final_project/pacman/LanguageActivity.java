package final_project.pacman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class LanguageActivity extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	ImageView engleza;
	ImageView romana;
	ImageView franceza;
	Intent intent;
	String limba;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_language);

		intent = getIntent();

		engleza = (ImageView) findViewById(R.id.imageView4);
		romana = (ImageView) findViewById(R.id.imageView5);
		franceza = (ImageView) findViewById(R.id.imageView6);
		

		
		engleza.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				limba = "engleza";
				setResult(1);
				finish();
			}
		});

		romana.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				limba = "romana";
				setResult(2);
				finish();
			}
		});

		franceza.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				limba = "franceza";
				setResult(3);
				finish();
			}
		});
		
	}

	@Override
	protected void onStop() {
		super.onStop();

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString("limba", limba);
		editor.commit();
		System.out.println("am scris limba: "+limba);

	}
}
