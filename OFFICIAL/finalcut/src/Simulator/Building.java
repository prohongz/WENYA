package Simulator;

import java.awt.Point;

/**
 * 
 * @author Sim Hong Xun
 *
 */
public class Building {

	private int minutedemand[] = new int[60];
	private int normalqueue = 0;
	private int priorityqueue = 0;
	private Point location;
	
	Building(){
		minutedemand = new int[60];
		normalqueue = 0;
		priorityqueue = 0;
		location = new Point(0,0);
	}
	
	void setlocation(Point x){
		location = x;
	}
	
	Point getlocation(){
		return location;
	}
	
	void addnormalqueue(){
		normalqueue++;
	}
	
	void subnormalqueue(){
		normalqueue--;
	}
	
	int getnormalqueue(){
		return normalqueue;
	}
	
	void addpriorityqueue(){
		priorityqueue++;
	}
	
	void subpriorityqueue(){
		priorityqueue--;
	}
	
	int getpriorityqueue(){
		return priorityqueue;
	}
	
	void addminutedemand(int minute){
		minutedemand[minute]++;
	}
	
	void subminutedemand(int minute){
		minutedemand[minute]--;
	}
	
	int getminutedemand(int minute){
		return minutedemand[minute];
	}
	
}
