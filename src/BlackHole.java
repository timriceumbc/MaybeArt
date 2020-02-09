import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BlackHole extends GameObject{

	private Color color;
	private int width;
	private int height;
	private int mass;
	private boolean reverse;
	private boolean move;

	public BlackHole(Coordinate pos, Vector vel, int m, boolean rev, boolean mov, Color c, Game gam) {
		super(pos, vel, gam);
		mass=m;
		width=m/100;
		height=width;
		reverse=rev;
		color=c;
		move=mov;
	}

	public void setMove(boolean mov){
		move=mov;
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
		ArrayList<GameObject> objects=game.getAllOtherObjects(this);
		for(GameObject obj:objects){
			Vector directionVector=new Vector(getCenter().getX()-obj.getCenter().getX(),getCenter().getY()-obj.getCenter().getY(),false);
			if(directionVector.getMagnitude()!=0){
				Vector gravity= new Vector(mass/(directionVector.getMagnitude()*directionVector.getMagnitude()),directionVector.getDirection(),true);
				if(reverse)
					gravity.setDirection(gravity.getDirection()+Math.PI);
				obj.forces.add(gravity);
			}
			else
				obj.forces.add(new Vector(0,0,true));
		}

		if(move){
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
		}
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