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
import javax.swing.ImageIcon;
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
/**
 * This is an rewritten version of BuildAnAuton2.0
 * This is modular, and you can add whatever functions you like
 * @author Will Corvino
 * @author Ryan Davis
 * For reference, see BuildAnAuton2.0
 */
public class BuildAnAuton extends JFrame implements MouseListener, KeyListener{
	public static ArrayList<Point> pathPts = new ArrayList<Point>();
	public static ArrayList<Double> turnSpeeds = new ArrayList<Double>();
	public static ArrayList<Double> speeds = new ArrayList<Double>();
	public static ArrayList<Boolean> backwards = new ArrayList<Boolean>();
	
	public static int indexGet = 0;
	public static int indexDraw = 0;
	public static int indexHover = -1;
	
	public static boolean dragging = false;
	Point refPoint = new Point(0,0);
	
	static JScrollPane scrollPane = new JScrollPane();
	static GuidedDraw drawWindow = new GuidedDraw();
	static SpeedModifier speedWindow = new SpeedModifier();
	public static HashMap<Integer, Boolean> keys = new HashMap<>();
	private boolean controlPressed = false;
	
	static CommandEditor cmdEditor;
	static CommandSet[] commands = new CommandSet[100]; //100 is the maximum number of points you can have
	
	static double initialAngle;
	
	static String teamNumber = "";
	
	static double defaultSpeed = 0.5;
	static double defaultTurnSpeed = 0.5;
	
	public static int mousePositionX;
	public static int mousePositionY;
	
	//inchPerPixel is 708/807 (Inches on the field/Pixels in the image)
	public final static double inchPerPixel = 708d/807d;
	public static double minDistance = 10; //The minimum distance where the point becomes highlighted (EDIT, SELECT, DELETE, TURNSPEED)
	
	//Tools that allow you to manipulate the path/actions
	static JToolBar toolbar = new JToolBar(); 
		static JButton add = new JButton("Add");
		static JButton autodraw = new JButton("Auto Draw");
		static JButton edit = new JButton("Edit");
		static JButton select = new JButton("Select");
		static JButton delete = new JButton("Delete");
		static JButton restart = new JButton("Restart");
		static JButton speed = new JButton("Speed");
		static JButton turnSpeed = new JButton("Turn Speed");
		static JButton mirror = new JButton("Mirror");
		static JButton translate = new JButton("Translate");
		//Array of tools, allows program to disable/enable all of the tools
	static JButton[] tools = {add, autodraw, edit, select, delete, restart, speed, turnSpeed, translate};
	
	JFileChooser fs = new JFileChooser();
	
	//Menu options for file actions and settings
	static JMenuBar menu = new JMenuBar();
	static JMenu file = new JMenu("File");
		public static JMenuItem save = new JMenuItem("Save");
		public static JMenuItem load = new JMenuItem("Load");
		public static JMenuItem export = new JMenuItem("Export");
		
	static JMenu settings = new JMenu("Settings");
		public static JMenuItem setDefaultSpeed = new JMenuItem("Set Default Speed");
		public static JMenuItem setDefaultTurnSpeed = new JMenuItem("Set Default Turn Speed");
		public static JMenuItem setInitialAngle = new JMenuItem("Set Initial Angle");
		public static JMenuItem setTeamNumber= new JMenuItem("Set Team Number");
		public static JCheckBoxMenuItem snapToPoints = new JCheckBoxMenuItem("Snap to Existing Points");
		
