package Simulator;

import java.util.Random;

public class Central {

	Central(){
		
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
