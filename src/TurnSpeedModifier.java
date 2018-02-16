import java.awt.Color;
import java.awt.geom.Ellipse2D;
import javax.swing.JOptionPane;

public class TurnSpeedModifier {
	public static void turnSpeedDraw() {
		//Highlights the points
		if(BuildAnAuton.indexHover != -1){
			BuildAnAuton.g2.setColor(Color.MAGENTA);
			BuildAnAuton.g2.fill(new Ellipse2D.Double(BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).x - 7, BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).y - 7, 14, 14));
		}
	}
	
	public static void turnSpeedTool() { 
		//Sets the Turnspeed
		int tempIndex = BuildAnAuton.indexHover;
		String input = JOptionPane.showInputDialog(null, "Turn Speed (0 to 1): ", BuildAnAuton.turnSpeeds.get(BuildAnAuton.indexHover));
		if(input == null || input == "") return;
		double val = Double.parseDouble(input);
		if(val < 0) val = 0;
		else if(val > 1.0) val = 1.0;
		BuildAnAuton.turnSpeeds.set(tempIndex, val);
	}
}
