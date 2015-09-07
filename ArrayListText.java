package memorygame;

import static java.util.Collections.shuffle;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JButton;

// button values into gameList: ArrayList<Integer>
public class ArrayListText implements Serializable{
	
	private ArrayList<Integer> gameList = new ArrayList<Integer>();
	
	public ArrayListText(){}
	
	// get gameList
	public ArrayList<Integer> getGameList(){
		return gameList;
	}
	
	// fills the gameList with numbers in random order
    public void setArrayListText(JButton[] btnNumbers) {
    	gameList.clear();
        for (int i = 0; i < 2; i++) {
            for (int ii = 1; ii < (btnNumbers.length / 2) + 1; ii++) {
                gameList.add(ii);
            }
        }
        // shuffle the numbers in the list
        shuffle(gameList);
    }
    
    // serialization - easily get back when needs, can save for future testing
    public void serialize(){
    	try{
    		FileOutputStream f = new FileOutputStream("./src/memorygame/serialization.txt");
    		ObjectOutputStream out = new ObjectOutputStream(f);
    		out.writeObject(gameList);
    		out.close();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    // get back the ArrayList, can be write out easily 
    public ArrayList<Integer> deserialize(){
    	try{
    		FileInputStream f = new FileInputStream("./src/memorygame/serialization.txt");
    		ObjectInputStream in = new ObjectInputStream(f);
    		gameList = (ArrayList<Integer>)in.readObject();
    		in.close();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    	catch(ClassNotFoundException e){
    		e.printStackTrace();
    	}
    	
    	return gameList;
    }
}
