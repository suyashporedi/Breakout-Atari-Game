import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Slider implements Observer, KeyListener, Cloneable,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int sliderX = 500;
	private int sliderY = 500;
	private int moveX = 10;
	private int moveY = 10;
	private final int SLIDER_WIDTH = 5;
	private final int SLIDER_HEIGHT = 100;

	public Slider(int bx, int by, int moveX, int moveY) {
		this.sliderX = bx;
		this.sliderY = by;
		this.moveX = moveX;
		this.moveY = moveY;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		g.fillRect(sliderX, sliderY, SLIDER_WIDTH, SLIDER_HEIGHT);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		HashMap<String, Object> objects = (HashMap<String, Object>) arg;
		Slider slider = (Slider) objects.get("Slider");

		Command sliderCommand = new SliderCommand(slider);
		sliderCommand.execute();
	}

	public void moveSlider(KeyEvent e) {

		SliderCommand sliderCommand = new SliderCommand(this);
		sliderCommand.execute();

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP) {

			sliderY = sliderY - moveY;
		}

		if (key == KeyEvent.VK_DOWN) {

			sliderY = sliderY + moveY;

		}
	}

	public void registerSlider() {
		BreakoutObservable oberservable = new BreakoutObservable();
		oberservable.addObserver(this);
	}

	public void unregisterSlider() {
		BreakoutObservable observable = new BreakoutObservable();
		observable.removeObserver(this);
	}

	int getSliderX() {
		// TODO Auto-generated method stub
		return sliderX;
	}

	int getSliderY() {
		// TODO Auto-generated method stub
		return sliderY;
	}

	void setSliderY(int i) {
		sliderY = i;
	}

	int getWidth() {
		return SLIDER_WIDTH;
	}

	int getHeight() {
		return SLIDER_HEIGHT;
	}

	void setMoveY(int i) {
		// TODO Auto-generated method stub
		moveY = i;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		moveSlider(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		moveSlider(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
