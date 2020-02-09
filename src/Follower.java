import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Follower extends GameObject{

	private Coordinate gravityToward;
	private int width;
	private int height;

	public Follower(Coordinate pos, Vector vel, int w, int h, Color c, Game gam) {
		super(pos, vel, gam);
		color=c;
		width=w;
		height=h;
	}

	public boolean outOfBounds(){
		if(position.getY()>=game.getHeight()-height||position.getY()<=0||position.getX()>=game.getWidth()-width||position.getX()<=0)
			return true;
		return false;
	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval((int)position.getX(),(int)position.getY(),width,height);
		g.setColor(Color.black);
		g.drawOval((int)position.getX(),(int)position.getY(),width,height);
	}

	public void act(){
		double direction=new Vector(gravityToward.getX()-position.getX(),gravityToward.getY()-position.getY(),false).getDirection();
		forces.add(new Vector(.5,direction,true));
		forces.add(new Vector(0,game.g(),false));
		forces.add(new Vector(game.getAirRes()*.001*velocity.getMagnitude()*velocity.getMagnitude(),velocity.getDirection()+Math.PI,true));

		acceleration=new Vector(0,0,false);
		for(Vector v:forces){
			acceleration=acceleration.add(v);
		}
		acceleration.setMagnitude(acceleration.getMagnitude());
		velocity= velocity.add(acceleration);
		prevPos=position.getCopy();
		if(velocity.getMagnitude()>10)
			velocity.setMagnitude(10);
		position.update(velocity);
		forces.clear();

		if(position.getY()>=game.getHeight()-height||position.getY()<=0){
			velocity.setYComponent(velocity.getYComponent()*-1);
			position=prevPos;
		}

		if(position.getX()>=game.getWidth()-width||position.getX()<=0){
			velocity.setXComponent(velocity.getXComponent()*-1);
			position=prevPos;
		}

	}

	public Coordinate getCenter(){
		center=new Coordinate(position.getX()+width/2,position.getY()+height/2);
		return center;
	}

	public Rectangle getHitbox() {
		return new Rectangle((int)position.getX(),(int)position.getY(),width,height);
	}

	public void mousePressed(MouseEvent e) {
		gravityToward=new Coordinate(e.getX(),e.getY());
	}

}