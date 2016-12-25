package home.filecopier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class utility {
	
	public static void listDirectory(File file){
		String dirname = file.getAbsolutePath();
		
		if (file.isDirectory()) {
			System.out.println("Directory is: " + file.toString());
			String s[] = file.list();
			for (int i=0; i < s.length; i++){
				File f = new File(dirname + "/" + s[i]);
				if (f.isDirectory()){
					System.out.println(s[i] + " is a directory");
				} else {
					System.out.println(s[i] + " is a file");
				}
			}
		} else {
			System.out.println(dirname + " is not a directory");
		}
	}
	
	public static void emptyDirectory(File dirname) {
		if (!dirname.exists()) { System.out.println("Directory does not exist"); return; }
		
		if (!dirname.isDirectory()) { System.out.println(dirname + " is not a directory"); return; }
		
		File[] files = dirname.listFiles();
		
		for (File file : files) {
			if (file.isFile()) { System.out.println("Deleting file: " + file); file.delete(); }
		}
	}
	
	public static void directoryGenerator(File basedir, String filePrefix, int filesNumber) {
		Random random = new Random();
		
		if (!basedir.exists()) basedir.mkdir();
		
		emptyDirectory(basedir);
		
		System.out.println();
		for (int i = 0; i < filesNumber; i++) {
			int randomInt = random.nextInt(filesNumber*10);
			String filename = filePrefix + String.valueOf(randomInt);
			File file = new File(basedir + "/" + filename);
			
			try (FileOutputStream fout = new FileOutputStream(file, true)) {
				System.out.println("File created: " + file);
			} catch (IOException e) {
				System.out.println("I/O error");
			}
			// System.out.println("File name would be: " + filename);
		}
		
		System.out.println();
		listDirectory(basedir);
	}

	public static void main(String[] args) {
		File dirname = new File("test_data");
		String filePrefix = "Files#1-";
		int filesNumber = 10;
		
		directoryGenerator(dirname, filePrefix, filesNumber);
	}
}
