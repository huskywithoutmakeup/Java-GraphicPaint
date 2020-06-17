import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
@SuppressWarnings("serial")
public class ThePanel extends JPanel {

	private MyShape[] shapes;//�洢���е�ͼ��
	private MyShape currentShape;//��ǰͼ��
	private int shapeNum;//ͼ�ε�����
	private int shapeType; //ͼ�ε�����
	private Paint currentColor;  //��ǰͼ����ɫ
	private boolean ifFilled; //ͼ���Ƿ����
	private float currentStroke;//��ǰ��������ϸ
	
	private JLabel mouseLabel;  //չʾ��ǰ���λ��

	public float getCurrentStroke(){ //��õ�ǰstroke����
		return currentStroke;
	}

	public void setCurrentStroke(float currentStroke){//���õ�ǰstroke����
		this.currentStroke = currentStroke;
	}


	public ThePanel(JLabel label){ //���췽��
		mouseLabel=label;
		shapes = new MyShape[100]; //���ٴ洢�ռ�
		shapeNum=0;
		shapeType=0;
		currentShape=null;
		currentColor=Color.BLACK; //Ĭ��Ϊ��ɫ
		currentStroke=1.0f;
		
		setBackground(Color.white);//����屳��ɫ����Ϊ��ɫ	
		MouseHandler handler=new MouseHandler(); //��������¼�������
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);		
	}

	public void paintComponent(Graphics g){ //����shapes�����е�ÿһ��ͼ��
		super.paintComponent(g);
		for(int i=0;i<shapeNum;i++) {
			shapes[i].draw((Graphics2D)g);
		}
	}	

	public MyShape[] getShapes(){//��ȡͼ������
		return shapes;
	}
	
	public void setShapes(MyShape[] shapess){//����ͼ������
		this.shapes = shapess;
	}
	
	public int getShapeNum(){//��ȡͼ������
		return shapeNum;
	}
	
	public void setShapeNum(int shapeNum){//����ͼ������
		this.shapeNum = shapeNum;
	}
	
	public int getShapeType(){//��ȡͼ������
		return shapeType;
	}

	public void setShapeType(int shapeType){//����ͼ������
		this.shapeType = shapeType;
	}

	public Paint getCurrentColor(){//��ȡ��ǰͼ����ɫ
		return currentColor;
	}

	public void setCurrentColor(Paint currentColor){//���õ�ǰͼ����ɫ
		this.currentColor = currentColor;
	}

	public boolean isFilledShape(){ //�жϵ�ǰͼ���Ƿ����
		return ifFilled;
	}

	public void setFilledShape(boolean isFilled){ //���õ�ǰͼ�ε�������
		this.ifFilled = isFilled;
	}
	
	public void resetShapeColor(int i,Paint color) {
		this.shapes[i].setMyColor(color);
		repaint();
	}
	
	public void resetShapeSize(int i,float scale) {
		int x1 = this.shapes[i].getX1();
		int x2 = this.shapes[i].getX2();
		int y1 = this.shapes[i].getY1();
		int y2 = this.shapes[i].getY2();
		float c = scale -1;
		this.shapes[i].setX1((int)(x1+x1*0.5*c-x2*0.5*c));
		this.shapes[i].setX2((int)(x2+x2*0.5*c-x1*0.5*c));
		this.shapes[i].setY1((int)(y1+y1*0.5*c-y2*0.5*c));
		this.shapes[i].setY2((int)(y2+y2*0.5*c-y1*0.5*c));
		repaint();
	}
	
	public void resetShapeLocation(int i,int x,int y) {
		int x1 = this.shapes[i].getX1();
		int x2 = this.shapes[i].getX2();
		int y1 = this.shapes[i].getY1();
		int y2 = this.shapes[i].getY2();
		this.shapes[i].setX1((int)(x+x1*0.5-x2*0.5));
		this.shapes[i].setX2((int)(x+x2*0.5-x1*0.5));
		this.shapes[i].setY1((int)(y+y1*0.5-y2*0.5));
		this.shapes[i].setY2((int)(y+y2*0.5-y1*0.5));
		repaint();
	}
	
	public void clearLastShape(){ //��������
		if(shapeNum>=1)
			shapeNum--;
		repaint();
	}
	
	public void clearDrawing(){ //�������ͼ��
		shapeNum=0;
		repaint();
	}
	
	private class MouseHandler extends MouseAdapter implements MouseMotionListener{ //������
		public void mousePressed(MouseEvent event){ //��갴��-��ͼ��ʼ
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
	
		public void mouseReleased(MouseEvent event){ //����ɿ�-��ͼ����
			currentShape.setX2(event.getX());
			currentShape.setY2(event.getY());
			shapes[shapeNum++]=currentShape;
			currentShape=null;
			repaint();
		}

		public void mouseMoved(MouseEvent event){//����ƶ�
			mouseLabel.setText(String.format("����ƶ��� [%d,%d]",event.getX(),event.getY()));
		}

		public void mouseDragged(MouseEvent event){//����϶�
			currentShape.setX2(event.getX());
			currentShape.setY2(event.getY());
	        repaint();
			mouseLabel.setText(String.format("����ƶ���[%d,%d]",event.getX(),event.getY()));
		}
	}
}