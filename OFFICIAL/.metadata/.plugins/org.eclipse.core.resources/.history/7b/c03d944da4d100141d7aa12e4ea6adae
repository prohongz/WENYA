package Simulator;

import java.util.Random;

/**CENTRAL CLASS ACTS LIKE A HEADQUARTER CONTAIN ALL THE INFORMATION FOR VEHICLES, FACTORIES, CDC
 * 
 * @author Sim Hong Xun
 * 
 * */

public class Central {

	public static int cdcdemandcount = 0;
	public static int[] factdemandcount = new int[30];
	public static int cdcprioritycount = 0;
	public static int[] factprioritycount = new int[30];
	
	public static int[][] factdock = new int[30][Constant.Factbay];
	public static int[] cdcdock = new int[Constant.CDCbay];
	
	public Central(){
		
	}
	
	static int spawnfactdemand(){
		Random randomgen = new Random();
		int factorynumber = randomgen.nextInt(30);
		return factorynumber;
	}
	
	static int spawndemandtarget(){
		Random randomgen = new Random();
		int factorynumber = randomgen.nextInt(30);
		return factorynumber;
	}
	
	static int spawndemandtime(){
		Random randomgen = new Random();
		int demandtime = randomgen.nextInt(60);
		return demandtime;
	}
}
