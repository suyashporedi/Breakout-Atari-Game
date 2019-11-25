import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class BreakoutObservable extends Observable {

	Ball ball;
	Slider slider;
	Brick brick;
	public static ArrayList<Observer> observers = new ArrayList<Observer>();
	HashMap<String, Observer> objects = new HashMap<String, Observer>();
	
	public BreakoutObservable(Ball ball, Slider slider, Brick brick) {
		this.ball = ball;
		this.slider = slider;
		this.brick = brick;
	}

	public BreakoutObservable() {

	}

	@Override
	public synchronized void addObserver(Observer o) {
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	public int countObservers() {
		return observers.size();
	}

	public void notifyObservers() {
		
		objects.put("Ball", this.ball);
		objects.put("Slider", this.slider);
		objects.put("Brick", this.brick);

		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).update(this, objects);
		}
	}
}
