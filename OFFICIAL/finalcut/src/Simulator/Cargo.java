package Simulator;

public class Cargo {

	int spawnhour = 0;
	int spawnmin = 0;
	int destination = 0;
	
	Cargo(){
		
	}
	
	Cargo(int h, int m, int dest){
		this.spawnhour = h;
		this.spawnmin = m;
		this.destination = dest;
	}
}
