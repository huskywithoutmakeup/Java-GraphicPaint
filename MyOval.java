import java.awt.*;

public class MyOval extends MyShape{
	public MyOval(){  //构造1
		this.setStroke(new BasicStroke());
	}
	
	public MyOval(Paint p,Stroke s){ //构造2
		super(p,s);
	}
	
	public MyOval(int x1,int y1,int x2,int y2,Color myColor) { //构造3
		super(x1,y1,x2,y2,myColor);
	}
	
	public void draw(Graphics2D g) {
		g.setPaint(getMyColor());
		g.setStroke(getStroke());
		if(isFilled())
			g.fillOval(getX1(), getY1(),getX2()-getX1(), getY2()-getY1());
		else 
			g.drawOval(getX1(), getY1(),getX2()-getX1(), getY2()-getY1());
	}
}