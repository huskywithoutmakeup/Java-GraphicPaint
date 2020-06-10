import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class TheFrame extends JFrame{
	private JLabel mouseLabel;//��ʾ��굱ǰλ��
	private ThePanel drawPanel;//��ͼ�����
	private JPanel control1; //������Ҫ���ƻ�ͼѡ������
	private JPanel control2; //������Ҫ���ƻ�ͼѡ������
	private Color color1=Color.YELLOW,color2=Color.GREEN,drawColor = Color.WHITE;	//��ʾ��ĩɫ��,����ɫ�͵�Ĭ��ֵ
	private JTextField width,dashWidth; //�߿�����߿�
	private float[] dashes;  //���ߴ洢
	
	public TheFrame(){
		super("xxx�ļ�������ͼ�α༭��"); //��ӡ��ܱ���title
		
		setLayout(new BorderLayout()); //�ѿ������Ϊ�߽粼��
		
		mouseLabel=new JLabel("�������");
		drawPanel=new ThePanel(mouseLabel);
		control1=new JPanel();
		control2=new JPanel();
	
		control1.setLayout(new FlowLayout());//���ÿ������Ϊ��ʽ����
		control1.setAlignmentX(CENTER_ALIGNMENT); //���ж���
		
		JButton revoke=new JButton("����");
		revoke.addActionListener( //���ڵ�����ť
				new ActionListener(){ //�����ڲ���
					public void actionPerformed(ActionEvent event){
						drawPanel.clearLastShape();
					}
				});
		control1.add(revoke);//�ڿ������1�м��볷����ť
		
		JButton clear=new JButton("���");		
		clear.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						drawPanel.clearDrawing();
					}
				});
		control1.add(clear);//�ڿ������1�м�����հ�ť
		
		
		String []shapeName={"ֱ��","����","Բ","������"};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JComboBox shapeChoose=new JComboBox(shapeName);//����ͼ��ѡ��Ĺ�ѡ��
		shapeChoose.addItemListener( 
				new ItemListener(){ //�����ڲ���
					public void itemStateChanged(ItemEvent event){
						drawPanel.setShapeType(shapeChoose.getSelectedIndex());
						}
				});
		control1.add(shapeChoose);//�ڿ������1�м���ͼ��ѡ����
		
		final JCheckBox filled=new JCheckBox("���");//�����Ƿ����Ĺ�ѡ��
		filled.addItemListener(
				new ItemListener(){ 
					public void itemStateChanged(ItemEvent event){
						drawPanel.setFilledShape(filled.isSelected());
					}
				});
		control1.add(filled);//�ڿ������1�м�����乴ѡ��
		
		control2.setLayout(new GridLayout(1,9,10,20)); //����Ϊ���񲼾�1��8��
		
        final JButton panelColor=new JButton("����ɫ��");//�����������ƽ�����ɫ�İ�ť
        panelColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				drawColor=JColorChooser.showDialog(TheFrame.this, "ѡ����ɫ", color1);//ѡ����ɫ1
				if(drawColor==null)
					drawColor=Color.WHITE;
				drawPanel.setBackground(drawColor);	
			}
		});
        control2.add(panelColor);
		
		final JCheckBox gradient=new JCheckBox("��ɫ����");//�����Ƿ���ɫ����Ĺ�ѡ��
		gradient.addItemListener( //���ڹ�ѡ
				new ItemListener(){ 
					public void itemStateChanged(ItemEvent event){
						if(gradient.isSelected())
						drawPanel.setCurrentColor(new GradientPaint(0,0,color1,50,50,color2,true));
						else
							drawPanel.setCurrentColor(color1);
					}
				});
		control2.add(gradient);//�ڿ������2�м�����ɫ���乴ѡ��
		
		final JButton initialColor=new JButton("��ɫ��"),finalColor=new JButton("ĩɫ��");//�����������ƽ�����ɫ�İ�ť
		
		initialColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				color1=JColorChooser.showDialog(TheFrame.this, "ѡ����ɫ", color1);//ѡ����ɫ1
				drawPanel.setCurrentColor(color1);
				if(color1==null)
					color1=Color.YELLOW;
				if(gradient.isSelected()) //�������ѡ
					drawPanel.setCurrentColor(new GradientPaint(0,0,color1,50,50,color2,true));
			}
		});
		
		finalColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				color2=JColorChooser.showDialog(TheFrame.this, "ѡ����ɫ", color2);//ѡ����ɫ2
				if(color2==null)
					color2=Color.GREEN;
				if(gradient.isSelected()) //�������ѡ
					drawPanel.setCurrentColor(new GradientPaint(0,0,color1,50,50,color2,true)); //���õ�ǰ����ɫ
			}
		});
		
		control2.add(initialColor); //�ڿ������2�м����ĩ��ɫѡ��ť
		control2.add(finalColor);
		
		width=new JTextField();     //������ʾ�߿���ı���
		dashWidth=new JTextField();//������ʾ���߿���ı���
		final JCheckBox dash=new JCheckBox("����");//�����Ƿ��������ߵĹ�ѡ��
		
		control2.add(new JLabel("                   �� ��"));//����"�߿�"�ı�ǩ
	    width.addActionListener(//�����ı���Ļس�
	    		new ActionListener(){
	    			public  void actionPerformed(ActionEvent event){
	    				if(!width.getText().isEmpty()){ //����߿����߿���ı�Ϊ��Ϊ��������ѡ���ѡ
	    					if(dash.isSelected()&&!dashWidth.getText().isEmpty()){
								dashes=new float[1];
								dashes[0]=Integer.parseInt(dashWidth.getText());
								drawPanel.setCurrentStroke(new BasicStroke(Integer.parseInt(width.getText()),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,
										10,dashes,0));	
							}
							else
								drawPanel.setCurrentStroke(new BasicStroke(Integer.parseInt(width.getText()),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
								
	    				}
	    			}
	    		});
		control2.add(width);
		
		control2.add(new JLabel("                  ���߿�"));//����"���߿�"�ı�ǩ
		 dashWidth.addActionListener(
		    		new ActionListener(){
		    			public void actionPerformed(ActionEvent event){
		    				if(!dashWidth.getText().isEmpty()&&!width.getText().isEmpty()){ //����߿����߿���ı�Ϊ��Ϊ��������ѡ���ѡ
		    					if(dash.isSelected()){
									dashes=new float[1]; 
									dashes[0]=Integer.parseInt(dashWidth.getText());  //����stroke���ԣ���Ҫ����д�߿�͸������ߵ�����
									drawPanel.setCurrentStroke(new BasicStroke(Integer.parseInt(width.getText()),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,
											10,dashes,0));	
								}	
		    				}
		    			}
		    		});
		control2.add(dashWidth);///�ڿ������2�м������߿��ı���
		
		dash.addItemListener(
				new ItemListener(){
					public void itemStateChanged(ItemEvent event){
						if(dash.isSelected()){ //�ж��Ƿ�ѡ
							dashes=new float[1];
							if(!dashWidth.getText().isEmpty()&&!width.getText().isEmpty()){ //���߿��ʵ�߿�Ϊ��
								dashes[0]=Integer.parseInt(dashWidth.getText());
							    drawPanel.setCurrentStroke(new BasicStroke(Integer.parseInt(width.getText()),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,
							    10,dashes,0));	
							}						
						}
						   
						else { //δ��ѡ
							if(!width.getText().isEmpty())
							drawPanel.setCurrentStroke(new BasicStroke(Integer.parseInt(width.getText()),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
							
						}
					}
				});
		control2.add(dash);//�ڿ������2�м����Ƿ����߹�ѡ��
		
		
		JPanel CONTROL=new JPanel();//�����������
		CONTROL.setLayout(new BorderLayout());//����Ϊ�߽粼��
		CONTROL.add(control1,BorderLayout.NORTH);//��control1����control2�������������
		CONTROL.add(control2,BorderLayout.SOUTH);
		add(CONTROL,BorderLayout.NORTH);  //����������������������
		add(drawPanel,BorderLayout.CENTER);//����ͼ���������������м�
		add(mouseLabel,BorderLayout.SOUTH);//����ʾ�������ı�ǩ���������׶�
	}
}

