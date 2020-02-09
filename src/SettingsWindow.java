import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingsWindow extends JPanel implements ActionListener{

	private JFrame frame;
	private JButton ball, follower, blackHole, reverseBlackHole;
	private JButton gravity, clear, square, airRes, color, movement;
	public final int BALL=0;
	public final int FOLLOWER=1;
	public final int BLACKHOLE=2;
	public final int REVERSEBLACKHOLE=3;
	public final int SQUARE=6;
	private int selectedObject=0;
	private boolean gravityOn=false, airResOn=false, blackHoleMove=false;
	private Game game;
	private Color selectedColor=Color.red;

	public SettingsWindow(Game gam){
		game=gam;
		frame=new JFrame("Settings");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(game.getWidth()+5,0,200,game.getHeight()+30);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		ball=new JButton("Ball");
		ball.addActionListener(this);
		ball.setActionCommand("0");
		follower=new JButton("Follower");
		follower.addActionListener(this);
		follower.setActionCommand("1");
		blackHole=new JButton("Black Hole");
		blackHole.addActionListener(this);
		blackHole.setActionCommand("2");
		reverseBlackHole=new JButton("Reverse Black Hole");
		reverseBlackHole.addActionListener(this);
		reverseBlackHole.setActionCommand("3");
		gravity=new JButton("Gravity Off");
		gravity.addActionListener(this);
		gravity.setActionCommand("4");
		movement=new JButton("Black Hole Movement Off");
		movement.addActionListener(this);
		movement.setActionCommand("8");
		airRes=new JButton("Air Resistance Off");
		airRes.addActionListener(this);
		airRes.setActionCommand("7");
		clear=new JButton("Clear");
		clear.addActionListener(this);
		clear.setActionCommand("5");
		square=new JButton("Square");
		square.addActionListener(this);
		square.setActionCommand("6");
		color=new JButton("            ");
		color.addActionListener(this);
		color.setActionCommand("color");
		color.setBackground(Color.red);
		frame.add(ball);
		frame.add(follower);
		frame.add(blackHole);
		frame.add(reverseBlackHole);
		frame.add(square);
		frame.add(gravity);
		frame.add(airRes);
		frame.add(movement);
		frame.add(clear);
		frame.add(color);
		frame.setVisible(true);
		ball.setEnabled(false);
	}

	public int getSelectedObject(){
		return selectedObject;
	}

	public boolean getMove(){
		return blackHoleMove;
	}

	public Color getSelectedColor(){
		return selectedColor;
	}

	public boolean gravityOn(){
		return gravityOn;
	}

	public boolean airResOn(){
		return airResOn;
	}

	public void actionPerformed(ActionEvent event) {
		String code=event.getActionCommand();

		if(!(code.equals("4")||code.equals("5")||code.equals("7")||code.equals("color")||code.equals("8"))){
			ball.setEnabled(true);
			follower.setEnabled(true);
			square.setEnabled(true);
			blackHole.setEnabled(true);
			reverseBlackHole.setEnabled(true);
		}

		if(code.equals("0")){
			selectedObject=0;
			ball.setEnabled(false);
		}
		if(code.equals("1")){
			selectedObject=1;
			follower.setEnabled(false);
		}
		if(code.equals("2")){
			selectedObject=2;
			blackHole.setEnabled(false);
		}
		if(code.equals("3")){
			selectedObject=3;
			reverseBlackHole.setEnabled(false);
		}
		if(code.equals("4")){
			if(gravityOn){
				gravityOn=false;
				gravity.setText("Gravity Off");
			}
			else{
				gravityOn=true;
				gravity.setText("Gravity On");
			}
		}
		if(code.equals("7")){
			if(airResOn){
				airResOn=false;
				airRes.setText("Air Resistance Off");
			}
			else{
				airResOn=true;
				airRes.setText("Air Resistance On");
			}
		}
		if(code.equals("5"))
			game.clear();
		if(code.equals("6")){
			selectedObject=6;
			square.setEnabled(false);
		}
		if(code.equals("color")){
			if(selectedColor.equals(Color.red)){
				selectedColor=Color.blue;
				color.setBackground(Color.blue);
			}
			else if(selectedColor.equals(Color.blue)){
				selectedColor=Color.green;
				color.setBackground(Color.green);
			}
			else if(selectedColor.equals(Color.green)){
				selectedColor=Color.magenta;
				color.setBackground(Color.magenta);
			}
			else if(selectedColor.equals(Color.magenta)){
				selectedColor=Color.orange;
				color.setBackground(Color.orange);
			}
			else if(selectedColor.equals(Color.orange)){
				selectedColor=Color.red;
				color.setBackground(Color.red);
			}
		}
		if(code.equals("8")){
			if(blackHoleMove){
				blackHoleMove=false;
				movement.setText("Black Hole Movement Off");
			}
			else{
				blackHoleMove=true;
				movement.setText("Black Hole Movement On");
			}
		}
	}

}
