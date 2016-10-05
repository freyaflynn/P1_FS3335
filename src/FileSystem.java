import java.io.*;

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
	
	public boolean newFile (String fName, String data) {
		//CHECK FOR SPACE
		int reqBlocks = (int)Math.ceil((double)data.length()/BLOCK_SIZE);
		if (reqBlocks > freeBlocks || data.equals("ERROR CODE : !@$$!!%%#@@#$^@#$helloworld")) {
			return false;
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
			if (data.length() > (i+1)*BLOCK_SIZE-1)
				drive[blockIndices[i]] = data.substring(i*BLOCK_SIZE, (i+1)*BLOCK_SIZE - 1);	//place 256 char substrings in drive
			else
				drive[blockIndices[i]] = data.substring(i*BLOCK_SIZE);
			
			blockInfo[blockIndices[i]] = blockIndices[i+1];	//set the values in blockInfo to the next block of the file
		}
		
		drive[blockIndices[foundIndices-1]] = data.substring((foundIndices-1) * BLOCK_SIZE);
		blockInfo[blockIndices[foundIndices-1]] = -1;	//End of File
		return true;
	}
	
	public boolean remFile (String fName) {
		if (!hasFile(fName)) {
			return false;
		}
		
		int currBlock = -1;
		int nextBlock;
		for (int i = 0; i < MAX_BLOCKS; i++) {
			if (fileInfo[i].fileName.equalsIgnoreCase(fName)) {
				currBlock = fileInfo[i].firstBlock;
				fileInfo[i] = null;
				break;
			}
		}
		
		while (blockInfo[currBlock] > 0) {
			nextBlock = blockInfo[currBlock];
			blockInfo[currBlock] = 0;
			currBlock = nextBlock;
		}
		blockInfo[currBlock] = 0;
		return true;
	}
	
	public String importData (String fName) {
		String data = "";
		String line = "";
		try {
			FileReader fileReader = new FileReader(fName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) {
				data += line + "\n";
			}
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			data = "ERROR CODE : !@$$!!%%#@@#$^@#$helloworld";
		} catch (IOException e) {
			data = "ERROR CODE : !@$$!!%%#@@#$^@#$helloworld";
		}
		return data;
	}
	
	public boolean backUpSystem () {
		//TODO
		//save toString to file
		//if successful, return true
		return true;
	}
	
	public boolean hasFile (String fName) {
		//look through fileInfo for fName
		boolean hasFile = false;
		for (int i = 0; i < MAX_BLOCKS; i++) {
			if (fileInfo[i] != null && fName.equalsIgnoreCase(fileInfo[i].fileName)) {
				hasFile = true;
				break;
			}
		}
		return hasFile;
	}
	
	public String toString () {
		//256 char boxes with block num and file name at the top, block order
		String temp = "";
		String border = "|-";
		String [] blockToFile = new String [MAX_BLOCKS];
		int height;
		int width;
		if (BLOCK_SIZE/8 > 60) {
			width = 60;
			height = BLOCK_SIZE/60;
		} else {
			height = 8;
			width = BLOCK_SIZE/8;
		}
		
		for (int i = 0; i < width; i++)
			border += "-";
		
		
		//Generates array blockToFile
		for (int i = 0; i < MAX_BLOCKS; i++) {
			if (fileInfo[i] != null) {
				int currBlock = fileInfo[i].firstBlock;
				while (blockInfo[currBlock] > 0) {
					blockToFile[currBlock] = fileInfo[i].fileName;
					
					//Truncate overly long filenames
					if(width-10-blockToFile[currBlock].length() < 0) {
						int cutOff = blockToFile[currBlock].length() - (width - 15);
						blockToFile[currBlock] = "..." + blockToFile[currBlock].substring(cutOff);
					}
					currBlock = blockInfo[currBlock];
				}
				blockToFile[currBlock] = fileInfo[i].fileName;
				if(width-10-blockToFile[currBlock].length() < 0) {
					int cutOff = blockToFile[currBlock].length() - (width - 15);
					blockToFile[currBlock] = "..." + blockToFile[currBlock].substring(cutOff);
				}
			} else {
			}
			if (blockToFile[i] == null)
				blockToFile[i] = "";
		}
		
		//Go through all blocks and add to temp string
		for (int i = 0; i < MAX_BLOCKS; i++) {
			int fileLength = 0;
			if (blockToFile[i] != null)
					fileLength = blockToFile[i].length();
			temp += border + "\n";
			temp += "|Block:" + String.format("%1$04d", i);
			//Right align filename
			for (int j = 0; j < width-10-fileLength; j++) {
				temp+= " ";
			}
			temp += blockToFile[i];
			temp += "|\n";
			temp += border + "\n";
			String data;
			if (blockInfo[i] != 0 && blockInfo[i] > -2)
				data = drive[i];
			else
				data = "";
			if (data == null)
				data = "";
			data = data.replaceAll("\n", "\n|");
			
			//Add raw data
			for (int k = 0; k < data.length(); k += width) {
				if (data.length() > k+width)
					temp += "|" + data.substring(k, k+width) + "\n";
				else
					temp += "|" + data.substring(k) + "\n";
			}
			temp += "\n";
		}
		return temp;
	}
	
	//Adds a new FileInfo object to the fileInfo array
	private void addFileInfo (String fName, int firstBlock) {
		FileInfo fi = new FileInfo(fName, firstBlock);
		if (hasFile(fName))
			for (int i = 0; i < MAX_BLOCKS; i++) {
				if (fileInfo[i] != null && fileInfo[i].fileName.equalsIgnoreCase(fName)) {
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
