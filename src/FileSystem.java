
public class FileSystem {
	
	public static final int MAX_BLOCKS = 256;
	public static final int BLOCK_SIZE = 256;
	
	private FileInfo [] fileInfo = new FileInfo[MAX_BLOCKS]; //index i is null if file is deleted
	private int [] blockInfo = new int [MAX_BLOCKS]; //block# for the next block, 0 if available, -1 for EOF, -2 for bad block 
	private String [] drive = new String [MAX_BLOCKS]; //Actual block data
	
	
	public void newFile (String fName, String data) {
		//check if there's enough space
		//check if there's a file of the same name?
		//find out how many blocks are req
		//find first available blocks (blockInfo)
		//Update fileInfo for fName with 1st block
		//Put data in block (drive), update block (blockInfo), repeat
	}
	
	public void remFile (String fName) {
		//Find file in fileInfo
		//Get 1st block from fileInfo
		//Clear fileInfo
		//Get next block from current block (blockInfo), clear current blockInfo, go to next block, repeat
	}
	
	public String importData (String fName) {
		//open file
		//read to string
		//return
		return "";
	}
	
	public boolean backUpSystem () {
		//save toString to file
		//if successful, return true
		return true;
	}
	
	public boolean hasFile (String fName) {
		//look through fileInfo for fName
		return true;
	}
	
	public String toString () {
		//256 char boxes with block num and file name at the top, block order
		return "";
	}

}
