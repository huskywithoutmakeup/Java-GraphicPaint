import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;


public class MyLine extends MyShape{
	public MyLine(){
		super();
	}
	
	public MyLine(Paint p,float s){
		super(p,s);
	}
	
	public MyLine(int x1,int y1,int x2,int y2,Paint myColor) {
		super(x1,y1,x2,y2,myColor);
	}
	
	public void draw(Graphics2D g) //÷ÿ–¥ª≠œﬂ
	{
		g.setPaint(getMyColor());
		g.setStroke(new BasicStroke(this.getStroke(), BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g.drawLine(getX1(), getY1(), getX2(), getY2());
	}
}
