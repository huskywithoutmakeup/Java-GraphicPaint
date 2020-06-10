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
	private Stroke currentStroke;//��ǰstroke����
	
	private JLabel mouseLabel;  //չʾ��ǰ���λ��


	public Stroke getCurrentStroke(){ //��õ�ǰstroke����
		return currentStroke;
	}

	public void setCurrentStroke(Stroke currentStroke){//���õ�ǰstroke����
		this.currentStroke = currentStroke;
	}


	public ThePanel(JLabel label){ //���췽��
		mouseLabel=label;
		
		shapes = new MyShape[100]; //���ٴ洢�ռ�
		shapeNum=0;
		shapeType=0;
		currentShape=null;
		currentColor=Color.BLACK; //Ĭ��Ϊ��ɫ
		currentStroke=new BasicStroke();
		
		setBackground(Color.white);//����屳��ɫ����Ϊ��ɫ	
		MouseHandler handler=new MouseHandler(); //��������¼�������
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);		
	}
	

	public void paintComponent(Graphics g){ //����shapes�����е�ÿһ��ͼ��
		super.paintComponent(g);
		for(int i=0;i<shapeNum;i++)
			shapes[i].draw((Graphics2D)g);
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