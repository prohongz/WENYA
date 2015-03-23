package finalcut;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Toolkit;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Sim Hong Xun
 *
 */
@SuppressWarnings("serial")
public class Preset extends JFrame {
	
	byte[] bs = new byte[32];
	String name[] = new String[100];
	int presentcount = 0;

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Preset() {
		setTitle("WIS Mode Selector");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Preset.class.getResource("/misc/Icon.gif")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150);
		Toolkit t = Toolkit.getDefaultToolkit();
        int x = (int)((t.getScreenSize().getWidth() - getWidth()) / 2);
        int y = (int)((t.getScreenSize().getHeight() - getHeight()) / 2);
        setLocation(x, y);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
		);
		
		
		//i is the number of preset in the txt file 'Preset'
		presentcount = loadpreset();
		//System.out.print("There is/are " + i + " preset(s).\n");
		
		JLabel lblSelectTheFollowing = new JLabel("Select one of the presets and press OK to continue:");
		
		JComboBox comboboxMode = new JComboBox();
		for(int k=0; k<presentcount; k++){
			comboboxMode.addItem(name[k]);
		}
		comboboxMode.addItem("Custom");
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboboxMode.getSelectedIndex() == presentcount){
					System.out.println("Setting custom preset");
					setVisible(false);
					dispose();
					
					//Promoter w instruction
					JOptionPane.showMessageDialog(null, "Please select the parameters."
							+ "\nPress 'Run' to start the Simulation.");
					
					//Start Customizer
					StartUI frame = new StartUI();
					frame.setVisible(true);
				}else{
					System.out.println("Setting Preset \"" + comboboxMode.getSelectedItem() + "\"/n");
					
					//Preset the CONSTANT CLASS
					
					
					
					
					
					
					
					//KILL CURRRENT WINDOW AND CALL FOR THE NEXT WINDOW
					//setVisible(false);
					dispose();
					SimulatorWindow frame = new SimulatorWindow();
					frame.setVisible(true);	
				}
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(comboboxMode, 0, 252, Short.MAX_VALUE)
								.addComponent(lblSelectTheFollowing))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(btnOK, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addGap(47))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectTheFollowing)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboboxMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOK)
						.addComponent(btnCancel))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	int loadpreset(){
		int count = 0;
		
		//READ IN PRESENT FILE HERE
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(new File("src/Preset/Preset.txt"));
			while(fileIn.hasNextLine() == true){
				name[count] = fileIn.nextLine();
				count++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		try{
			fileIn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
