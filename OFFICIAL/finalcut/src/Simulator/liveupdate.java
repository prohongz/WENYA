package Simulator;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLayeredPane;

import java.awt.event.ActionListener;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
/**
 * 
 * @author Sim Hong Xun
 *
 */
@SuppressWarnings("serial")
public class liveupdate extends JPanel implements Runnable {
	
	//TESTING PURPOSE
	JLabel rtactive = new JLabel("0");	
	JLabel rtinactive = new JLabel("0");
	JLabel rttruckactive = new JLabel("0");
	JLabel rttruckinactive = new JLabel("0");
	JLabel rtagvactive = new JLabel("0");
	JLabel rtagvinactive = new JLabel("0");
	
	JLabel rtfuel = new JLabel("0 Litres");
	JLabel rtfuelcost = new JLabel("$0.00");
	
	JLabel rtday = new JLabel("0");
	JLabel rthour = new JLabel("00");
	JLabel rtmin = new JLabel("00");
	JLabel rtsec = new JLabel("00");
	
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
		
		JPanel rtvehpanel = new JPanel();
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Vehicle - Related");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        rtvehpanel.setBorder(titledBorder);
		
		JPanel rttimepanel = new JPanel();
		TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Time - Related");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        rttimepanel.setBorder(titledBorder1);
		
		
		
