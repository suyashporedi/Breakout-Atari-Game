import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Brick implements Observer, Cloneable, Serializable{

	private int brickX = 800;
	private int brickY = 300;

	private int BRICK_WIDTH = 30;
	private int BRICK_HEIGHT = 100;
	private Brick breakoutBrick;
	boolean brickBlast = false;

	public Brick(int bx, int by) {
		this.brickX = bx;
		this.brickY = by;
	}

	public Brick(Brick breakoutBrick) {
		super();
		this.breakoutBrick = breakoutBrick;
	}

	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		g.fillRect(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")

		HashMap<String, Object> objects = (HashMap<String, Object>) arg;
		Ball ball = (Ball) objects.get("Ball");

		Brick brick = (Brick) objects.get("Brick");

		if (ball.getBallX() + ball.getWidth() >= brick.getBrickX()
				&& ball.getBallX() <= brick.getBrickX() + brick.getBRICK_WIDTH()
				&& ball.getBallY() + ball.getHeight() >= brick.getBrickY()
				&& ball.getBallY() <= brick.getBrickY() + brick.getBRICK_HEIGHT()) {
			brick.setBrickX(0);
			brick.setBrickY(0);
			brick.setBRICK_WIDTH(0);
			brick.setBRICK_HEIGHT(0);
			brick.brickBlast = true;
		}

	}

	public void registerBrick() {
		BreakoutObservable observable = new BreakoutObservable();
		observable.addObserver(this);
	}

	public void unregisterBrick() {
		BreakoutObservable observable = new BreakoutObservable();
		observable.removeObserver(this);

	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getBrickX() {
		return brickX;
	}

	public void setBrickX(int bx) {
		this.brickX = bx;
	}

	public int getBrickY() {
		return brickY;
	}

	public void setBrickY(int by) {
		this.brickY = by;
	}

	public Brick getBreakoutBrick() {
		return breakoutBrick;
	}

	public void setBreakoutBrick(Brick breakoutBrick) {
		this.breakoutBrick = breakoutBrick;
	}

	public int getBRICK_WIDTH() {
		return BRICK_WIDTH;
	}

	public int getBRICK_HEIGHT() {
		return BRICK_HEIGHT;
	}

	public void setBRICK_WIDTH(int bRICK_WIDTH) {
		BRICK_WIDTH = bRICK_WIDTH;
	}

	public void setBRICK_HEIGHT(int bRICK_HEIGHT) {
		BRICK_HEIGHT = bRICK_HEIGHT;
	}

	public boolean isBrickBlast() {
		return brickBlast;
	}

	public void setBrickBlast(boolean brickBlast) {
		this.brickBlast = brickBlast;
	}

}
