package analysisData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jfree.ui.RefineryUtilities;

public class LeastSquareWin {
	private String fileName = "nullnull";
	private String fileName1 = "nullnull";
	private JLabel kLabel = null;
	private JLabel bLabel = null;
	private JLabel kbLabel = null;
	private JLabel dLabel = null;
	
	private JLabel curveLabel = null;
	private JLabel RLabel = null;
	private JLabel SLabel = null;
	private JLabel MLabel = null;
	private JLabel NLabel = null;
	private JLabel LLabel = null;
	
	
	private GridBagLayout gb = new GridBagLayout();
	private GridBagConstraints gbc = new GridBagConstraints();
	
	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
	    for (Enumeration<Object> keys = UIManager.getDefaults().keys();
	         keys.hasMoreElements(); ) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get(key);
	      if (value instanceof FontUIResource) {
	        UIManager.put(key, fontRes);
	      }
	    }
	}

	
	public void createWin() {
		Font font = new Font("宋体",Font.PLAIN,20);
		InitGlobalFont(font);
		JFrame frame = new JFrame("泥页岩水压致裂效果评价的可视化系统");
		
		//********
		JLabel title = new JLabel("泥页岩水压致裂效果评价的可视化系统",JLabel.CENTER);
		Font fontt=new Font("宋体", Font.PLAIN, 30);
		title.setFont(fontt);
		frame.add(title, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();		
		// Configure the layout
		panel.setLayout(this.gb);
		gbc.fill = GridBagConstraints.BOTH;
		
		//********
		gbc.weightx = 1;
		gbc.gridwidth=1;
		gbc.insets= new Insets(4,50,4,10);
		JButton bSelect = new JButton("选择二维数据文件");
		gb.setConstraints(bSelect, gbc);
		panel.add(bSelect);
				
		FileDialog fd = new FileDialog(frame,"打开文件",FileDialog.LOAD);		
		bSelect.addActionListener(e->{
			fd.setVisible(true);
			fileName = fd.getDirectory()+fd.getFile();			
		});
		
		//********
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets= new Insets(4,10,4,50);
		JButton bGenerate = new JButton("生成二维拟合图形");
		gb.setConstraints(bGenerate, gbc);
		panel.add(bGenerate);
		
		bGenerate.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent arg0) {
            	if (fileName.equals("nullnull")) {
            		JOptionPane.showMessageDialog(
                            frame,
                            "请选中文件",
                            "文件是空",
                            JOptionPane.WARNING_MESSAGE
                    );
    			}else {
    				LeastSquareDrawLineScatter dls = new LeastSquareDrawLineScatter("工程数据线性拟合", fileName);
    				dls.pack();
    		        RefineryUtilities.centerFrameOnScreen(dls);
    		        dls.setVisible(true);
    		        dls.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		        
    		        kbLabel.setText("拟合直线：Y=" + String.format("%.6f",dls.getK()) +"X"+String.format("%.6f",dls.getB()));
    		        
    				kLabel.setText("拟合直线的k值：" + String.format("%.10f",dls.getK()));
    				bLabel.setText("拟合直线的b值：" + String.format("%.10f",dls.getB()));
    				dLabel.setText("分形维数D值：" + String.format("%.10f",dls.getK()));
    			}
            }
        });
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		kLabel = new JLabel();
		kLabel.setForeground(Color.red);
		kLabel.setText("无拟合直线的k值");
		gb.setConstraints(kLabel, gbc);
		panel.add(kLabel);
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		bLabel = new JLabel();
		bLabel.setForeground(Color.red);
		bLabel.setText("无拟合直线的b值");
		gb.setConstraints(bLabel, gbc);
		panel.add(bLabel);
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		dLabel = new JLabel();
		dLabel.setForeground(Color.red);
		dLabel.setText("无分形维数D值");
		gb.setConstraints(dLabel, gbc);
		panel.add(dLabel);
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		kbLabel = new JLabel();
		kbLabel.setForeground(Color.red);
		kbLabel.setText("无拟合直线");
		gb.setConstraints(kbLabel, gbc);
		panel.add(kbLabel);
		
		
		//********
		gbc.gridwidth=1;
		gbc.insets= new Insets(60,50,4,10);
		JButton bSelect1 = new JButton("选择多维数据文件");
		gb.setConstraints(bSelect1, gbc);
		panel.add(bSelect1);
				
		FileDialog fd1 = new FileDialog(frame,"打开文件",FileDialog.LOAD);		
		bSelect1.addActionListener(e->{
			fd1.setVisible(true);
			fileName1 = fd1.getDirectory()+fd1.getFile();			
		});
		
		//******
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets= new Insets(60,10,4,50);
		JButton bGenerate1 = new JButton("生成多维拟合曲线方程");
		gb.setConstraints(bGenerate1, gbc);
		panel.add(bGenerate1);
		
		bGenerate1.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent arg0) {
            	if (fileName1.equals("nullnull")) {
            		JOptionPane.showMessageDialog(
                            frame,
                            "请选中文件",
                            "文件是空",
                            JOptionPane.WARNING_MESSAGE
                    );
    			}else {
    				MultFitLine mfl = new MultFitLine();
    		        ArrayList<ArrayList<Double>> Xdata = mfl.readFile(fileName1);
    		        ArrayList<Double> KB = mfl.valuesKB(Xdata);
    		        
    		        curveLabel.setText("拟合曲线：D="+String.format("%.6f",KB.get(0))+"S+"+String.format("%.6f",KB.get(1))+
    		        		"M+"+String.format("%.6f",KB.get(2))+"N+" +String.format("%.6f",KB.get(3)));
    		        SLabel.setText("S拟合系数：" + String.format("%.10f",KB.get(0)));
    		        MLabel.setText("M拟合系数：" + String.format("%.10f",KB.get(1)));
    		        NLabel.setText("N拟合系数：" + String.format("%.10f",KB.get(2)));
    		        LLabel.setText("余项：" + String.format("%.10f",KB.get(3)));
    		        
    		        RLabel.setText("拟合优度：" + String.format("%.6f",KB.get(4)));    		        
    			}
            }
        });
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		SLabel = new JLabel();
		SLabel.setForeground(Color.red);
		SLabel.setText("无S拟合系数");
		gb.setConstraints(SLabel, gbc);
		panel.add(SLabel);
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		MLabel = new JLabel();
		MLabel.setForeground(Color.red);
		MLabel.setText("无M拟合系数");
		gb.setConstraints(MLabel, gbc);
		panel.add(MLabel);
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		NLabel = new JLabel();
		NLabel.setForeground(Color.red);
		NLabel.setText("无N拟合系数");
		gb.setConstraints(NLabel, gbc);
		panel.add(NLabel);
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		LLabel = new JLabel();
		LLabel.setForeground(Color.red);
		LLabel.setText("无余项");
		gb.setConstraints(LLabel, gbc);
		panel.add(LLabel);
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		RLabel = new JLabel();
		RLabel.setForeground(Color.red);
		RLabel.setText("无拟合优度");
		gb.setConstraints(RLabel, gbc);
		panel.add(RLabel);
		
		//********
		gbc.insets= new Insets(4,50,4,0);
		curveLabel = new JLabel();
		curveLabel.setForeground(Color.red);
		curveLabel.setText("无拟合曲线方程");
		gb.setConstraints(curveLabel, gbc);
		panel.add(curveLabel);
		
		//*******
		frame.add(panel);
		frame.setBounds(100, 100, 600, 560);
//		frame.setResizable(false);
		frame.setVisible(true);
		// close the windows
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	
	public static void main(String[] args) {
		LeastSquareWin lsw = new LeastSquareWin();
		lsw.createWin();
	}
	
}
