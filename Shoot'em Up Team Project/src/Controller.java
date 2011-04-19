import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Vector;



public class Controller implements Runnable {
	private View view;
	private Gun gun;
	private HitDetect detect;
	private CowboyShoot shoot;
	private Thread runCowboys;
	private Thread hitDetect;
	private Thread cowboyShoot;
	private int cowboyCount;
	private Vector<GunMissile> missile = new Vector<GunMissile>();
	private Vector<CowboyGunMissile> cowboyMissile = new Vector<CowboyGunMissile>();
	private Vector<Cowboy> cowboys=new Vector<Cowboy>();
	private boolean gameStarted = false;
	private boolean paused = false;
	private boolean gameOver;
	private boolean gameOverLose;
	private boolean removing=false;
	
	public Controller(View view)
	{
		this.view=view;
		setupListeners();
		runCowboys = new Thread(this);
		detect = new HitDetect();
		hitDetect = new Thread(detect);
		shoot = new CowboyShoot();
		cowboyShoot = new Thread(shoot);
		refresh();
	}

	void setupGame(){
		gun = new Gun();
		cowboyCount=5;
		for(int i = 0; i<cowboyCount;i++)
		{
			cowboys.add(new Cowboy(i));
		}
		gameOver=false;
		gameOverLose=false;
		view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboyMissile);
	}
	void refresh(){
		view.drawlingPanel.refresh();
	}
	private void setupListeners(){
		view.addKeyListener(new MyKeyListener());
	}
	
	public boolean getGameStatus(){
		if(gameOver||gameOverLose){
			return true;
		}
		else
			return false;
	}
	
	public void removeCowboy(int x){
		cowboys.remove(x);
		cowboyCount--;
	}
	public void removeMissile(){
		missile.remove(0);
		missile.trimToSize();
	}
	
	public void newLevel(){
		removing=true;
		missile.removeAllElements();
		cowboyMissile.removeAllElements();
	}

	public void removeCowboyMissile() {
		cowboyMissile.remove(0);
		cowboyMissile.trimToSize();
	}

	public Vector<GunMissile> getMissile(){
		return missile;
	}

	public Vector<CowboyGunMissile> getCowboyMissile(){
		return cowboyMissile;
	}

	public Vector<Cowboy> getCowboys(){
		return cowboys;
	}

	public class CowboyShoot implements Runnable{
		private Random numberGenerator = new Random();
		@Override
		public void run() {
			while(true) {
				if(!paused&&!gameOverLose&&!gameOver) {
					for(int i = 0; i < cowboys.size(); i++) {
						if(numberGenerator.nextInt(100) <= 20) {
							if(cowboyMissile.size() > 20) {
								removeCowboyMissile(); }
							
							cowboyMissile.add(new CowboyGunMissile(cowboys.get(i).getX(), cowboys.get(i).getY() - 5));
						}
					}
				}
				try { Thread.sleep(1000); } catch(Exception e) {}
			}
		}
	}

	public class HitDetect implements Runnable{
		
		public void detecting(){
		
				for(int i = 0; i < (cowboyMissile.size()); i++) {
						if(!removing&&cowboyMissile.get(i).getXStart() >= gun.getX() &&
							cowboyMissile.get(i).getXStart() <= gun.getX() + 30 &&
							cowboyMissile.get(i).getYStart() >= 385 &&
							cowboyMissile.get(i).getYStart() <= 455) {
								removeCowboyMissile();
								gameOverLose=true;
								view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboyMissile);
						}
				}
				for(int i=0;i<(missile.size()-1);i++)
				{
					for(int j=0;j<cowboys.size();j++)
					{
						if(missile.get(i).getXStart()>=cowboys.get(j).getX()&&
						missile.get(i).getXStart()<(cowboys.get(j).getX()+25))
						{
							if(missile.get(i).getYStart()<=cowboys.get(j).getY()&&
									missile.get(i).getYStart()>(cowboys.get(j).getY()-12))
							{
								removeMissile();
								removeCowboy(j);
							}
						}
					}
				}
		}
		@Override
		public void run() {
			while(true){
				if(!paused&&!gameOver&&!gameOverLose){
					detecting();
				}
				else
					refresh();
			}
		}
	}
	public int moveCowboys(int round){
			for(int j=0;j<60;j++)
			{	
				for(int i = 0;i<cowboyCount;i++)
				{
					if (j<30) {
						cowboys.get(i).moveRight(5);
					}
					else
						cowboys.get(i).moveLeft(5);
				}
				refresh();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) { }
			}
			if(cowboys.isEmpty()&&round==1){
				newLevel();
				cowboyCount=10;
				for(int i = 0; i<cowboyCount;i++)
				{
					cowboys.add(new Cowboy(i));
				}
				removing=false;
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboyMissile);
				round++;
			}
			else if(cowboys.isEmpty()&&round==2){
				cowboyCount=15;
				newLevel();
				for(int i = 0; i<cowboyCount;i++)
				{
					cowboys.add(new Cowboy(i));
				}
				removing=false;
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose,cowboyMissile);
				round++;
			}
			else if(cowboys.isEmpty()&&round==3){
				cowboyCount=20;
				newLevel();
				for(int i = 0; i<cowboyCount;i++)
				{
					cowboys.add(new Cowboy(i));
				}
				removing=false;
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboyMissile);
				round++;
			}
			else if(cowboys.isEmpty()&&round==4){
				cowboyCount=25;
				newLevel();
				for(int i = 0; i<cowboyCount;i++)
				{
					cowboys.add(new Cowboy(i));
				}
				removing=false;
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboyMissile);
				round++;
			}
			else if(cowboys.isEmpty()&&round==5){
				gameOver=true;
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboyMissile);
			}
		return round;
	}
	@Override
	public void run() {
		int round = 1;
		while(true){
			if(!paused&&!gameOver&&!gameOverLose){
				round=moveCowboys(round);
			}
			else{
				if(gameOver||gameOverLose){
					round=1;
				}
				refresh();
			}
		}
		
	}
	class MyKeyListener extends KeyAdapter {
		
		public void keyPressed(KeyEvent ke) {
			int keyCode = ke.getKeyCode();
			
			if (keyCode == KeyEvent.VK_RIGHT) {
				if (gun.getX()<=400&&gameStarted) {
					gun.moveRight(8);
				}
			}
			else if (keyCode == KeyEvent.VK_LEFT) {
				if (gun.getX()>=0&&gameStarted){
					gun.moveLeft(8);
				}
			
			}
			else if(keyCode == KeyEvent.VK_UP) {
				if(gameStarted)
				{
					if(missile.size()>15)
					{
						removeMissile();
					}
					missile.add(new GunMissile(gun.getX()+15));
				}
			}
			else if(keyCode == KeyEvent.VK_ENTER)
			{
				if(!gameStarted){
					gameStarted=true;
					setupGame();
					runCowboys.start();
					hitDetect.start();
					cowboyShoot.start();
				}
				if(gameOver||gameOverLose){
					try {
						hitDetect.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						runCowboys.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cowboys.removeAllElements();
					setupGame();
				}
			}
			else if (keyCode == KeyEvent.VK_ESCAPE)
			{
				if(gameStarted)
				{
					if(!paused){
						paused=true;
						view.drawlingPanel.setup(gun, cowboys, missile,gameStarted, paused,gameOver,gameOverLose, cowboyMissile);
					}
					else{
						paused=false;
						view.drawlingPanel.setup(gun, cowboys, missile,gameStarted, paused,gameOver,gameOverLose, cowboyMissile);
					}
				}
			}
			refresh();
		}
	}

}
