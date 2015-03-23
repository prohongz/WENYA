package Simulator;

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
	private int factoryaddress;
	
	//for testing
	int i=0;
	JLabel lblNewLabel = new JLabel("New label");
	
	public Buildingframe(int i) {
		
		factoryaddress = i;
		if(i==0){
			setTitle("Properties of CDC");
		}else{
			setTitle("Properties of Factory " + factoryaddress);
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
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(275, Short.MAX_VALUE)
					.addComponent(Close)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
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

	public void paint(Graphics g){
		lblNewLabel.setText(Integer.toString(i));
		
		//PAINT THE LOADING/UNLOADING BAY
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
			
			i++;
			repaint();
		}
	}
	
	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	} 
	
	public void stop() {
		runner = null;
	} 
}
