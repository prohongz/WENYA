package Simulator;

/**
 * 
 * @author Sim Hong Xun
 *
 */
public class Factory extends Building{

	private int factorynumber = 0;
	
	
	Factory(int number){
		super();
		factorynumber = number;
	}
	
	public int getfactorynumber(){
		return factorynumber;
	}
}
