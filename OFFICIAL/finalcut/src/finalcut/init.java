package finalcut;

import java.awt.EventQueue;

import javax.swing.UIManager;
/**
 * 
 * @author Sim Hong Xun
 *
 */
public class init {

	public static void main(String[] args){
		
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch(Exception e) {
	        System.out.println("Error setting native LAF: " + e);
	    }
		
		EventQueue.invokeLater(new Runnable() {
			public void run(){
			try{
				//init Splash Screen;
				//Thread.sleep(1000);
			}catch (Exception e){
				e.printStackTrace();
			}
			
			//init Preset Mode Windpw here
			Preset frame = new Preset();
			frame.setVisible(true);
			}
		});
	}
}
