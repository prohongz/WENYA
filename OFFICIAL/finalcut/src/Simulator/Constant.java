package Simulator;

/**Constant is used to store intermediate values between the transition from Preset interface to Simulator interface.
 * 
 * @author Sim Hong Xun
 *
 */
public class Constant {

	public static int starthour =0;
	public static int startminute =0;
	
	public static int endday =0;
	
	public static int endhour =0;
	public static int endminute =0;
	
	public static int CDCstarth =0;
	public static int CDCstartm =0;
	public static int CDCendh =0;
	public static int CDCendm =0;
	public static int CDCcargoturnover =0;
	public static int CDCcargolimit =0;
	public static int CDCbay =0;
	public static int CDCdemandh[] = new int[15];
	
	
	public static int Factstarth =0;
	public static int Factstartm =0;
	public static int Factendh =0;
	public static int Factendm =0;
	public static int Factcargoturnover =0;
	public static int Factcargolimit =0;
	public static int Factbay =0;
	public static int Factdemandh[] = new int[15];
	
	
	
	
	public static boolean TruckMode = false;
	public static int TruckQty = 0;
	public static double TruckSpd = .0;
	public static double TruckAccSpd = .0;
	public static double TruckDecSpd = .0;
	public static double TruckTimeGap = .0;
	public static double TruckDistGap = .0;
	public static double TruckPoliteness = .0;
	public static double TruckChanging = .0;
	
	public static boolean AgvMode = false;
	public static int AgvQty = 0;
	public static double AgvSpd = .0;
	public static double AgvAccSpd = .0;
	public static double AgvDecSpd = .0;
	public static double AgvTimeGap = .0;
	public static double AgvDistGap = .0;
	
	static final double TIMESTEP_S = 0.2;   // simulation time step
	public static int tsleep_ms = 20;
	
	static boolean suspended = false;
	
}
