import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Ball extends GameObject {

	private Color color;
	private int width;
	private int height;

	public Ball(Coordinate pos, Vector vel, int w, int h, Color c, Game gam) {
		super(pos, vel,gam);
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

		forces.add(new Vector(0,game.g(),false));
		forces.add(new Vector(game.getAirRes()*.001*velocity.getMagnitude()*velocity.getMagnitude(),velocity.getDirection()+Math.PI,true));

		acceleration=new Vector(0,0,false);
		for(Vector v:forces){
			acceleration=acceleration.add(v);
		}
		acceleration.setMagnitude(acceleration.getMagnitude());
		velocity= velocity.add(acceleration);
		prevPos=position.getCopy();
		Coordinate prevPos2=position.getCopy();
		if(velocity.getMagnitude()>40)
			velocity.setMagnitude(40);
		
		position.updateX(velocity);
		ArrayList<GameObject> intersections=game.getIntersections(this);
		if(intersections.size()>0){
			position=prevPos2.getCopy();
			velocity.setXComponent(0);
		}
		else{
			prevPos2=position.getCopy();
		}
		
		position.updateY(velocity);
		intersections=game.getIntersections(this);
		if(intersections.size()>0){
			position=prevPos2.getCopy();
			velocity.setYComponent(0);
		}
		else{
			prevPos2=position.getCopy();
		}
		
		forces.clear();

		if(position.getY()>=game.getHeight()-height||position.getY()<=0){
			velocity.setYComponent(velocity.getYComponent()*-1);
			position=prevPos.getCopy();
		}

		if(position.getX()>=game.getWidth()-width||position.getX()<=0){
			velocity.setXComponent(velocity.getXComponent()*-1);
			position=prevPos.getCopy();
		}

	}

	public Coordinate getCenter(){
		center=new Coordinate(position.getX()+width/2,position.getY()+height/2);
		return center;
	}

	public Rectangle getHitbox() {
		return new Rectangle((int)position.getX(),(int)position.getY(),width,height);
	}

}
