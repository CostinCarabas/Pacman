package final_project.pacman;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.PaintDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity implements SensorEventListener {

	private PowerManager.WakeLock wl;
	public static final String PREFS_NAME = "MyPrefsFile";
	CountDownTimer timer;
	CountDownTimer timeUntilMove;
	SurfaceView surfaceView;
	SurfaceHolder holder;
	SensorManager sensorManager;
	TextView textX;
	TextView score;
	TextView theScore;
	ImageView pause;
	// ImageView meniu;
	Bitmap zaPacman;
	Paint paint;

	Bitmap perete;
	Bitmap pereteAux;
	Bitmap food;
	Bitmap casutaEmpty;
	Bitmap smartEnemy;
	Bitmap smartEnemy2;
	Bitmap enemy1;
	Bitmap enemy2;
	Bitmap portal;
	Bitmap portalAux;
	Display display;
	int ecranWidth;
	int ecranHeight;
	int casutaWidth;
	int casutaHeight;
	int myWidth = 1;
	int myHeight = 1;
	int enemySmartWidth = 18;
	int enemySmartHeight = 6;
	int enemySmartWidth2 = 18;
	int enemySmartHeight2 = 8;

	MyPoint enemyRandom1 = new MyPoint(16, 8);
	MyPoint enemyRandom2 = new MyPoint(16, 7);
	int contorPauza = 0;
	int myScor = 0;
	int x = 0;
	int y = 0;
	int dificultate = 5;
	int sensitivitate = 5;
	int level = 1;
	int portalI;
	int portalJ;
	double sensitivitate_joc = 3;
	int contor = 0;
	float oldLeftRight;
	float oldDownUp;
	Boolean tim = true;
	Boolean t = true;
	boolean canMove = false;
	boolean moveUp = false;
	boolean moveDown = false;
	boolean moveLeft = false;
	boolean moveRight = false;
	boolean stopTimer = false;
	Boolean vibratii;
	Boolean sunet;
	boolean crazy_mode;
	boolean level_finished = false;
	Integer[][] mapMatrix = new Integer[10][20];
	AudioManager a;
	Vibrator v;
	MediaPlayer mediaPlayer;
	String colour;

	int pacman_inchis_sus;
	int pacman_inchis_jos;
	int pacman_inchis_dr;
	int pacman_inchis_st;
	int pacman_deschis_sus;
	int pacman_deschis_jos;
	int pacman_deschis_dr;
	int pacman_deschis_st;
	Intent incepeJoc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
		setContentView(R.layout.activity_play);

		incepeJoc = getIntent();
		paint = new Paint();

		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		a = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		vibratii = settings.getBoolean("vibratii", false);
		sunet = settings.getBoolean("sunet", false);
		dificultate = settings.getInt("dificultate", 5);
		sensitivitate = settings.getInt("sensitivitate", 5);
		colour = settings.getString("caracter", "");
		crazy_mode = settings.getBoolean("crazy_mode", false);

		level = settings.getInt("level", 1);
		initColour(colour);

		sensitivitate_joc = 1.5 + sensitivitate * 0.3;

		oldLeftRight = 0;
		oldDownUp = 0;

		mediaPlayer = MediaPlayer.create(this, R.raw.background);
		if (sunet)
			mediaPlayer.start();

		textX = (TextView) findViewById(R.id.textView1);
		score = (TextView) findViewById(R.id.textView2);
		theScore = (TextView) findViewById(R.id.textView3);
		// meniu = (ImageView) findViewById(R.id.imageView9);
		pause = (ImageView) findViewById(R.id.imageView1);

		display = getWindowManager().getDefaultDisplay();
		ecranWidth = display.getWidth();
		ecranHeight = display.getHeight();

		casutaWidth = ecranWidth / 21;
		casutaHeight = ecranHeight / 12;

		pereteAux = BitmapFactory
				.decodeResource(getResources(), R.drawable.zid);

		// String path = "pacmanfull_dreapta";
		zaPacman = BitmapFactory.decodeResource(getResources(),
				pacman_deschis_dr);

		food = BitmapFactory.decodeResource(getResources(),
				R.drawable.casutafill);

		casutaEmpty = BitmapFactory.decodeResource(getResources(),
				R.drawable.casutaempty);

		portalAux = BitmapFactory.decodeResource(getResources(),
				R.drawable.portal);

		System.out.println("!!!" + level);

		if (crazy_mode) {
			smartEnemy2 = BitmapFactory.decodeResource(getResources(),
					R.drawable.ghost);
			smartEnemy2 = Bitmap.createScaledBitmap(smartEnemy2, casutaWidth,
					casutaHeight, true);
		}

		if (level >= 2) {
			enemy1 = BitmapFactory.decodeResource(getResources(),
					R.drawable.ghost1);
			enemy1 = Bitmap.createScaledBitmap(enemy1, casutaWidth,
					casutaHeight, true);
		}
		if (level >= 3) {
			enemy2 = BitmapFactory.decodeResource(getResources(),
					R.drawable.ghost2);
			enemy2 = Bitmap.createScaledBitmap(enemy2, casutaWidth,
					casutaHeight, true);
		}

		if (level >= 4) {

			smartEnemy = BitmapFactory.decodeResource(getResources(),
					R.drawable.ghost);
			smartEnemy = Bitmap.createScaledBitmap(smartEnemy, casutaWidth,
					casutaHeight, true);
		}

		perete = Bitmap.createScaledBitmap(pereteAux, casutaWidth,
				casutaHeight, true);
		portal = Bitmap.createScaledBitmap(portalAux, casutaWidth,
				casutaHeight, true);
		zaPacman = Bitmap.createScaledBitmap(zaPacman, casutaWidth,
				casutaHeight, true);
		food = Bitmap.createScaledBitmap(food, casutaWidth, casutaHeight, true);

		casutaEmpty = Bitmap.createScaledBitmap(casutaEmpty, casutaWidth,
				casutaHeight, true);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

		System.out.println("!!!" + level);
		if (crazy_mode) {
			initializareMapaCrazyMode(mapMatrix);
		} else {
			switch (level) {
			case 1:
				initializareMapaLevel(mapMatrix, R.drawable.llevel1);
				myWidth = 6;
				myHeight = 3;
				break;
			case 2:
				initializareMapaLevel(mapMatrix, R.drawable.level2);
				break;
			case 3:
				initializareMapaLevel(mapMatrix, R.drawable.level3);
				break;
			case 4:
				initializareMapaLevel(mapMatrix, R.drawable.level4);
				break;
			case 5:
				initializareMapaLevel(mapMatrix, R.drawable.level5);
				break;
			default:
				initializareMapaCrazyMode(mapMatrix);
				;
				break;
			}
		}

		pause.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (contorPauza % 2 == 0) {
					mediaPlayer.pause();
					timer.cancel();
					pause.setImageResource(R.drawable.ic_media_play);
					contorPauza++;
				} else {
					mediaPlayer.start();
					pause.setImageResource(R.drawable.ic_media_pause);
					timer.start();
					contorPauza++;
				}
			}
		});

		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		holder = surfaceView.getHolder();

		holder.addCallback(new Callback() {

			public void surfaceDestroyed(SurfaceHolder arg0) {
			}

			public void surfaceCreated(SurfaceHolder arg0) {

				timer = new CountDownTimer(999999999, (11 - dificultate) * 80) {

					@Override
					public void onTick(long millisUntilFinished) {

						if (tim)
							drawCanvas(holder);

					}

					@Override
					public void onFinish() {
						// Trimit scor la FinishActivity

						stopTimer = true;

					}
				}.start();

			}

			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
					int arg3) {

			}
		});

	}

	// Metode auxiliare

	public void drawCanvas(SurfaceHolder holder) {
		Canvas canvas = holder.lockCanvas();
		// zid -1
		// mancare 1
		// fantoma -10,-11,-12
		// gol 0
		// portal 100

		for (int i = 0; i < mapMatrix[0].length; i++) {
			for (int j = 0; j < mapMatrix.length; j++) {
				if (mapMatrix[j][i] == -1) {
					// desenez PERETE
					canvas.drawBitmap(perete, i * casutaWidth,
							j * casutaHeight, paint);
				} else if (mapMatrix[j][i] == 1) {
					// desenez MANCARE
					canvas.drawBitmap(food, i * casutaWidth, j * casutaHeight,
							paint);
				} else if (mapMatrix[j][i] == 0) {
					// desenez SPATIU GOL
					canvas.drawBitmap(casutaEmpty, i * casutaWidth, j
							* casutaHeight, paint);
				} else if (mapMatrix[j][i] == 100) {
					// desenez PORTAL
					canvas.drawBitmap(portal, i * casutaWidth,
							j * casutaHeight, paint);
					portalJ = j;
					portalI = i;
				}

			}
		}

		if (moveDown && mapMatrix[myHeight + 1][myWidth] != -1) {
			// move DOWN
			x = 1;
			y = 0;
			if (contor % 2 == 1) {
				zaPacman = BitmapFactory.decodeResource(getResources(),
						pacman_inchis_jos);
				zaPacman = Bitmap.createScaledBitmap(zaPacman, casutaWidth,
						casutaHeight, true);
			} else {
				zaPacman = BitmapFactory.decodeResource(getResources(),
						pacman_deschis_jos);
				zaPacman = Bitmap.createScaledBitmap(zaPacman, casutaWidth,
						casutaHeight, true);
			}

		} else if (moveUp && mapMatrix[myHeight - 1][myWidth] != -1) {
			// move UP
			x = -1;
			y = 0;

			if (contor % 2 == 1) {
				zaPacman = BitmapFactory.decodeResource(getResources(),
						pacman_inchis_sus);
				zaPacman = Bitmap.createScaledBitmap(zaPacman, casutaWidth,
						casutaHeight, true);
			} else {
				zaPacman = BitmapFactory.decodeResource(getResources(),
						pacman_deschis_sus);
				zaPacman = Bitmap.createScaledBitmap(zaPacman, casutaWidth,
						casutaHeight, true);
			}

		} else if (moveRight && mapMatrix[myHeight][myWidth + 1] != -1) {
			// move RIGHT
			x = 0;
			y = 1;

			if (contor % 2 == 1) {
				zaPacman = BitmapFactory.decodeResource(getResources(),
						pacman_inchis_dr);
				zaPacman = Bitmap.createScaledBitmap(zaPacman, casutaWidth,
						casutaHeight, true);
			} else {
				zaPacman = BitmapFactory.decodeResource(getResources(),
						pacman_deschis_dr);
				zaPacman = Bitmap.createScaledBitmap(zaPacman, casutaWidth,
						casutaHeight, true);

			}

		} else if (moveLeft && mapMatrix[myHeight][myWidth - 1] != -1) {
			// move LEFT
			x = 0;
			y = -1;
			if (contor % 2 == 1) {
				zaPacman = BitmapFactory.decodeResource(getResources(),
						pacman_inchis_st);
				zaPacman = Bitmap.createScaledBitmap(zaPacman, casutaWidth,
						casutaHeight, true);
			} else {
				zaPacman = BitmapFactory.decodeResource(getResources(),
						pacman_deschis_st);
				zaPacman = Bitmap.createScaledBitmap(zaPacman, casutaWidth,
						casutaHeight, true);
			}
		}

		// nu depasesc limitele hartii
		if (mapMatrix[myHeight + x][myWidth + y] != -1) {
			if (mapMatrix[myHeight + x][myWidth + y] == 1) {
				mapMatrix[myHeight + x][myWidth + y] = 0;
				// initializez scorul
				myScor += 5;
				theScore.setText(myScor + "");
			}
			myHeight += x;
			myWidth += y;
		}

		// inamic destept
		if (level >= 4) {
			MyPoint enemySm1 = createEnemyMove(canvas, smartEnemy,
					enemySmartHeight, enemySmartWidth);
			enemySmartHeight = enemySm1.x;
			enemySmartWidth = enemySm1.y;
		}
		if (crazy_mode) {
			MyPoint enemySm2 = createEnemyMove(canvas, smartEnemy2,
					enemySmartHeight, enemySmartWidth);
			enemySmartHeight = enemySm2.x;
			enemySmartWidth = enemySm2.y;
		}

		// inamici prosti
		if (level >= 2)
			createDumbEnemyMove(canvas, enemyRandom1, enemy1);
		if (level >= 3)
			createDumbEnemyMove(canvas, enemyRandom2, enemy2);

		// deseneaza PACMAN
		canvas.drawBitmap(zaPacman, myWidth * casutaWidth, myHeight
				* casutaHeight, paint);

		boolean finish = false;

		finish = sameCoord(enemySmartWidth, enemySmartHeight)
				|| sameCoord(enemyRandom1.x, enemyRandom1.y)
				|| sameCoord(enemyRandom2.x, enemyRandom2.y);

		if (sameCoord(portalI, portalJ)) {
			level++;
			finish = true;
			level_finished = true;
		}

		if (finish) {
			if (!stopTimer) {

				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();

				int lastHighscore = settings.getInt("locul3", 0);

				editor.putInt("level", level);
				editor.commit();
				if (level_finished) {
					Intent intent123 = new Intent(PlayActivity.this,
							FinishActivity.class);
					intent123.putExtra("scorJucator", myScor);
					intent123.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent123);
				}
				else {
					Intent intent123 = new Intent(PlayActivity.this,
							SaveHighscoreActivity.class);
					intent123.putExtra("scorJucator", myScor);
					intent123.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent123);
					
				}
				if (vibratii)
					v.vibrate(2000);

				tim = false;
				holder.unlockCanvasAndPost(canvas);
				t = false;
			}
			timer.onFinish();

		}

		contor++;
		if (t)
			holder.unlockCanvasAndPost(canvas);

	}

	@Override
	protected void onPause() {
		super.onPause();
		wl.release();
	}

	@Override
	protected void onResume() {
		super.onResume();
		wl.acquire();
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	public void onSensorChanged(SensorEvent event) {

		if (oldDownUp - event.values[0] < -sensitivitate_joc) {
			oldDownUp = (event.values[0] + oldDownUp) / 4;
			textX.setText("Ma duc in jos ");
			if (myHeight < mapMatrix.length - 2) {

				if (mapMatrix[myHeight + 1][myWidth] == 1) {
					mapMatrix[myHeight + 1][myWidth] = 0;
					myScor += 5;
					theScore.setText(myScor + "");

				}
				moveDown = true;
				moveUp = false;
				moveRight = false;
				moveLeft = false;
			}

		} else if (oldDownUp - event.values[0] > sensitivitate_joc) {
			oldDownUp = (event.values[0] + oldDownUp) / 4;
			textX.setText("Ma duc in sus");
			if (myHeight > 1) {

				if (mapMatrix[myHeight - 1][myWidth] == 1) {
					mapMatrix[myHeight - 1][myWidth] = 0;
					myScor += 5;
					theScore.setText(myScor + "");

				}
				moveDown = false;
				moveUp = true;
				moveRight = false;
				moveLeft = false;
			}

		} else if (event.values[1] - oldLeftRight > sensitivitate_joc) {
			oldLeftRight = (event.values[1] + oldLeftRight) / 4;
			textX.setText("Ma duc in dreapta");
			if (myWidth < mapMatrix[0].length - 2) {
				if (mapMatrix[myHeight][myWidth + 1] == 1) {
					mapMatrix[myHeight][myWidth + 1] = 0;
					myScor += 5;
					theScore.setText(myScor + "");
				}
				moveDown = false;
				moveUp = false;
				moveRight = true;
				moveLeft = false;
			}

		} else if (event.values[1] - oldLeftRight < -sensitivitate_joc) {
			oldLeftRight = (event.values[1] + oldLeftRight) / 4;
			textX.setText("Ma duc in stanga");
			if (myWidth > 1) {
				if (mapMatrix[myHeight][myWidth - 1] == 1) {
					mapMatrix[myHeight][myWidth - 1] = 0;
					myScor += 5;
					theScore.setText(myScor + "");
				}
				moveDown = false;
				moveUp = false;
				moveRight = false;
				moveLeft = true;
			}

		}
	}

	public void setarePortalInitializareMapa(Integer[][] mapMatrix, int linie,
			int coloana) {
		mapMatrix[linie][coloana] = 100;
	}

	public void setareZidInitializareMapa(Integer[][] mapMatrix, int linie,
			int coloana) {
		mapMatrix[linie][coloana] = -1;
	}

	public Point goDown(Point p) {
		p.x++;
		zaPacman = BitmapFactory.decodeResource(getResources(),
				pacman_deschis_jos);
		return p;
	}

	public Point goUp(Point p) {
		p.x--;
		zaPacman = BitmapFactory.decodeResource(getResources(),
				pacman_deschis_sus);
		return p;
	}

	public Point goRight(Point p) {
		p.y++;
		zaPacman = BitmapFactory.decodeResource(getResources(),
				pacman_deschis_dr);
		return p;
	}

	public Point goLeft(Point p) {
		p.y--;
		zaPacman = BitmapFactory.decodeResource(getResources(),
				pacman_deschis_st);
		return p;
	}

	public void initializareMapa(Integer[][] mapMatrix) {
		for (int i = 0; i < mapMatrix[0].length; i++) {
			mapMatrix[0][i] = -1;
			mapMatrix[mapMatrix.length - 1][i] = -1;
		}
		for (int i = 0; i < mapMatrix.length; i++) {
			mapMatrix[i][0] = -1;
			mapMatrix[i][mapMatrix[0].length - 1] = -1;
		}

		for (int i = 1; i < mapMatrix.length - 1; i++) {
			for (int j = 1; j < mapMatrix[0].length - 1; j++) {
				mapMatrix[i][j] = 1;
			}
		}
	}

	public void initializareMapaCrazyMode(Integer[][] mapMatrix) {
		initializareMapa(mapMatrix);

		try {

			String str = "";
			StringBuffer buf = new StringBuffer();
			InputStream is = this.getResources().openRawResource(
					R.drawable.crazy_map);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			if (is != null) {
				while ((str = reader.readLine()) != null) {
					String[] coord = str.split(" ");
					setareZidInitializareMapa(mapMatrix,
							Integer.parseInt(coord[0]),
							Integer.parseInt(coord[1]));
				}
			}
			is.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void initializareMapaLevel(Integer[][] mapMatrix, int mapa) {
		String[] coord;
		initializareMapa(mapMatrix);

		try {

			String str = "";
			StringBuffer buf = new StringBuffer();
			InputStream is = this.getResources().openRawResource(mapa);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			if (is != null && ((str = reader.readLine()) != null)) {

				str = reader.readLine();
				coord = str.split(" ");

				setarePortalInitializareMapa(mapMatrix,
						Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));

				while ((str = reader.readLine()) != null) {
					coord = str.split(" ");
					setareZidInitializareMapa(mapMatrix,
							Integer.parseInt(coord[0]),
							Integer.parseInt(coord[1]));
				}
			}
			is.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public boolean sameCoord(int enemyWidth, int enemyHeight) {
		boolean rez = false;
		if (myWidth == enemyWidth && myHeight == enemyHeight) {
			rez = true;
		}
		return rez;
	}

	public ArrayList<MyPoint> getNeigh(MyPoint p) {
		ArrayList<MyPoint> vecini = new ArrayList<MyPoint>();
		if (mapMatrix[p.x + 1][p.y] != -1 && p.x < mapMatrix.length - 2)
			vecini.add(new MyPoint(p.x + 1, p.y));
		if (mapMatrix[p.x - 1][p.y] != -1 && p.x > 1)
			vecini.add(new MyPoint(p.x - 1, p.y));
		if (mapMatrix[p.x][p.y + 1] != -1 && p.y < mapMatrix[0].length - 2)
			vecini.add(new MyPoint(p.x, p.y + 1));
		if (mapMatrix[p.x][p.y - 1] != -1 && p.y > 1)
			vecini.add(new MyPoint(p.x, p.y - 1));
		return vecini;
	}

	public void nextRandomEnemyMove(MyPoint enemyRandom) {
		Random generator = new Random();
		ArrayList<MyPoint> vector = new ArrayList<MyPoint>();
		vector.add(new MyPoint(-1, 0));
		vector.add(new MyPoint(1, 0));
		vector.add(new MyPoint(0, 1));
		vector.add(new MyPoint(0, -1));

		int rnd;

		while (true) {
			rnd = generator.nextInt(4);

			if (mapMatrix[enemyRandom.y + vector.get(rnd).y][enemyRandom.x
					+ vector.get(rnd).x] != -1) {
				enemyRandom.x += vector.get(rnd).x;
				enemyRandom.y += vector.get(rnd).y;
				break;
			}
		}
	}

	public MyPoint createEnemyMove(Canvas canvas, Bitmap enemy,
			Integer enemyHeight, int enemyWidth) {

		MyPoint rez = nextEnemyMove(enemyHeight, enemyWidth);
		enemyHeight = rez.x;
		enemyWidth = rez.y;
		canvas.drawBitmap(enemy, enemyWidth * casutaWidth, enemyHeight
				* casutaHeight, paint);

		return rez;
	}

	public void createDumbEnemyMove(Canvas canvas, MyPoint enemyRandom1,
			Bitmap enemy1) {

		nextRandomEnemyMove(enemyRandom1);
		canvas.drawBitmap(enemy1, enemyRandom1.x * casutaWidth, enemyRandom1.y
				* casutaHeight, paint);

	}

	public MyPoint nextEnemyMove(int enemyHeight, int enemyWidth) {
		LinkedList<MyPoint> Q = new LinkedList<MyPoint>();
		Set<MyPoint> visited = new HashSet<MyPoint>();
		MyPoint me = new MyPoint(myHeight, myWidth);
		MyPoint enemy = new MyPoint(enemyHeight, enemyWidth);
		MyPoint a = me;
		MyPoint toReturn = new MyPoint(enemy);

		visited.add(enemy);
		Q.add(enemy);

		while (!Q.isEmpty()) {
			MyPoint p = Q.removeFirst();
			ArrayList<MyPoint> vecini = getNeigh(p);
			for (MyPoint vecin : vecini) {
				if (!visited.contains(vecin)) {
					Q.addLast(vecin);
					visited.add(vecin);
					vecin.setParent(p);

					if (vecin.x == me.x && vecin.y == me.y) {
						a = new MyPoint(vecin);
						MyPoint b = a.parent;
						while (!b.equals(enemy)) {

							a = b;
							b = b.parent;
						}

					}

				}

			}

		}

		return a;
	}

	@Override
	public void onActivityResult(int requestCode, int responseCode, Intent data) {
		System.out.println("PlayActivity!");

		if (requestCode == 200) {
			// s-a apasat long click => EDIT
			// s-a apasat butonul Adauga
			System.out.println("am intrat la 105 in Play");

			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();

			if (responseCode == 1) {
				System.out.println("raspuns 1 in PA");
				editor.putInt("level", 1);
				editor.commit();
				setResult(99, getIntent());
				finish();
			}
			// s-a apasat butonul Renunta
			else if (responseCode == 0) {
				System.out.println("raspuns 0 in play!");
				setResult(9, getIntent());
				finish();
			}

		}

	}

	public float average(ArrayList<Float> array) {
		float average = 0;

		for (float e : array) {
			average += e;
		}

		return average / array.size();
	}

	public void initColour(String cul) {
		if (cul.compareTo("gri") == 0) {
			pacman_inchis_sus = R.drawable.pacman_gura_inchisa_sus_gri;
			pacman_inchis_jos = R.drawable.pacman_gura_inchisa_jos_gri;
			pacman_inchis_dr = R.drawable.pacman_gura_inchisa_dr_gri;
			pacman_inchis_st = R.drawable.pacman_gura_inchisa_stanga_gri;
			pacman_deschis_sus = R.drawable.pacmanfull_sus_gri;
			pacman_deschis_jos = R.drawable.pacmanfull_jos_gri;
			pacman_deschis_dr = R.drawable.pacmanfull_dreapta_gri;
			pacman_deschis_st = R.drawable.pacmanfull_stanga_gri;
		} else if (cul.compareTo("custom") == 0) {
			pacman_inchis_sus = R.drawable.pacman_gura_inchisa_sus_custom;
			pacman_inchis_jos = R.drawable.pacman_gura_inchisa_jos_custom;
			pacman_inchis_dr = R.drawable.pacman_gura_inchisa_dr_custom;
			pacman_inchis_st = R.drawable.pacman_gura_inchisa_stanga_custom;
			pacman_deschis_sus = R.drawable.pacmanfull_sus_custom;
			pacman_deschis_jos = R.drawable.pacmanfull_jos_custom;
			pacman_deschis_dr = R.drawable.pacmanfull_dreapta_custom;
			pacman_deschis_st = R.drawable.pacmanfull_stanga_custom;
		} else {
			pacman_inchis_sus = R.drawable.pacman_gura_inchisa_sus;
			pacman_inchis_jos = R.drawable.pacman_gura_inchisa_jos;
			pacman_inchis_dr = R.drawable.pacman_gura_inchisa_dr;
			pacman_inchis_st = R.drawable.pacman_gura_inchisa_st;
			pacman_deschis_sus = R.drawable.pacmanfull_sus;
			pacman_deschis_jos = R.drawable.pacmanfull_jos;
			pacman_deschis_dr = R.drawable.pacmanfull_dreapta;
			pacman_deschis_st = R.drawable.pacmanfull_stanga;
		}
	}

	/*
	 * public void functieMeniu(View v) { if (contorPauza % 2 == 0) {
	 * mediaPlayer.pause(); timer.cancel(); contorPauza++; } else {
	 * mediaPlayer.start(); timer.start(); contorPauza++; } openOptionsMenu();
	 * 
	 * }
	 */

	/*
	 * Create Menu Option
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { MenuInflater
	 * inflater = getMenuInflater(); inflater.inflate(R.menu.activity_main,
	 * menu); return true;
	 * 
	 * }
	 */
	/*
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) {
	 * 
	 * Intent intent = new Intent();
	 * 
	 * switch (item.getItemId()) { case R.id.dashboard_setari:
	 * intent.setClass(this, SetariActivity.class); intent.putExtra("fromPlay",
	 * true); // Toast.makeText(this, "Adaugare!!!!",
	 * Toast.LENGTH_SHORT).show(); startActivityForResult(intent, 900); return
	 * true; case R.id.dashboard_caracter: intent.setClass(this,
	 * LevelActivity.class); // Toast.makeText(this, "Adaugare!!!!",
	 * Toast.LENGTH_SHORT).show(); startActivityForResult(intent, 901); return
	 * true; default: return false; } }
	 */

	@Override
	protected void onStop() {
		super.onStop();
		mediaPlayer.stop();
		timer.cancel();
		finish();
	}

}
