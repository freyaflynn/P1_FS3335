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
		while (active) {
			System.out.println(sysDialogue.get("options") + "\n");
			char choice = input.next().charAt(0);	//Get first character entered
			boolean abort = false;
			String fName = null;
			switch(choice) {
			
			//IMPORT FILE
			case '1':
				System.out.println(sysDialogue.get("case1") + "\n");
				
				fName = input.next();
				File f = new File(fName);
				if (fName.equalsIgnoreCase("cancel")) {
					System.out.println(sysDialogue.get("case1cancel"));
					abort = true;
				}
				if (!f.isFile()) {
					System.out.println(sysDialogue.get("case1fnf") + "\n");
					abort = true;
				}
				
				if (abort)
					break;
				
				String data = FS3335.ImportData(fName);
				FS3335.newFile(fName, data);
				System.out.println(sysDialogue.get("case1confirm") + "\n");
				
				
				break;
			
			//REMOVE FILE
			case '2':
				System.out.println(sysDialogue.get("case2") + "\n");
				
				fName = input.next();
				
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
				
				FS3335.remFile(fName);
				System.out.println(sysDialogue.get("case2confirm") + "\n");
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
			while (dialogue.hasNext()) {
				temp.put(dialogue.next().trim(), dialogue.next().trim());
				}
			dialogue.close();
			}
		catch (FileNotFoundException e) {
			temp.put("options", "Unfortunately ");
		}
		return temp;
	}
}
