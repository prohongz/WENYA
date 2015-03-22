package Simulator;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import finalcut.StartUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("serial")
public class Save extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public Save() {
		setTitle("Save Preset");
		setFont(new Font("Arial", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartUI.class.getResource("/misc/Icon.gif")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 150);
		setMinimumSize(new Dimension(150, 100));
		Toolkit t = Toolkit.getDefaultToolkit();
        int x = (int)((t.getScreenSize().getWidth() - getWidth()) / 2);
        int y = (int)((t.getScreenSize().getHeight() - getHeight()) / 2);
        setLocation(x, y);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblEnterAName = new JLabel("Enter a name for the preset:");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File file = new File("src/Preset");
				if (!file.exists()) {
					if (file.mkdir()) {
						System.out.println("Directory is created!");
					} else {
						System.out.println("Failed to create directory!");
					}
				}
								
				//SAVE SETTING
				File file1 = new File("src/Preset/Preset.txt");
				FileWriter fileWritter = null;
				try {
					fileWritter = new FileWriter(file.getName(),true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	        BufferedWriter out = new BufferedWriter(fileWritter);
			
				try {
					out.write(textField.getText());
					out.write(Constant.starthour 
							+ Constant.startminute 
							+ Constant.endday
							+ Constant.endhour 
							+ Constant.endminute
							+ Constant.CDCstarth 
							+ Constant.CDCstartm 
							+ Constant.CDCendh 
							+ Constant.CDCendm 
							+ Constant.CDCcargoto 
							+ Constant.CDCbay + "\n");
					
					for(int i = 0; i < 15; i++){
						out.write(Constant.CDCdemandh[i]);
					}
					
					out.write(Constant.Factstarth
							+ Constant.Factstartm
							+ Constant.Factendh
							+ Constant.Factendm
							+ Constant.Factcargoto
							+ Constant.Factbay + "\n");
							
					for(int i = 0; i < 15; i++){
						out.write(Constant.Factdemandh[i]);
					}
					
					if(Constant.TruckMode==true)
						out.write("1");
					else
						out.write("0");
					
					out.write(Constant.TruckQty
							+Constant.TruckSpd
							+Constant.TruckAccSpd
							+Constant.TruckDecSpd
							+Constant.TruckTimeGap
							+Constant.TruckDistGap
							+Constant.TruckPoliteness
							+Constant.TruckChanging + "\n");
					
					if(Constant.AgvMode==true)
						out.write(1);
					else
						out.write(0);
					
					out.write(Constant.AgvQty
							+ Constant.AgvSpd
							+ Constant.AgvAccSpd
							+ Constant.AgvDecSpd
							+ Constant.AgvTimeGap
							+ Constant.AgvDistGap + "\n");
					
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
							
				//PROMPT USER DONE WITH SAVING
				JOptionPane.showMessageDialog(null, "Preset \"" + textField.getText() + "\" has been saved.");
				
				setVisible(false);
				dispose();
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblEnterAName)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnSave)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addComponent(btnCancel))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGap(8)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEnterAName)
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnSave)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