	//Helps with code readability and keeps track of selected tool
	public enum SelectedTool {
		NONE,
		ADD,
		AUTODRAW,
		SELECT,
		EDIT,
		DEL,
		SPEED,
		TURNSPEED,
		TRANSLATE,
	}
	static SelectedTool tool = SelectedTool.NONE;
	public static Graphics2D g2;
	//Draw the main frame
	public static JComponent mainPanel = new JComponent(){
		public void paintComponent(Graphics g) { //Paint component allows for the program to draw where the robot should go, and also in general draws everything
				g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setColor(Color.BLACK);
					g2.setStroke(new BasicStroke(3));
			g2.drawImage(field, 0, 0, null); //Draws image of field
			indexGet = pathPts.size() - 1; //This index is used to get the coordinates of the current point
			//Draws for each function.
			if(tool == SelectedTool.ADD && mainPanel.getMousePosition() != null){
				//Snaps angles if shift is enabled
				if(keys.equals(KeyEvent.VK_SHIFT)) {
					if(indexGet >= 0){
						AddFunction.addDraw2();
					}
				}
				else{
					if(indexGet >= 0){
						AddFunction.addDraw();
					}
				}
			}
			if(tool == SelectedTool.AUTODRAW){
			//	drawWindow.autoDraw(); This is not needed as the window is toggled on and off
			}
			if(tool == SelectedTool.EDIT && mainPanel.getMousePosition() != null){
				EditFunction.editDraw();
			}
			if(tool == SelectedTool.SELECT && mainPanel.getMousePosition() != null){
				PointCommands.selectDraw();
			}
			if(tool == SelectedTool.DEL && mainPanel.getMousePosition() != null){
				DeleteFunction.deleteDraw();
			}
			if(tool == SelectedTool.SPEED && mainPanel.getMousePosition() != null){
			//	SpeedModifier.speedDraw();
			}
			if(tool == SelectedTool.TURNSPEED && mainPanel.getMousePosition() != null){
				TurnSpeedModifier.turnSpeedDraw();
			}
			//This section draws the lines
			if(tool != SelectedTool.SPEED){
				SpeedModifier.lineIndex = -1;
			}
			for(indexDraw = 1; indexDraw <= pathPts.size() - 1; indexDraw++){
				if(indexDraw >=1 && pathPts.isEmpty() == false && speeds.get(indexDraw - 1) < 0 && indexDraw != SpeedModifier.lineIndex){//Changes the lines to red if the speed is backwards
					BuildAnAuton.g2.setColor(Color.RED);
					BuildAnAuton.g2.drawLine(pathPts.get(indexDraw - 1).x, pathPts.get(indexDraw - 1).y, pathPts.get(indexDraw).x, pathPts.get(indexDraw).y);
				}
				else if(indexDraw >= 1 && pathPts.isEmpty() == false && speeds.get(indexDraw - 1) > 0 && indexDraw != SpeedModifier.lineIndex){
					BuildAnAuton.g2.setColor(Color.BLACK);
					BuildAnAuton.g2.drawLine(pathPts.get(indexDraw - 1).x, pathPts.get(indexDraw - 1).y, pathPts.get(indexDraw).x, pathPts.get(indexDraw).y);
				}
			}
			//Draws the points
			for(indexDraw = 0; indexDraw <= pathPts.size() - 1; indexDraw++){
				if(indexDraw == 0){//Draws the starting point
					BuildAnAuton.g2.setColor(Color.GREEN);
					BuildAnAuton.g2.fill(new Ellipse2D.Double(pathPts.get(indexDraw).x - 5, pathPts.get(indexDraw).y - 5, 10, 10));
				}
				else if(indexDraw >= 1){
					BuildAnAuton.g2.setColor(Color.BLUE);
					BuildAnAuton.g2.fill(new Ellipse2D.Double(pathPts.get(indexDraw).x - 5, pathPts.get(indexDraw).y - 5, 10, 10));
				}
			}
			//Determines what point you are hovering over
			if(mainPanel.getMousePosition() != null && pathPts.isEmpty() == false && !dragging && tool != SelectedTool.AUTODRAW && tool != SelectedTool.TRANSLATE){
				for(int indexHoverCheck = 0; indexHoverCheck <= pathPts.size(); indexHoverCheck++){
					//Distance Formula
					double z = Math.sqrt(Math.pow(mainPanel.getMousePosition().x - pathPts.get(indexHoverCheck).x, 2) + Math.pow(mainPanel.getMousePosition().y - pathPts.get(indexHoverCheck).y, 2));
					//Determines if you are close enough to the point
					if(z <= minDistance){
						indexHover = indexHoverCheck;
						break;
					}
					else if(indexHoverCheck == pathPts.size() - 1){
						indexHover = -1;
						break;
					}
					else{
						indexHover = -1;
					}
				}
			}
		};
	};
		static BufferedImage field; //The screen
	public BuildAnAuton() {
		//Sets the image that the field variable refers to
		try {
			URL ImageURL = BuildAnAuton.class.getResource("field2018.png");
			if(ImageURL != null) {
					field = ImageIO.read(ImageURL);
				

			}
			else{
				field = ImageIO.read(new File("field2018.png"));

			}

			}
			catch (IOException e) {
				e.printStackTrace();

			}
		//Creates the starting point
		if(pathPts.isEmpty()){
			pathPts.add(new Point(field.getWidth()/2, field.getHeight()/2));
		}
		
		scrollPane.setViewportView(mainPanel);
		scrollPane.setPreferredSize(new Dimension(field.getWidth()+4, field.getHeight()+4));
		mainPanel.setPreferredSize(new Dimension(field.getWidth(), field.getHeight()));
		scrollPane.addMouseListener(this);
		
		toolbar.add(add);
		//Disabled because curves aren't accurate.
//		toolbar.add(add2);
		toolbar.add(autodraw);
		toolbar.add(edit);
		toolbar.add(select);
		toolbar.add(delete);
		toolbar.add(speed);
		toolbar.add(turnSpeed);
		toolbar.add(mirror);
		toolbar.add(translate);
		toolbar.add(restart);
		
		file.add(save);
		file.add(load);
		file.add(export);
		menu.add(file);
		
		settings.add(setDefaultSpeed);
		settings.add(setDefaultTurnSpeed);
		settings.add(setInitialAngle);
		settings.add(setTeamNumber);
		snapToPoints.setSelected(true);
		settings.add(snapToPoints);
		menu.add(settings);
			
		speeds.add(defaultSpeed); 
		turnSpeeds.add(defaultTurnSpeed);
		
		//This section defines what occurs when the tool buttons are selected
		add.addActionListener((ActionEvent mouseClicked) -> { //Add tool
			for(JButton buttonSet: tools){
				buttonSet.setEnabled(true);
			}
			add.setEnabled(false);
			drawWindow.setVisible(false);
			tool = SelectedTool.ADD;
			Thread t = new Thread(() ->{
				while(tool == SelectedTool.ADD) {
					mainPanel.repaint();
					try {
						Thread.sleep(20);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			t.start(); //Restarts the thread so that the tool stays enabled
		});
		
		autodraw.addActionListener((ActionEvent mouseClicked) -> {//Auto Draw Tool
			for(JButton buttonSet: tools){
				buttonSet.setEnabled(true);
			}
			autodraw.setEnabled(false);
			drawWindow.setVisible(true);
			/*
			 * Unfortunately, there is not a less messy way to set the window to visible if the autodraw function
			 * is enable
			 * Sorry for the inconvenience
			 * Will
			 */
			tool = SelectedTool.AUTODRAW;
			Thread t = new Thread(() ->{
				while(tool == SelectedTool.AUTODRAW) {
					mainPanel.repaint();
					try {
						Thread.sleep(20);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			t.start();
		});
		
		edit.addActionListener((ActionEvent mouseClicked) -> {//Edit Tool
			for(JButton buttonSet: tools){
				buttonSet.setEnabled(true);
			}
			edit.setEnabled(false);
			drawWindow.setVisible(false);
			tool = SelectedTool.EDIT;
			Thread t = new Thread(() ->{
				while(tool == SelectedTool.EDIT) {
					mainPanel.repaint();
					try {
						Thread.sleep(20);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			t.start();
		});
		
		select.addActionListener((ActionEvent mouseClicked) -> {//Select Tool
			for(JButton buttonSet: tools){
				buttonSet.setEnabled(true);
			}
			select.setEnabled(false);
			drawWindow.setVisible(false);
			tool = SelectedTool.SELECT;
			Thread t = new Thread(() ->{
				while(tool == SelectedTool.SELECT) {
					mainPanel.repaint();
					try {
						Thread.sleep(20);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			t.start();
		});
		
		delete.addActionListener((ActionEvent mouseClicked) -> {//Delete Tool
			for(JButton buttonSet: tools){
				buttonSet.setEnabled(true);
			}
			delete.setEnabled(false);
			drawWindow.setVisible(false);
			tool = SelectedTool.DEL;
			Thread t = new Thread(() ->{
				while(tool == SelectedTool.DEL) {
					mainPanel.repaint();
					try {
						Thread.sleep(20);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			t.start();
		});
		
		speed.addActionListener((ActionEvent mouseClicked) -> {//Speed Tool
			for(JButton buttonSet: tools){
				buttonSet.setEnabled(true);
			}
			speed.setEnabled(false);
			drawWindow.setVisible(false);
			speedWindow.setVisible(true);
			tool = SelectedTool.SPEED;
			Thread t = new Thread(() ->{
				while(tool == SelectedTool.SPEED) {
					mainPanel.repaint();
					try {
						Thread.sleep(20);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			t.start();
		});
		
		turnSpeed.addActionListener((ActionEvent mouseClicked) -> {//Turnspeed Tool
			for(JButton buttonSet: tools){
				buttonSet.setEnabled(true);
			}
			turnSpeed.setEnabled(false);
			drawWindow.setVisible(false);
			tool = SelectedTool.TURNSPEED;
			Thread t = new Thread(() ->{
				while(tool == SelectedTool.TURNSPEED) {
					mainPanel.repaint();
					try {
						Thread.sleep(20);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			t.start();
		});
		/* Mirror is unimplemented currently
		mirror.addActionListener((ActionEvent e) -> { //Mirror Tool
			AffineTransform t1 = new AffineTransform();

			int w = path.getBounds().x + path.getBounds().width/2;
			t1.translate(-2*w, 0);
			path.transform(t1);

			AffineTransform t2 = new AffineTransform();

			t2.scale(-1, 1);

			path.transform(t2);
			p.repaint();
		});
		*/
		translate.addActionListener((ActionEvent e) -> { //Translate Tool
			for(JButton b: tools) {
				b.setEnabled(true);
			}
			drawWindow.setVisible(false);
			translate.setEnabled(false);
			tool = SelectedTool.TRANSLATE;
			Thread t = new Thread(() ->{
				while(tool == SelectedTool.TRANSLATE) {
					mainPanel.repaint();
					try {
						Thread.sleep(20);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			t.start();
		});
		
		restart.addActionListener((ActionEvent e) -> {//Restarts the program (technically)
			while(pathPts.size() > 1){
				pathPts.remove(0);
			}
			pathPts.set(0, new Point(field.getWidth()/2, field.getHeight()/2));
		});
		
		export.addActionListener((ActionEvent e) -> {	//Export to Robot				
			Export exporter = new Export(pathPts, inchPerPixel, backwards, commands, true, speeds, turnSpeeds, initialAngle);
			if(teamNumber == "") {
				teamNumber = JOptionPane.showInputDialog(null,"Enter Team Number");
			}
			exporter.export(teamNumber);
			

		});
		
		save.addActionListener((ActionEvent e)  -> { //Save Program
			fs.showSaveDialog(this);
			File file = fs.getSelectedFile();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				oos.writeObject(backwards);
				oos.writeObject(speeds);
				oos.writeObject(commands);
				oos.writeObject(turnSpeeds);
				oos.writeObject(pathPts);
				oos.writeDouble(defaultSpeed);
				oos.writeDouble(defaultTurnSpeed);
				oos.writeDouble(initialAngle);
				oos.close();
			} catch (Exception e1) {
				e1.printStackTrace();

			}
		});
		
		load.addActionListener((ActionEvent e)  -> { //Loads File
			fs.showOpenDialog(this);
			File file = fs.getSelectedFile();
			try {
				System.out.println(file.getName());
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
				backwards = (ArrayList<Boolean>) ois.readObject();
				speeds = (ArrayList<Double>) ois.readObject();
				commands = (CommandSet[]) ois.readObject();
				turnSpeeds =  (ArrayList<Double>) ois.readObject();
				pathPts = (ArrayList<Point>) ois.readObject();
				defaultSpeed = ois.readDouble();
				defaultTurnSpeed = ois.readDouble();
				initialAngle = ois.readDouble();


				ois.close();
				mainPanel.repaint();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		setDefaultSpeed.addActionListener((ActionEvent e)  -> { //Sets default Speed
			String input = JOptionPane.showInputDialog(null, "Default Speed (0 to 1): ", defaultSpeed);
			if(input == null || input == "") return;
			double val = Double.parseDouble(input);
			if(val < 0) val = 0;
			else if(val > 1.0) val = 1.0;
			
			for(int i = 0; i < speeds.size(); i++)
				if(speeds.get(i) == defaultSpeed)
					speeds.set(i, val);
			
			defaultSpeed = val;
		});
		
		setDefaultTurnSpeed.addActionListener((ActionEvent e) -> { //Sets default Turn Speed
			String input = JOptionPane.showInputDialog(null, "Default Turn Speed (0 to 1): ", defaultTurnSpeed);
			if(input == null || input == "") return;
			double val = Double.parseDouble(input);
			if(val < 0) val = 0;
			else if(val > 1.0) val = 1.0;
			System.out.println(val);
			for(int i = 0; i < turnSpeeds.size(); i++)
				if(turnSpeeds.get(i) == defaultTurnSpeed)
					turnSpeeds.set(i, val);
			
			defaultTurnSpeed = val;
		});

		setInitialAngle.addActionListener((ActionEvent e)  -> { //Sets initial angle of the Robot
			String input = JOptionPane.showInputDialog(null, "Initial Angle: ", initialAngle);
			if(input == null || input == "") return;
			initialAngle = 360 - Double.parseDouble(input);
			while(Math.abs(initialAngle) > 360) {
				initialAngle -= Math.signum(initialAngle)*360;
			}
		});
		
		setTeamNumber.addActionListener((ActionEvent e) -> { //Sets Team Number
			String input = JOptionPane.showInputDialog(null, "Team Number: ", initialAngle);
			if(input == null || input == "") return;
			teamNumber = input;
			
		});
		
		for(JButton b: tools) {
			b.setFocusPainted(false);
			b.setFocusable(false);
			commands[0] = new CommandSet();
			//Sets the Border
			JPanel top = new JPanel();
			top.setLayout(new BorderLayout());
			
			top.add(toolbar, BorderLayout.SOUTH);
			top.add(menu, BorderLayout.NORTH);
			
			add(top, BorderLayout.NORTH);
			add(scrollPane, BorderLayout.CENTER);
//			add(prompt, BorderLayout.SOUTH);
		}
	}

	public void mousePressed(MouseEvent e){//These tools activate when the mouse is pressed
		if(tool == SelectedTool.SELECT){
			if(cmdEditor != null)
				cmdEditor.dispose();
			cmdEditor = new CommandEditor();
			cmdEditor.setVisible(true);
			cmdEditor.load(commands[indexHover]);
		}
		else if(tool == SelectedTool.ADD){
			AddFunction.addTool();
		}
		else if(tool == SelectedTool.DEL){
			DeleteFunction.deleteTool();
		}
		else if(tool == SelectedTool.SPEED){
		//	SpeedModifier.speedTool();
		}
		else if(tool == SelectedTool.TURNSPEED){
			TurnSpeedModifier.turnSpeedTool();
		}
		else if(tool == SelectedTool.EDIT){
				dragging = true;
		}
		else if(tool == SelectedTool.TRANSLATE){
			if(!dragging){
				refPoint = mainPanel.getMousePosition();
				System.out.println(refPoint);
			}
			dragging = true;
		}
	}
	public static void main(String[] args) { //Sets close operation and applies screen parameters
		BuildAnAuton frame = new BuildAnAuton();
		frame.pack();
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseReleased(MouseEvent e) {//These are tools that trigger when the mouse is released
		if(tool == SelectedTool.EDIT && dragging){
			EditFunction.editTool();
			dragging = false;
		}
		else if(tool == SelectedTool.TRANSLATE && dragging){
			Point mouse = mainPanel.getMousePosition();
			for(int indexTrans = 0; indexTrans <= pathPts.size() - 1; indexTrans++){
				pathPts.set(indexTrans, new Point(pathPts.get(indexTrans).x + mouse.x - refPoint.x, pathPts.get(indexTrans).y + mouse.y - refPoint.y));
			}//Translates the path
			mainPanel.repaint();
			dragging = false;
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	/*
	 * Zoom would be implemented here if it were useful
	 * :(
	 * Will
	 */
/**
 * This code was written by Ryan Davis.
 * This implements keybinds for the toobar and menu
 */
@Override
 public void keyTyped(KeyEvent e) {
 }
		
 	@Override
 	public void keyPressed(KeyEvent e) {
 		
 		int key = e.getKeyCode();
 		//Checks if button pressed is number (0x30 is hex decimal representation of keyboard key 0)
 		 		if (key >= 0x30 && key <= 0x39) {
 		 			try {
 		 				
 		 				//Checks if key pressed is 0
 		 				if (key == KeyEvent.VK_0)
 		 					key = 58;
 		 				
 		 				//Converts hex into keyboard key number value plus 1 and clicks toolbar button at the index
 		 				((JButton) toolbar.getComponent(key-49)).doClick();
 		 				
 		 			} catch (ArrayIndexOutOfBoundsException ex) {}
 		 		}
 		 		
 		 		if (key == KeyEvent.VK_CONTROL || key == KeyEvent.VK_META) {
 		 			controlPressed = true; //Shows control key is currently pressed
 		 		}
 		 		
 		 		//Control s check
 		 		if (key == KeyEvent.VK_S && controlPressed)
 		 			save.doClick();
 		 		
 		 		//Control o check
 		 		if (key == KeyEvent.VK_O && controlPressed)
 		 			load.doClick();
 		 		
 		 		//Control e check
 		 		if (key == KeyEvent.VK_E && controlPressed)
 		 			export.doClick();
 		 	}
 		 
 		 	@Override
 		 	public void keyReleased(KeyEvent e) {
 		 		int key = e.getKeyCode();
 		 		
 		 		if (key == KeyEvent.VK_CONTROL || key == KeyEvent.VK_META) {
 		 			controlPressed = false; //Shows control key is released
 		 		}
 		  	}
}
