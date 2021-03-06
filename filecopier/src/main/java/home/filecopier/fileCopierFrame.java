package home.filecopier;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class fileCopierFrame extends JFrame {
	
	private JFileChooser chooser;
	JTextField fileField1;
	JTextField fileField2;
	
	String dir1;
	String dir2;
	
	public fileCopierFrame () {
		chooser = new JFileChooser();
		
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 4));
		panel.add(new JLabel("Choose path to compare:", SwingConstants.RIGHT));
		
		JButton pathToCompare1 = new JButton("Path 1");
		pathToCompare1.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent event) {
				  chooser.setCurrentDirectory(new File("."));
				  int result = chooser.showOpenDialog(fileCopierFrame.this);
				  
				  if(result == JFileChooser.APPROVE_OPTION)
				  {
				    dir1 = chooser.getSelectedFile().getPath();
				    fileField1.setText(dir1);
				    pack();
				  }
			  }
			});
		
		panel.add(pathToCompare1);
		
		panel.add(new JLabel("Directory to compare 1:", SwingConstants.RIGHT));
		fileField1 = new JTextField("Directory 1", 20);
		panel.add(fileField1);
		
		panel.add(new JLabel("Path to compare:", SwingConstants.RIGHT));
		
		JButton pathToCompare2 = new JButton("Path 2");
		pathToCompare2.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent event) {
				  chooser.setCurrentDirectory(new File("."));
				  int result = chooser.showOpenDialog(fileCopierFrame.this);
				  
				  if(result == JFileChooser.APPROVE_OPTION)
				  {
				    dir2 = chooser.getSelectedFile().getPath();
				    fileField2.setText(dir2);
				    pack();
				  }
			  }
			});
		
		panel.add(pathToCompare2);
		
		panel.add(new JLabel("Directory to compare 2:", SwingConstants.RIGHT));
		fileField2 = new JTextField("Directory 2", 20);
		panel.add(fileField2);
		
		add(panel, BorderLayout.CENTER);
		pack();
		
		//File curDir = new File(dir1);
		//File[] fileList = curDir.listFiles();
		
		
	}
}
