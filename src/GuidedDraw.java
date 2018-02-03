import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

//import BuildAnAuton2.SelectedTool;
//import visualrobot.CommandSet;

import java.lang.Math;

//import visualrobot.CommandSet; This will be used to reference other classes

import java.net.URL;

public class GuidedDraw extends JFrame{
	public static JButton draw = new JButton("Add");
	public static JTextField angle = new JTextField (6);
	public static JTextField distance = new JTextField(6);
	public double identiFire = 0;
	GuidedDraw() {//Layout of Window
		setLayout(new FlowLayout());
		JLabel setDistance = new JLabel ("Distance");
		JLabel setAngle = new JLabel ("Angle");
		add(setDistance);
		add(distance);
		add(setAngle);
		add(angle);
		add(draw);
		setSize(100,175);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		draw.addActionListener((ActionEvent e1) -> {//Adds the point to the path
			double setDistance1 = Double.parseDouble(distance.getText());
			double setAngle1 = Double.parseDouble(angle.getText());
			double setAngle2 = Math.toRadians(360 - setAngle1);
			double displacement = ((double) setDistance1/BuildAnAuton.inchPerPixel);
			double displacementX = displacement * Math.cos(setAngle2);
			double displacementY = displacement * Math.sin(setAngle2);
			double finaldistX = BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x + displacementX;
			double finaldistY = BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y + displacementY;
			int finaldistx = (int) finaldistX;
			int finaldisty = (int) finaldistY;
			BuildAnAuton.pathPts.add(new Point(finaldistx, finaldisty));
			BuildAnAuton.speeds.add(BuildAnAuton.defaultSpeed);
			BuildAnAuton.turnSpeeds.add(BuildAnAuton.defaultTurnSpeed);
			if(BuildAnAuton.defaultSpeed >= 0){
			BuildAnAuton.backwards.add(false);
			}
			else if(BuildAnAuton.defaultSpeed < 0){
			BuildAnAuton.backwards.add(true);
			}
		});
	}
}