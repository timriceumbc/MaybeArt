import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener,MouseListener{

	private JFrame frame;
	private Timer timer;
	private ArrayList<GameObject> gameObjects;
	private SettingsWindow settings;
	private int selectedObject;
	private double g=.5;
	private int airRes=1;

	public Game(){
		gameObjects=new ArrayList<GameObject>();
		frame=new JFrame("");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,1000,800);
		frame.addMouseListener(this);
		frame.setResizable(false);
		frame.setVisible(true);
		settings=new SettingsWindow(this);
	}

	public double g(){
		return g;
	}

	public double getAirRes(){
		return airRes;
	}

	public void clear(){
		gameObjects.clear();
	}

	public void remove(GameObject object){
		gameObjects.remove(object);
	}

	public void add(GameObject obj){
		if(obj.outOfBounds())
			return;
		gameObjects.add(obj);
	}

	public ArrayList<GameObject> getAllOtherObjects(GameObject current){
		ArrayList<GameObject> objects=new ArrayList<GameObject>();
		for(GameObject obj:gameObjects){
			if(obj!=current)
				objects.add(obj);
		}
		return objects;
	}

	public ArrayList<GameObject> getIntersections(GameObject current){
		ArrayList<GameObject> intersections=new ArrayList<GameObject>();
		for(GameObject obj:gameObjects){
			if(obj!=current&&obj.getHitbox().intersects(current.getHitbox()))
				intersections.add(obj);
		}
		return intersections;
	}

	public void iterate(){
		if(settings.gravityOn())
			g=.5;
		else
			g=0;
		if(settings.airResOn())
			airRes=1;
		else
			airRes=0;

		for(int i=0;i<gameObjects.size();i++){
			GameObject obj=gameObjects.get(i);
			obj.act();
			if(obj instanceof BlackHole){
				((BlackHole) obj).setMove(settings.getMove());
			}
		}

		repaint();
	}

	public void startGame(){
		timer=new Timer(10,this);
		timer.start();
	}

	public void paintComponent(Graphics g){

		g.clearRect(0, 0, getWidth(), getHeight());
		for(GameObject obj:gameObjects){
			obj.draw(g);
		}

	}

	public void actionPerformed(ActionEvent arg0) {
		iterate();
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		double x=e.getX();
		double y=e.getY()-23;
		Color color=settings.getSelectedColor();
		selectedObject=settings.getSelectedObject();
		boolean move=settings.getMove();
		if(selectedObject==settings.BALL){
			add(new Ball(new Coordinate(x-25,y-25),new Vector(5,Math.PI/4,true),50,50,color,this));
		}
		if(selectedObject==settings.FOLLOWER){
			for(int i=0;i<5;i++)
				add(new Follower(new Coordinate(x-10,y-10),new Vector(10,Math.random()*(2*Math.PI),true),10,10,color,this));
		}
		if(selectedObject==settings.SQUARE){
			add(new Square(new Coordinate(x-25,y-25),new Vector(2,Math.random()*(2*Math.PI),true),50,50,color,this));
		}
		if(selectedObject==settings.BLACKHOLE){
			add(new BlackHole(new Coordinate(x-4000/200,y-4000/200),new Vector(0,0,true),4000,false,move,Color.black,this));
		}
		if(selectedObject==settings.REVERSEBLACKHOLE){
			add(new BlackHole(new Coordinate(x-4000/200,y-4000/200),new Vector(0,0,true),4000,true,move,Color.lightGray,this));
		}

		for(GameObject obj:gameObjects){
			if(obj instanceof Follower&& obj.getColor().equals(color)&&selectedObject==settings.FOLLOWER)
				obj.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public static void main(String[] args) {
		Game game=new Game();
		game.startGame();
	}
}