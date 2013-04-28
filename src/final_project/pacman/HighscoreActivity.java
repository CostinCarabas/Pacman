package final_project.pacman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HighscoreActivity extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	TextView numeTV1;
	TextView numeTV2;
	TextView numeTV3;

	TextView scor1;
	TextView scor2;
	TextView scor3;
	
	Button meniu;

	int highscore1;
	int highscore2;
	int highscore3;
	int myScore;

	String nume1;
	String nume2;
	String nume3;
	String numeJucator;
	
	Intent intent;
	
	

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.activity_highscore);

		intent = getIntent();


		numeTV1 = (TextView) findViewById(R.id.textViewName1);
		numeTV2 = (TextView) findViewById(R.id.textViewName2);
		numeTV3 = (TextView) findViewById(R.id.textViewName3);
		scor1 = (TextView) findViewById(R.id.textViewScor1);
		scor2 = (TextView) findViewById(R.id.textViewScor2);
		scor3 = (TextView) findViewById(R.id.textViewScor3);
		meniu = (Button) findViewById(R.id.button1);
		
		
		// Restore preferences
	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
		
	    
	    
		myScore = intent.getIntExtra("scorJucator", 0);
		numeJucator = intent.getStringExtra("numeJucator");
		
		highscore1 = settings.getInt("1", 0);
	    highscore2 = settings.getInt("2", 0);
	    highscore3 = settings.getInt("3", 0);
		nume1 = settings.getString("nume1", "");
	    nume2 = settings.getString("nume2", "");
	    nume3 = settings.getString("nume3", "");

		meniu.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(HighscoreActivity.this,
						MeniuActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
		
		System.out.println("myScore = "+ numeJucator);
		System.out.println("1 = "+ nume1);
		System.out.println("2 = "+ nume2);
		System.out.println("3 = "+ nume3);
		
		if (myScore > highscore1) {
			scor1.setText(myScore + "");
			scor2.setText(highscore1 + "");
			scor3.setText(highscore2 + "");
			numeTV1.setText(numeJucator + "");
			numeTV2.setText(nume1 + "");
			numeTV3.setText(nume2 + "");
			
		    editor.putInt("1", myScore);
		    editor.putInt("2", highscore1);
		    editor.putInt("3", highscore2);
		    editor.putString("nume1", numeJucator);
		    editor.putString("nume2", nume1);
		    editor.putString("nume3", nume2);
			editor.commit();
		}
		else if (myScore > highscore2) {
			scor1.setText(highscore1 + "");
			scor2.setText(myScore + "");
			scor3.setText(highscore2 + "");
			numeTV1.setText(nume1 + "");
			numeTV2.setText(numeJucator + "");
			numeTV3.setText(nume3 + "");
			
			editor.putInt("1", highscore1);
		    editor.putInt("2", myScore);
		    editor.putInt("3", highscore2);
		    editor.putString("nume1", nume1);
		    editor.putString("nume2", numeJucator);
		    editor.putString("nume3", nume2);
			editor.commit();
		}
		else if (myScore > highscore3) {
			scor1.setText(highscore1 + "");
			scor2.setText(highscore2 + "");
			scor3.setText(myScore + "");
			numeTV1.setText(nume1 + "");
			numeTV2.setText(nume2 + "");
			numeTV3.setText(numeJucator + "");
			
			editor.putInt("1", highscore1);
			editor.putInt("2", highscore2);
		    editor.putInt("3", myScore);
		    editor.putString("nume1", nume1);
		    editor.putString("nume2", nume2);
		    editor.putString("nume3", numeJucator);
			editor.commit();
		}
		else {
			scor1.setText(highscore1 + "");
			scor2.setText(highscore2 + "");
			scor3.setText(highscore3 + "");
			numeTV1.setText(nume1 + "");
			numeTV2.setText(nume2 + "");
			numeTV3.setText(nume3 + "");
			
		    editor.putInt("1", highscore1);
		    editor.putInt("2", highscore2);
		    editor.putInt("3", highscore3);
		    editor.putString("nume1", nume1);
		    editor.putString("nume2", nume2);
		    editor.putString("nume3", nume3);
			editor.commit();
		}

	}


	protected void onStop() {
		super.onStop();

	}
}
