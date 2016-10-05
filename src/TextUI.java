import java.util.Scanner;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class TextUI {

	public static void main(String[] args) {
		
		FileSystem FS3335 = new FileSystem();
		Map<String, String> sysDialogue = systemDialogue();

		Scanner input = new Scanner(System.in);
		boolean active = true;
		//main loop
		
		System.out.println(sysDialogue.get("welcome") + "\n");
		while (active) {
			System.out.println(sysDialogue.get("options") + "\n");
			char choice = input.nextLine().charAt(0);	//Get first character entered
			boolean abort = false;
			String fName = null;
			boolean noError;
			switch(choice) {
			
			//IMPORT FILE
			case '1':
				System.out.println(sysDialogue.get("case1") + "\n");
				
				fName = input.nextLine();
				File f = new File(fName);
				if (fName.equalsIgnoreCase("cancel")) {
					System.out.println(sysDialogue.get("case1cancel") + "\n");
					abort = true;
				}
				if (!f.isFile()) {
					System.out.println(sysDialogue.get("case1fnf") + "\n");
					abort = true;
				}
				
				if (abort)
					break;
				
				String data = FS3335.importData(fName);
				noError = FS3335.newFile(fName, data);
				if (noError)
					System.out.println(sysDialogue.get("case1confirm") + "\n");
				else
					System.out.println(sysDialogue.get("case1error") + "\n");
				
				break;
			
			//REMOVE FILE
			case '2':
				System.out.println(sysDialogue.get("case2") + "\n");
				
				fName = input.nextLine();
				
				if (fName.equalsIgnoreCase("cancel")) {
					System.out.println(sysDialogue.get("case2cancel") + "\n");
					abort = true;
				}
				if (!FS3335.hasFile(fName)) {
					System.out.println(sysDialogue.get("case2fnf") + "\n");
					abort = true;
				}
				
				if (abort)
					break;
				
				noError = FS3335.remFile(fName);
				if (noError)
					System.out.println(sysDialogue.get("case2confirm") + "\n");
				else
					System.out.println(sysDialogue.get("case2error") + "\n");
				break;
			
			//BACKUP SYSTEM
			case '3':
				System.out.println(sysDialogue.get("case3") + "\n");
				System.out.println("");
				
				boolean noErrors = FS3335.backUpSystem();
				
				if (noErrors)
					System.out.println(sysDialogue.get("case3confirm") + "\n");
				else
					System.out.println(sysDialogue.get("case3error") + "\n");
				break;
			
			//EXIT SYSTEM
			case '4':
				System.out.println(sysDialogue.get("case4") + "\n");
				active = false;
				break;
			
			//NEW FILE FROM INPUT
			case '5':
				System.out.println(sysDialogue.get("case5") + "\n");
				fName = input.nextLine();
				System.out.println(sysDialogue.get("case5dataprompt") + "\n");
				data = input.nextLine();
				noError = FS3335.newFile(fName, data);
				if (noError)
					System.out.println(sysDialogue.get("case5confirm") + "\n");
				else
					System.out.println(sysDialogue.get("case5error") + "\n");
				break;
				
			//PRINT SYSTEM TO SCREEN
			case '6':
				System.out.println(sysDialogue.get("case6") + "\n");
				System.out.println(FS3335.toString());
				break;
				
			default:
				System.out.println(sysDialogue.get("invalidcase") + "\n");
				break;
			}
		}
		input.close();

	}
	
	//returns a map of ("event":"message") based on sysDialogue.txt
	private static Map<String, String> systemDialogue() {
		Map<String,String> temp = new HashMap<String,String>();
		try {
			Scanner dialogue = new Scanner(new File("sysDialogue.txt"));
			dialogue.useDelimiter("~~");
			while (dialogue.hasNext()) {
				temp.put(dialogue.next().trim(), dialogue.next().trim());
				}
			dialogue.close();
			}
		catch (FileNotFoundException e) {
			temp.put("welcome", "Unfortunately sysDialogue.txt appears to have been moved\n"
					+ "or be missing from your installation. Please put it back\n"
					+ "in the project root folder or reinstall FS3335.\n"
					+ "Use option 4 to exit the system.");
			temp.put("case4", "Now exiting FS3335.\n\n---Process Terminated---");
		}
		return temp;
	}
}
