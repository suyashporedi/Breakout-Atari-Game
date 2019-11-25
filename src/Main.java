import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main {
public static void main(String[] args) {
final int frame_width = 1200;
final int frame_height = 800;
JFrame frame = new JFrame();

JPanel buttons = new JPanel();
JPanel buttonsLeft=new JPanel();
buttons.setSize(150, 400);

frame.setSize(frame_width, frame_height);
frame.setResizable(false);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setTitle("Breakout");

Ball ball = new Ball(120, 200, 10, 10);
Slider slider = new Slider(700, 500, 10, 10);
Brick brick = new Brick(800, 300);
Breakout breakout = new Breakout(ball, slider, brick, buttons,frame);

breakout.setSize(900, 600);
breakout.setBackground(Color.white);
frame.add(breakout);
buttonsLeft=buttons;
frame.add(buttons);
buttons.setLocation(0, 0);
breakout.setLocation(150, 50);
buttons.setVisible(true);
//buttonsLeft.setVisible(true);
frame.setVisible(true);
breakout.startGame();
}
}