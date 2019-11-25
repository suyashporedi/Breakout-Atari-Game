import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Ball implements Observer, Cloneable ,Serializable

{
	private int ballX = 120;
	private int ballY = 200;
	private int moveX = 10;
	private int moveY = 10;
	final int BALL_WIDTH = 30;
	final int BALL_HEIGHT = 30;
	private Ball breakoutBall;

	public Ball(int bx, int by, int moveX, int moveY) {
		this.ballX = bx;
		this.ballY = by;
		this.moveX = moveX;
		this.moveY = moveY;
	}

	public Ball(Ball ball) {
		breakoutBall = ball;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		HashMap<String, Object> objects = (HashMap<String, Object>) arg;
		Ball ball = (Ball) objects.get("Ball");
		Slider slider = (Slider) objects.get("Slider");

		Command ballCommands = new BallCommand(ball);
		ballCommands.execute();

		if (ball.getBallX() + ball.getWidth() >= slider.getSliderX()
				&& ball.getBallX() <= slider.getSliderX() + slider.getWidth()
				&& ball.getBallY() + ball.getWidth() >= slider.getSliderY()
				&& ball.getBallY() <= slider.getSliderY() + slider.getHeight()) {
			ball.setMoveX(-ball.getMoveX());
		}

	}

	public void registerBall() {
		BreakoutObservable oberservable = new BreakoutObservable();
		oberservable.addObserver(this);
	}

	public void unregisterBall() {
		BreakoutObservable observable = new BreakoutObservable();
		observable.removeObserver(this);
	}

	public void moveBall() {
		ballX = ballX + moveX;
		ballY = ballY + moveY;
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.BLUE);
		g2d.fillOval(ballX, ballY, BALL_WIDTH, BALL_HEIGHT);
	}

	int getBallX() {
		return ballX;
	}

	int getBallY() {
		return ballY;
	}

	void setBallX(int x) {
		ballX = x;
	}

	void setBallY(int y) {
		ballY = y;
	}

	int getMoveX() {
		return moveX;
	}

	void setMoveX(int x) {
		moveX = x;
	}

	int getMoveY() {
		return moveY;
	}

	void setMoveY(int y) {
		moveY = y;
	}

	int getWidth() {
		return BALL_WIDTH;
	}

	int getHeight() {
		return BALL_HEIGHT;
	}

}