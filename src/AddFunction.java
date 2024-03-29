import java.awt.Color;
import java.awt.Point;
import java.lang.Math;

public class AddFunction {
	public static double addAngle = 0;
	public static double addDistance = 0;
	static double magnitude = 0;
	static double angleRadians = 0;

	public static void addDraw() {
		BuildAnAuton.g2.setColor(Color.BLACK);
		Point curr = BuildAnAuton.pathPts.get(BuildAnAuton.indexGet);
		Point mouse = BuildAnAuton.mainPanel.getMousePosition();

		if (BuildAnAuton.shiftPressed) {
			// Actual angle between mouse and last point
			double angle = Math.atan2(mouse.y - curr.y, mouse.x - curr.x) * 180.0 / Math.PI;

			if (angle < 0)
				angle += 360; // Keeps angle from 0-360 degrees
			addAngle = Math.round(angle / 45.0) * 45.0;

			// Horizontal Lines
			if (addAngle == 180 || addAngle == 0) {
				BuildAnAuton.g2.drawLine(curr.x, curr.y, mouse.x, curr.y);
				addDistance = Math.abs(mouse.x - curr.x) * BuildAnAuton.inchPerPixel;
			}
			// Vertical Lines
			else if (addAngle == 90 || addAngle == 270) {
				BuildAnAuton.g2.drawLine(curr.x, curr.y, curr.x, mouse.y);
				addDistance = Math.abs(mouse.y - curr.y) * BuildAnAuton.inchPerPixel;
			}
			//45 degree Lines
			else {
				magnitude = Math.sqrt(Math.pow(curr.x - mouse.x, 2) + Math.pow(curr.y - mouse.y, 2));
				addDistance = magnitude * BuildAnAuton.inchPerPixel;
				angleRadians = addAngle * Math.PI / 180.0;
				BuildAnAuton.g2.drawLine(curr.x, curr.y, (int) (curr.x + Math.cos(angleRadians) * magnitude), (int) (curr.y + Math.sin(angleRadians) * magnitude));
			}
		}
		else {
			//Draws the Line, then calculates the angle and distance
			BuildAnAuton.g2.drawLine((int) curr.x, (int) curr.y, mouse.x, mouse.y);
			addAngle = Math.atan2(mouse.y - curr.y, mouse.x - curr.x) * 180.0 / Math.PI;
			addDistance = new Point((int) curr.x, (int) curr.y).distance(new Point(mouse.x, mouse.y));// * inchPerPixel;
		}
		if (addAngle < 0)
			addAngle += 360;
		addAngle = addAngle == 0 ? 0 : 360 - addAngle;
		addAngle = Math.round(addAngle * 100.0) / 100.0;
		addDistance = Math.round(addDistance * 100.0) / 100.0;
		//Tells you the information for your line
		//These two lines draw the strings in the lower left hand corner of the screen.
		BuildAnAuton.g2.drawString("Angle: " + addAngle + " degrees", 50, 65);
		BuildAnAuton.g2.drawString("Distance: " + addDistance + " in", 50, 50);
	}

	public static void addTool() {
		int mouseX = BuildAnAuton.mainPanel.getMousePosition().x;
		int mouseY = BuildAnAuton.mainPanel.getMousePosition().y;
		//Adds the points and sets the speed
		if (BuildAnAuton.shiftPressed) {
			if (addAngle == 180 || addAngle == 0)
				BuildAnAuton.pathPts.add(new Point(mouseX, BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y));
			else if (addAngle == 270 || addAngle == 90)
				BuildAnAuton.pathPts.add(new Point(BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x, mouseY));
			else {
				BuildAnAuton.pathPts.add(new Point((int) (BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).x + Math.cos(angleRadians) * magnitude), (int) (BuildAnAuton.pathPts.get(BuildAnAuton.indexGet).y + Math.sin(angleRadians) * magnitude)));
			}
		}
		else {
			BuildAnAuton.pathPts.add(new Point(mouseX, mouseY));
			BuildAnAuton.speeds.add(BuildAnAuton.defaultSpeed);
			BuildAnAuton.turnSpeeds.add(BuildAnAuton.defaultTurnSpeed);
		}

	}
}
