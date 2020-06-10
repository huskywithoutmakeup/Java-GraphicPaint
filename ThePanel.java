import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
@SuppressWarnings("serial")
public class ThePanel extends JPanel {

	private MyShape[] shapes;//存储所有的图形
	private MyShape currentShape;//当前图形
	private int shapeNum;//图形的数量
	private int shapeType; //图形的类型
	private Paint currentColor;  //当前图形颜色
	private boolean ifFilled; //图形是否填充
	private Stroke currentStroke;//当前stroke属性
	
	private JLabel mouseLabel;  //展示当前鼠标位置


	public Stroke getCurrentStroke(){ //获得当前stroke属性
		return currentStroke;
	}

	public void setCurrentStroke(Stroke currentStroke){//设置当前stroke属性
		this.currentStroke = currentStroke;
	}


	public ThePanel(JLabel label){ //构造方法
		mouseLabel=label;
		
		shapes = new MyShape[100]; //开辟存储空间
		shapeNum=0;
		shapeType=0;
		currentShape=null;
		currentColor=Color.BLACK; //默认为黑色
		currentStroke=new BasicStroke();
		
		setBackground(Color.white);//把面板背景色设置为白色	
		MouseHandler handler=new MouseHandler(); //创建鼠标事件处理器
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);		
	}
	

	public void paintComponent(Graphics g){ //画出shapes数组中的每一个图形
		super.paintComponent(g);
		for(int i=0;i<shapeNum;i++)
			shapes[i].draw((Graphics2D)g);
	}	

	public int getShapeType(){//获取图形类型
		return shapeType;
	}

	public void setShapeType(int shapeType){//设置图形类型
		this.shapeType = shapeType;
	}

	public Paint getCurrentColor(){//获取当前图形颜色
		return currentColor;
	}

	public void setCurrentColor(Paint currentColor){//设置当前图形颜色
		this.currentColor = currentColor;
	}

	public boolean isFilledShape(){ //判断当前图形是否填充
		return ifFilled;
	}

	public void setFilledShape(boolean isFilled){ //设置当前图形的填充情况
		this.ifFilled = isFilled;
	}
	
	
	public void clearLastShape(){ //撤销操作
		if(shapeNum>=1)
			shapeNum--;
		repaint();
	}
	
	public void clearDrawing(){ //清空所有图形
		shapeNum=0;
		repaint();
	}
	
	private class MouseHandler extends MouseAdapter implements MouseMotionListener{ //鼠标控制
		public void mousePressed(MouseEvent event){ //鼠标按下-画图开始
			switch(shapeType){
			case 0:currentShape=new MyLine(currentColor,currentStroke);
			break;
			case 1:currentShape=new MyRectangle(currentColor,currentStroke);
			break;
			case 2:currentShape=new MyOval(currentColor,currentStroke);
			break;
			case 3:currentShape=new MyTriangle(currentColor,currentStroke);
			}
			currentShape.setFilled(ifFilled);
			currentShape.setX1(event.getX());
			currentShape.setY1(event.getY());
		}
	
		public void mouseReleased(MouseEvent event){ //鼠标松开-画图结束
			currentShape.setX2(event.getX());
			currentShape.setY2(event.getY());
			shapes[shapeNum++]=currentShape;
			currentShape=null;
			repaint();
		}

		public void mouseMoved(MouseEvent event){//鼠标移动
			mouseLabel.setText(String.format("鼠标移动到 [%d,%d]",event.getX(),event.getY()));
		}

		public void mouseDragged(MouseEvent event){//鼠标拖动
			currentShape.setX2(event.getX());
			currentShape.setY2(event.getY());
	        repaint();
			mouseLabel.setText(String.format("鼠标移动到[%d,%d]",event.getX(),event.getY()));
		}
	}
}