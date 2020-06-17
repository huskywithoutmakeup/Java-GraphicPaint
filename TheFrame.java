import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;


@SuppressWarnings("serial")
public class TheFrame extends JFrame{
	private JLabel mouseLabel;//表示鼠标当前位置
	private ThePanel drawPanel;//绘图主面板
	private JPanel control1; //包含主要控制绘图选项的面板
	private JPanel control2; //包含次要控制绘图选项的面板
	private Color shapeColor=Color.YELLOW,drawColor = Color.WHITE;	//表示初末色型,画板色型的默认值
	private JTextField width; //线宽和虚线宽
	private ObjectOutputStream output; // 定义输入输出流，用来调用和保存图像文件
	private ObjectInputStream input;
	private int index;
	
	public TheFrame(){
		super("1904910002张泽群的简易适量图形编辑器"); //打印框架标题title
		
		setLayout(new BorderLayout()); //把框架设置为边界布局
		
		mouseLabel=new JLabel("鼠标坐标");
		drawPanel=new ThePanel(mouseLabel);
		control1=new JPanel();
		control2=new JPanel();
	
		control1.setLayout(new FlowLayout());//设置控制面板为流式布局
		control1.setAlignmentX(CENTER_ALIGNMENT); //居中对齐
		
		JButton openFile=new JButton("打开图片");
		openFile.addActionListener( 
				new ActionListener(){ 
					public void actionPerformed(ActionEvent event){
						fileOpen();
					}
				});
		control1.add(openFile);
		
		JButton saveFile=new JButton("保存图片");
		saveFile.addActionListener( 
				new ActionListener(){ 
					public void actionPerformed(ActionEvent event){
						fileSave();
					}
				});
		control1.add(saveFile);
		
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
		
		control1.add(new JLabel(" 选取"));
		
		String[] number = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JComboBox numChoose=new JComboBox(number);//选择修改的图形
		numChoose.addItemListener( 
				new ItemListener(){ //匿名内部类
					public void itemStateChanged(ItemEvent event){						
						index =numChoose.getSelectedIndex();
						}
				});
		control1.add(numChoose);//在控制面板1中加入图形选择项
		
		control2.setLayout(new GridLayout(1,7,10,20)); //设置为网格布局1行6列
		
        final JButton panelColor=new JButton("画板色型");//创建两个控制渐变颜色的按钮
        panelColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				drawColor=JColorChooser.showDialog(TheFrame.this, "选择颜色", shapeColor);//选择颜色1
				if(drawColor==null)
					drawColor=Color.WHITE;
				drawPanel.setBackground(drawColor);	
			}
		});
        control2.add(panelColor);
		
		final JButton changeColor=new JButton("图形色型");//创建一个控制颜色的按钮
		changeColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				shapeColor=JColorChooser.showDialog(TheFrame.this, "选择颜色", shapeColor);//选择颜色1
				drawPanel.setCurrentColor(shapeColor);
				if(shapeColor==null)
					shapeColor=Color.YELLOW;
			}
		});		
		control2.add(changeColor); //在控制面板2中加入颜色选择按钮
		
		final JButton resetColor=new JButton("重置选取图形颜色");//创建一个重置选取图形颜色的按钮
		resetColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Paint newColor=JColorChooser.showDialog(TheFrame.this, "选择颜色", shapeColor);
				if(shapeColor==null)
					shapeColor=Color.YELLOW;
				drawPanel.resetShapeColor(index, newColor);
			}
		});		
		control2.add(resetColor);
		
		final JButton resetSize=new JButton("重置选取图形规模");//创建一个重置选取图形规模的按钮
		resetSize.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){				
				String input = JOptionPane.showInputDialog("请输入一个大于0的数来设置缩放规模!");
				if(input==null) {
					input = "1";
				}
				float scale = Float.parseFloat(input);
				drawPanel.resetShapeSize(index, scale);
			}
		});		
		control2.add(resetSize);
		
		final JButton resetLocation=new JButton("重置选取图形位置");//创建一个重置选取图形位置的按钮
		resetLocation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){				
				String input1 = JOptionPane.showInputDialog("请输入一个整数来设置图形中心的X值!");
				String input2 = JOptionPane.showInputDialog("请输入一个整数来设置图形中心的Y值!");
				if(input1==null||input2==null) {
					JOptionPane.showMessageDialog(null, "输入错误!"); 
				}
				int x = Integer.parseInt(input1);
				int y = Integer.parseInt(input2);
				drawPanel.resetShapeLocation(index,x,y);
			}
		});		
		control2.add(resetLocation);
		
		control2.add(new JLabel("                         线 宽"));//加入"线宽"的标签
		
		width=new JTextField();     //创建表示线宽的文本框				
	    width.addActionListener(//用于文本框的回车
	    		new ActionListener(){
	    			public  void actionPerformed(ActionEvent event){
	    				if(!width.getText().isEmpty()) //如果线宽文本为不为空
	    					drawPanel.setCurrentStroke(Integer.parseInt(width.getText()));
							
						else  //否则就设置为默认值
							drawPanel.setCurrentStroke(1.0f);								
	    				}
	    		   });
		control2.add(width);	
		
		JPanel CONTROL=new JPanel();//创建主控面板
		CONTROL.setLayout(new BorderLayout());//设置为边界布局
		CONTROL.add(control1,BorderLayout.NORTH);//将control1面板和control2面板加入主控面板
		CONTROL.add(control2,BorderLayout.SOUTH);
		add(CONTROL,BorderLayout.NORTH);  //将主控制面板放在容器顶端
		add(drawPanel,BorderLayout.CENTER);//将画图的主面板放在容器中间
		add(mouseLabel,BorderLayout.SOUTH);//将显示鼠标坐标的标签放在容器底端
	}
	
	public void fileSave() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.CANCEL_OPTION)
			return;
		File fileName = fileChooser.getSelectedFile();
		fileName.canWrite();

		if (fileName == null || fileName.getName().equals(""))
			JOptionPane.showMessageDialog(fileChooser, "Invalid File Name",
					"Invalid File Name", JOptionPane.ERROR_MESSAGE);
		else {
			try {
				fileName.delete();
				FileOutputStream fos = new FileOutputStream(fileName);

				output = new ObjectOutputStream(fos);
                MyShape[] list = drawPanel.getShapes();
				output.writeInt(drawPanel.getShapeNum());

				for (int i = 0; i < drawPanel.getShapeNum(); i++) {
					MyShape p = list[i];
					output.writeObject(p);
					output.flush(); // 将所有图形信息强制转换成父类线性化存储到文件中
				}
				output.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void fileOpen() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.CANCEL_OPTION)
			return;
		File fileName = fileChooser.getSelectedFile();
		fileName.canRead();
		if (fileName == null || fileName.getName().equals(""))
			JOptionPane.showMessageDialog(fileChooser, "Invalid File Name",
					"Invalid File Name", JOptionPane.ERROR_MESSAGE);
		else {
			try {
				FileInputStream fis = new FileInputStream(fileName);
				input = new ObjectInputStream(fis);
				MyShape inputRecord;
				int countNumber = 0;
				countNumber = input.readInt();
				int i;
				MyShape[] list = new MyShape[100];
				for (i = 0; i < countNumber; i++) {
					inputRecord = (MyShape) input.readObject();
					list[i] = inputRecord;
				}
				drawPanel.setShapeNum(i);
				drawPanel.setShapes(list);
				input.close();
				repaint();
			} catch (EOFException endofFileException) {
				JOptionPane.showMessageDialog(this, "no more record in file",
						"class not found", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException classNotFoundException) {
				JOptionPane.showMessageDialog(this, "Unable to Create Object",
						"end of file", JOptionPane.ERROR_MESSAGE);
			} catch (IOException ioException) {
				JOptionPane.showMessageDialog(this,
						"error during read from file", "read Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

