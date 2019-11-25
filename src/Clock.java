import java.awt.event.ActionEvent;
import java.io.Serializable;

import javax.swing.JButton;

public class Clock implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton timeDisplay = new JButton();
	ActionEvent evt;
	int count;

	public void setTime(String time) {
		timeDisplay.setText(time);
	}

	public String getTime() {
		return timeDisplay.getText();

	}

}
