import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class ClockCommand implements Command {
	private Clock clock;

	public ClockCommand(Clock clock) {
		this.clock = clock;
	}

	@Override
	public void execute() {
		clock.count++;
		if (clock.count < 100000) {
			clock.setTime(String.format("%02d:%02d", clock.count / 60, clock.count % 60));
		} else {
			((Timer) (clock.evt.getSource())).stop();
		}

	}

	/*
	 * @Override public Object undo() { // TODO Auto-generated method stub String
	 * time = Breakout.sliderUndo.pop(); return time; }
	 */
	@Override
	public Object replay() {
		//// TODO Auto-generated method stub
		//String time = Breakout.clockReplay.get(Breakout.counter);
		List<Integer> coord=new ArrayList<Integer>();
		
		coord = Breakout.allCo.get(Breakout.counter);
		
		return String.format("%02d:%02d",coord.get(9),coord.get(10));
		
	}

	@Override
	public Object undo() {
		// TODO Auto-generated method stub
		List<Integer> coord=new ArrayList<Integer>();
		int size=(Breakout.undoCo.size())-1;
		coord = Breakout.undoCo.get(size);
		clock.count=coord.get(11);
		clock.setTime(String.format("%02d:%02d",coord.get(9),coord.get(10)));
		
		return clock;
	}

	@Override
	public Object load() {
		// TODO Auto-generated method stub
		List<Integer> coord=new ArrayList<Integer>();
		
		int size=(Breakout.undoCo.size())-1;
		coord = Breakout.undoCo.get(size);
		
		//System.out.println("in Clock Load :"+coord.toString());
		
		clock.count=coord.get(11);
		clock.setTime(String.format("%02d:%02d",coord.get(9),coord.get(10)));
		
		
		return clock;
	}

}
