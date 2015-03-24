package Simulator;


import java.util.PriorityQueue;
/**
 * 
 * @author Sim Hong Xun
 *
 */
public class Building {

	private int minutedemand[] = new int[60];
	
	Building(){
		minutedemand = new int[60];
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
