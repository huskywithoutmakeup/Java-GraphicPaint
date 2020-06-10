
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

public abstract class MyShape{
	private int x1;  //��һ�����xֵ	
	private int y1;  //��һ�����yֵ	
	private int x2;  //�ڶ������xֵ	
	private int y2;  //�ڶ������yֵ	
	private Paint myColor;//ͼ�ε���ɫ
	private boolean filled;//ͼ���Ƿ����
	private Stroke stroke; //stroke�������ڿ��������Ŀ�ȡ�������ʽ���߶����ӷ�ʽ��̻���ͼ��
	
	public MyShape(){//ȱʡ���취
		stroke=new BasicStroke(); 
	}
	
	public MyShape(Paint p,Stroke s){ //����2 ��ɫ+stroke
		this.setMyColor(p);
		this.setStroke(s);
	}
	
	public MyShape(Stroke s){ //����3 stroke
		this.setStroke(s);
	}
	
	public MyShape(int x1, int y1, int x2, int y2,Paint myColor){ //����4
		super();
		this.x1 = x1;  
		this.y1 = y1;  
		this.x2 = x2;  
		this.y2 = y2;   
		this.myColor = myColor;  
	}
	
	public Stroke getStroke(){//���stroke����
		return stroke;
	}
	
	public void setStroke(Stroke stroke){//����stroke����
		this.stroke = stroke;
	}
	
	public int getX1(){//��ȡ��һ�����xֵ
		return x1;      
	}

	public void setX1(int x1){//���õ�һ�����xֵ
		this.x1 = x1;    
	}

	public int getY1(){//��ȡ��һ�����yֵ
		return y1;   
	}

	public void setY1(int y1){//���õ�һ�����yֵ
		this.y1 = y1;   
	}

	public int getX2(){//��ȡ�ڶ������xֵ
		return x2;   
	}

	public void setX2(int x2){//���õڶ������xֵ
		this.x2 = x2;   
	}

	public int getY2(){ //��ȡ�ڶ������yֵ
		return y2;
	}

	public void setY2(int y2){ //���õڶ������yֵ
		this.y2 = y2;
	}

	public Paint getMyColor(){ //��ȡ��ɫ
		return myColor;   
	}

	public void setMyColor(Paint myColor){//������ɫ
		this.myColor = myColor;    
	}

	public boolean isFilled(){//�ж��Ƿ�Ҫ���
		return filled;
	}

	public void setFilled(boolean filled){//�����Ƿ����
		this.filled = filled;
	}
	public abstract void draw(Graphics2D g); //draw��������������д
	
}
