package jw.practice.game.plane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author Amber
 *
 */
public class MyGameFrame  extends  Frame {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Plane plane = new Plane(Constant.PLANE_IMG,250,250);
	ArrayList<Shell> shellList = new ArrayList<Shell>();
	
	Explode exp;
	Date  startTime = new Date();
	Date  endTime;
	int playTime; 
	boolean pause = false; 
	
	public void startOver() {
		shellList = new ArrayList<Shell>();
		startTime = new Date();
		
	}
	
	public void cleanUp() {
		plane = new Plane(Constant.PLANE_IMG,250,250);
		shellList.clear();
		endTime =null;
		playTime = 0;
		exp = null;
	}
	@Override
	public void paint(Graphics g) {		
		Color   c =  g.getColor();
		g.drawImage(Constant.BACKGROUND_IMG, 0, 0, null);
		if(shellList.isEmpty()) {
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			g.drawString("GAME START", 100, 250);
		}
		plane.drawSelf(g); 
		
		for(Shell s:shellList){
			s.drawSelf(g);
			
			if(s.getRect().intersects(plane.getRect())){
				plane.live = false;
				if(exp ==null){
					exp  = new Explode(plane.x, plane.y);
					
					endTime = new Date();
					playTime = (int)((endTime.getTime()-startTime.getTime())/1000);
				}
				exp.draw(g);
			}
			
			if(!plane.live){
				g.setColor(Color.LIGHT_GRAY);
				Font f = new Font("TimesRoman", Font.BOLD, 25);
				g.setFont(f);
				g.drawString("You have played "+playTime+" seconds", 50, 250);
			}
			
		}
		
		g.setColor(c);
	}
	
	

	class  PaintThread  extends  Thread  {
		@Override
		public void run() {
			while(!pause){
				repaint();		
				
				try {
					Thread.sleep(40);   	//1s=1000ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
		}
		
	}
	
	class   KeyMonitor extends  KeyAdapter  {

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				cleanUp();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				pause = !pause;
				if(!pause) {
					new PaintThread().start();	 
				}
			}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				startOver();
			}
		}
		
		
	}
	
	
	
	public  void  launchFrame(){
		this.setTitle("Plane game practice");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(300, 300);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		new PaintThread().start();	
		addKeyListener(new KeyMonitor());  
		addShellList(); 
		
	}
	
	public void addShellList() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				shellList.add(new Shell());
				if(shellList.size() == 30) {
					timer.cancel();
				}
				
			}
		};
		timer.schedule(task, 1000L, 1000L);
				
	}
	public static void main(String[] args) {
		MyGameFrame  f = new MyGameFrame();
		f.launchFrame();
	}
	
	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}
	
}
