package final_project.pacman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FinishActivity extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	TextView scor;
	TextView theScor;
	TextView gameOver;
	Button meniu;
	Button next_level;
	Button submit;
	EditText numeJucator;
	int myScor = 0;
	int level;
	boolean level_finished;
	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_finish);

		intent = getIntent();
		myScor = intent.getIntExtra("scorJucator", 0);
		level_finished = intent.getBooleanExtra("levelFinished", false);

		scor = (TextView) findViewById(R.id.textView1);
		gameOver = (TextView) findViewById(R.id.textView4);
		theScor = (TextView) findViewById(R.id.textView2);
		meniu = (Button) findViewById(R.id.button1);
		next_level = (Button) findViewById(R.id.button2);
		// 9++++++submit = (Button) findViewById(R.id.button3);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		level = settings.getInt("level", 1);

	
		theScor.setText(myScor + "");

	
			next_level.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					Intent intent = new Intent(FinishActivity.this,
							PlayActivity.class);
					SharedPreferences settings = getSharedPreferences(
							PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					level++;
					editor.putInt("level", level);
					editor.commit();
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);

				}
			});
			
			meniu.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent(FinishActivity.this,
							MeniuActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);

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

		editor.putInt("scorJucator", myScor);

		// Commit the edits!
		editor.commit();
	}

}
