package home.filecopier;

import java.io.File;
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
	
	public static void directoryGenerator(String filePrefix, int filesNumber) {
		Random random = new Random();
		String dirname = "test_data";
		File f1 = new File(dirname);
		
		if (!f1.exists()) f1.mkdir();
		
		for (int i = 0; i < filesNumber; i++) {
			int randomInt = random.nextInt(filesNumber);
			String filename = filePrefix + String.valueOf(randomInt);
			File file = new File(filename);
			// System.out.println("File name would be: " + filename);
		}
		
		listDirectory(f1);
	}

	public static void main(String[] args) {
		String filePrefix = "Files#1-";
		int filesNumber = 10;
		directoryGenerator(filePrefix, filesNumber);
	}
}
