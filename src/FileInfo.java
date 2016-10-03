
public class FileInfo {
	public String fileName;		//name of the file to be stored
	public int firstBlock;		//index of the first data block for the file
	
	FileInfo(String fname, int block1) {
		fileName = fname;
		firstBlock = block1;
	}
	
	public String toString () {
		return "FILE NAME: " + fileName + ", FIRST BLOCK: " + firstBlock;
	}
}
