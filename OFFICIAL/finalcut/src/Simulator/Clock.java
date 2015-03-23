package Simulator;

public class Clock {

	static double time = 0.;
	double endtime = 0.;
	static int day=0, hour=0, min=0, sec=0;
	
	Clock(){
		;
	}
	
	public static void clockupdate() {
		// TODO Auto-generated method stub

		min = (int) (time / 60.0);
		sec = (int) (time - min * 60.0);
		
		hour = (int) (min / 60.0);
		min = (int) (min - hour * 60.0);
		
		day = (int) (hour / 24.0);
		hour = (int) (hour - day * 24.0);
	}
	
	public static int returnhour(double t){

		min = (int) (t / 60.0);
		sec = (int) (t - min * 60.0);
		
		hour = (int) (min / 60.0);
		min = (int) (min - hour * 60.0);
		
		day = (int) (hour / 24.0);
		return hour = (int) (hour - day * 24.0);
	}
	
	public int returnminute(double t){

		min = (int) (t / 60.0);
		sec = (int) (t - min * 60.0);
		
		hour = (int) (min / 60.0);
		return min = (int) (min - hour * 60.0);
	}
	
	public static double calendtime() {
		double temp = (Constant.endday * 24 * 60 * 60) + (Constant.endhour * 60 * 60);
		
		return temp;
	}
}
