import java.util.ArrayList;
import java.util.List;

public class BrickCommand implements Command {

	private Brick brick;

	public BrickCommand(Brick brick) {
		// TODO Auto-generated constructor stub
		this.brick = brick;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object undo() {
		// TODO Auto-generated method stub
		//brick = Breakout.brickUndo.pop();
		
		List<Integer> coord=new ArrayList<Integer>();
		int size=(Breakout.undoCo.size())-1;
		coord = Breakout.undoCo.get(size);
		System.out.println(coord.toString());
		brick.setBrickX(coord.get(5));
		brick.setBrickY(coord.get(6));
		brick.setBRICK_WIDTH(coord.get(7));
		brick.setBRICK_HEIGHT(coord.get(8));
		
		if (brick.brickBlast) {
			brick.setBrickBlast(false);
		}
		return brick;
	}

	@Override
	public Object replay() {
		// TODO Auto-generated method stub

		List<Integer> coord=new ArrayList<Integer>();
		coord=Breakout.allCo.get(Breakout.counter);
		brick.setBrickX(coord.get(5));
		brick.setBrickY(coord.get(6));
		brick.setBRICK_WIDTH(coord.get(7));
		brick.setBRICK_HEIGHT(coord.get(8));
		
		return brick;
	}

	@Override
	public Object load() {
		// TODO Auto-generated method stub
		List<Integer> coord=new ArrayList<Integer>();
		int size=(Breakout.undoCo.size())-1;
		coord = Breakout.undoCo.get(size);
		brick.setBrickX(coord.get(5));
		brick.setBrickY(coord.get(6));
		brick.setBRICK_WIDTH(coord.get(7));
		brick.setBRICK_HEIGHT(coord.get(8));
		
		return brick;
	}
}
