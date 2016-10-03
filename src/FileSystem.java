
public class FileSystem {
	
	public static final int MAX_BLOCKS = 256;
	public static final int BLOCK_SIZE = 256;
	
	private FileInfo [] fileInfo;	//file name and 1st block. Null if deleted.
	private int [] blockInfo;		//0 = available, -1 = EOF, -2 = bad block. 
	private String [] drive;		//raw data
	
	private int freeBlocks;
	
	public FileSystem () {
		fileInfo = new FileInfo[MAX_BLOCKS];
		blockInfo = new int [MAX_BLOCKS];
		drive = new String [MAX_BLOCKS];
		
		freeBlocks = MAX_BLOCKS;
	}
	
	public void newFile (String fName, String data) {
		//CHECK FOR SPACE
		int reqBlocks = (int)Math.ceil((double)data.length()/BLOCK_SIZE);
		if (reqBlocks > freeBlocks) {
			System.out.println("Error: Not enough free space on drive.");
			return;
		}
		
		int [] blockIndices = new int [reqBlocks];
		int foundIndices = 0;
		
		//FIND FREE BLOCKS
		for (int i = 0; i < MAX_BLOCKS && foundIndices < reqBlocks; i++) {
			if (blockInfo[i] == 0) {
				blockIndices[foundIndices] = i;
				foundIndices++;
			}
		}
		
		addFileInfo(fName, blockIndices[0]);
		
		for (int i = 0; i < foundIndices-1; i++) {
			drive[blockIndices[i]] = data.substring(i*BLOCK_SIZE, (i+1)*BLOCK_SIZE - 1);	//place 256 char substrings in drive
			blockInfo[blockIndices[i]] = blockIndices[i+1];	//set the values in blockInfo to the next block of the file
		}
		
		blockInfo[foundIndices-1] = -1;	//End of File
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
	
	//Adds a new FileInfo object to the fileInfo array
	private void addFileInfo (String fName, int firstBlock) {
		FileInfo fi = new FileInfo(fName, firstBlock);
		if (hasFile(fName))
			for (int i = 0; i < MAX_BLOCKS; i++) {
				if (fileInfo[i].fileName.equalsIgnoreCase(fName)) {
					fileInfo[i] = fi;
					break;
				}
			}
		else
			for (int i = 0; i < MAX_BLOCKS; i++) {
				if (fileInfo[i] == null) {
					fileInfo[i] = fi;
					break;
				}
			}
		
	}

}
