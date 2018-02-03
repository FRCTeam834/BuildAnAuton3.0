import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.lang.Math;

import visualrobot.CommandSet;

import java.net.URL;

public class AddFunction{
	public static double addAngle = 0; 
	public static double addDistance = 0;
	public static void addDraw(){
		BuildAnAuton.g2.setColor(Color.BLACK);
			BuildAnAuton.g2.drawLine((int) BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x, (int) BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y, BuildAnAuton.mainPanel.getMousePosition().x, BuildAnAuton.mainPanel.getMousePosition().y);
			addAngle = Math.atan2(BuildAnAuton.mainPanel.getMousePosition().y - BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y, BuildAnAuton.mainPanel.getMousePosition().x - BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x) * 180.0/Math.PI;
			addDistance = new Point((int) BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x, (int) BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y).distance(new Point(BuildAnAuton.mainPanel.getMousePosition().x, BuildAnAuton.mainPanel.getMousePosition().y));// * inchPerPixel;
		if(addAngle < 0) addAngle += 360;
		addAngle = addAngle == 0 ? 0 :360-addAngle;
		addAngle = Math.round(addAngle * 100.0) / 100.0; 
		addDistance = Math.round(addDistance * 100.0) / 100.0;
		//Tells you the information for your line
		BuildAnAuton.g2.drawString("Angle: " + addAngle + " degrees", 50, 65); //These two lines draw the strings in the lower left hand corner of the screen.
		BuildAnAuton.g2.drawString("Distance: " + addDistance + " in", 50, 50);
	}
	/*
	 * This does not currently work
	 */
	public static void addDraw2(){
			//Calculates the Angle
			double angle = Math.atan2(BuildAnAuton.mainPanel.getMousePosition().y-BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y, BuildAnAuton.mainPanel.getMousePosition().x-BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x)*180.0/Math.PI; //Actual angle between mouse and last point
			
			if(angle < 0) angle += 360; //Keeps angle from 0-360 degrees
			addAngle = Math.round(angle/45.0) * 45.0;
			
			//Horizontal Lines
			if(addAngle == 180 || addAngle == 0) {
				BuildAnAuton.g2.drawLine(BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x, BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y, BuildAnAuton.mainPanel.getMousePosition().x, BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y);
				addDistance = Math.abs(BuildAnAuton.mainPanel.getMousePosition().x - BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x) * (708/807);
			}
			//Vertical Lines
			else if (addAngle == 90 || addAngle == 270) {
				BuildAnAuton.g2.drawLine(BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x, BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y, BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x, BuildAnAuton.mainPanel.getMousePosition().y);
				addDistance = Math.abs(BuildAnAuton.mainPanel.getMousePosition().y-BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y) * (708/807);
			}
			//45 degree Lines
			else {
				double magnitude = Math.sqrt(Math.pow(BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x - BuildAnAuton.mainPanel.getMousePosition().x, 2) + Math.pow(BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y - BuildAnAuton.mainPanel.getMousePosition().y, 2));
				addDistance = magnitude * (708/807);
				double angleRadians = addAngle * Math.PI/180.0;
				BuildAnAuton.g2.drawLine(BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x, BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y, (int) (BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x + Math.cos(angleRadians) * magnitude), (int) (BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y + Math.sin(angleRadians) * magnitude));	
			}
	}
	public static void addTool(){
		//Adds the points and sets the speed
		int mouseX = BuildAnAuton.mainPanel.getMousePosition().x;
		int mouseY = BuildAnAuton.mainPanel.getMousePosition().y;
		BuildAnAuton.pathPts.add(new Point(mouseX, mouseY));
		BuildAnAuton.speeds.add(BuildAnAuton.defaultSpeed);
		BuildAnAuton.turnSpeeds.add(BuildAnAuton.defaultTurnSpeed);
		if(BuildAnAuton.defaultSpeed >= 0){
			BuildAnAuton.backwards.add(false);
			}
		else if(BuildAnAuton.defaultSpeed < 0){
		BuildAnAuton.backwards.add(true);
	}
	}
}
