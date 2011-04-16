import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;



public class Controller implements Runnable {
	private View view;
	private Gun gun;
	private HitDetect detect;
	private Thread runCowboys;
	private Thread hitDetect;
	private int cowboyCount;
	private Vector<GunMissile> missile = new Vector<GunMissile>();
	private Vector<Cowboy> cowboys=new Vector<Cowboy>();
	private boolean gameStarted = false;
	private boolean paused = false;
	private boolean gameOver = false;
	private boolean gameOverLose = false;
	
	public Controller(View view)
	{
		this.view=view;
		gun = new Gun();
		cowboyCount=5;
		for(int i = 0; i<cowboyCount;i++)
		{
			cowboys.add(new Cowboy(i));
		}
		setupGame();
		setupListeners();
		runCowboys = new Thread(this);
		detect = new HitDetect(cowboys, missile);
		hitDetect = new Thread(detect);
		
		refresh();
	}
	void setupGame(){
		view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose);
	}
	void refresh(){
		view.drawlingPanel.refresh();
	}
	private void setupListeners(){
		view.addKeyListener(new MyKeyListener());
	}
	
	public void removeAlien(int x){
		cowboys.remove(x);
		cowboyCount--;
	}
	public void removeMissile(){
		missile.remove(0);
		missile.trimToSize();
	}
	public Vector<GunMissile> getMissile(){
		return missile;
	}
	public Vector<Cowboy> getCowboys(){
		return cowboys;
	}
	
	public class HitDetect implements Runnable{
		
		private Vector<GunMissile> missile = new Vector<GunMissile>();
		private Vector<Cowboy> cowboys=new Vector<Cowboy>();
		public HitDetect( Vector<Cowboy> cowboys, Vector<GunMissile> missile){
			this.missile=missile;
			this.cowboys=cowboys;
			
		}
		@Override
		public void run() {
			while(true){
				if(!paused)
				{
					//System.out.println("Hit Detect");
					for(int i=0;i<(missile.size()-1);i++)
					{
						//System.out.println("missile.size()="+missile.size());
						for(int j=0;j<cowboys.size();j++)
						{
							reGet();
							//System.out.println("i="+i);
							//System.out.println("j="+j);
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
							//System.out.println("cowboys.get(i).get(Y)="+cowboys.get(i).getY()+"gun.getY()="+gun.getY());
							gameOverLose=true;
							view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose);
						}
						
					}
				}
			}
		}
		private void reGet(){
			this.cowboys=getCowboys();
			this.missile=getMissile();
		}
	}
	
	@Override
	public void run() {
		int count=1;
		int round=1;
		while(true)
		{
			//System.out.println(paused);
				for(int j=0;j<60;j++)
				{	
					for(int i = 0;i<cowboyCount;i++)
					{
					//	System.out.println("count="+count+"\ncount%2="+count%2);
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
				//System.out.println(cowboys.isEmpty());
				if(cowboys.isEmpty()&&round==1){
					missile.removeAllElements();
					cowboyCount=10;
					for(int i = 0; i<cowboyCount;i++)
					{
						cowboys.add(new Cowboy(i));
					}
					view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose);
					round++;
				}
				else if(cowboys.isEmpty()&&round==2){
					cowboyCount=15;
					missile.removeAllElements();
					for(int i = 0; i<cowboyCount;i++)
					{
						cowboys.add(new Cowboy(i));
					}
					view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose);
					round++;
				}
				else if(cowboys.isEmpty()&&round==3){
					cowboyCount=20;
					missile.removeAllElements();
					for(int i = 0; i<cowboyCount;i++)
					{
						cowboys.add(new Cowboy(i));
					}
					view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose);
					round++;
				}
				else if(cowboys.isEmpty()&&round==4){
					cowboyCount=25;
					missile.removeAllElements();
					for(int i = 0; i<cowboyCount;i++)
					{
						cowboys.add(new Cowboy(i));
					}
					view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose);
					round++;
				}
				else if(cowboys.isEmpty()&&round==5){
					gameOver=true;
					view.drawlingPanel.setup(gun,cowboys,missile,gameStarted,paused,gameOver,gameOverLose);
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
					//System.out.println("gameStarted="+gameStarted);
				}
			}
			else if (keyCode == KeyEvent.VK_ESCAPE)
			{
				if(gameStarted)
				{
					if(paused==false){
						System.out.println("paused set TRUE");
						paused=true;
						view.drawlingPanel.setup(gun, cowboys, missile,gameStarted, paused,gameOver,gameOverLose);
					}
					else{
						System.out.println("paused set FALSE");
						paused=false;
						//missile.removeAllElements();
						view.drawlingPanel.setup(gun, cowboys, missile,gameStarted, paused,gameOver,gameOverLose);
					}
				}
			}
			refresh();
		}
	}

}
