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

public class SpeedModifier {//Theoretically highlights the line
	public static int lineIndex = -1;
	public static void speedDraw(){
		double ldist = Integer.MAX_VALUE; //This tells defines the path points
	    for(int l = 0; l < BuildAnAuton.pathPts.size() - 1; l++){// This draws a new line if the endpoints have changed.
	    	double d = Line2D.ptSegDist(BuildAnAuton.pathPts.get(l).x, BuildAnAuton.pathPts.get(l).y, BuildAnAuton.pathPts.get(l + 1).x, BuildAnAuton.pathPts.get(l + 1).y, BuildAnAuton.mainPanel.getMousePosition().x, BuildAnAuton.mainPanel.getMousePosition().y);
	    	if(d < BuildAnAuton.minDistance)
	    	{
	    		BuildAnAuton.minDistance = d;
	    		lineIndex = l;
	    		BuildAnAuton.mainPanel.repaint();
	    		BuildAnAuton.g2.setColor(Color.MAGENTA);
	    		BuildAnAuton.g2.drawLine(BuildAnAuton.pathPts.get(l).x, BuildAnAuton.pathPts.get(l).y, BuildAnAuton.pathPts.get(l + 1).x, BuildAnAuton.pathPts.get(l + 1).y);
	    		break;
	    	}
	    	else{
	    		lineIndex = -1;
	    		break;
	    	}
	    }
	}
	public static void speedTool(){//Assigns the speed
		int tempIndex = lineIndex;
		String input = JOptionPane.showInputDialog(null, "Speed (-0.5 to 0.5): ");
		if(input == null || input == "") return;
		double val = Double.parseDouble(input);
		if(Math.abs(val) > 0.5) val = BuildAnAuton.defaultSpeed;
		BuildAnAuton.speeds.set(tempIndex, val);
		if(BuildAnAuton.defaultSpeed >= 0){
			BuildAnAuton.backwards.set(tempIndex, false);
		}
		else if(BuildAnAuton.defaultSpeed < 0){
			BuildAnAuton.backwards.set(tempIndex, true);
		}
	//	System.out.println(Double.toString(BuildAnAuton.speeds.get(tempIndex)));
	}
}
