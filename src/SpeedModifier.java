import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import java.lang.Math;

import visualrobot.CommandSet;

import java.net.URL;

public class SpeedModifier extends JFrame{//Theoretically highlights the line
	SpeedModifier(){
		setVisible(false);
		JTextField indexSetter = new JTextField(6);
		JLabel indexLabel = new JLabel ("Line");
		JTextField speedSetter = new JTextField(6);
		JLabel speedLabel = new JLabel ("Speed (-0.5 to 0.5)");
		JButton setSpeed = new JButton("Set Speed");
		setSize(150,175);
		setLayout(new FlowLayout());
		add(indexLabel);
		add(indexSetter);
		add(speedLabel);
		add(speedSetter);
		add(setSpeed);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getRootPane().setDefaultButton(setSpeed);
		setSpeed.addActionListener((ActionEvent e) -> {
			int indexSet = (int) Double.parseDouble(indexSetter.getText());
			double speedSet = Double.parseDouble(speedSetter.getText());
			BuildAnAuton.speeds.set(indexSet, speedSet);
			indexSetter.setText("");
			speedSetter.setText("");
		});	
	}
}
