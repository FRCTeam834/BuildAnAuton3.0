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

public class EditFunction {
	/**
	 * Takes care of highlighting the point and moving the point
	 */
	public static void editDraw(){
		if(BuildAnAuton.indexHover != -1){ //Highlights the point
		BuildAnAuton.g2.setColor(Color.CYAN);
		BuildAnAuton.g2.fill(new Ellipse2D.Double(BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).x - 7, BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).y - 7, 14, 14));
		if(BuildAnAuton.dragging){ //Makes the point follow the mouse
		BuildAnAuton.pathPts.set(BuildAnAuton.indexHover, BuildAnAuton.mainPanel.getMousePosition());
		}
		}
	}
	/**
	 * Probably not needed, but forces the program to change the location of the point and
	 * forces the program to recalculate the point you are hovering over.
	 */
	public static void editTool(){
		BuildAnAuton.pathPts.set(BuildAnAuton.indexHover, BuildAnAuton.mainPanel.getMousePosition());
		BuildAnAuton.indexHover = -1;
	}
}
