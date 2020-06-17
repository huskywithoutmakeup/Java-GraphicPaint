import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;

public class MyOval extends MyShape{
	public MyOval(){  //构造1
		super();
	}
	
	public MyOval(Paint p,float s){ //构造2
		super(p,s);
	}
	
	public MyOval(int x1,int y1,int x2,int y2,Paint myColor) { //构造3
		super(x1,y1,x2,y2,myColor);
	}
	
	public void draw(Graphics2D g) {
		g.setPaint(getMyColor());
		g.setStroke(new BasicStroke(this.getStroke(), BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		if(isFilled())
			g.fillOval(getX1(), getY1(),getX2()-getX1(), getY2()-getY1());
		else 
			g.drawOval(getX1(), getY1(),getX2()-getX1(), getY2()-getY1());
	}
}