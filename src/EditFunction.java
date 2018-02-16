import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class EditFunction {
	/**
	 * Takes care of highlighting the point and moving the point
	 */
	public static void editDraw() {
		//Highlights the point
		if (BuildAnAuton.indexHover != -1) { 
			BuildAnAuton.g2.setColor(Color.CYAN);
			BuildAnAuton.g2.fill(new Ellipse2D.Double(BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).x - 7, BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).y - 7, 14, 14));
			if (BuildAnAuton.dragging) { //Makes the point follow the mouse
				BuildAnAuton.pathPts.set(BuildAnAuton.indexHover, BuildAnAuton.mainPanel.getMousePosition());
			}
		}
	}
	/**
	 * Probably not needed, but forces the program to change the location of the point and
	 * forces the program to recalculate the point you are hovering over.
	 */
	public static void editTool() {
		BuildAnAuton.pathPts.set(BuildAnAuton.indexHover, BuildAnAuton.mainPanel.getMousePosition());
		BuildAnAuton.indexHover = -1;
	}
}
