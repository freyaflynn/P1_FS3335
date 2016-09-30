import java.util.Scanner;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class TextUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FileSystem FS3335 = new FileSystem();
		Map<String, String> sysDialogue = systemDialogue();

		Scanner input = new Scanner(System.in);
		
		
		boolean active = true;
		while (active) {
			System.out.println("SOME TEXT");
			char choice = input.next().charAt(0);	//Get first character entered
			switch(choice) {
			
			case '1':
				break;
			case '2':
				break;
			case '3':
				break;
			case '4':
				break;
			}
		}
		input.close();

	}
	
	//returns a map of ("event":"message") based on sysDialogue.txt
	private static Map<String, String> systemDialogue() {
		Map<String,String> temp = new HashMap<String,String>();
		try {
		Scanner dialogue = new Scanner(new File("sysDialogue.txt"));	//
		dialogue.close();
		}
		catch (FileNotFoundException e) {
			
		}
		return temp;
	}

}
