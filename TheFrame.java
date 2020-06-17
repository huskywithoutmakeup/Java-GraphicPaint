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
	private JLabel mouseLabel;//��ʾ��굱ǰλ��
	private ThePanel drawPanel;//��ͼ�����
	private JPanel control1; //������Ҫ���ƻ�ͼѡ������
	private JPanel control2; //������Ҫ���ƻ�ͼѡ������
	private Color shapeColor=Color.YELLOW,drawColor = Color.WHITE;	//��ʾ��ĩɫ��,����ɫ�͵�Ĭ��ֵ
	private JTextField width; //�߿�����߿�
	private ObjectOutputStream output; // ����������������������úͱ���ͼ���ļ�
	private ObjectInputStream input;
	private int index;
	
	public TheFrame(){
		super("1904910002����Ⱥ�ļ�������ͼ�α༭��"); //��ӡ��ܱ���title
		
		setLayout(new BorderLayout()); //�ѿ������Ϊ�߽粼��
		
		mouseLabel=new JLabel("�������");
		drawPanel=new ThePanel(mouseLabel);
		control1=new JPanel();
		control2=new JPanel();
	
		control1.setLayout(new FlowLayout());//���ÿ������Ϊ��ʽ����
		control1.setAlignmentX(CENTER_ALIGNMENT); //���ж���
		
		JButton openFile=new JButton("��ͼƬ");
		openFile.addActionListener( 
				new ActionListener(){ 
					public void actionPerformed(ActionEvent event){
						fileOpen();
					}
				});
		control1.add(openFile);
		
		JButton saveFile=new JButton("����ͼƬ");
		saveFile.addActionListener( 
				new ActionListener(){ 
					public void actionPerformed(ActionEvent event){
						fileSave();
					}
				});
		control1.add(saveFile);
		
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
		
		control1.add(new JLabel(" ѡȡ"));
		
		String[] number = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JComboBox numChoose=new JComboBox(number);//ѡ���޸ĵ�ͼ��
		numChoose.addItemListener( 
				new ItemListener(){ //�����ڲ���
					public void itemStateChanged(ItemEvent event){						
						index =numChoose.getSelectedIndex();
						}
				});
		control1.add(numChoose);//�ڿ������1�м���ͼ��ѡ����
		
		control2.setLayout(new GridLayout(1,7,10,20)); //����Ϊ���񲼾�1��6��
		
        final JButton panelColor=new JButton("����ɫ��");//�����������ƽ�����ɫ�İ�ť
        panelColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				drawColor=JColorChooser.showDialog(TheFrame.this, "ѡ����ɫ", shapeColor);//ѡ����ɫ1
				if(drawColor==null)
					drawColor=Color.WHITE;
				drawPanel.setBackground(drawColor);	
			}
		});
        control2.add(panelColor);
		
		final JButton changeColor=new JButton("ͼ��ɫ��");//����һ��������ɫ�İ�ť
		changeColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				shapeColor=JColorChooser.showDialog(TheFrame.this, "ѡ����ɫ", shapeColor);//ѡ����ɫ1
				drawPanel.setCurrentColor(shapeColor);
				if(shapeColor==null)
					shapeColor=Color.YELLOW;
			}
		});		
		control2.add(changeColor); //�ڿ������2�м�����ɫѡ��ť
		
		final JButton resetColor=new JButton("����ѡȡͼ����ɫ");//����һ������ѡȡͼ����ɫ�İ�ť
		resetColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				Paint newColor=JColorChooser.showDialog(TheFrame.this, "ѡ����ɫ", shapeColor);
				if(shapeColor==null)
					shapeColor=Color.YELLOW;
				drawPanel.resetShapeColor(index, newColor);
			}
		});		
		control2.add(resetColor);
		
		final JButton resetSize=new JButton("����ѡȡͼ�ι�ģ");//����һ������ѡȡͼ�ι�ģ�İ�ť
		resetSize.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){				
				String input = JOptionPane.showInputDialog("������һ������0�������������Ź�ģ!");
				if(input==null) {
					input = "1";
				}
				float scale = Float.parseFloat(input);
				drawPanel.resetShapeSize(index, scale);
			}
		});		
		control2.add(resetSize);
		
		final JButton resetLocation=new JButton("����ѡȡͼ��λ��");//����һ������ѡȡͼ��λ�õİ�ť
		resetLocation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){				
				String input1 = JOptionPane.showInputDialog("������һ������������ͼ�����ĵ�Xֵ!");
				String input2 = JOptionPane.showInputDialog("������һ������������ͼ�����ĵ�Yֵ!");
				if(input1==null||input2==null) {
					JOptionPane.showMessageDialog(null, "�������!"); 
				}
				int x = Integer.parseInt(input1);
				int y = Integer.parseInt(input2);
				drawPanel.resetShapeLocation(index,x,y);
			}
		});		
		control2.add(resetLocation);
		
		control2.add(new JLabel("                         �� ��"));//����"�߿�"�ı�ǩ
		
		width=new JTextField();     //������ʾ�߿���ı���				
	    width.addActionListener(//�����ı���Ļس�
	    		new ActionListener(){
	    			public  void actionPerformed(ActionEvent event){
	    				if(!width.getText().isEmpty()) //����߿��ı�Ϊ��Ϊ��
	    					drawPanel.setCurrentStroke(Integer.parseInt(width.getText()));
							
						else  //���������ΪĬ��ֵ
							drawPanel.setCurrentStroke(1.0f);								
	    				}
	    		   });
		control2.add(width);	
		
		JPanel CONTROL=new JPanel();//�����������
		CONTROL.setLayout(new BorderLayout());//����Ϊ�߽粼��
		CONTROL.add(control1,BorderLayout.NORTH);//��control1����control2�������������
		CONTROL.add(control2,BorderLayout.SOUTH);
		add(CONTROL,BorderLayout.NORTH);  //����������������������
		add(drawPanel,BorderLayout.CENTER);//����ͼ���������������м�
		add(mouseLabel,BorderLayout.SOUTH);//����ʾ�������ı�ǩ���������׶�
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
					output.flush(); // ������ͼ����Ϣǿ��ת���ɸ������Ի��洢���ļ���
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

