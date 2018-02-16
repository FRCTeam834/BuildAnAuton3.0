import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SpeedModifier extends JFrame{

	//Theoretically highlights the line
	SpeedModifier() {
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
