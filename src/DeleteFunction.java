import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class DeleteFunction {
	// Highlights the point
	public static void deleteDraw() {
		if (BuildAnAuton.indexHover != -1) {
			BuildAnAuton.g2.setColor(Color.RED);
			BuildAnAuton.g2.fill(new Ellipse2D.Double(BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).x - 7,
				BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).y - 7, 14, 14));
		}
	}
	
	// Removes the point
	public static void deleteTool() {
		BuildAnAuton.pathPts.remove(BuildAnAuton.indexHover);
		BuildAnAuton.mainPanel.repaint();
		BuildAnAuton.indexHover = -1;
	}
}