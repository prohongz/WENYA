package Simulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * MAIN BRAIN OF THE SIMULATOR RUN() is the main portion that determines the
 * flow of events.
 * 
 * @author Sim Hong Xun
 * 
 * */

@SuppressWarnings({ "serial", "unused" })
public class DrawSim extends JPanel implements Runnable {

	// DRAWING VARIABLES
	Color DARKGREEN = new Color(56, 138, 30);
	Color LIGHTGRAY = new Color(189, 189, 189);

	// THREAD RELATED VARIABLES
	private Thread runner = null;
	boolean simuend = false;

	// TIME RELATED VARIABLES
	private int demandtiming = 1;
	private int demandtiming2 = 1;

	private long simTime_ms = 0;
	private long saveTime_ms = 0;
	private long TotalTime_ms = 0;

	// DEMAND VARIABLES
	int hour = 0;
	int day = 0;

	// VARIABLES
	ArrayList<AGV> Agvforce = new ArrayList<AGV>();
	ArrayList<Truck> Truckforce = new ArrayList<Truck>();
	ArrayList<Factory> Factoryforce = new ArrayList<Factory>();
	CDC CDCforce = new CDC();

	// MAIN CONTENT
	public DrawSim() {
		System.out.println("Entering Simulator...");

		// DECLARE ALL THE VEHICLE
		for (int i = 0; i < Constant.AgvQty; i++) {
			Agvforce.add(new AGV());
		}

		for (int i = 0; i < Constant.TruckQty; i++) {
			Truckforce.add(new Truck());
		}

		for (int i = 0; i < 30; i++) {
			Factoryforce.add(new Factory(i));
		}

		CDCforce = new CDC();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(DARKGREEN);
		Painting.paintroad(g);

		// AGV
		if (Constant.AgvMode == true) {
			// CALCULATE NEW LOCATION
			for (AGV x : Agvforce) {
				x.drawupdate(g, 'a');
			}
		}

		// TRUCK
		if (Constant.TruckMode == true) {
			// CALCULATE NEW LOCATION
			for (Truck x : Truckforce) {
				x.drawupdate(g, 't');
			}
		}

		Painting.paintfactoryqueue(g);
		Painting.testing(g);
	}

