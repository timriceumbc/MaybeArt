import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ReverseBlackHole extends GameObject{

	private Color color;
	private int width;
	private int height;
	private int mass;

	public ReverseBlackHole(Coordinate pos, Vector vel, int m, Game gam) {
		super(pos, vel,gam);
		mass=m;
		color=Color.lightGray;
		width=m/100;
		height=width;
	}

	public boolean outOfBounds(){
		if(position.getY()>=game.getHeight()-height||position.getY()<=0||position.getX()>=game.getWidth()-width||position.getX()<=0)
			return true;
		return false;
	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval((int)position.getX(),(int)position.getY(),width,height);
	}

	public void act(){
		ArrayList<GameObject> objects=game.getAllOtherObjects(this);
		for(GameObject obj:objects){
			Vector v=new Vector(obj.getCenter().getX()-getCenter().getX(),obj.getCenter().getY()-getCenter().getY(),false);
			if(v.getMagnitude()!=0)
				obj.forces.add(new Vector(mass/(v.getMagnitude()*v.getMagnitude()),v.getDirection(),true));
			else
				obj.forces.add(new Vector(0,v.getDirection(),true));
		}

		forces.add(new Vector(0,game.g(),false));
		forces.add(new Vector(game.getAirRes()*.001*velocity.getMagnitude()*velocity.getMagnitude(),velocity.getDirection()+Math.PI,true));

		acceleration=new Vector(0,0,false);
		for(Vector v:forces){
			acceleration=acceleration.add(v);
		}
		velocity= velocity.add(acceleration);
		prevPos=position.getCopy();
		if(velocity.getMagnitude()>20)
			velocity.setMagnitude(20);
		position.update(velocity);
		forces.clear();

		ArrayList<GameObject> intersections=game.getIntersections(this);
		for(GameObject obj: intersections){
			game.remove(obj);
		}

		if(position.getY()>=game.getHeight()-height||position.getY()<=0){
			velocity.setYComponent(velocity.getYComponent()*-1);
			position=prevPos;
		}

		if(position.getX()>=game.getWidth()-width||position.getX()<=0){
			velocity.setXComponent(velocity.getXComponent()*-1);
			position=prevPos;
		}

	}

	public Rectangle getHitbox() {
		return new Rectangle((int)position.getX(),(int)position.getY(),width,height);
	}

	public Coordinate getCenter() {
		center=new Coordinate(position.getX()+width/2,position.getY()+height/2);
		return center;
	}

}