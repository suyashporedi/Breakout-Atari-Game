import java.util.ArrayList;
import java.util.List;

public class SliderCommand implements Command {

	private Slider slider;

	public SliderCommand(Slider slider) {
		this.slider = slider;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if (slider.getSliderY() == -10) {
			slider.setSliderY(slider.getSliderY() + 10);
		}

		if (slider.getSliderY() == 610 - slider.getHeight()) {
			slider.setSliderY(slider.getSliderY() - 10);
		}
	}

	@Override
	public Object undo() {
		// TODO Auto-generated method stub
		
		
		List<Integer> coord=new ArrayList<Integer>();
		int size=(Breakout.undoCo.size())-1;
		coord = Breakout.undoCo.get(size);
		slider.setSliderY(coord.get(4));
		return slider;
	}

	@Override
	public Object replay() {
		// TODO Auto-generated method stub

		List<Integer> coord=new ArrayList<Integer>();
		coord=Breakout.allCo.get(Breakout.counter);
		slider.setSliderY(coord.get(4));
		
		return slider;
	}

	@Override
	public Object load() {
		// TODO Auto-generated method stub
		List<Integer> coord=new ArrayList<Integer>();
		int size=(Breakout.undoCo.size())-1;
		coord = Breakout.undoCo.get(size);
		slider.setSliderY(coord.get(4));
		return slider;

	}

}
