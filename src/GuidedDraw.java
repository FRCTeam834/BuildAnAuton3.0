import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


//import BuildAnAuton2.SelectedTool;
//import visualrobot.CommandSet;

import java.lang.Math;

//import visualrobot.CommandSet; This will be used to reference other classes


public class GuidedDraw extends JFrame {
	private static final long serialVersionUID = 0;
	
	public static JButton draw = new JButton("Add");
	public static JTextField angle = new JTextField (6);
	public static JTextField distance = new JTextField(6);
	public double identiFire = 0;
	
	//Layout of Window
	public GuidedDraw() {
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
		getRootPane().setDefaultButton(draw);
		
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
			
			if (BuildAnAuton.defaultSpeed >= 0) {
				BuildAnAuton.backwards.add(false);
			}
			else if (BuildAnAuton.defaultSpeed < 0) {
				BuildAnAuton.backwards.add(true);
			}
		});
	}
}
