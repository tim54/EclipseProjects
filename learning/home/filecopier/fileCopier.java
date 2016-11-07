package home.filecopier;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class fileCopier {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
              fileCopierFrame frame = new fileCopierFrame();
              frame.setTitle("File Copier");               
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setVisible(true);
           }
        });
		
	}

}
