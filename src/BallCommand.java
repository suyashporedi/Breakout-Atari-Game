import java.util.ArrayList;
import java.util.List;

public class BallCommand implements Command {
	private Ball ball;

	public BallCommand(Ball ball) {
		// TODO Auto-generated constructor stub
		this.ball = ball;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if (ball.getBallX() == 0) {
			ball.setMoveX(10);
		}
		if (ball.getBallX() == (900 - ball.getWidth())) {
			ball.setMoveX(-(10));
		}
		if (ball.getBallY() == 0) {
			ball.setMoveY(10);
		}
		if (ball.getBallY() == 600 - ball.getHeight()) {
			ball.setMoveY(-10);
		}
	}

	@Override
	public Object undo() {
		// TODO Auto-generated method stub
		List<Integer> coord=new ArrayList<Integer>();
		int size=(Breakout.undoCo.size())-1;
		coord = Breakout.undoCo.get(size);
		ball.setBallX(coord.get(0));
		ball.setBallY(coord.get(1));
		ball.setMoveX(coord.get(2));
		ball.setMoveY(coord.get(3));
		return ball;
	}

	@Override
	public Object replay() {
		// TODO Auto-generated method stub
		
		List<Integer> coord=new ArrayList<Integer>();
		coord=Breakout.allCo.get(Breakout.counter);
		ball.setBallX(coord.get(0));
		ball.setBallY(coord.get(1));
		ball.setMoveX(coord.get(2));
		ball.setMoveY(coord.get(3));
		return ball;
	}

	@Override
	public Object load() {
		List<Integer> coord=new ArrayList<Integer>();
		int size=(Breakout.undoCo.size())-1;
		coord = Breakout.undoCo.get(size);
		ball.setBallX(coord.get(0));
		ball.setBallY(coord.get(1));
		ball.setMoveX(coord.get(2));
		ball.setMoveY(coord.get(3));
		return ball;
	}
}
