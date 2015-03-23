package Simulator;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLayeredPane;

import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class liveupdate extends JPanel implements Runnable {
	
	//TESTING PURPOSE
	JLabel lblCdc = new JLabel("cdc");
	
	////////////
	
	private Thread runner = null;
	
	
	public liveupdate() {
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 650, GroupLayout.PREFERRED_SIZE)
		);
		
		
		JPanel operpanel = new JPanel();
		tabbedPane.addTab("Operation", null, operpanel, null);
		
		JLabel lblActiveVehicles = new JLabel("Active vehicles: ");
		
		JLabel lblInactiveVehicles = new JLabel("Inactive vehicles: ");
		
		GroupLayout gl_operpanel = new GroupLayout(operpanel);
		gl_operpanel.setHorizontalGroup(
			gl_operpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_operpanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_operpanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblActiveVehicles)
						.addComponent(lblInactiveVehicles))
					.addContainerGap(183, Short.MAX_VALUE))
		);
		gl_operpanel.setVerticalGroup(
			gl_operpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_operpanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblActiveVehicles)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInactiveVehicles)
					.addContainerGap(566, Short.MAX_VALUE))
		);
		operpanel.setLayout(gl_operpanel);
		
		JPanel rtpanel = new JPanel();
		tabbedPane.addTab("Specific Real Time Data", null, rtpanel, null);
		
		JButton btnCdc = new JButton("CDC");
		
		
		
		JButton btnFactory = new JButton("Factory");
		
		
		JButton btnTruck = new JButton("Truck");
		if(Constant.TruckMode == false){
			btnTruck.setEnabled(false);
		}
		
		JButton btnAgv = new JButton("AGV");
				if(Constant.AgvMode == false){
			btnAgv.setEnabled(false);
		}
		
		JLayeredPane layeredPane = new JLayeredPane();
		
		GroupLayout gl_rtpanel = new GroupLayout(rtpanel);
		gl_rtpanel.setHorizontalGroup(
			gl_rtpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rtpanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_rtpanel.createParallelGroup(Alignment.LEADING)
						.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
						.addGroup(gl_rtpanel.createSequentialGroup()
							.addComponent(btnCdc)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnFactory)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnTruck)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAgv)))
					.addContainerGap())
		);
		gl_rtpanel.setVerticalGroup(
			gl_rtpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rtpanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_rtpanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCdc)
						.addComponent(btnFactory)
						.addComponent(btnTruck)
						.addComponent(btnAgv))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel rtcdcpnl = new JPanel();
		rtcdcpnl.setBounds(0, 0, 252, 560);
		layeredPane.add(rtcdcpnl);
		
		
		rtcdcpnl.add(lblCdc);
		
		JPanel rtfactpnl = new JPanel();
		rtfactpnl.setBounds(0, 0, 252, 560);
		layeredPane.add(rtfactpnl);
		
		JLabel lblFat = new JLabel("fat");
		rtfactpnl.add(lblFat);
		
		JPanel rttruckpnl = new JPanel();
		rttruckpnl.setBounds(0, 0, 252, 560);
		layeredPane.add(rttruckpnl);
		
		JLabel lblTr = new JLabel("tr");
		rttruckpnl.add(lblTr);
		
		JPanel rtagvpnl = new JPanel();
		rtagvpnl.setBounds(0, 0, 252, 560);
		layeredPane.add(rtagvpnl);
		
		JLabel lblAgv = new JLabel("agv");
		rtagvpnl.add(lblAgv);
		rtpanel.setLayout(gl_rtpanel);
		
		JPanel Settingpanel = new JPanel();
		tabbedPane.addTab("Parameters", null, Settingpanel, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		GroupLayout gl_Settingpanel = new GroupLayout(Settingpanel);
		gl_Settingpanel.setHorizontalGroup(
			gl_Settingpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Settingpanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_Settingpanel.setVerticalGroup(
			gl_Settingpanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_Settingpanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JTextArea parameter = new JTextArea();
		parameter.setFont(new Font("Calibri", Font.PLAIN, 12));
		parameter.setEditable(false);
		parameter.setBackground(UIManager.getColor("Button.background"));
		parameter.setText("GENERAL\r\n");
		parameter.append("Start Time: " + String.format("%02d", Constant.starthour) + ":" + String.format("%02d", Constant.startminute) +
				"\nEnd Day: Day " + Constant.endday +
				"\nEnd Time: " + String.format("%02d", Constant.endhour) + ":" + String.format("%02d", Constant.endminute) + "\n\n");
		parameter.append("CARGO\n");
		
		parameter.append("CDC\n" +
		"Operation Hours: " + String.format("%02d", Constant.CDCstarth) + ":" + String.format("%02d", Constant.CDCstartm) + " - " + String.format("%02d", Constant.CDCendh) + ":" + String.format("%02d", Constant.CDCendm) + "\n");
		parameter.append("Cargo processing time: " + Constant.CDCcargoturnover + " min"
				+"\nMaximum Cargo Limit: " + Constant.CDCcargolimit + "\n\n");
		
		parameter.append("Factory\n" +
		"Operation Hours: " + String.format("%02d", Constant.Factstarth) + ":" + String.format("%02d", Constant.Factstartm) + " - " + String.format("%02d", Constant.Factendh) + ":" + String.format("%02d", Constant.Factendm) + "\n");
		parameter.append("Cargo processing time: " + Constant.Factcargoturnover + " min"
				+"\nMaximum Cargo Limit: " + Constant.Factcargolimit + "\n\n");
		
		parameter.append("Truck\n");
		if(Constant.TruckMode == true){
			parameter.append("Truck Quantity: " + Constant.TruckQty);
			parameter.append("\nTruck Top Speed: " + Constant.TruckSpd);
			parameter.append("\nTruck Acceleration Speed: " + Constant.TruckAccSpd);
			parameter.append("\nTruck Deceleration Speed: " + Constant.TruckDecSpd);
			parameter.append("\nTruck Time Gap: " + Constant.TruckTimeGap);
			parameter.append("\nTruck Distance Gap: " + Constant.TruckDistGap);
			parameter.append("\nTruck Politeness: " + Constant.TruckPoliteness);
			parameter.append("\nTruck Changing Lane duration: " + Constant.TruckChanging + "\n\n");
		}else{
			parameter.append("DISABLED\n\n");
		}
		
		parameter.append("AGV\n---------------------------------------------------------------\n");
		if(Constant.AgvMode == true){
			parameter.append("AGV Quantity: " + Constant.AgvQty);
			parameter.append("\nAGV Top Speed: " + Constant.AgvSpd);
			parameter.append("\nAGV Acceleration Speed: " + Constant.AgvAccSpd);
			parameter.append("\nAGV Deceleration Speed: " + Constant.AgvDecSpd);
			parameter.append("\nAGV Time Gap: " + Constant.AgvTimeGap);
			parameter.append("\nAGV Distance Gap: " + Constant.AgvDistGap + "\n\n");
		}else{
			parameter.append("DISABLED\n\n");
		}
		
		parameter.setCaretPosition(0);
		
		scrollPane.setViewportView(parameter);
		Settingpanel.setLayout(gl_Settingpanel);
		setLayout(groupLayout);
		
		btnCdc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtfactpnl.setVisible(false);
				rttruckpnl.setVisible(false);
				rtagvpnl.setVisible(false);
				rtcdcpnl.setVisible(true);
			}
		});
		
		btnFactory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtcdcpnl.setVisible(false);
				rttruckpnl.setVisible(false);
				rtagvpnl.setVisible(false);
				rtfactpnl.setVisible(true);
			}
		});
		
		btnTruck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtcdcpnl.setVisible(false);
				rtfactpnl.setVisible(false);
				rtagvpnl.setVisible(false);
				rttruckpnl.setVisible(true);
			}
		});
		
		btnAgv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtcdcpnl.setVisible(false);
				rttruckpnl.setVisible(false);
				rttruckpnl.setVisible(false);
				rtagvpnl.setVisible(true);
			}
		});
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (Thread.currentThread() == runner) {
			lblCdc.setText(Integer.toString(Result.active));
			
			synchronized (this) {
				while (Constant.suspended){
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
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
	
	public void suspend(){
		Constant.suspended = true;
	}
	
	public synchronized void resume() {
		System.out.println("Real time data resuming...");
		notify();
	}
}
