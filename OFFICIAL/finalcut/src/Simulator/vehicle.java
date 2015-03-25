package Simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
/**
 * 
 * @author Sim Hong Xun
 *
 */
@SuppressWarnings("unused")
public class Vehicle {

	Point position = new Point(10,10);
	private boolean mode = false;
	private boolean cargo = false;
	
	private double loadingclock = .0;
	//Time in which vehicle enter loading bay
	
	private int PreviousPhase = 0;
	private int Phase = 0;
	/*
	Phase 0: Idle
	Phase 1: Driving to factory empty (to pick up cargo)
	Phase 2: Driving to CDC w cargo
	Phase 3: Driving to factory w cargo
	Phase 4: Driving to CDC empty
	Phase 5: Driving to CDC on order
	phase 6: Loading/Unloading
	*/
	
	private int destination = 100;
	/*
	CDC = 100
	Factory = [0,29]
	No destination = 99
	 */
	
	private double fuel = .0;
	
	private int lane = 0;
	private int zone = 8;
	private double theta = 0;
	
	Vehicle(){
		position = new Point(10,10);
		mode = false;
		cargo = false;
		loadingclock = .0;
		Phase = 0;
		destination = 100;
		lane = 0;
		zone = 0;
		theta = 0;
	}
	
	void enterCDC(){
		
	}
	
	void enterfact(){
		
	}
	
	double getpositionx(){
		return position.getX();
	}
	
	double getpositiony(){
		return position.getY();
	}
	
	void addfuel(double x){
		fuel = fuel + x;
	}
	
	double getfuel(){
		return fuel;
	}
	
	void setlane(int i){
		lane= i;
	}
	
	int getlane(){
		return lane;
	}
	
	void setzone(int i){
		Phase = i;
	}
	
	int getzone(){
		return zone;
	}
	
	void setphase(int i){
		Phase = i;
	}
	
	int getphase(){
		return Phase;
	}
	
	void setpreviousphase(int i){
		PreviousPhase = i;
	}
	
	int getpreviousphase(){
		return PreviousPhase;
	}
	
	void setdestination(int i){
		destination = i;
	}
	
	int getdestination(){
		return destination;
	}
	
	int calculatedisttocdc(){
		int distance = 0;
		
		if(zone ==5){
				distance = (int) ((850-position.x) + ((2*Math.PI*findradius(6))/2) + 650 + ((2*Math.PI*findradius(8))/4) + 285);
		}
		
		if(zone ==6){
				distance = (int) (((2*Math.PI*findradius(6))/2)*((theta-((3/2)*Math.PI))/Math.PI) + 650 + ((2*Math.PI*findradius(8))/4) + 285);
		}
		
		if(zone ==7){
				distance = (int) ((position.x-200) + ((2*Math.PI*19)/4) + 285);
		}
		
		if(zone ==8){
				distance = (int) (((2*Math.PI*findradius(8))/4)*((Math.PI/2)-theta) + 285);
		}
		
		if(zone ==0){
				distance = (int) (440-position.y);
		}
		
		return distance;
	}
	
	int calculatedisttofact(int target){
		int distance = 0;
		return distance;
		
	}
	
	double findradius(int zone){
		double radius = 0;
		if(zone == 6){
			radius = Math.sqrt( (position.x - 850)^2 + (position.y - 96)^2 );
		}
		if(zone == 8){
			radius = Math.sqrt( (position.x - 200)^2 + (position.y - 155)^2 );
		}
		return radius;
	}
	//FOR SETTING WAITING TIME 
	void setloadingclock(double time, char type){
		if(type == 'c'){
			loadingclock = time + (Constant.CDCcargoturnover * 60);
		}
		if(type == 'f'){
			loadingclock = time + (Constant.Factcargoturnover * 60);
		}
	}
		
	double getloadingclock(){
		return loadingclock;
	}
	
	//FOR DRAWING THE TRUCK AND AGV IMAGE
	void drawupdate(Graphics g, char type){
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(mode == true){
			BufferedImage image = null;
			try {
				if(type == 't'){
					if(cargo==false){
						image = ImageIO.read(new File ("src/misc/Truck.gif"));
					}
					if(cargo==true){
						image = ImageIO.read(new File ("src/misc/Truckloaded.gif"));
					}
				}
				if(type == 'a'){
					if(cargo==false){
						image = ImageIO.read(new File ("src/misc/AGV.gif"));
					}
					if(cargo==true){
						image = ImageIO.read(new File ("src/misc/AGVloaded.gif"));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			AffineTransform at = new AffineTransform();
	
			at.translate(position.x, position.y);
			at.rotate(theta);
	        at.translate(-image.getWidth()/2, -image.getHeight()/2);
	        at.scale(0.5, 0.5);
	        g2.drawImage(image, at, null);
		}
	}
}
