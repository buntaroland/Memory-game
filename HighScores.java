package memorygame;

import java.io.*;
import java.util.ArrayList;

// scores in ArrayList<String>
// read out from rekordok.txt
// write to the same file
public class HighScores{
	private ArrayList<String> hScores = new ArrayList<String>();
	private BufferedReader reader = null;
	private BufferedWriter output = null ;
	private File file = new File("./src/memorygame/rekordok.txt");
	
	public HighScores(){
	}
	
	// reads high scores from a file
	// line - a record
	// + exception handling
	public void readFromFile(){
		hScores.clear();
		try{
			reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null){
				hScores.add(line);
				}
			}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	// writes record into a string
	// using StringBuilder for easily get string from the ArrayList
	public String getRecords(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < hScores.size(); i++) {
			sb.append(hScores.get(i).toString()+"\n");
		}
		String finalstr = sb.toString();
		return finalstr;
	}
	
	// made for testing
	public ArrayList<String> getList(){
		return hScores;
	}
	
	/* when game is over, if the new player's score is same or higher than
	   the other player's on the list, the hScores will be updated
	   (same score - > new player ahead)
	*/
	public void checkNewRecord(String name, int moves){
		
		moves = moves/2;
		
		// set default name
		if(name == null){
			name = "Névtelen játékos";
		}
		//if there were no high scores it will be the first one
		if (hScores.isEmpty()){
			hScores.add(moves + " lépés - " + name);
			try {
				output = new BufferedWriter(new FileWriter(file));
				
				// writes without brackets
				output.write(hScores.toString().replace("[", "").replace("]", ""));
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			// checks all records
			// splitting ArrayList into String Array
			for(int i = 0; i < hScores.size(); i++){
				String[] strArray = null;
				strArray = hScores.get(i).toString().split("\\s+");
					
					// if new record is lower or same value as some of the old ones
					// it will be above it
					if(Integer.parseInt((strArray[0])) >= moves){
						hScores.add(i, moves + " lépés - " + name);
						//maximum 10 elements
						if(hScores.size() == 11){
							hScores.remove(10);
						}
				
					try {
						output = new BufferedWriter(new FileWriter(file));
						for(int k = 0; k < hScores.size(); k++){
							output.write(hScores.get(k).toString().replace("[", "").replace("]", "")+ "\n");
						}
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
					}
				}
			/* if new record was higher than the others it will be the last one
			   if there are less items than 10*/
			if(hScores.size()<10){
				hScores.add(moves + " lépés - " + name);
			}
			
			try {
				output = new BufferedWriter(new FileWriter(file));
				for(int k = 0; k < hScores.size(); k++){
					output.write(hScores.get(k).toString().replace("[", "").replace("]", "")+ "\n");
				}
				output.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
				}
		}
		return;
	}
}

