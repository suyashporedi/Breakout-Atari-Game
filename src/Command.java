
public interface Command {
	public void execute();

	public Object undo();

	public Object replay();
	
	public Object load();
}