		GroupLayout gl_operpanel = new GroupLayout(operpanel);
		gl_operpanel.setHorizontalGroup(
			gl_operpanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_operpanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_operpanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(rtvehpanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 251, Short.MAX_VALUE)
						.addComponent(rttimepanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_operpanel.setVerticalGroup(
			gl_operpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_operpanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rttimepanel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rtvehpanel, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(254, Short.MAX_VALUE))
		);
		
		JLabel lblDay = new JLabel("Day ");
		lblDay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblTime = new JLabel("Time  - ");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		rtday.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		rthour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label = new JLabel(":");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		rtmin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label_1 = new JLabel(":");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
	
		rtsec.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_rttimepanel = new GroupLayout(rttimepanel);
		gl_rttimepanel.setHorizontalGroup(
			gl_rttimepanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rttimepanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_rttimepanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_rttimepanel.createSequentialGroup()
							.addComponent(lblDay)
							.addGap(6)
							.addComponent(rtday))
						.addGroup(gl_rttimepanel.createSequentialGroup()
							.addComponent(lblTime)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rthour)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label)
							.addGap(6)
							.addComponent(rtmin)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rtsec)))
					.addContainerGap(89, Short.MAX_VALUE))
		);
		gl_rttimepanel.setVerticalGroup(
			gl_rttimepanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rttimepanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_rttimepanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDay)
						.addComponent(rtday))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_rttimepanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTime)
						.addComponent(rthour)
						.addComponent(label)
						.addComponent(rtmin)
						.addComponent(label_1)
						.addComponent(rtsec))
					.addContainerGap(47, Short.MAX_VALUE))
		);
		rttimepanel.setLayout(gl_rttimepanel);
		
		JLabel lblActiveVehicles = new JLabel("Total number of active vehicles: ");
		lblActiveVehicles.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblInactiveVehicles = new JLabel("Total number of inactive vehicles: ");
		lblInactiveVehicles.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNumberOfActive = new JLabel("Number of active Trucks:");
		lblNumberOfActive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNumberOfInactive = new JLabel("Number of inactive Trucks:");
		lblNumberOfInactive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNumberOfActive_1 = new JLabel("Number of active AGVs:");
		lblNumberOfActive_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNumberOfInactive_1 = new JLabel("Number of inactive AGVs:");
		lblNumberOfInactive_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		if(Constant.TruckMode == true){
			lblNumberOfActive.setEnabled(true);
			lblNumberOfInactive.setEnabled(true);
			rttruckinactive.setEnabled(true);
			rttruckactive.setEnabled(true);
		}else{
			lblNumberOfActive_1.setEnabled(false);
			lblNumberOfInactive_1.setEnabled(false);
			rtagvactive.setEnabled(false);
			rtagvinactive.setEnabled(false);
		}
		if(Constant.AgvMode == true){
			lblNumberOfActive_1.setEnabled(true);
			lblNumberOfInactive_1.setEnabled(true);
			rtagvactive.setEnabled(true);
			rtagvinactive.setEnabled(true);
		}else{
			lblNumberOfActive_1.setEnabled(false);
			lblNumberOfInactive_1.setEnabled(false);
			rtagvactive.setEnabled(false);
			rtagvinactive.setEnabled(false);
		}
		
		JSeparator separator = new JSeparator();
		
		JSeparator separator_1 = new JSeparator();
		
		JSeparator separator_2 = new JSeparator();
		
		JLabel lblTotalFuelConsumed = new JLabel("Total fuel consumed:");
		lblTotalFuelConsumed.setToolTipText("Refer to Specific Real Time Data for individual vehicle fuel consumption.");
		lblTotalFuelConsumed.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rtfuel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		rtfuel.setToolTipText("Refer to Specific Real Time Data for individual vehicle fuel consumption.");
		rtfuel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblAmountSpentOn = new JLabel("Amount spent on Fuel:");
		lblAmountSpentOn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rtfuelcost.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		rtfuelcost.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		GroupLayout gl_rtvehpanel = new GroupLayout(rtvehpanel);
		gl_rtvehpanel.setHorizontalGroup(
			gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rtvehpanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_rtvehpanel.createSequentialGroup()
							.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_rtvehpanel.createSequentialGroup()
									.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblActiveVehicles)
										.addComponent(lblInactiveVehicles))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
										.addComponent(rtinactive, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
										.addComponent(rtactive, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_rtvehpanel.createSequentialGroup()
									.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNumberOfActive)
										.addComponent(lblNumberOfInactive))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
										.addComponent(rttruckinactive)
										.addComponent(rttruckactive)))
								.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_rtvehpanel.createSequentialGroup()
									.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNumberOfActive_1)
										.addComponent(lblNumberOfInactive_1))
									.addGap(17)
									.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
										.addComponent(rtagvinactive)
										.addComponent(rtagvactive)))
								.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_rtvehpanel.createSequentialGroup()
							.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAmountSpentOn)
								.addComponent(lblTotalFuelConsumed))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
								.addComponent(rtfuel, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
								.addComponent(rtfuelcost, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
							.addGap(20))))
		);
		gl_rtvehpanel.setVerticalGroup(
			gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rtvehpanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblActiveVehicles)
						.addComponent(rtactive))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInactiveVehicles)
						.addComponent(rtinactive))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumberOfActive)
						.addComponent(rttruckactive))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumberOfInactive)
						.addComponent(rttruckinactive))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumberOfActive_1)
						.addComponent(rtagvactive))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumberOfInactive_1)
						.addComponent(rtagvinactive))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalFuelConsumed)
						.addComponent(rtfuel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_rtvehpanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAmountSpentOn)
						.addComponent(rtfuelcost))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		rtagvinactive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rtagvactive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rttruckinactive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rttruckactive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rtinactive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rtactive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rtvehpanel.setLayout(gl_rtvehpanel);
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
				"\nEnd Time: " + String.format("%02d", Constant.endhour) + ":" + String.format("%02d", Constant.endminute) + 
				"\nFuel Cost: $" + String.format("%1$,.3f", Constant.fuelcost) + "\n\n");
		parameter.append("CARGO\n");
		
		parameter.append("CDC\n" +
		"Operation Hours: " + String.format("%02d", Constant.CDCstarth) + ":" + String.format("%02d", Constant.CDCstartm) + " - " + String.format("%02d", Constant.CDCendh) + ":" + String.format("%02d", Constant.CDCendm) + "\n");
		parameter.append("Cargo processing time: " + Constant.CDCcargoturnover + " min"
				+"\nMaximum Cargo Limit: " + Constant.CDCcargolimit
				+"\nNumber of Cargo Bays: " + Constant.CDCbay + "\n\n");
		
		parameter.append("Factory\n" +
		"Operation Hours: " + String.format("%02d", Constant.Factstarth) + ":" + String.format("%02d", Constant.Factstartm) + " - " + String.format("%02d", Constant.Factendh) + ":" + String.format("%02d", Constant.Factendm) + "\n");
		parameter.append("Cargo processing time: " + Constant.Factcargoturnover + " min"
				+"\nMaximum Cargo Limit: " + Constant.Factcargolimit
				+"\nNumber of Cargo Bays: " + Constant.Factbay + "\n\n");
		
		parameter.append("Truck\n");
		if(Constant.TruckMode == true){
			parameter.append("Truck Quantity: " + Constant.TruckQty);
			parameter.append("\nTruck Top Speed: " + Constant.TruckSpd);
			parameter.append("\nTruck Acceleration Speed: " + Constant.TruckAccSpd);
			parameter.append("\nTruck Deceleration Speed: " + Constant.TruckDecSpd);
			parameter.append("\nTruck Time Gap: " + Constant.TruckTimeGap);
			parameter.append("\nTruck Distance Gap: " + Constant.TruckDistGap);
			parameter.append("\nTruck Politeness: " + Constant.TruckPoliteness);
			parameter.append("\nTruck Changing Lane duration: " + Constant.TruckChanging);
			parameter.append("\nTruck Fuel efficiency with Cargo: " + Constant.Truckfewcargo);
			parameter.append("\nTruck Fuel efficiency w/o Cargo: " + Constant.Truckfewocargo + "\n\n");
		}else{
			parameter.append("DISABLED\n\n");
		}
		
		parameter.append("AGV\n");
		if(Constant.AgvMode == true){
			parameter.append("AGV Quantity: " + Constant.AgvQty);
			parameter.append("\nAGV Top Speed: " + Constant.AgvSpd);
			parameter.append("\nAGV Acceleration Speed: " + Constant.AgvAccSpd);
			parameter.append("\nAGV Deceleration Speed: " + Constant.AgvDecSpd);
			parameter.append("\nAGV Time Gap: " + Constant.AgvTimeGap);
			parameter.append("\nAGV Distance Gap: " + Constant.AgvDistGap);
			parameter.append("\nAGV Fuel efficiency with Cargo: " + Constant.Agvfewcargo);
			parameter.append("\nAGV Fuel efficiency w/o Cargo: " + Constant.Agvfewocargo + "\n\n");
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
			
			rtday.setText(Integer.toString(Clock.day));
			rthour.setText(((Clock.hour > 9) ? "" : "0")+ Integer.toString(Clock.hour));
			rtmin.setText(((Clock.min > 9) ? "" : "0")+ Integer.toString(Clock.min));
			rtsec.setText(((Clock.sec > 9) ? "" : "0")+ Integer.toString(Clock.sec));
			
			rtactive.setText(Integer.toString(Central.truckactive + Central.agvactive));
			rtinactive.setText(Integer.toString(Central.truckinactive + Central.agvinactive));
			
			rttruckactive.setText(Integer.toString(Central.truckactive));
			rttruckinactive.setText(Integer.toString(Central.truckinactive));
			
			rtagvactive.setText(Integer.toString(Central.agvactive));
			rtagvinactive.setText(Integer.toString(Central.agvinactive));
			
			rtfuel.setText(Integer.toString(Central.totalfuel) + " Litres");
			rtfuelcost.setText("$" + String.format("%.3f", (Constant.fuelcost * Central.totalfuel)));
			
			
			

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
			runner.setName("Real time panel");
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
