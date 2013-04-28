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

public class SaveHighscoreActivity extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	TextView scor;
	TextView theScore;
	TextView gameOver;
	EditText enterName;
	Button save;
	Button submit;
	EditText numeJucator;
	int myScor = 0;
	int level;
	boolean level_finished;
	Intent intent;
	String name = "";

	@Override
	public void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_save_highscore);

		intent = getIntent();
		theScore = (TextView) findViewById(R.id.textView2);
		enterName = (EditText) findViewById(R.id.editText1);
		save = (Button) findViewById(R.id.button1);

		myScor = intent.getIntExtra("scorJucator", 0);

		theScore.setText(myScor + "");

		save.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				name = enterName.getText().toString();
				Intent intent = new Intent(SaveHighscoreActivity.this,
						HighscoreActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("scorJucator", myScor);
				intent.putExtra("numeJucator", name);
				startActivity(intent);

			}
		});

	}

	@Override
	protected void onStop() {
		super.onStop();

	}
}
