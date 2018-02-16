import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class PointCommands {
	public static void selectDraw() {
		//Highlights the point
		if(BuildAnAuton.indexHover != -1){
			BuildAnAuton.g2.setColor(Color.ORANGE);
			BuildAnAuton.g2.fill(new Ellipse2D.Double(BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).x - 7, BuildAnAuton.pathPts.get(BuildAnAuton.indexHover).y - 7, 14, 14));
		}
	}
}
