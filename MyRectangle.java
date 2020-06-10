import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

public class MyRectangle extends MyShape{
	public MyRectangle(){
		this.setStroke(new BasicStroke());
	}
	
	public MyRectangle(Paint p,Stroke s){
		super(p,s);
	}
	
	public MyRectangle(int x1,int y1,int x2,int y2,Paint myColor){
		super(x1,y1,x2,y2,myColor);
	}
	
	public void draw(Graphics2D g) { //ÖØÐ´»­¾ØÐÎ
		g.setPaint(getMyColor());
		g.setStroke(getStroke());
		if(isFilled())
			g.fillRect(getX1(), getY1(), getX2()-getX1(), getY2()-getY1());
		else
			g.drawRect(getX1(), getY1(), getX2()-getX1(), getY2()-getY1());
	}
}
