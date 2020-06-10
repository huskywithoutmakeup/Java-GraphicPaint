import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

public class MyTriangle extends MyShape{
	public MyTriangle(){
		this.setStroke(new BasicStroke());
	}
	
	public MyTriangle(Paint p,Stroke s){
		super(p,s);
	}
	
	public MyTriangle(int x1,int y1,int x2,int y2,Paint myColor){
		super(x1,y1,x2,y2,myColor);
	}
	
	public void draw(Graphics2D g) { //ÖØÐ´»­¾ØÐÎ
		g.setPaint(getMyColor());
		g.setStroke(getStroke());
		int[] xp = new int[] {getX1(),getX2(),(int) ((getX1()+getX2())/2)};
		int[] yp = new int[] {getY1(),getY1(),getY2()};
		if(isFilled())
			g.fillPolygon(xp, yp, 3);
		else
			g.drawPolygon(xp, yp, 3);
	}
}
