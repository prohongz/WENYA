package Simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import finalcut.StartUI;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Buildingframe extends JFrame implements Runnable {

	private JPanel contentPane;
	private Thread runner = null;
	private int buildingaddress = 0;
	private char buildingtype = ' ';
	
	//for testing
	JLabel lblNewLabel = new JLabel("New label");
	
	public Buildingframe(int i, char type) {
		
		buildingaddress = i-1;
		buildingtype = type;
		if(i==0){
			setTitle("Properties of CDC");
		}else{
			setTitle("Properties of Factory " + (buildingaddress+1));
		}	
		setFont(new Font("Arial", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartUI.class.getResource("/misc/Icon.gif")));
		setSize(400, 300);
		setMinimumSize(new Dimension(400, 300));
		Toolkit t = Toolkit.getDefaultToolkit();
        int x = (int)((t.getScreenSize().getWidth() - getWidth()) / 2);
        int y = (int)((t.getScreenSize().getHeight() - getHeight()) / 2);
        setLocation(x, y+50);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		JButton Close = new JButton("Close");
		Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(312, Short.MAX_VALUE)
					.addComponent(Close)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(103, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(Close))
		);
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addContainerGap(315, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(21)
					.addComponent(lblNewLabel)
					.addContainerGap(187, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}

	public void paint(Graphics g2){
		//Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("Arial", Font.PLAIN, 12));
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		       // RenderingHints.VALUE_ANTIALIAS_ON);
		
		//lblNewLabel.setText(Integer.toString(i));
		
		if(buildingtype == 'F'){
			for(int i=0; i < Constant.Factbay; i++){
				g2.setColor(Color.black);
				g2.drawRect(310, 40+(40*i), 70, 30);
				if(Central.factdock[buildingaddress][i]==1){
					g2.setColor(Color.cyan);
					g2.fillRect(315, 45+(40*i), 60, 20);
					g2.setColor(Color.black);
					g2.drawString("Loading", 320, 60+(40*i));
				}else if(Central.factdock[buildingaddress][i]==2){
					g2.setColor(Color.cyan);
					g2.fillRect(315, 45+(40*i), 60, 20);
					g2.setColor(Color.black);
					g2.drawString("Unloading", 315, 60+(40*i));
				}else{
					g2.setColor(Color.black);
					g2.drawString("Empty", 328, 60+(40*i));
				}
			}
		}
		if(buildingtype == 'C'){
			for(int i=0; i < Constant.CDCbay; i++){
				g2.setColor(Color.black);
				g2.drawRect(310, 40+(40*i), 70, 30);
				if(Central.cdcdock[i]==1){
					g2.setColor(Color.cyan);
					g2.fillRect(315, 45+(40*i), 60, 20);
					g2.setColor(Color.black);
					g2.drawString("Loading", 320, 60+(40*i));
				}else if(Central.cdcdock[i]==2){
					g2.setColor(Color.cyan);
					g2.fillRect(315, 45+(40*i), 60, 20);
					g2.setColor(Color.black);
					g2.drawString("Unloading", 315, 60+(40*i));
				}else{
					g2.setColor(Color.black);
					g2.drawString("Empty", 328, 60+(40*i));
				}
			}
		}
	}
	
	@Override
	public void run() {
		while (Thread.currentThread() == runner) {
			try {
				//ADJUST THE SPEED OF THE SIMULATOR HERE (
				//[max delay: 200 - 1 sec by 1 sec]
				//[min delay: 5 - keep the CPU load at ~50%]
				Thread.sleep(Constant.tsleep_ms);
				
			} catch (InterruptedException e) {
				;
			}
			repaint();
		}
	}
	
	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.setName("Building " + (buildingaddress+1));
			runner.start();
		}
	} 
	
	public void stop() {
		runner = null;
	} 
}