	@Override
	public void run() {

		// Initialize start time of simulation
		Clock.time = 0 + (Constant.starthour * 60 * 60);

		while (Thread.currentThread() == runner) {

			// Update the clock (i.e. sec min hour day)
			Clock.clockupdate();

			try {
				// ADJUST THE SPEED OF THE SIMULATOR HERE (
				// [max delay: 200 - 1 sec by 1 sec]
				// [min delay: 5 - keep the CPU load at ~50%]
				Thread.sleep(Constant.tsleep_ms);

			} catch (InterruptedException e) {
				;
			}

			long timeBeforeSim_ms = System.currentTimeMillis();

			// ///////////////////////////////////////////////////////
			// SIMULATE NEW TIMESTEP (I.E. UPDATE ALL THE VARIABLES)//
			// ///////////////////////////////////////////////////////

			// SPAWN DEMAND TIMING
			if ((int) Clock.time % 3600 == 0) {

				if (demandtiming == 5) { // CHANGE THIS IF TIMESTEP_S IS CHANGED

					// OPERATION FROM 06:00 - 21:00, BUT LAST DEMAND AT 20:00
					if (Clock.returnhour() >= 6 && Clock.returnhour() <= 20) {

						// GENERATE A NEW DEMAND WITH TIMING ALLOCATION
						for (int i = 0; i < (Constant.CDCdemandh[Clock
								.returnhour() - 6]); i++) {
							// DELIVERY LOCATION WILL ONLY BE SPAWN WHEN THE
							// DEMAND IS CALLED FOR BY CENTRAL
							// THIS IS JUST TO TELL THE CDC THAT THERE IS GOING
							// TO BE THIS DEMAND AT THIS PARTICULAR TIME
							// NOT SPAWN IMMEDIATELY

							// SET CARGO TIMING
							// STORE TO CDC
							Clock.returnhour();
							CDCforce.addminutedemand(Central.spawndemandtime());
						}

						for (Factory x : Factoryforce) {
							for (int i = 0; i < (Constant.Factdemandh[Clock
									.returnhour() - 6]); i++) {
								// DELIVERY LOCATION WILL ONLY BE SPAWN WHEN THE
								// DEMAND IS CALLED FOR BY CENTRAL
								// IS TO TELL FACTORY IN THIS HOUR, THERE IS
								// GOING TO BE THIS DEMAND AT THIS PARTICULAR
								// TIME
								// NOT SPAWN IMMEDIATELY

								// SET CARGO TIMING
								// STORE TO FACTORY
								Clock.returnhour();
								x.addminutedemand(Central.spawndemandtime());
							}
						}

					}
					demandtiming = 1;
				} else {
					demandtiming++;
				}
			}

			// ////////////////////////////////////////////////////////
			// CDC AND FACTORY NOTIFY CENTRAL FOR VEH TO CLEAR DEMAND//
			// PRIORTY GOES TO TRUCK FIRST THEN AGV IF BOTH ENABLED //
			// ////////////////////////////////////////////////////////

			// CDC
			// IF THERE IS DEMAND, INFORM CENTRAL
			// NORMAL OR PRIORTY QUEUE
			if (CDCforce.getminutedemand(Clock.returnminute()) > 0) {
				// OWNSELF TAKE NOTE AND NOTIFY CENTRAL AT THE SAME TIME
				// [DELIVER LOCATION: RANDOM FACTORY (0-29) (Default)]
				if (CDCforce.getnormalqueue() != Constant.CDCcargolimit) {
					CDCforce.addnormalqueue();
					Central.cdcdemandcount++;

					CDCforce.subminutedemand(Clock.returnminute());
				} else {
					CDCforce.addpriorityqueue();
					if (Central.cdcprioritycount >= 20) {
						Central.cdcoverload++;
					}
					Central.cdcprioritycount++;

					CDCforce.subminutedemand(Clock.returnminute());
				}
			}

			// FACTORY
			for (Factory x : Factoryforce) {
				// IF THERE IS DEMAND, INFORM CENTRAL
				// NORMAL OR PRIORTY QUEUE
				if (x.getminutedemand(Clock.returnminute()) > 0) {
					// OWNSELF TAKE NOTE AND NOTIFY CENTRAL AT THE SAME TIME
					// [DELIVER LOCATION: CDC (100) (Default)]
					if (x.getnormalqueue() != Constant.Factcargolimit) {
						x.addnormalqueue();
						Central.factdemandcount[x.getfactorynumber()]++;

						x.subminutedemand(Clock.returnminute());
					} else {
						x.addpriorityqueue();
						if (Central.factprioritycount[x.getfactorynumber()] >= 10) {
							Central.factoverload++;
						}
						Central.factprioritycount[x.getfactorynumber()]++;

						x.subminutedemand(Clock.returnminute());
					}
				}
			}

			// CENTRAL [BRAIN FOR CDC AND FACTORY]

			// ///////////////////////////ALLOCATE VEHICLE FOR CDC PRIORITY
			// LIST////////////////////////////////////////
			int temp = 0;
			temp = Central.cdcprioritycount;
			boolean notsuccess = true;
			for (int i = 0; i < temp; i++) {
				notsuccess = true;
				if (Constant.AgvMode == true && Constant.TruckMode == true) {

					// FOR CDC IDEAL CASE GET PHASE 0 VEHICLE
					if (Constant.Truckpriority == true) {
						// Search for free TRUCK first then AGV second
						for (Truck x : Truckforce) {
							if (x.getphase() == 0) {

								// ALLOCATE DESTINATION TO TRUCK
								x.setdestination(Central.spawnfactdemand());

								// if allocated success
								x.setphase(1);
								Central.truckactive++;
								Central.truckinactive--;
								;
								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
								break;
							}
						}
						for (AGV x : Agvforce) {
							if (x.getphase() == 0) {

								// ALLOCATE DESTINATION TO AGV
								x.setdestination(Central.spawnfactdemand());

								// if allocated success
								x.setphase(1);
								Central.agvactive++;
								Central.agvinactive--;
								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
								break;
							}
						}
						if (notsuccess == true) {
							int potential = 0;
							char potentialtype = 't';
							int disttemp = 1774;
							boolean found = false;
							for (int j = 0; j < Constant.TruckQty; j++) {
								if (Truckforce.get(j).getphase() == 4) {
									if (disttemp > Truckforce.get(j)
											.calculatedisttocdc()) {
										potential = j;
										potentialtype = 't';
										disttemp = Truckforce.get(j)
												.calculatedisttocdc();
										found = true;
									}
								}
							}
							for (int j = 0; j < Constant.AgvQty; j++) {
								if (Agvforce.get(j).getphase() == 4) {
									if (disttemp > Truckforce.get(j)
											.calculatedisttocdc()) {
										potential = j;
										potentialtype = 'a';
										disttemp = Truckforce.get(j)
												.calculatedisttocdc();
										found = true;
									}
								}
							}

							if (found == true && potentialtype == 't') {
								// ALLOCATE DESTINATION TO AGV
								Truckforce.get(potential).setdestination(100);

								// if allocated success
								// Truckforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (found == true && potentialtype == 'a') {
								// ALLOCATE DESTINATION TO AGV
								Agvforce.get(potential).setdestination(100);

								// if allocated success
								// Agvforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}
						}
						// IN EVENT WHERE THERE IS NO VEHICLE TO ALLOCATE TO
						// DO NOTHING BUT CONTINUE ON FIRST
					} else {
						// Search for free AGV first then TRUCK second
						for (AGV x : Agvforce) {
							if (x.getphase() == 0) {

								// ALLOCATE DESTINATION TO AGV
								x.setdestination(Central.spawnfactdemand());

								// if allocated success
								x.setphase(1);
								Central.agvactive++;
								Central.agvinactive--;
								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
								break;
							}
						}
						for (Truck x : Truckforce) {
							if (x.getphase() == 0) {

								// ALLOCATE DESTINATION TO TRUCK
								x.setdestination(Central.spawnfactdemand());

								// if allocated success
								x.setphase(1);
								Central.truckactive++;
								Central.truckinactive--;
								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
								break;
							}
						}
						if (notsuccess = true) {
							int potential = 0;
							char potentialtype = 't';
							int disttemp = 1774;
							boolean found = false;
							for (int j = 0; j < Constant.TruckQty; j++) {
								if (Truckforce.get(j).getphase() == 4) {
									if (disttemp > Truckforce.get(j)
											.calculatedisttocdc()) {
										potential = j;
										potentialtype = 't';
										disttemp = Truckforce.get(j)
												.calculatedisttocdc();
										found = true;
									}
								}
							}
							for (int j = 0; j < Constant.AgvQty; j++) {
								if (Agvforce.get(j).getphase() == 4) {
									if (disttemp > Truckforce.get(j)
											.calculatedisttocdc()) {
										potential = j;
										potentialtype = 'a';
										disttemp = Truckforce.get(j)
												.calculatedisttocdc();
										found = true;
									}
								}
							}

							if (found == true && potentialtype == 't') {
								// ALLOCATE DESTINATION TO AGV
								Truckforce.get(potential).setdestination(100);

								// if allocated success
								// Truckforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (found == true && potentialtype == 'a') {
								// ALLOCATE DESTINATION TO AGV
								Agvforce.get(potential).setdestination(100);

								// if allocated success
								// Agvforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}
						}
					}
				}
				// ONLY TRUCK
				if (Constant.AgvMode == false && Constant.TruckMode == true) {
					for (Truck x : Truckforce) {
						if (x.getphase() == 0) {

							// ALLOCATE DESTINATION TO TRUCK
							x.setdestination(Central.spawnfactdemand());

							// if allocated success
							x.setphase(1);
							Central.truckactive++;
							Central.truckinactive--;
							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
							break;
						}
					}
					if (notsuccess == true) {
						int potential = 0;
						int disttemp = 1774;
						boolean found = false;
						for (int j = 0; j < Constant.TruckQty; j++) {
							if (Truckforce.get(j).getphase() == 4) {
								if (disttemp > Truckforce.get(j)
										.calculatedisttocdc()) {
									potential = j;
									disttemp = Truckforce.get(j)
											.calculatedisttocdc();
									found = true;
								}
							}
						}

						if (found == true) {
							// ALLOCATE DESTINATION TO AGV
							Truckforce.get(potential).setdestination(100);

							// if allocated success
							// Truckforce.get(potential).setphase(1);

							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
						}
					}
				}
				// ONLY AGV
				if (Constant.AgvMode == true && Constant.TruckMode == false) {
					for (AGV x : Agvforce) {
						if (x.getphase() == 0) {

							// ALLOCATE DESTINATION TO AGV
							x.setdestination(Central.spawnfactdemand());

							// if allocated success
							x.setphase(1);
							Central.agvactive++;
							Central.agvinactive--;
							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
							break;
						}
					}
					if (notsuccess == true) {
						int potential = 0;
						int disttemp = 1774;
						boolean found = false;
						for (int j = 0; j < Constant.AgvQty; j++) {
							if (Agvforce.get(j).getphase() == 4) {
								if (disttemp > Agvforce.get(j)
										.calculatedisttocdc()) {
									potential = j;
									disttemp = Agvforce.get(j)
											.calculatedisttocdc();
									found = true;
								}
							}
						}

						if (found == true) {
							// ALLOCATE DESTINATION TO AGV
							Agvforce.get(potential).setdestination(100);

							// if allocated success
							// Truckforce.get(potential).setphase(1);

							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
						}
					}
				}
				notsuccess = false;
			}

			// ///////////////////////////////////ALLOCATE VEHICLE FOR FACTORY
			// PRIORITY LIST//////////////////////////////////
			for (int i = 0; i < 30; i++) {
				temp = Central.factprioritycount[i];
				for (int j = 0; j < temp; j++) {
					notsuccess = true;

					// FOR FACT IDEAL CASE GET PHASE 4 VEHICLE CLOSE BY
					if (Constant.AgvMode == true && Constant.TruckMode == true) {
						if (Constant.Truckpriority == true) {

							int potential = 0;
							char potentialtype = 't';
							int disttemp = Central.factdistance[i];
							boolean found = false;
							for (int k = 0; k < Constant.TruckQty; k++) {
								if (Truckforce.get(k).getphase() == 4) {
									if (Truckforce.get(k)
											.calculatedisttofact(i) > 0) {
										if (disttemp > Truckforce.get(k)
												.calculatedisttofact(i)) {
											potential = k;
											potentialtype = 't';
											disttemp = Truckforce.get(k)
													.calculatedisttofact(i);
											found = true;
										}
									}
								}
							}
							for (int k = 0; k < Constant.AgvQty; k++) {
								if (Agvforce.get(k).getphase() == 4) {
									if (Agvforce.get(k).calculatedisttofact(i) > 0) {
										if (disttemp > Agvforce.get(k)
												.calculatedisttofact(i)) {
											potential = k;
											potentialtype = 't';
											disttemp = Agvforce.get(k)
													.calculatedisttofact(i);
											found = true;
										}
									}
								}
							}

							if (found == true && potentialtype == 't') {
								// ALLOCATE DESTINATION TO AGV
								Truckforce.get(potential).setdestination(i);

								// if allocated success
								Truckforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (found == true && potentialtype == 'a') {
								// ALLOCATE DESTINATION TO AGV
								Agvforce.get(potential).setdestination(i);

								// if allocated success
								Agvforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (notsuccess == true) {
								for (Truck x : Truckforce) {
									if (x.getphase() == 0) {

										// ALLOCATE DESTINATION TO TRUCK
										x.setdestination(i);

										// if allocated success
										x.setphase(1);
										Central.truckactive++;
										Central.truckinactive--;
										Central.factprioritycount[i]--;
										Central.factprioritycountx[i]++;
										notsuccess = false;
										break;
									}
								}
							}
							if (notsuccess == true) {
								for (AGV x : Agvforce) {
									if (x.getphase() == 0) {

										// ALLOCATE DESTINATION TO TRUCK
										x.setdestination(i);

										// if allocated success
										x.setphase(1);
										Central.agvactive++;
										Central.agvinactive--;
										Central.factprioritycount[i]--;
										Central.factprioritycountx[i]++;
										notsuccess = false;
										break;
									}
								}
							}
						} else {

							int potential = 0;
							char potentialtype = 't';
							int disttemp = Central.factdistance[i];
							boolean found = false;
							for (int k = 0; k < Constant.TruckQty; k++) {
								if (Truckforce.get(k).getphase() == 4) {
									if (Truckforce.get(k)
											.calculatedisttofact(i) > 0) {
										if (disttemp > Truckforce.get(k)
												.calculatedisttofact(i)) {
											potential = k;
											potentialtype = 't';
											disttemp = Truckforce.get(k)
													.calculatedisttofact(i);
											found = true;
										}
									}
								}
							}
							for (int k = 0; k < Constant.AgvQty; k++) {
								if (Agvforce.get(k).getphase() == 4) {
									if (Agvforce.get(k).calculatedisttofact(i) > 0) {
										if (disttemp > Agvforce.get(k)
												.calculatedisttofact(i)) {
											potential = k;
											potentialtype = 't';
											disttemp = Agvforce.get(k)
													.calculatedisttofact(i);
											found = true;
										}
									}
								}
							}

							if (found == true && potentialtype == 't') {
								// ALLOCATE DESTINATION TO AGV
								Truckforce.get(potential).setdestination(i);

								// if allocated success
								Truckforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (found == true && potentialtype == 'a') {
								// ALLOCATE DESTINATION TO AGV
								Agvforce.get(potential).setdestination(i);

								// if allocated success
								Agvforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (notsuccess == true) {
								for (AGV x : Agvforce) {
									if (x.getphase() == 0) {

										// ALLOCATE DESTINATION TO TRUCK
										x.setdestination(i);

										// if allocated success
										x.setphase(1);
										Central.agvactive++;
										Central.agvinactive--;
										Central.factprioritycount[i]--;
										Central.factprioritycountx[i]++;
										notsuccess = false;
										break;
									}
								}
							}
							if (notsuccess == true) {
								for (Truck x : Truckforce) {
									if (x.getphase() == 0) {

										// ALLOCATE DESTINATION TO TRUCK
										x.setdestination(i);

										// if allocated success
										x.setphase(1);
										Central.truckactive++;
										Central.truckinactive--;
										Central.factprioritycount[i]--;
										Central.factprioritycountx[i]++;
										notsuccess = false;
										break;
									}
								}
							}
						}
					}

					if (Constant.AgvMode == false && Constant.TruckMode == true) {

						int potential = 0;
						char potentialtype = 't';
						int disttemp = Central.factdistance[i];
						boolean found = false;
						for (int k = 0; k < Constant.TruckQty; k++) {
							if (Truckforce.get(k).getphase() == 4) {
								if (Truckforce.get(k).calculatedisttofact(i) > 0) {
									if (disttemp > Truckforce.get(k)
											.calculatedisttofact(i)) {
										potential = k;
										potentialtype = 't';
										disttemp = Truckforce.get(k)
												.calculatedisttofact(i);
										found = true;
									}
								}
							}
						}

						if (found == true && potentialtype == 't') {
							// ALLOCATE DESTINATION TO AGV
							Truckforce.get(potential).setdestination(i);

							// if allocated success
							Truckforce.get(potential).setphase(1);

							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
						}

						if (notsuccess == true) {
							for (Truck x : Truckforce) {
								if (x.getphase() == 0) {

									// ALLOCATE DESTINATION TO TRUCK
									x.setdestination(i);

									// if allocated success
									x.setphase(1);
									Central.truckactive++;
									Central.truckinactive--;
									Central.factprioritycount[i]--;
									Central.factprioritycountx[i]++;
									notsuccess = false;
									break;
								}
							}
						}
					}

					if (Constant.AgvMode == true && Constant.TruckMode == false) {
						int potential = 0;
						char potentialtype = 't';
						int disttemp = Central.factdistance[i];
						boolean found = false;
						for (int k = 0; k < Constant.AgvQty; k++) {
							if (Agvforce.get(k).getphase() == 4) {
								if (Agvforce.get(k).calculatedisttofact(i) > 0) {
									if (disttemp > Agvforce.get(k)
											.calculatedisttofact(i)) {
										potential = k;
										potentialtype = 't';
										disttemp = Agvforce.get(k)
												.calculatedisttofact(i);
										found = true;
									}
								}
							}
						}

						if (found == true && potentialtype == 'a') {
							// ALLOCATE DESTINATION TO AGV
							Agvforce.get(potential).setdestination(i);

							// if allocated success
							Agvforce.get(potential).setphase(1);

							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
						}

						if (notsuccess == true) {
							for (AGV x : Agvforce) {
								if (x.getphase() == 0) {

									// ALLOCATE DESTINATION TO TRUCK
									x.setdestination(i);

									// if allocated success
									x.setphase(1);
									Central.agvactive++;
									Central.agvinactive--;
									Central.factprioritycount[i]--;
									Central.factprioritycountx[i]++;
									notsuccess = false;
									break;
								}
							}
						}
					}

				}
				notsuccess = false;
			}

			// /////////////////////////////////ALLOCATE VEHICLE FOR CDC NORMAL
			// LIST//////////////////////////////////////
			temp = Central.cdcdemandcount;
			for (int i = 0; i < temp; i++) {
				if (Constant.AgvMode == true && Constant.TruckMode == true) {

					notsuccess = true;

					// FOR CDC IDEAL CASE GET PHASE 0 VEHICLE
					if (Constant.Truckpriority == true) {
						// Search for free TRUCK first then AGV second
						for (Truck x : Truckforce) {
							if (x.getphase() == 0) {

								// ALLOCATE DESTINATION TO TRUCK
								x.setdestination(Central.spawnfactdemand());

								// if allocated success
								x.setphase(1);
								Central.truckactive++;
								Central.truckinactive--;
								Central.cdcdemandcount--;
								Central.cdcdemandcountx++;
								notsuccess = false;
								break;
							}
						}
						for (AGV x : Agvforce) {
							if (x.getphase() == 0) {

								// ALLOCATE DESTINATION TO AGV
								x.setdestination(Central.spawnfactdemand());

								// if allocated success
								x.setphase(1);
								Central.agvactive++;
								Central.agvinactive--;
								Central.cdcdemandcount--;
								Central.cdcdemandcountx++;
								notsuccess = false;
								break;
							}
						}
						if (notsuccess == true) {
							int potential = 0;
							char potentialtype = 't';
							int disttemp = 1774;
							boolean found = false;
							for (int j = 0; j < Constant.TruckQty; j++) {
								if (Truckforce.get(j).getphase() == 4) {
									if (disttemp > Truckforce.get(j)
											.calculatedisttocdc()) {
										potential = j;
										potentialtype = 't';
										disttemp = Truckforce.get(j)
												.calculatedisttocdc();
										found = true;
									}
								}
							}
							for (int j = 0; j < Constant.AgvQty; j++) {
								if (Agvforce.get(j).getphase() == 4) {
									if (disttemp > Truckforce.get(j)
											.calculatedisttocdc()) {
										potential = j;
										potentialtype = 'a';
										disttemp = Truckforce.get(j)
												.calculatedisttocdc();
										found = true;
									}
								}
							}

							if (found == true && potentialtype == 't') {
								// ALLOCATE DESTINATION TO AGV
								Truckforce.get(potential).setdestination(
										Central.spawnfactdemand());

								// if allocated success
								// Truckforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (found == true && potentialtype == 'a') {
								// ALLOCATE DESTINATION TO AGV
								Agvforce.get(potential).setdestination(
										Central.spawnfactdemand());

								// if allocated success
								// Agvforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}
						}
						// IN EVENT WHERE THERE IS NO VEHICLE TO ALLOCATE TO
						// DO NOTHING BUT CONTINUE ON FIRST
					} else {
						// Search for free AGV first then TRUCK second
						for (AGV x : Agvforce) {
							if (x.getphase() == 0) {

								// ALLOCATE DESTINATION TO AGV
								x.setdestination(Central.spawnfactdemand());

								// if allocated success
								x.setphase(1);
								Central.agvactive++;
								Central.agvinactive--;
								Central.cdcdemandcount--;
								Central.cdcdemandcountx++;
								notsuccess = false;
								break;
							}
						}
						for (Truck x : Truckforce) {
							if (x.getphase() == 0) {

								// ALLOCATE DESTINATION TO TRUCK
								x.setdestination(Central.spawnfactdemand());

								// if allocated success
								x.setphase(1);
								Central.truckactive++;
								Central.truckinactive--;
								Central.cdcdemandcount--;
								Central.cdcdemandcountx++;
								notsuccess = false;
								break;
							}
						}
						if (notsuccess = true) {
							int potential = 0;
							char potentialtype = 't';
							int disttemp = 1774;
							boolean found = false;
							for (int j = 0; j < Constant.TruckQty; j++) {
								if (Truckforce.get(j).getphase() == 4) {
									if (disttemp > Truckforce.get(j)
											.calculatedisttocdc()) {
										potential = j;
										potentialtype = 't';
										disttemp = Truckforce.get(j)
												.calculatedisttocdc();
										found = true;
									}
								}
							}
							for (int j = 0; j < Constant.AgvQty; j++) {
								if (Agvforce.get(j).getphase() == 4) {
									if (disttemp > Truckforce.get(j)
											.calculatedisttocdc()) {
										potential = j;
										potentialtype = 'a';
										disttemp = Truckforce.get(j)
												.calculatedisttocdc();
										found = true;
									}
								}
							}

							if (found == true && potentialtype == 't') {
								// ALLOCATE DESTINATION TO AGV
								Truckforce.get(potential).setdestination(
										Central.spawnfactdemand());

								// if allocated success
								// Truckforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (found == true && potentialtype == 'a') {
								// ALLOCATE DESTINATION TO AGV
								Agvforce.get(potential).setdestination(
										Central.spawnfactdemand());

								// if allocated success
								// Agvforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}
						}
					}
				}
				// ONLY TRUCK
				if (Constant.AgvMode == false && Constant.TruckMode == true) {
					for (Truck x : Truckforce) {
						if (x.getphase() == 0) {

							// ALLOCATE DESTINATION TO TRUCK
							x.setdestination(Central.spawnfactdemand());

							// if allocated success
							x.setphase(1);
							Central.truckactive++;
							Central.truckinactive--;
							Central.cdcdemandcount--;
							Central.cdcdemandcountx++;
							notsuccess = false;
							break;
						}
					}
					if (notsuccess == true) {
						int potential = 0;
						char potentialtype = 't';
						int disttemp = 1774;
						boolean found = false;
						for (int j = 0; j < Constant.TruckQty; j++) {
							if (Truckforce.get(j).getphase() == 4) {
								if (disttemp > Truckforce.get(j)
										.calculatedisttocdc()) {
									potential = j;
									potentialtype = 't';
									disttemp = Truckforce.get(j)
											.calculatedisttocdc();
									found = true;
								}
							}
						}

						if (found == true) {
							// ALLOCATE DESTINATION TO AGV
							Truckforce.get(potential).setdestination(
									Central.spawnfactdemand());

							// if allocated success
							// Truckforce.get(potential).setphase(1);

							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
						}
					}
				}
				// ONLY AGV
				if (Constant.AgvMode == true && Constant.TruckMode == false) {
					for (AGV x : Agvforce) {
						if (x.getphase() == 0) {

							// ALLOCATE DESTINATION TO AGV
							x.setdestination(Central.spawnfactdemand());

							// if allocated success
							x.setphase(1);
							Central.agvactive++;
							Central.agvinactive--;
							Central.cdcdemandcount--;
							Central.cdcdemandcountx++;
							notsuccess = false;
							break;
						}
					}
					if (notsuccess == true) {
						int potential = 0;
						char potentialtype = 't';
						int disttemp = 1774;
						boolean found = false;
						for (int j = 0; j < Constant.AgvQty; j++) {
							if (Agvforce.get(j).getphase() == 4) {
								if (disttemp > Truckforce.get(j)
										.calculatedisttocdc()) {
									potential = j;
									potentialtype = 'a';
									disttemp = Truckforce.get(j)
											.calculatedisttocdc();
									found = true;
								}
							}
						}

						if (found == true) {
							// ALLOCATE DESTINATION TO AGV
							Agvforce.get(potential).setdestination(
									Central.spawnfactdemand());

							// if allocated success
							// Agvforce.get(potential).setphase(1);

							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
						}
					}
				}
				notsuccess = false;
			}

			// ///////////////////////////////ALLOCATE VEHICLE FOR FACTORY
			// NORMAL LIST////////////////////////////////////
			for (int i = 0; i < 30; i++) {
				temp = Central.factdemandcount[i];
				for (int j = 0; j < temp; j++) {

					notsuccess = true;
					// FOR FACT IDEAL CASE GET PHASE 4 VEHICLE CLOSE BY
					if (Constant.AgvMode == true && Constant.TruckMode == true) {
						if (Constant.Truckpriority == true) {

							int potential = 0;
							char potentialtype = 't';
							int disttemp = Central.factdistance[i];
							boolean found = false;
							for (int k = 0; k < Constant.TruckQty; k++) {
								if (Truckforce.get(k).getphase() == 4) {
									if (Truckforce.get(k)
											.calculatedisttofact(i) > 0) {
										if (disttemp > Truckforce.get(k)
												.calculatedisttofact(i)) {
											potential = k;
											potentialtype = 't';
											disttemp = Truckforce.get(k)
													.calculatedisttofact(i);
											found = true;
										}
									}
								}
							}
							for (int k = 0; k < Constant.AgvQty; k++) {
								if (Agvforce.get(k).getphase() == 4) {
									if (Agvforce.get(k).calculatedisttofact(i) > 0) {
										if (disttemp > Agvforce.get(k)
												.calculatedisttofact(i)) {
											potential = k;
											potentialtype = 't';
											disttemp = Agvforce.get(k)
													.calculatedisttofact(i);
											found = true;
										}
									}
								}
							}

							if (found == true && potentialtype == 't') {
								// ALLOCATE DESTINATION TO AGV
								Truckforce.get(potential).setdestination(i);

								// if allocated success
								Truckforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (found == true && potentialtype == 'a') {
								// ALLOCATE DESTINATION TO AGV
								Agvforce.get(potential).setdestination(i);

								// if allocated success
								Agvforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (notsuccess == true) {
								for (Truck x : Truckforce) {
									if (x.getphase() == 0) {

										// ALLOCATE DESTINATION TO TRUCK
										x.setdestination(i);

										// if allocated success
										x.setphase(1);
										Central.truckactive++;
										Central.truckinactive--;
										Central.factdemandcount[i]--;
										Central.factdemandcountx[i]++;
										notsuccess = false;
										break;
									}
								}
							}
							if (notsuccess == true) {
								for (AGV x : Agvforce) {
									if (x.getphase() == 0) {

										// ALLOCATE DESTINATION TO TRUCK
										x.setdestination(i);

										// if allocated success
										x.setphase(1);
										Central.agvactive++;
										Central.agvinactive--;
										Central.factdemandcount[i]--;
										Central.factdemandcountx[i]++;
										notsuccess = false;
										break;
									}
								}
							}
						} else {
							int potential = 0;
							char potentialtype = 't';
							int disttemp = Central.factdistance[i];
							boolean found = false;
							for (int k = 0; k < Constant.TruckQty; k++) {
								if (Truckforce.get(k).getphase() == 4) {
									if (Truckforce.get(k)
											.calculatedisttofact(i) > 0) {
										if (disttemp > Truckforce.get(k)
												.calculatedisttofact(i)) {
											potential = k;
											potentialtype = 't';
											disttemp = Truckforce.get(k)
													.calculatedisttofact(i);
											found = true;
										}
									}
								}
							}
							for (int k = 0; k < Constant.AgvQty; k++) {
								if (Agvforce.get(k).getphase() == 4) {
									if (Agvforce.get(k).calculatedisttofact(i) > 0) {
										if (disttemp > Agvforce.get(k)
												.calculatedisttofact(i)) {
											potential = k;
											potentialtype = 't';
											disttemp = Agvforce.get(k)
													.calculatedisttofact(i);
											found = true;
										}
									}
								}
							}

							if (found == true && potentialtype == 't') {
								// ALLOCATE DESTINATION TO AGV
								Truckforce.get(potential).setdestination(i);

								// if allocated success
								Truckforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (found == true && potentialtype == 'a') {
								// ALLOCATE DESTINATION TO AGV
								Agvforce.get(potential).setdestination(i);

								// if allocated success
								Agvforce.get(potential).setphase(1);

								Central.cdcprioritycount--;
								Central.cdcprioritycountx++;
								notsuccess = false;
							}

							if (notsuccess == true) {
								for (AGV x : Agvforce) {
									if (x.getphase() == 0) {

										// ALLOCATE DESTINATION TO TRUCK
										x.setdestination(i);

										// if allocated success
										x.setphase(1);
										Central.agvactive++;
										Central.agvinactive--;
										Central.factdemandcount[i]--;
										Central.factdemandcountx[i]++;
										notsuccess = false;
										break;
									}
								}
							}
							if (notsuccess == true) {
								for (Truck x : Truckforce) {
									if (x.getphase() == 0) {

										// ALLOCATE DESTINATION TO TRUCK
										x.setdestination(i);

										// if allocated success
										x.setphase(1);
										Central.truckactive++;
										Central.truckinactive--;
										Central.factdemandcount[i]--;
										Central.factdemandcountx[i]++;
										notsuccess = false;
										break;
									}
								}
							}
						}
					}

					if (Constant.AgvMode == false && Constant.TruckMode == true) {
						int potential = 0;
						char potentialtype = 't';
						int disttemp = Central.factdistance[i];
						boolean found = false;
						for (int k = 0; k < Constant.TruckQty; k++) {
							if (Truckforce.get(k).getphase() == 4) {
								if (Truckforce.get(k).calculatedisttofact(i) > 0) {
									if (disttemp > Truckforce.get(k)
											.calculatedisttofact(i)) {
										potential = k;
										potentialtype = 't';
										disttemp = Truckforce.get(k)
												.calculatedisttofact(i);
										found = true;
									}
								}
							}
						}

						if (found == true && potentialtype == 't') {
							// ALLOCATE DESTINATION TO AGV
							Truckforce.get(potential).setdestination(i);

							// if allocated success
							Truckforce.get(potential).setphase(1);

							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
						}

						if (notsuccess == true) {
							for (Truck x : Truckforce) {
								if (x.getphase() == 0) {

									// ALLOCATE DESTINATION TO TRUCK
									x.setdestination(i);

									// if allocated success
									x.setphase(1);
									Central.truckactive++;
									Central.truckinactive--;
									Central.factdemandcount[i]--;
									Central.factdemandcountx[i]++;
									notsuccess = false;
									break;
								}
							}
						}
					}

					if (Constant.AgvMode == true && Constant.TruckMode == false) {
						int potential = 0;
						char potentialtype = 't';
						int disttemp = Central.factdistance[i];
						boolean found = false;
						for (int k = 0; k < Constant.AgvQty; k++) {
							if (Agvforce.get(k).getphase() == 4) {
								if (Agvforce.get(k).calculatedisttofact(i) > 0) {
									if (disttemp > Agvforce.get(k)
											.calculatedisttofact(i)) {
										potential = k;
										potentialtype = 't';
										disttemp = Agvforce.get(k)
												.calculatedisttofact(i);
										found = true;
									}
								}
							}
						}

						if (found == true && potentialtype == 'a') {
							// ALLOCATE DESTINATION TO AGV
							Agvforce.get(potential).setdestination(i);

							// if allocated success
							Agvforce.get(potential).setphase(1);

							Central.cdcprioritycount--;
							Central.cdcprioritycountx++;
							notsuccess = false;
						}

						if (notsuccess == true) {
							for (AGV x : Agvforce) {
								if (x.getphase() == 0) {

									// ALLOCATE DESTINATION TO TRUCK
									x.setdestination(i);

									// if allocated success
									x.setphase(1);
									Central.agvactive++;
									Central.agvinactive--;
									Central.factdemandcount[i]--;
									Central.factdemandcountx[i]++;
									notsuccess = false;
									break;
								}
							}
						}
					}

				}
				notsuccess = false;
			}

			// ///////////////////////////////
			// TRUCK //
			// ///////////////////////////////

			if (Constant.TruckMode == true) {
				for (Truck x : Truckforce) {
					int distance = 0; // in pixels

					if (x.getphase() == 0) {
						x.setdestination(99);
						x.setpreviousphase(x.getphase());
						x.setphase(0);
					}
					// PHASE 1
					if (x.getphase() == 1) {

						if (x.position == Factoryforce.get(x.getdestination())
								.getlocation()) {
							for (int i = 0; i < Constant.Factbay; i++) {
								if (Central.factdock[x.getdestination()][i] == 0) {

									// ENTER THE FACTORY
									// RMB TO ADD DIST

									x.setpreviousphase(x.getphase());
									x.setphase(6);
									break;
								}
							}
						}

						if (x.position != Factoryforce.get(x.getdestination())
								.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewocargo * distance
								* (1500 / 650));
					}
					if (x.getphase() == 2) {

						if (x.position == CDCforce.getlocation()) {
							for (int i = 0; i < Constant.CDCbay; i++) {
								if (Central.cdcdock[i] == 0) {

									// ENTER THE CDC
									// RMB TO ADD DIST

									x.setpreviousphase(x.getphase());
									x.setphase(6);
									break;
								}
							}
						}

						if (x.position != CDCforce.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewcargo * distance
								* (1500 / 650));
					}
					if (x.getphase() == 3) {

						if (x.position == Factoryforce.get(x.getdestination())
								.getlocation()) {
							for (int i = 0; i < Constant.Factbay; i++) {
								if (Central.factdock[x.getdestination()][i] == 0) {

									// ENTER THE FACTORY
									// RMB TO ADD DIST

									x.setpreviousphase(x.getphase());
									x.setphase(6);
									break;
								}
							}
						}

						if (x.position != Factoryforce.get(x.getdestination())
								.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewcargo * distance
								* (1500 / 650));
					}
					if (x.getphase() == 4) {

						if (x.position == CDCforce.getlocation()) {
							x.setpreviousphase(x.getphase());
							x.setphase(0);
							break;

						}

						if (x.position != CDCforce.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewocargo * distance
								* (1500 / 650));
					}

					if (x.getphase() == 5) {

						if (x.position == CDCforce.getlocation()) {
							for (int i = 0; i < Constant.CDCbay; i++) {
								if (Central.cdcdock[i] == 0) {

									// ENTER THE CDC
									// RMB TO ADD DIST

									x.setpreviousphase(x.getphase());
									x.setphase(6);
									break;
								}
							}
						}

						if (x.position != CDCforce.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewocargo * distance
								* (1500 / 650));
					}
					if (x.getphase() == 6) {
						if (x.getpreviousphase() == 0) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.CDCcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(3);
							}
						}
						if (x.getpreviousphase() == 1) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.Factcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(2);
							}
						}
						if (x.getpreviousphase() == 2) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.CDCcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(0);
							}
						}
						if (x.getpreviousphase() == 3) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.Factcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(4);
							}
						}
						if (x.getpreviousphase() == 5) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.CDCcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(3);
							}
						}
					}
				}

			}

			// ///////////////////////////////
			// AGV //
			// ///////////////////////////////

			if (Constant.AgvMode == true) {

				for (AGV x : Agvforce) {
					int distance = 0; // in pixels

					if (x.getphase() == 0) {
						x.setdestination(99);
						x.setpreviousphase(x.getphase());
						x.setphase(0);
					}
					// PHASE 1
					if (x.getphase() == 1) {

						if (x.position == Factoryforce.get(x.getdestination())
								.getlocation()) {
							for (int i = 0; i < Constant.Factbay; i++) {
								if (Central.factdock[x.getdestination()][i] == 0) {

									// ENTER THE FACTORY
									// RMB TO ADD DIST

									x.setpreviousphase(x.getphase());
									x.setphase(6);
									break;
								}
							}
						}

						if (x.position != Factoryforce.get(x.getdestination())
								.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewocargo * distance
								* (1500 / 650));
					}
					if (x.getphase() == 2) {

						if (x.position == CDCforce.getlocation()) {
							for (int i = 0; i < Constant.CDCbay; i++) {
								if (Central.cdcdock[i] == 0) {

									// ENTER THE CDC
									// RMB TO ADD DIST

									x.setpreviousphase(x.getphase());
									x.setphase(6);
									break;
								}
							}
						}

						if (x.position != CDCforce.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewcargo * distance
								* (1500 / 650));
					}
					if (x.getphase() == 3) {

						if (x.position == Factoryforce.get(x.getdestination())
								.getlocation()) {
							for (int i = 0; i < Constant.Factbay; i++) {
								if (Central.factdock[x.getdestination()][i] == 0) {

									// ENTER THE FACTORY
									// RMB TO ADD DIST

									x.setpreviousphase(x.getphase());
									x.setphase(6);
									break;
								}
							}
						}

						if (x.position != Factoryforce.get(x.getdestination())
								.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewcargo * distance
								* (1500 / 650));
					}
					if (x.getphase() == 4) {

						if (x.position == CDCforce.getlocation()) {
							x.setpreviousphase(x.getphase());
							x.setphase(0);
							break;

						}

						if (x.position != CDCforce.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewocargo * distance
								* (1500 / 650));
					}

					if (x.getphase() == 5) {

						if (x.position == CDCforce.getlocation()) {
							for (int i = 0; i < Constant.CDCbay; i++) {
								if (Central.cdcdock[i] == 0) {

									// ENTER THE CDC
									// RMB TO ADD DIST

									x.setpreviousphase(x.getphase());
									x.setphase(6);
									break;
								}
							}
						}

						if (x.position != CDCforce.getlocation()) {
							// CALCULATE NEW LOCATION
						}

						// Fuel
						x.addfuel(Constant.Truckfewocargo * distance
								* (1500 / 650));
					}
					if (x.getphase() == 6) {
						if (x.getpreviousphase() == 0) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.CDCcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(3);
							}
						}
						if (x.getpreviousphase() == 1) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.Factcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(2);
							}
						}
						if (x.getpreviousphase() == 2) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.CDCcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(0);
							}
						}
						if (x.getpreviousphase() == 3) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.Factcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(4);
							}
						}
						if (x.getpreviousphase() == 5) {
							// if waiting time reach
							if (Clock.time >= x.getloadingclock()
									+ (Constant.CDCcargoturnover * 60)) {
								x.setpreviousphase(x.getphase());
								x.setphase(3);
							}
						}
					}
				}
			}

			// FOR CALCULATING THE TIME TAKEN TO DO ALL THE SIMULATION
			// CALCULATIONS
			simTime_ms = (long) (System.currentTimeMillis() - timeBeforeSim_ms);
			// System.out.println(simTime_ms);
			long timeBeforeSave_ms = System.currentTimeMillis();

			// FOR EVERY 15min, 30min, 1h and 1day, TRIGGER SAVE_STATE INTO
			// RESULT
			// CLASS
			if ((int) Clock.time % 900 == 0) {
				if (demandtiming2 == 5) {
					Result.savestate(15);
					demandtiming2 = 1;
				} else
					demandtiming2++;
			}

			if ((int) Clock.time % 1800 == 0) {
				if (demandtiming2 == 5) {
					Result.savestate(30);
					demandtiming2 = 1;
				} else
					demandtiming2++;
			}

			if ((int) Clock.time % 3600 == 0) {
				if (demandtiming2 == 5) {
					Result.savestate(60);
					demandtiming2 = 1;
				} else
					demandtiming2++;
			}

			if ((int) Clock.time % 86400 == 0) {
				if (demandtiming2 == 5) {
					Result.savestate(24);
					demandtiming2 = 1;
				} else
					demandtiming2++;
			}

			// FOR CALCULATING THE TIME TAKEN TO DO ALL THE SIMULATION
			// CALCULATIONS
			// TOTALTIMES_MS SHOULD ALWAYS BE SMALLER THAN TIMESTEP_S
			saveTime_ms = (long) (System.currentTimeMillis() - timeBeforeSim_ms);
			// System.out.println(saveTime_ms);
			TotalTime_ms = simTime_ms + saveTime_ms;
			// System.out.println(TotalTime_ms);

			// PAINT ALL THE COMPONENTS BEFORE ADDING NEW TIME
			repaint();

			// CALCULATE THE NEW TIME BASED ON THE TIME SPEED
			Clock.time += Constant.TIMESTEP_S;
			Clock.clockupdate();

			// Check for end of simulation
			if (Clock.time >= Clock.calendtime()) {
				if (simuend == false) {
					Constant.suspended = true;
				}
			}

			// FOR SUSPENSION OF THREAD
			synchronized (this) {
				while (Constant.suspended) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.setName("Simulator");
			runner.start();
		}
	}

	public void stop() {
		runner = null;
	}

	public void suspend() {
		Constant.suspended = true;
	}

	public synchronized void resume() {
		System.out.println("Simulator resuming...");
		Constant.suspended = false;
		notify();
	}
}
