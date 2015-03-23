package Simulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings({"serial", "unused"})
public class DrawSim extends JPanel implements Runnable{
	
	//DRAWING VARIABLES
	Color DARKGREEN = new Color(56, 138, 30);
	Color LIGHTGRAY = new Color(189,189,189);
	
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
		
		endtime = Clock.calendtime();
		
		
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
	       Painting.paintroad(g);
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
	       
	       Painting.paintfactoryqueue(g);
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
