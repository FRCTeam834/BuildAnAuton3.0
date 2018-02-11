import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JButton;

public class ToggleListener implements KeyListener{
	private HashMap<Integer, Boolean> keysBound;
	public BuildAnAuton frame;
	
	public ToggleListener(BuildAnAuton b, HashMap<Integer, Boolean> keys) {
		frame = b;
		keysBound = keys;
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		keysBound.replace(e.getKeyCode(), true);
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			frame.tool = BuildAnAuton.SelectedTool.NONE;
			
			for(JButton b: frame.tools) {
				b.setEnabled(true);
			}
			frame.mainPanel.repaint();
			
		}
		/*
		if(e.getKeyCode() == KeyEvent.VK_MINUS) {
			frame.zoom(-.05);
		}
		else if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
			frame.zoom(+.05);
		}
		
		*/
	}

	public void keyReleased(KeyEvent e) {
		keysBound.replace(e.getKeyCode(), false);
	}
	
	
	
}
