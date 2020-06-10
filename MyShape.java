
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

public abstract class MyShape{
	private int x1;  //第一个点的x值	
	private int y1;  //第一个点的y值	
	private int x2;  //第二个点的x值	
	private int y2;  //第二个点的y值	
	private Paint myColor;//图形的颜色
	private boolean filled;//图形是否填充
	private Stroke stroke; //stroke属性用于控制线条的宽度、笔形样式、线段连接方式或短划线图案
	
	public MyShape(){//缺省构造法
		stroke=new BasicStroke(); 
	}
	
	public MyShape(Paint p,Stroke s){ //构造2 颜色+stroke
		this.setMyColor(p);
		this.setStroke(s);
	}
	
	public MyShape(Stroke s){ //构造3 stroke
		this.setStroke(s);
	}
	
	public MyShape(int x1, int y1, int x2, int y2,Paint myColor){ //构造4
		super();
		this.x1 = x1;  
		this.y1 = y1;  
		this.x2 = x2;  
		this.y2 = y2;   
		this.myColor = myColor;  
	}
	
	public Stroke getStroke(){//获得stroke属性
		return stroke;
	}
	
	public void setStroke(Stroke stroke){//设置stroke属性
		this.stroke = stroke;
	}
	
	public int getX1(){//获取第一个点的x值
		return x1;      
	}

	public void setX1(int x1){//设置第一个点的x值
		this.x1 = x1;    
	}

	public int getY1(){//获取第一个点的y值
		return y1;   
	}

	public void setY1(int y1){//设置第一个点的y值
		this.y1 = y1;   
	}

	public int getX2(){//获取第二个点的x值
		return x2;   
	}

	public void setX2(int x2){//设置第二个点的x值
		this.x2 = x2;   
	}

	public int getY2(){ //获取第二个点的y值
		return y2;
	}

	public void setY2(int y2){ //设置第二个点的y值
		this.y2 = y2;
	}

	public Paint getMyColor(){ //获取颜色
		return myColor;   
	}

	public void setMyColor(Paint myColor){//设置颜色
		this.myColor = myColor;    
	}

	public boolean isFilled(){//判断是否要填充
		return filled;
	}

	public void setFilled(boolean filled){//设置是否填充
		this.filled = filled;
	}
	public abstract void draw(Graphics2D g); //draw方法在子类中重写
	
}
