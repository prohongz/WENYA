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
	
	
	Vehicle(){
		position = new Point(10,10);
		mode = false;
		cargo = false;
		loadingclock = .0;
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
	
			at.translate(position.getX(), position.getY());
			//at.rotate(Math.PI/4);
	        at.translate(-image.getWidth()/2, -image.getHeight()/2);
	        at.scale(0.5, 0.5);
	        g2.drawImage(image, at, null);
		}
	}
}
