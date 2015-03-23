package Simulator;

import java.util.ArrayList;
/**
 * 
 * @author Sim Hong Xun
 *
 */
public class Result {
	
	//For Offline Usage
	public static int starthour = 0;
	public static int startminute = 0;
	
	public static int endday = 0;
	
	public static int endhour = 0;
	public static int endminute = 0;
	
	static ArrayList<Integer> truckactive15 = new ArrayList<Integer>();
	static ArrayList<Integer> truckactive30 = new ArrayList<Integer>();
	static ArrayList<Integer> truckactive60 = new ArrayList<Integer>();
	static ArrayList<Integer> truckactive24 = new ArrayList<Integer>();
	
	static ArrayList<Integer> agvactive15 = new ArrayList<Integer>();
	static ArrayList<Integer> agvactive30 = new ArrayList<Integer>();
	static ArrayList<Integer> agvactive60 = new ArrayList<Integer>();
	static ArrayList<Integer> agvactive24 = new ArrayList<Integer>();
	
	static ArrayList<Integer> truckinactive15 = new ArrayList<Integer>();
	static ArrayList<Integer> truckinactive30 = new ArrayList<Integer>();
	static ArrayList<Integer> truckinactive60 = new ArrayList<Integer>();
	static ArrayList<Integer> truckinactive24 = new ArrayList<Integer>();
	
	static ArrayList<Integer> agvinactive15 = new ArrayList<Integer>();
	static ArrayList<Integer> agvinactive30 = new ArrayList<Integer>();
	static ArrayList<Integer> agvinactive60 = new ArrayList<Integer>();
	static ArrayList<Integer> agvinactive24 = new ArrayList<Integer>();
	
	static ArrayList<Integer> cdccargoserve15 = new ArrayList<Integer>();
	static ArrayList<Integer> cdccargoserve30 = new ArrayList<Integer>();
	static ArrayList<Integer> cdccargoserve60 = new ArrayList<Integer>();
	static ArrayList<Integer> cdccargoserve24 = new ArrayList<Integer>();

	static ArrayList<Integer> cdccargounserve15 = new ArrayList<Integer>();
	static ArrayList<Integer> cdccargounserve30 = new ArrayList<Integer>();
	static ArrayList<Integer> cdccargounserve60 = new ArrayList<Integer>();
	static ArrayList<Integer> cdccargounserve24 = new ArrayList<Integer>();
	
	static ArrayList<Integer> factcargoserve15 = new ArrayList<Integer>();
	static ArrayList<Integer> factcargoserve30 = new ArrayList<Integer>();
	static ArrayList<Integer> factcargoserve60 = new ArrayList<Integer>();
	static ArrayList<Integer> factcargoserve24 = new ArrayList<Integer>();

	static ArrayList<Integer> factcargounserve15 = new ArrayList<Integer>();
	static ArrayList<Integer> factcargounserve30 = new ArrayList<Integer>();
	static ArrayList<Integer> factcargounserve60 = new ArrayList<Integer>();
	static ArrayList<Integer> factcargounserve24 = new ArrayList<Integer>();
	
	static ArrayList<Integer> totalcargoserve15 = new ArrayList<Integer>();
	static ArrayList<Integer> totalcargoserve30 = new ArrayList<Integer>();
	static ArrayList<Integer> totalcargoserve60 = new ArrayList<Integer>();
	static ArrayList<Integer> totalcargoserve24 = new ArrayList<Integer>();

	static ArrayList<Integer> totalcargounserve15 = new ArrayList<Integer>();
	static ArrayList<Integer> totalcargounserve30 = new ArrayList<Integer>();
	static ArrayList<Integer> totalcargounserve60 = new ArrayList<Integer>();
	static ArrayList<Integer> totalcargounserve24 = new ArrayList<Integer>();
	
	static ArrayList<Integer> totalcargocount15 = new ArrayList<Integer>();
	static ArrayList<Integer> totalcargocount30 = new ArrayList<Integer>();
	static ArrayList<Integer> totalcargocount60 = new ArrayList<Integer>();
	static ArrayList<Integer> totalcargocount24 = new ArrayList<Integer>();
	
	public static void savestate(int i){
		if(i==15){
			truckactive15.add(Central.truckactive);
			truckinactive15.add(Central.truckinactive);
			
			agvactive15.add(Central.agvactive);
			agvinactive15.add(Central.agvinactive);
			
			cdccargoserve15.add(Central.cdccargoserve);
			cdccargounserve15.add(Central.cdccargounserve);
			
			factcargoserve15.add(Central.factcargoserve);
			factcargounserve15.add(Central.factcargounserve);
			
			totalcargoserve15.add(Central.totalcargoserve);
			totalcargounserve15.add(Central.totalcargounserve);
			totalcargocount15.add(Central.totalcargoserve + Central.totalcargounserve);
		}
		
		if(i==30){
			truckactive30.add(Central.truckactive);
			truckinactive30.add(Central.truckinactive);
			
			agvactive30.add(Central.agvactive);
			agvinactive30.add(Central.agvinactive);
			
			cdccargoserve30.add(Central.cdccargoserve);
			cdccargounserve30.add(Central.cdccargounserve);
			
			factcargoserve30.add(Central.factcargoserve);
			factcargounserve30.add(Central.factcargounserve);
			
			totalcargoserve30.add(Central.totalcargoserve);
			totalcargounserve30.add(Central.totalcargounserve);
			totalcargocount30.add(Central.totalcargoserve + Central.totalcargounserve);
		}
		
		if(i==60){
			truckactive60.add(Central.truckactive);
			truckinactive60.add(Central.truckinactive);
			
			agvactive60.add(Central.agvactive);
			agvinactive60.add(Central.agvinactive);
			
			cdccargoserve60.add(Central.cdccargoserve);
			cdccargounserve60.add(Central.cdccargounserve);
			
			factcargoserve60.add(Central.factcargoserve);
			factcargounserve60.add(Central.factcargounserve);
			
			totalcargoserve60.add(Central.totalcargoserve);
			totalcargounserve60.add(Central.totalcargounserve);
			totalcargocount60.add(Central.totalcargoserve + Central.totalcargounserve);
		}
		
		if(i==24){
			truckactive24.add(Central.truckactive);
			truckinactive24.add(Central.truckinactive);
			
			agvactive24.add(Central.agvactive);
			agvinactive24.add(Central.agvinactive);
			
			cdccargoserve24.add(Central.cdccargoserve);
			cdccargounserve24.add(Central.cdccargounserve);
			
			factcargoserve24.add(Central.factcargoserve);
			factcargounserve24.add(Central.factcargounserve);
			
			totalcargoserve24.add(Central.totalcargoserve);
			totalcargounserve24.add(Central.totalcargounserve);
			totalcargocount24.add(Central.totalcargoserve + Central.totalcargounserve);
		}
		
	}
}
