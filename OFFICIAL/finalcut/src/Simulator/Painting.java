package Simulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Painting {

	//DRAWING VARIABLES
	static Color LIGHTGRAY = new Color(189,189,189);
	static Color DARKGREEN = new Color(56, 138, 30);
	
	static int introadwidth = 41;
	static int outroadwidth = 51;
	static int hzlength = 650;
	static int vrlength = 433;
		
	public static void paintroad(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Vertical
		//g.fillRect(200, 140, 40, 400);
		//g.fillRect(140, 140, 40, 400);
		
		//OUTER
		g2.setColor(LIGHTGRAY);
		g2.fillRect(200, 40, hzlength, outroadwidth);
		g2.fillRect(200, 40+60, hzlength, outroadwidth);
		
		g2.fillRect(85, 40+115, outroadwidth, vrlength);
		g2.fillRect(85+60, 40+115, outroadwidth, vrlength);
		
		
		
		/************/
		//INNER
		g2.setColor(Color.gray);
		g2.fillRect(200, 45, hzlength, introadwidth);
		g2.fillRect(200, 45+60, hzlength, introadwidth);
		
		g2.fillRect(90, 40+115, introadwidth, vrlength);
		g2.fillRect(90+60, 40+115, introadwidth, vrlength);
		
		
		/************/
		
		//DIVIDER
		g2.setColor(Color.white);
		g2.drawLine(200, 66, 200+hzlength, 66);
		g2.drawLine(200, 66+59, 200+hzlength, 66+59);
		g2.drawLine(85+25, 40+115, 85+25, 40+115+vrlength);
		g2.drawLine(85+51+9+25, 40+115, 85+51+9+25, 40+115+vrlength);
		
		
		
		//RIGHT TOP CURVE
		g2.setColor(LIGHTGRAY);
		g2.fillArc(200+hzlength-outroadwidth-5, 40, 111, 111, 270, 180);		
		g2.setColor(Color.gray);
		g2.fillArc(200+hzlength-outroadwidth-1, 40+5, 101, 101, 270, 180);
		g2.setColor(Color.white);
		g2.drawArc(200+hzlength-outroadwidth+20, 40+26, 59, 59, 270, 180);
		g2.setColor(LIGHTGRAY);
		g2.fillArc(200+hzlength-12, 40+46, 19, 19, 270, 180);
		g2.setColor(DARKGREEN);
		g2.fillArc(200+hzlength-8, 40+51, 9, 9, 270, 180);
		
		//LEFT TOP CURVE
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85, 40, 115*2, 115*2, 90, 90); //1
		g2.setColor(Color.gray);
		g2.fillArc(85+5, 40+5, (115-5)*2, (115-5)*2, 90, 90);//2
		g2.setColor(Color.white);
		g2.drawArc(85+25, 40+25+1, (115-25)*2, (115-25)*2, 90, 90);
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85+46, 40+46, (115-46)*2, (115-46)*2, 90, 90); //3
		g2.setColor(DARKGREEN);
		g2.fillArc(85+51, 40+51, (115-51)*2, (115-51)*2, 90, 90); //4
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85+60, 40+60, (115-60)*2, (115-60)*2, 90, 90);
		g2.setColor(Color.gray);
		g2.fillArc(85+65, 40+65, (115-65)*2, (115-65)*2, 90, 90);
		g2.setColor(Color.white);
		g2.drawArc(85+85, 40+85, (115-85)*2, (115-85)*2, 90, 90);
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85+106, 40+106, (115-106)*2, (115-106)*2, 90, 90);
		g2.setColor(DARKGREEN);
		g2.fillArc(85+111, 40+111, (115-111)*2, (115-111)*2, 90, 90);
		
		//LEFT BOTTOM CURVE
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85, 40+115+vrlength-56, 111, 111, 180, 180);		
		g2.setColor(Color.gray);
		g2.fillArc(85+5, 40+115+vrlength-52, 101, 101, 180, 180);
		g2.setColor(Color.white);
		g2.drawArc(85+5+20, 40+115+vrlength-32, 59+1, 59, 180, 180);
		g2.setColor(LIGHTGRAY);
		g2.fillArc(85+5+20+21, 40+115+vrlength-12, 19, 19, 180, 180);
		g2.setColor(DARKGREEN);
		g2.fillArc(85+5+20+21+5, 40+115+vrlength-8, 9, 9, 180, 180);
		
	}
	
	public static void paintfactoryqueue(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		int x = 425;
		int y = 225;
		
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(400, 195, 550, 440);
		
		g2.setColor(Color.WHITE);
		g2.fillRect(410, 205, 530, 420);
		
		//DRAWING FOR CDC DEMAND STOCK
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Calibri", Font.PLAIN, 11));
		g2.drawString("CDC", x, y+10);
		g2.drawString("N", x+57, y);
		g2.drawString("P", x+57+30, y);
		
		for(int i=0; i<Constant.CDCcargolimit; i++){
			System.out.println("hello");
			g2.setColor(Color.BLACK);
			g2.drawRect(x+50, y+5+(5*i), 20, 5);
		}
		
		for(int i=0; i<Constant.CDCcargolimit; i++){
			System.out.println("hello");
			g2.setColor(Color.BLACK);
			g2.drawRect(x+50+30, y+5+(5*i), 20, 5);
		}
		
		//DRAWING FOR FACTORY DEMAND STOCK
		for(int i=0; i<30; i++){
			
		}
		
	}
}
