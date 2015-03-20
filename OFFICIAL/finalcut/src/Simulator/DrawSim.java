package Simulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawSim extends JPanel implements Runnable{
	
	//DRAW VARIABLES
	Color DARKGREEN = new Color(56, 138, 30);
	Color LIGHTGRAY = new Color(189,189,189);
	
	int introadwidth = 41;
	int outroadwidth = 51;
	int hzlength = 650;
	int vrlength = 433;
	
	
	//THREAD RELATED VARIABLES
	boolean suspended = false;
	
	//TIME RELATED VARIABLES
	private double time = 0.;
	private double endtime = 0.;
	
	private Thread runner = null;
	boolean simuend = false;
	
	//MAIN CONTENT
	public DrawSim(){
		System.out.println("BEEN HERE");
		
		endtime = calendtime();
		//DECLARE ALL THE VEHICLE FACTORY 
		
		
		
		
		
		
	}
	
	public void paintComponent(Graphics g) {
	       super.paintComponent(g);
	       setBackground(DARKGREEN);


	       paintroad(g);
	       clockupdate(g);
	}

	private void paintroad(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Vertical
		//g.fillRect(200, 140, 40, 400);
		//g.fillRect(140, 140, 40, 400);
		
		//OUTER
		g2.setColor(LIGHTGRAY);
		g2.fillRect(200, 40, hzlength, outroadwidth);
		g2.fillRect(200, 40+60, hzlength, outroadwidth);
		
		g2.fillRect(85, 40+115, outroadwidth, vrlength);
		g2.fillRect(85+60, 40+115, outroadwidth, vrlength);
		
		
		
		/************/
		//INNER
		g2.setColor(Color.gray);
		g2.fillRect(200, 45, hzlength, introadwidth);
		g2.fillRect(200, 45+60, hzlength, introadwidth);
		
		g2.fillRect(90, 40+115, introadwidth, vrlength);
		g2.fillRect(90+60, 40+115, introadwidth, vrlength);
		
		
		/************/
		
		//DIVIDER
		g2.setColor(Color.white);
		g2.drawLine(200, 66, 200+hzlength, 66);
		g2.drawLine(200, 66+59, 200+hzlength, 66+59);
		g2.drawLine(85+25, 40+115, 85+25, 40+115+vrlength);
		g2.drawLine(85+51+9+25, 40+115, 85+51+9+25, 40+115+vrlength);
		
		
		
		//RIGHT TOP CURVE
		g2.setColor(LIGHTGRAY);
		g2.fillArc(200+hzlength-outroadwidth-5, 40, 111, 111, 270, 180);		
		g2.setColor(Color.gray);
		g2.fillArc(200+hzlength-outroadwidth-1, 40+5, 101, 101, 270, 180);
		g2.setColor(Color.white);
		g2.drawArc(200+hzlength-outroadwidth+20, 40+26, 59, 59, 270, 180);
		g2.setColor(LIGHTGRAY);
		g2.fillArc(200+hzlength-12, 40+46, 19, 19, 270, 180);
		g2.setColor(DARKGREEN);
		g2.fillArc(200+hzlength-8, 40+51, 9, 9, 270, 180);
		
		//LEFT TOP CURVE
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85, 40, 115*2, 115*2, 90, 90); //1
		g2.setColor(Color.gray);
		g2.fillArc(85+5, 40+5, (115-5)*2, (115-5)*2, 90, 90);//2
		g2.setColor(Color.white);
		g2.drawArc(85+25, 40+25+1, (115-25)*2, (115-25)*2, 90, 90);
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85+46, 40+46, (115-46)*2, (115-46)*2, 90, 90); //3
		g2.setColor(DARKGREEN);
		g2.fillArc(85+51, 40+51, (115-51)*2, (115-51)*2, 90, 90); //4
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85+60, 40+60, (115-60)*2, (115-60)*2, 90, 90);
		g2.setColor(Color.gray);
		g2.fillArc(85+65, 40+65, (115-65)*2, (115-65)*2, 90, 90);
		g2.setColor(Color.white);
		g2.drawArc(85+85, 40+85, (115-85)*2, (115-85)*2, 90, 90);
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85+106, 40+106, (115-106)*2, (115-106)*2, 90, 90);
		g2.setColor(DARKGREEN);
		g2.fillArc(85+111, 40+111, (115-111)*2, (115-111)*2, 90, 90);
		
		//LEFT BOTTOM CURVE
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85, 40+115+vrlength-56, 111, 111, 180, 180);		
		g2.setColor(Color.gray);
		g2.fillArc(85+5, 40+115+vrlength-52, 101, 101, 180, 180);
		g2.setColor(Color.white);
		g2.drawArc(85+5+20, 40+115+vrlength-32, 59+1, 59, 180, 180);
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85+5+20+21, 40+115+vrlength-12, 19, 19, 180, 180);
		g2.setColor(DARKGREEN);
		g2.fillArc(85+5+20+21+5, 40+115+vrlength-8, 9, 9, 180, 180);
		
	}

	private double calendtime() {
		double temp = (Constant.endday * 24 * 60 * 60) + (Constant.endhour * 60 * 60) - (Constant.starthour * 60 * 60);
		
		return temp;
	}

	private void clockupdate(Graphics g) {
		// TODO Auto-generated method stub
		int day, hour, min, sec;
		int x0 = 10; // start position of time display (left upper corner)
		int y0 = 20;

		min = (int) (time / 60.0);
		sec = (int) (time - min * 60.0);
		
		hour = (int) (min / 60.0);
		min = (int) (min - hour * 60.0);
		
		day = (int) (hour / 24.0);
		hour = (int) (hour - day * 24.0);
		
		String str_time = "Day / Time: ";
		String timeString = "DAY " + (new Integer(day)).toString()
				+ ((hour > 9) ? " / " : " / 0") +(new Integer(hour)).toString()
				+ ((min > 9) ? ":" : ":0") +(new Integer(min)).toString()
				+ ((sec > 9) ? ":" : ":0") + (new Integer(sec)).toString();

		g.setColor(Color.BLACK);
		// g.setColor(Color.white);

		// display new time
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.black);
		g.drawString(str_time, x0, y0);
		
		x0 = 10; // start position of time display (left upper corner)
		y0 = 40;
		g.drawString(timeString, x0, y0);
	}


	@Override
	public void run() {
		
		//Initialize start time of simulation
		time = 0;
		
		while (Thread.currentThread() == runner) {
			
			try {
				//ADJUST THE SPEED OF THE SIMULATOR HERE (
				//[max delay: 200 - 1 sec by 1 sec]
				//[min delay: 5 - keep the CPU load at ~50%]
				Thread.sleep(Constant.tsleep_ms);
				
			} catch (InterruptedException e) {
				;
			}
			
			//Check for end of simulation
			if(time >= endtime){
				if(simuend == false){
					suspended = true;
				}
			}

			//SIMULATE NEW TIMESTEP (IE UPDATE ALL THE VARIABLES)
			if(Result.active > 962){
				Result.active = 0;
			}
			Result.active++;
			//System.out.println(Result.active);
			
			//CALCULATE THE NEW TIME BASED ON THE TIME SPEED
			time+=Constant.TIMESTEP_S;
			repaint();
			
			//FOR SUSPENSION OF THREAD
			synchronized (this) {
				while (suspended){
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
		}
		
	}
	
	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.setName("Simulator");
			runner.start();
		}
	} 
	
	public void stop() {
		runner = null;
	} 
	
	public void suspend() {
		suspended = true;
	}
	   
	public synchronized void resume() {
		System.out.println("Resume");
		suspended = false;
		notify();
	}
	
}
