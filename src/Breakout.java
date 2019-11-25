import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Breakout extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static Graphics2D graphobj = null;
	static boolean gameIsOn = true;
	static boolean undogame = true;
	static boolean gameOver = false;
	static boolean loadGame = false;
	private int replayGame = 0;
	static int startGame = 1;
	public static int counter;
	private Ball ball;
	private Slider slider;
	private Brick brick;
	private JFrame frame;
	private boolean stop = true;
	
	private int countLayout=1;
	
	static Stack<List<Integer>> undoCo;
	static ArrayList<List<Integer>> allCo = new ArrayList<>() ;
	private List<Integer> coordinates ;
	private List<Integer> undoCoordinates;
	private BreakoutObservable observable;
	private Command clockcommand;

	private JButton replay, undo, pause, start , save, load , changeLayout;

	private Clock clock = new Clock();
	private Timer timer;
	private int count;
	private JPanel buttonsPanel;

	public Breakout() {
		// TODO Auto-generated constructor stub
	}

	public Breakout(Ball ball, Slider slider, Brick brick, JPanel buttonsPanel, JFrame frame) {
		this.ball = ball;
		this.slider = slider;
		this.brick = brick;
		this.frame=frame;
		this.buttonsPanel=buttonsPanel;
		
		this.addKeyListener(slider);

		replay = new JButton("Replay");
		replay.setFocusable(false);
		undo = new JButton("Undo");
		undo.setFocusable(false);
		pause = new JButton("Pause");
		pause.setFocusable(false);
		start = new JButton("Start/Restart");
		start.setFocusable(false);
		

		save = new JButton("Save");
		save.setFocusable(false);

		load = new JButton("Load");
		load.setFocusable(false);
		
		changeLayout=new JButton("Change Layout");
		changeLayout.setFocusable(false);

		pause.addActionListener(this);
		start.addActionListener(this);
		undo.addActionListener(this);
		replay.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		changeLayout.addActionListener(this);
		
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		
		clock.timeDisplay.disable();
		clock.timeDisplay.setBorderPainted(false);
		buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		buttonsPanel.add(Box.createRigidArea(new Dimension(1, 0)));

		buttonsPanel.add(clock.timeDisplay);
		buttonsPanel.add(pause);
		buttonsPanel.add(undo);
		buttonsPanel.add(start);
		buttonsPanel.add(replay);
		buttonsPanel.add(save);
		buttonsPanel.add(load);
		buttonsPanel.add(changeLayout);
		
		
		this.replayGame = 0;

		
		undoCo= new Stack<List<Integer>>();
		
		setFocusable(true);
		this.ball.registerBall();
		this.slider.registerSlider();
		this.brick.registerBrick();

		clockcommand = new ClockCommand(clock);
		

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		graphobj = (Graphics2D) g;
		ball.draw(graphobj);
		slider.draw(graphobj);
		brick.draw(graphobj);
	}

	public void startGame() {
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clock.evt = e;
				clockcommand.execute();
			}

		});
		timer.setInitialDelay(0);
		timer.start();

		while (stop) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			if (this.replayGame == 1) {
				for (int i = 0; i < allCo.size(); i++) {
					counter = i;

					Command ballCommand = new BallCommand(ball);
					Command sliderCommand = new SliderCommand(slider);
					Command brickCommand = new BrickCommand(brick);

					
					
					ball = (Ball) ballCommand.replay();
					slider = (Slider) sliderCommand.replay();
					brick = (Brick) brickCommand.replay();
					timer.stop();
					clock.setTime(clockcommand.replay().toString());

					try {
						Thread.sleep(80);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					repaint();
				}

				startGame = 1;
				replayGame = 0;

			}
			
			
			if (startGame == 1) {
				while (gameIsOn) {
					if (startGame == 0) {
						break; // break the while(gameIsOn) loop?
					}
					
					ball.moveBall();
					storeObjects(ball, slider, brick, clock);
					observable = new BreakoutObservable(ball, slider, brick);

					observable.notifyObservers();

					if (brick.isBrickBlast()) {
						timer.stop();
						gameIsOn = false;
						gameOver = true;
						startGame = 1;
					}

					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaint();

				}
			}
			if (gameIsOn == false && undogame == true) {

				repaint();

			}
		}

	}

	public void storeObjects(Ball ball, Slider slider, Brick brick, Clock clock) {
		
		undoCoordinates=new ArrayList<Integer>();
		
		undoCoordinates.add(ball.getBallX());
		undoCoordinates.add(ball.getBallY());
		undoCoordinates.add(ball.getMoveX());
		undoCoordinates.add(ball.getMoveY());
		undoCoordinates.add(slider.getSliderY());
		undoCoordinates.add(brick.getBrickX());
		undoCoordinates.add(brick.getBrickY());
		undoCoordinates.add(brick.getBRICK_WIDTH());
		undoCoordinates.add(brick.getBRICK_HEIGHT());
		if(clock.getTime().isEmpty()) {
			undoCoordinates.add(0);
			undoCoordinates.add(0);
		}
		else {
				String[] arr=clock.getTime().split(":");
		undoCoordinates.add(Integer.parseInt(arr[0]));
		undoCoordinates.add(Integer.parseInt(arr[1]));
		}
		undoCoordinates.add(clock.count);
		undoCo.push(undoCoordinates);
		
		coordinates = new ArrayList<>();
		
		coordinates.add(ball.getBallX());
		coordinates.add(ball.getBallY());
		coordinates.add(ball.getMoveX());
		coordinates.add(ball.getMoveY());
		coordinates.add(slider.getSliderY());
		coordinates.add(brick.getBrickX());
		coordinates.add(brick.getBrickY());
		coordinates.add(brick.getBRICK_WIDTH());
		coordinates.add(brick.getBRICK_HEIGHT());
		if(clock.getTime().isEmpty()) {
			coordinates.add(0);
			coordinates.add(0);
		}
		else {
				String[] arr=clock.getTime().split(":");
		coordinates.add(Integer.parseInt(arr[0]));
		coordinates.add(Integer.parseInt(arr[1]));
		}
		coordinates.add(clock.count);
		allCo.add(coordinates);
		
		}

	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		// button click event for replay button
		if (arg0.getSource() == replay) {
			startGame = 0;
			gameIsOn = false;
			this.replayGame = 1;
		}

		// button click event for undo button
		else if (arg0.getSource() == undo) {
			if (replayGame == 0) {
				gameIsOn = false;
				startGame = 1;
				gameOver = false;

				Command ballobject = new BallCommand(ball);
				Command sliderobject = new SliderCommand(slider);
				Command brickobject = new BrickCommand(brick);
				Command clockObject= new ClockCommand(clock);
				
				ball = (Ball) ballobject.undo();
				slider = (Slider) sliderobject.undo();
				brick = (Brick) brickobject.undo();
				
				
				timer.stop();
				clockObject.undo();
				
				undoCo.pop();
				


				observable = new BreakoutObservable(ball, slider, brick);
				observable.notifyObservers();

				undogame = true;
			}

		}

		// button click event for pause button
		else if (arg0.getSource() == pause) {
			timer.stop();
			gameIsOn = false;
		}

		// button click event for start button
		else if (arg0.getSource() == start) {
			if (gameOver) {

				reset();

				gameIsOn = true;
				gameOver = false;
				timer.start();
				
			} else {
				stop = true;
				startGame = 1;
				gameIsOn = true;
				timer.restart();
				this.addKeyListener(slider);
			}

		}
		else if(arg0.getSource()== save)
		{
			timer.stop();
			gameIsOn = false;
			
			JFileChooser fileChooser = new JFileChooser(new File("/Users/suyashporedi/Desktop"));
			
			fileChooser.setDialogTitle("Save the Game");
			int success=fileChooser.showSaveDialog(null);
			if(success==JFileChooser.APPROVE_OPTION) {
				File file=fileChooser.getSelectedFile();
				
				try {
					FileOutputStream fout =new FileOutputStream(file.getPath());
					
					@SuppressWarnings("resource")
					ObjectOutputStream oos = new ObjectOutputStream(fout);
				    oos.writeObject(allCo);
				    
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
		
		else if(arg0.getSource()== load)
		{
			timer.stop();
			gameOver = false;
			gameIsOn = false;
			startGame=1;
			
			if (brick.isBrickBlast()) {
				brick.brickBlast=false;
			}
			
			this.addKeyListener(slider);
			
			
			JFileChooser fileChooser = new JFileChooser(new File("/Users/suyashporedi/Desktop"));
			
			fileChooser.setDialogTitle("Open Saved Game");
			int success=fileChooser.showOpenDialog(null);
			
			if(success==JFileChooser.APPROVE_OPTION) {
				File file=fileChooser.getSelectedFile();
				
				try {
					
				    FileInputStream fin = new FileInputStream(file.getPath());
				    ObjectInputStream ois = new ObjectInputStream(fin);
				    @SuppressWarnings("unchecked")
					ArrayList<List<Integer>> array = (ArrayList<List<Integer>>) ois.readObject();
				    ois.close();
				    allCo=array;
				    
				    undoCo.clear();
				    for(int i=0;i<array.size();i++)
				    {
				    undoCo.add(array.get(i));
				    }
				    
				    
				    Command ballobject = new BallCommand(ball);
					Command sliderobject = new SliderCommand(slider);
					Command brickobject = new BrickCommand(brick);
					Command clockObject= new ClockCommand(clock);
					
					ball = (Ball) ballobject.load();
					slider = (Slider) sliderobject.load();
					brick = (Brick) brickobject.load();
					
					clock=(Clock) clockObject.load();
					
				    clock.timeDisplay.repaint();
					
				    }
				   catch (Exception e) { e.printStackTrace(); }  
			}
			repaint();
		}
		
		else if (arg0.getSource() == changeLayout) {

		
			buttonsPanel.removeAll();
			buttonsPanel.add(clock.timeDisplay);
			buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
			buttonsPanel.add(Box.createRigidArea(new Dimension(1, 0)));
			buttonsPanel.add(pause);
			buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
			buttonsPanel.add(Box.createRigidArea(new Dimension(1, 0)));
			buttonsPanel.add(undo);
			buttonsPanel.add(start);
			buttonsPanel.add(replay);
			buttonsPanel.add(save);
			buttonsPanel.add(load);
			buttonsPanel.add(changeLayout);
			repaint();
			
			
			if(countLayout==0) {
			     Box b=Box.createVerticalBox();
			     b.add(clock.timeDisplay);
			     b.setLocation(0, 0);
			     b.add(pause);
			     b.add(undo);
			  
			     b.add(start);
			     b.add(replay);
			     b.add(save);
			     b.add(load);
			     b.add(changeLayout);
			     frame.add(b,BorderLayout.WEST);
			     countLayout++;  
			}
			
			else if(countLayout==1) {
				FlowLayout flow = new FlowLayout(FlowLayout.LEADING);
			 
			     buttonsPanel.setLayout(flow);
			     buttonsPanel.setPreferredSize(new Dimension(150,10));
			     countLayout++;
			     repaint();
			}
			else if(countLayout==2) {
				
				 buttonsPanel.setLayout(new BorderLayout());
			     buttonsPanel.setLocation(1020,0);
			     buttonsPanel.setBounds(1020,0,150,150);
			     buttonsPanel.setVisible(true);
			     Box b=Box.createVerticalBox();
			     b.add(clock.timeDisplay);
			     b.setLocation(1020, 0);
			     b.add(pause);
			     b.add(undo);
			  
			     b.add(start);
			     b.add(replay);
				 b.add(save);
				 b.add(load);
			     b.add(changeLayout);
			     frame.add(b,BorderLayout.EAST);
			     countLayout++;
			     repaint();
			}
			else if(countLayout==3) {
			    Box b=Box.createHorizontalBox();
			     b.add(clock.timeDisplay);
			     b.setLocation( 0,550);
			     b.add(pause);
			     b.add(undo);
			  
			     b.add(start);
			     b.add(replay);
			     b.add(save);
			     b.add(load);
			     b.add(changeLayout);
			     frame.add(b,BorderLayout.PAGE_END);
			   
			     countLayout=0;
			     repaint();
			}
		}
	}
	
	
	public void reset() {

		ball = new Ball(120, 200, 10, 10);
		slider = new Slider(700, 500, 10, 10);
		brick = new Brick(800, 300);
		brick.setBRICK_WIDTH(30);
		brick.setBRICK_HEIGHT(100);

		this.addKeyListener(slider);

		
		clock.count = 0;

		undoCo = new Stack<List<Integer>>();
		allCo= new ArrayList<List<Integer>>();
		
	}

}
