package Simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class DrawSim extends JPanel implements Runnable{
	
	//DRAWING VARIABLES
	Color DARKGREEN = new Color(56, 138, 30);
	Color LIGHTGRAY = new Color(189,189,189);
	
	int introadwidth = 41;
	int outroadwidth = 51;
	int hzlength = 650;
	int vrlength = 433;
	
	//THREAD RELATED VARIABLES
	private Thread runner = null;
	boolean simuend = false;
	
	//TIME RELATED VARIABLES
	private double time = 0.;
	private double endtime = 0.;
	private int demandtiming = 1;
	private long simTime_ms = 0;
	
	//DEMAND VARIABLES
	int hour = 0;
	int day = 0;
	
	//VARIABLES
	ArrayList<AGV> Agvforce = new ArrayList<AGV>();
	ArrayList<Truck> Truckforce = new ArrayList<Truck>();
	ArrayList<Factory> Factoryforce = new ArrayList<Factory>();
	ArrayList<CDC> CDCforce = new ArrayList<CDC>();
	
	//MAIN CONTENT
	public DrawSim(){
		System.out.println("BEEN HERE");
		
		endtime = calendtime();
		
		
		//DECLARE ALL THE VEHICLE
		for(int i=0; i < Constant.AgvQty; i++){
			Agvforce.add(new AGV());
		}
		
		for(int i=0; i < Constant.TruckQty; i++){
			Truckforce.add(new Truck());
		}		
		
	}
	
	public void paintComponent(Graphics g) {
	       super.paintComponent(g);
	       setBackground(DARKGREEN);
	       paintroad(g);
	       Clock.clockupdate(g, time);
	       
	       //AGV
	       if(Constant.AgvMode == true){
				//CALCULATE NEW LOCATION
				for(AGV x: Agvforce){
					x.drawupdate(g);
				}
	       }
			
	       //TRUCK
	       if(Constant.TruckMode == true){
				//CALCULATE NEW LOCATION
				for(Truck x: Truckforce){
					x.drawupdate(g);
				}
	       }
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

	@Override
	public void run() {
		
		//Initialize start time of simulation
		time = 0+(Constant.starthour*60*60);
		
		while (Thread.currentThread() == runner) {
			
			try {
				//ADJUST THE SPEED OF THE SIMULATOR HERE (
				//[max delay: 200 - 1 sec by 1 sec]
				//[min delay: 5 - keep the CPU load at ~50%]
				Thread.sleep(Constant.tsleep_ms);
				
			} catch (InterruptedException e) {
				;
			}
			
			long timeBeforeSim_ms = System.currentTimeMillis();
			
			//Check for end of simulation
			if(time >= endtime){
				if(simuend == false){
					Constant.suspended = true;
				}
			}

			/////////////////////////////////////////////////////////
			//SIMULATE NEW TIMESTEP (I.E. UPDATE ALL THE VARIABLES)//
			/////////////////////////////////////////////////////////
			
			
			//CENTRAL HEADQUARTER
			if((int) time%3600 == 0){
				
				if(demandtiming == 5){  //CHANGE THIS IF TIMESTEP_S IS CHANGED

					//OPERATION FROM 06:00 - 21:00, BUT LAST DEMAND AT 20:00
					if(Clock.returnhour(time) >= 6 && Clock.returnhour(time) <= 20){
						
						//GENERATE A NEW DEMAND WITH TIMING ALLOCATION						
						for(int i = 0; i < (Constant.CDCdemandh[Clock.returnhour(time)-6]); i++){
							//DETERMINE WHICH FACTORY TO DELIVER THE CARGO TO
							//IS TO TELL CDC IN THIS HOUR, THERE IS GOING TO BE THIS DEMAND AT THIS PARTICULAR TIME
							//NOT SPAWN IMMEDIATELY
							//[DELIVER LOCATION: RANDOM FACTORY (0-29) (Default)]
							
							//?????.demandcount++;
							
							//SET TIMING & DESTINATION
							//STORE TO CDC
							Clock.returnhour(time);
							Central.spawndemandtime();
							Central.spawndemandtarget();
						}
						
						for(int i = 0; i < (Constant.Factdemandh[Clock.returnhour(time)-6]); i++){
							//DETERMINE WHICH FACTORY TO SPAWN THE DEMAND
							//IS TO TELL FACTORY IN THIS HOUR, THERE IS GOING TO BE THIS DEMAND AT THIS PARTICULAR TIME
							//NOT SPAWN IMMEDIATELY
							//[DELIVER LOCATION: CDC (100) (Default)]
							//MODIFY HERE TO CHANGE THE DELIVER LOCATION
							
							//?????[Central.spawnfactdemand()].demandcount++;
							//System.out.println("Count " + i + ": " + Central.spawnfactdemand());
							
							//SET CARGO TIMING
							//STORE TO FACTORY
							Clock.returnhour(time);
							Central.spawndemandtime();
							//TARGET = 100
						}
					}
					demandtiming = 1;
				}else{
					demandtiming++;
				}
			}
			
			////////////////////////////////////////////////////////
			//CDC AND FACTORY CALL FOR VEHICLES TO CLEAR DEMAND   //
			//PRIORTY GOES TO TRUCK FIRST THEN AGV IF BOTH ENABLED//
			////////////////////////////////////////////////////////
			
			//CDC
			
			//IF THERE IS DEMAND INFORM CENTRAL
			//NORMAL OR PRIORITY QUEUE
			
			//FACTORY
			for(Factory x: Factoryforce){
				//IF THERE IS DEMAND, INFORM CENTRAL
				//NORMAL OR PRIORTY QUEUE
			}
			
			//TRUCK
			if(Constant.TruckMode == true){
				for(Truck x: Truckforce){
					//CHECK WITH CENTRAL FOR DEMAND IN NORMAL OR PRIORITY QUEUE
					
					//PLOT ROUTE TO DESTINATION
					
					//CALCULATE NEW LOCATION
				}
			}
			
			//AGV
			if(Constant.AgvMode == true){

				for(AGV x: Agvforce){
					//CHECK WITH CENTRAL FOR DEMAND IN NORMAL OR PRIORITY QUEUE
					
					//PLOT ROUTE TO DESTINATION
					
					//CALCULATE NEW LOCATION
				}
			}
			
			//FOR CALCULATING THE TIME TAKEN TO DO ALL THE SIMULATION CALCULATIONS
			//SIMTIMES_MS SHOULD ALWAYS BE SMALLER THAN TIMESTEP_S
			simTime_ms = (long) (System.currentTimeMillis() - timeBeforeSim_ms);
			//System.out.println(simTime_ms);
			
			//CALCULATE THE NEW TIME BASED ON THE TIME SPEED
			time+=Constant.TIMESTEP_S;
			//repaint();
			
			//FOR SUSPENSION OF THREAD
			synchronized (this) {
				while (Constant.suspended){
					try {
						wait();
					} catch (InterruptedException e) {
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
		Constant.suspended = true;
	}
	   
	public synchronized void resume() {
		System.out.println("Simulator resuming...");
		Constant.suspended = false;
		notify();
	}
}
