import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SpeedModifier extends JFrame{
	//Changes the Speed over the line
	public static void speedTool(){
		int tempIndex = BuildAnAuton.lineIndex;
		String input = JOptionPane.showInputDialog(null, "Speed (-0.5 to 0.5): ", BuildAnAuton.speeds.get(BuildAnAuton.lineIndex));
		if(input == null || input == "") return;
		double val = Double.parseDouble(input);
		if(val < -.5) val = -.5;
		else if(val > 0.5) val = .5;
		BuildAnAuton.speeds.set(tempIndex, val);
	}
}
