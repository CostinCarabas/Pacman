package final_project.pacman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MeniuActivity extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	ImageView start;
	ImageView setari;
	ImageView highscore;
	ImageView caractere;
	ImageView flags;
	ImageView meniu;
	ImageView crazy_mode;
	String limba_folosita;
	Button buton;
	int id_start;
	int id_setari;
	int id_caractere;
	int id_limba;
	int level;

	@Override
	public void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_meniu);
		
		Intent intent = getIntent();

		buton = (Button) findViewById(R.id.button1);
		start = (ImageView) findViewById(R.id.imageView4);
		// setari = (ImageView) findViewById(R.id.imageView2);
		highscore = (ImageView) findViewById(R.id.imageView1);
		crazy_mode = (ImageView) findViewById(R.id.imageView8);
		// caractere = (ImageView) findViewById(R.id.imageView5);
		flags = (ImageView) findViewById(R.id.flags);
		meniu = (ImageView) findViewById(R.id.imageView9);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		limba_folosita = settings.getString("limba", "engleza");
		
		level = 1;

		// chooseLanguage(limba_folosita);
		// System.out.println("citesc limba:  " + limba_folosita);

		// start.setImageResource(id_start);
		// setari.setImageResource(id_setari);
		// caractere.setImageResource(id_caractere);
		// limba.setImageResource(id_limba);

		start.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();	
				Intent startJoc = new Intent(MeniuActivity.this,
						LevelActivity.class);
				
				editor.putBoolean("crazy_mode", false);
				editor.putInt("level", 1);
				startJoc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(startJoc, 107);
				editor.commit();
			}
		});

		crazy_mode.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();			
				Intent startJoc = new Intent(MeniuActivity.this,
						PlayActivity.class);
				
				editor.putBoolean("crazy_mode", true);
				startJoc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(startJoc, 107);
				editor.commit();

			}
		});
		/*
		 * caractere.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View arg0) { Intent choose = new
		 * Intent(MeniuActivity.this, ChooseActivity.class);
		 * startActivityForResult(choose, 101);
		 * 
		 * } });
		 * 
		 * setari.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View arg0) { Intent setariJoc = new
		 * Intent(MeniuActivity.this, SetariActivity.class);
		 * startActivity(setariJoc);
		 * 
		 * } });
		 */
		highscore.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent highscoreJoc = new Intent(MeniuActivity.this,
						HighscoreActivity.class);
				highscoreJoc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(highscoreJoc,109);

			}
		});

		flags.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent_limba = new Intent(MeniuActivity.this,
						LanguageActivity.class);
				intent_limba.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent_limba, 102);

			}
		});

	}

	public void functieMeniu(View v) {
		openOptionsMenu();
	}

	/*
	 * Create Menu Option
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;

	}

	/*
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent = new Intent();

		switch (item.getItemId()) {
		case R.id.dashboard_setari:
			intent.setClass(this, SetariActivity.class);
			// Toast.makeText(this, "Adaugare!!!!", Toast.LENGTH_SHORT).show();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, 100);
			return true;
		case R.id.dashboard_caracter:
			intent.setClass(this, ChooseActivity.class);
			// Toast.makeText(this, "Adaugare!!!!", Toast.LENGTH_SHORT).show();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, 101);
			return true;
		default:
			return false;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int responseCode, Intent data) {
		// raspunsul vine de la formularul de adaugare
		// acolo am cerut raspunsul cu nr 100
		System.out.println("am intrat");
		if (requestCode == 102) {
			// Engleza
			if (responseCode == 1) {
				start.setImageResource(R.drawable.new_game);
				/*
				 * setari.setImageResource(R.drawable.settings);
				 * caractere.setImageResource(R.drawable.characters);
				 * flags.setImageResource(R.drawable.language);
				 */
			}
			// Romana
			else if (responseCode == 2) {
				start.setImageResource(R.drawable.jocnoub);
				/*
				 * setari.setImageResource(R.drawable.setarib);
				 * caractere.setImageResource(R.drawable.caractere);
				 * flags.setImageResource(R.drawable.limba);
				 */
			}
			// Franceza
			else if (responseCode == 3) {
				System.out.println("am intrat");
			}
		}
		
		
	}

}
