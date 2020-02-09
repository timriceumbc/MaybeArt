import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class GameObject {
	protected Coordinate prevPos;
	protected Coordinate position;
	protected Vector velocity;
	protected Vector acceleration;
	protected ArrayList<Vector> forces;
	protected Game game;
	protected Coordinate center;
	protected Color color;

	public abstract void draw(Graphics g);
	public abstract void act();
	public abstract Rectangle getHitbox();
	public abstract boolean outOfBounds();
	public abstract Coordinate getCenter();

	public GameObject(Coordinate pos, Vector vel, Game gam){
		position=pos;
		velocity=vel;
		prevPos=pos.getCopy();
		acceleration=new Vector(0,0,false);
		forces=new ArrayList<Vector>();
		game=gam;
	}

	public final Coordinate getPosition(){
		return position;
	}

	public final Color getColor(){
		return color;
	}

	public final Coordinate getPrevPos(){
		return prevPos;
	}

	public final Vector getVelocity(){
		return velocity;
	}

	public final Vector getAcceleration(){
		return acceleration;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

}