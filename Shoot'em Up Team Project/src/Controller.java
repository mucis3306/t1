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
	private Vector<CowboyGunMissile> cowboymissile = new Vector<CowboyGunMissile>();
	private Vector<Cowboy> cowboys=new Vector<Cowboy>();
	private boolean gameStarted = false;
	private boolean paused = false;
	private boolean gameOver;
	private boolean gameOverLose;
	
	public Controller(View view)
	{
		this.view=view;
		//setupGame();
		setupListeners();
		runCowboys = new Thread(this);
		detect = new HitDetect(cowboys, missile, cowboymissile);
		hitDetect = new Thread(detect);
		shoot = new CowboyShoot(cowboys, cowboymissile);
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
		view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboymissile);
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
	
	public void removeAlien(int x){
		cowboys.remove(x);
		cowboyCount--;
	}
	public void removeMissile(){
		missile.remove(0);
		missile.trimToSize();
	}

	public void removeCowboyMissile() {
		cowboymissile.remove(0);
		cowboymissile.trimToSize();
	}

	public Vector<GunMissile> getMissile(){
		return missile;
	}

	public Vector<CowboyGunMissile> getCowboyMissile(){
		return cowboymissile;
	}

	public Vector<Cowboy> getCowboys(){
		return cowboys;
	}


	public class CowboyShoot implements Runnable{
		private Vector<Cowboy> cowboys = new Vector<Cowboy>();
		private Vector<CowboyGunMissile> missile = new Vector<CowboyGunMissile>();
		private Random numberGenerator = new Random();
		public CowboyShoot( Vector<Cowboy> cowboys, Vector<CowboyGunMissile> missile) {
			this.missile = missile;
			this.cowboys = cowboys;
		}

		private void reGet(){
			this.cowboys=getCowboys();
			this.missile=getCowboyMissile();
		}

		@Override
		public void run() {
			while(true) {
				if( !(paused) && !(gameOverLose == true) && !(gameOver == true) ) {
					reGet();
					for(int i = 0; i < cowboys.size(); i++) {
						if(numberGenerator.nextInt(100) <= 20) {
							if(missile.size() > 20) {
								missile.removeAllElements();
								missile.trimToSize(); }
							
							missile.add(new CowboyGunMissile(cowboys.get(i).getX(), cowboys.get(i).getY() - 5));
						}
					}
				}
				try { Thread.sleep(1000); } catch(Exception e) {}
			}
		}
	}

	public class HitDetect implements Runnable{
		
		private Vector<GunMissile> missile = new Vector<GunMissile>();
		private Vector<Cowboy> cowboys=new Vector<Cowboy>();
		private Vector<CowboyGunMissile> cowboymissile = new Vector<CowboyGunMissile>();
		public HitDetect( Vector<Cowboy> cowboys, Vector<GunMissile> missile, Vector<CowboyGunMissile> cowboymissile){
			this.missile=missile;
			this.cowboys=cowboys;
			this.cowboymissile= cowboymissile;
			
		}
		public void detecting(){
				for(int i = 0; i < (cowboymissile.size()); i++) {

						if(cowboymissile.get(i).getXStart() >= gun.getX() &&
							cowboymissile.get(i).getXStart() <= gun.getX() + 30 &&
							cowboymissile.get(i).getYStart() >= 385 &&
							cowboymissile.get(i).getYStart() <= 455) {
							
								removeCowboyMissile();
								gameOverLose=true;
								view.drawlingPanel.setup(gun,cowboys,getMissile(),gameStarted,paused,gameOver,gameOverLose, cowboymissile);
						}
				}
				for(int i=0;i<(missile.size()-1);i++)
				{
					for(int j=0;j<cowboys.size();j++)
					{
						reGet();
						if(missile.get(i).getXStart()>=cowboys.get(j).getX()&&
						missile.get(i).getXStart()<(cowboys.get(j).getX()+25))
						{
							if(missile.get(i).getYStart()<=cowboys.get(j).getY()&&
									missile.get(i).getYStart()>(cowboys.get(j).getY()-12))
							{
								removeMissile();
								removeAlien(j);
								reGet();
							}
						}
					}
				}
				for(int i = 0;i<cowboys.size();i++)
				{
					if((cowboys.get(i).getY()+25)>=gun.getY()){
						gameOverLose=true;
						view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboymissile);
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
		private void reGet(){
			this.cowboys=getCowboys();
			this.missile=getMissile();
		}
	}
	public int moveAliens(int round){
		int count = 1;
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
			for(int i = 0;i<cowboyCount;i++)
			{
				if(count%2==0)
				cowboys.get(i).moveDown(5);
			}
			count++;
			if(cowboys.isEmpty()&&round==1){
				missile.removeAllElements();
				cowboyCount=10;
				for(int i = 0; i<cowboyCount;i++)
				{
					cowboys.add(new Cowboy(i));
				}
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboymissile);
				round++;
				count=1;
			}
			else if(cowboys.isEmpty()&&round==2){
				cowboyCount=15;
				missile.removeAllElements();
				for(int i = 0; i<cowboyCount;i++)
				{
					cowboys.add(new Cowboy(i));
				}
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose,cowboymissile);
				round++;
				count=1;
			}
			else if(cowboys.isEmpty()&&round==3){
				cowboyCount=20;
				missile.removeAllElements();
				for(int i = 0; i<cowboyCount;i++)
				{
					cowboys.add(new Cowboy(i));
				}
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboymissile);
				round++;
				count=1;
			}
			else if(cowboys.isEmpty()&&round==4){
				cowboyCount=25;
				missile.removeAllElements();
				for(int i = 0; i<cowboyCount;i++)
				{
					cowboys.add(new Cowboy(i));
				}
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboymissile);
				round++;
				count=1;
			}
			else if(cowboys.isEmpty()&&round==5){
				gameOver=true;
				view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose, cowboymissile);
			}
		return round;
	}
	@Override
	public void run() {
		int round = 1;
		while(true){
			if(!paused&&!gameOver&&!gameOverLose){
				round=moveAliens(round);
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
						missile.remove(0);
						missile.trimToSize();
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
						view.drawlingPanel.setup(gun, cowboys, missile,gameStarted, paused,gameOver,gameOverLose, cowboymissile);
					}
					else{
						paused=false;
						view.drawlingPanel.setup(gun, cowboys, missile,gameStarted, paused,gameOver,gameOverLose, cowboymissile);
					}
				}
			}
			refresh();
		}
	}

}
