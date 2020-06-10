import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class TheFrame extends JFrame{
	private JLabel mouseLabel;//表示鼠标当前位置
	private ThePanel drawPanel;//绘图主面板
	private JPanel control1; //包含主要控制绘图选项的面板
	private JPanel control2; //包含次要控制绘图选项的面板
	private Color color1=Color.YELLOW,color2=Color.GREEN,drawColor = Color.WHITE;	//表示初末色型,画板色型的默认值
	private JTextField width,dashWidth; //线宽和虚线宽
	private float[] dashes;  //虚线存储
	
	public TheFrame(){
		super("xxx的简易适量图形编辑器"); //打印框架标题title
		
		setLayout(new BorderLayout()); //把框架设置为边界布局
		
		mouseLabel=new JLabel("鼠标坐标");
		drawPanel=new ThePanel(mouseLabel);
		control1=new JPanel();
		control2=new JPanel();
	
		control1.setLayout(new FlowLayout());//设置控制面板为流式布局
		control1.setAlignmentX(CENTER_ALIGNMENT); //居中对齐
		
		JButton revoke=new JButton("撤销");
		revoke.addActionListener( //用于单击按钮
				new ActionListener(){ //匿名内部类
					public void actionPerformed(ActionEvent event){
						drawPanel.clearLastShape();
					}
				});
		control1.add(revoke);//在控制面板1中加入撤销按钮
		
		JButton clear=new JButton("清空");		
		clear.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						drawPanel.clearDrawing();
					}
				});
		control1.add(clear);//在控制面板1中加入清空按钮
		
		
		String []shapeName={"直线","矩形","圆","三角形"};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JComboBox shapeChoose=new JComboBox(shapeName);//创建图形选择的勾选项
		shapeChoose.addItemListener( 
				new ItemListener(){ //匿名内部类
					public void itemStateChanged(ItemEvent event){
						drawPanel.setShapeType(shapeChoose.getSelectedIndex());
						}
				});
		control1.add(shapeChoose);//在控制面板1中加入图形选择项
		
		final JCheckBox filled=new JCheckBox("填充");//创建是否填充的勾选项
		filled.addItemListener(
				new ItemListener(){ 
					public void itemStateChanged(ItemEvent event){
						drawPanel.setFilledShape(filled.isSelected());
					}
				});
		control1.add(filled);//在控制面板1中加入填充勾选项
		
		control2.setLayout(new GridLayout(1,9,10,20)); //设置为网格布局1行8列
		
        final JButton panelColor=new JButton("画板色型");//创建两个控制渐变颜色的按钮
        panelColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				drawColor=JColorChooser.showDialog(TheFrame.this, "选择颜色", color1);//选择颜色1
				if(drawColor==null)
					drawColor=Color.WHITE;
				drawPanel.setBackground(drawColor);	
			}
		});
        control2.add(panelColor);
		
		final JCheckBox gradient=new JCheckBox("颜色渐变");//创建是否颜色渐变的勾选项
		gradient.addItemListener( //用于勾选
				new ItemListener(){ 
					public void itemStateChanged(ItemEvent event){
						if(gradient.isSelected())
						drawPanel.setCurrentColor(new GradientPaint(0,0,color1,50,50,color2,true));
						else
							drawPanel.setCurrentColor(color1);
					}
				});
		control2.add(gradient);//在控制面板2中加入颜色渐变勾选项
		
		final JButton initialColor=new JButton("初色型"),finalColor=new JButton("末色型");//创建两个控制渐变颜色的按钮
		
		initialColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				color1=JColorChooser.showDialog(TheFrame.this, "选择颜色", color1);//选择颜色1
				drawPanel.setCurrentColor(color1);
				if(color1==null)
					color1=Color.YELLOW;
				if(gradient.isSelected()) //如果被勾选
					drawPanel.setCurrentColor(new GradientPaint(0,0,color1,50,50,color2,true));
			}
		});
		
		finalColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				color2=JColorChooser.showDialog(TheFrame.this, "选择颜色", color2);//选择颜色2
				if(color2==null)
					color2=Color.GREEN;
				if(gradient.isSelected()) //如果被勾选
					drawPanel.setCurrentColor(new GradientPaint(0,0,color1,50,50,color2,true)); //设置当前渐变色
			}
		});
		
		control2.add(initialColor); //在控制面板2中加入初末颜色选择按钮
		control2.add(finalColor);
		
		width=new JTextField();     //创建表示线宽的文本框
		dashWidth=new JTextField();//创建表示虚线宽的文本框
		final JCheckBox dash=new JCheckBox("虚线");//创建是否是用虚线的勾选项
		
		control2.add(new JLabel("                   线 宽"));//加入"线宽"的标签
	    width.addActionListener(//用于文本框的回车
	    		new ActionListener(){
	    			public  void actionPerformed(ActionEvent event){
	    				if(!width.getText().isEmpty()){ //如果线宽、虚线宽的文本为不为空且虚线选项被勾选
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
		
		control2.add(new JLabel("                  虚线宽"));//加入"虚线宽"的标签
		 dashWidth.addActionListener(
		    		new ActionListener(){
		    			public void actionPerformed(ActionEvent event){
		    				if(!dashWidth.getText().isEmpty()&&!width.getText().isEmpty()){ //如果线宽、虚线宽的文本为不为空且虚线选项被勾选
		    					if(dash.isSelected()){
									dashes=new float[1]; 
									dashes[0]=Integer.parseInt(dashWidth.getText());  //重设stroke属性，主要是重写线宽和赋予虚线的属性
									drawPanel.setCurrentStroke(new BasicStroke(Integer.parseInt(width.getText()),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,
											10,dashes,0));	
								}	
		    				}
		    			}
		    		});
		control2.add(dashWidth);///在控制面板2中加入虚线宽文本框
		
		dash.addItemListener(
				new ItemListener(){
					public void itemStateChanged(ItemEvent event){
						if(dash.isSelected()){ //判断是否勾选
							dashes=new float[1];
							if(!dashWidth.getText().isEmpty()&&!width.getText().isEmpty()){ //虚线宽和实线宽不为空
								dashes[0]=Integer.parseInt(dashWidth.getText());
							    drawPanel.setCurrentStroke(new BasicStroke(Integer.parseInt(width.getText()),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,
							    10,dashes,0));	
							}						
						}
						   
						else { //未勾选
							if(!width.getText().isEmpty())
							drawPanel.setCurrentStroke(new BasicStroke(Integer.parseInt(width.getText()),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
							
						}
					}
				});
		control2.add(dash);//在控制面板2中加入是否虚线勾选项
		
		
		JPanel CONTROL=new JPanel();//创建主控面板
		CONTROL.setLayout(new BorderLayout());//设置为边界布局
		CONTROL.add(control1,BorderLayout.NORTH);//将control1面板和control2面板加入主控面板
		CONTROL.add(control2,BorderLayout.SOUTH);
		add(CONTROL,BorderLayout.NORTH);  //将主控制面板放在容器顶端
		add(drawPanel,BorderLayout.CENTER);//将画图的主面板放在容器中间
		add(mouseLabel,BorderLayout.SOUTH);//将显示鼠标坐标的标签放在容器底端
	}
}

