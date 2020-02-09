import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Square extends GameObject{

	private Color color;
	private int width;
	private int height;

	public Square(Coordinate pos, Vector vel, int w, int h, Color c, Game gam) {
		super(pos, vel,gam);
		color=c;
		width=w;
		height=h;
	}

	public void act() {
		if(outOfBounds())
			game.remove(this);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect((int)position.getX(),(int)position.getY(),width,height);
		g.setColor(Color.black);
		g.drawRect((int)position.getX(),(int)position.getY(),width,height);
	}

	public Coordinate getCenter(){
		center=new Coordinate(position.getX()+width/2,position.getY()+height/2);
		return center;
	}

	public Rectangle getHitbox() {
		return new Rectangle((int)position.getX(),(int)position.getY(),width,height);
	}

	public boolean outOfBounds() {
		if(position.getY()>=game.getHeight()-height&&position.getY()<=0&&position.getX()>=game.getWidth()-width&&position.getX()<=0)
			return true;
		return false;
	}

}
