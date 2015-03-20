package Simulator;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Result {

	//Temporary Storage Options
	public static int active = 0;
	public static int inactive = 0;
	
	public static int cargoserve = 0;
	public static int cargounserve = 0;
	
	public static int cargocount = 0;
	
	//For Offline Usage
	public static int starthour = 0;
	public static int startminute = 0;
	
	public static int endday = 0;
	
	public static int endhour = 0;
	public static int endminute = 0;
	
	public ArrayList active15 = new ArrayList();
	ArrayList active30 = new ArrayList();
	ArrayList active60 = new ArrayList();
	ArrayList active24 = new ArrayList();
	
	ArrayList inactive15 = new ArrayList();
	ArrayList inactive30 = new ArrayList();
	ArrayList inactive60 = new ArrayList();
	ArrayList inactive24 = new ArrayList();
	
	ArrayList cargoserve15 = new ArrayList();
	ArrayList cargoserve30 = new ArrayList();
	ArrayList cargoserve60 = new ArrayList();
	ArrayList cargoserve24 = new ArrayList();

	ArrayList cargounserve15 = new ArrayList();
	ArrayList cargounserve30 = new ArrayList();
	ArrayList cargounserve60 = new ArrayList();
	ArrayList cargounserve24 = new ArrayList();
	
	ArrayList cargocount15 = new ArrayList();
	ArrayList cargocount30 = new ArrayList();
	ArrayList cargocount60 = new ArrayList();
	ArrayList cargocount24 = new ArrayList();
}
