
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

public class MyLine extends MyShape{
	public MyLine(){
		this.setStroke(new BasicStroke());
	}
	
	public MyLine(Paint p,Stroke s){
		super(p,s);
	}
	
	public MyLine(int x1,int y1,int x2,int y2,Color myColor) {
		super(x1,y1,x2,y2,myColor);
	}
	
	public void draw(Graphics2D g) //÷ÿ–¥ª≠œﬂ
	{
		g.setPaint(getMyColor());
		g.setStroke(getStroke());
		g.drawLine(getX1(), getY1(), getX2(), getY2());
	}
}
