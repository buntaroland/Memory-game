package memorygame;

import java.util.ArrayList;

import javax.swing.JButton;
import static org.junit.Assert.*;
import org.junit.Test;

public class Testing {
	@Test
	public void testArrayListText(){
		ArrayListText alt = new ArrayListText();
		JButton[] btns = new JButton[16];
		alt.setArrayListText(btns);
		int altnbr = Integer.parseInt(alt.getGameList().get(0).toString());
		
		// TEST 1: ArrayListText size is exactly 16 (4x4)
		assertEquals(16, alt.getGameList().size());
		
		// TEST 2: ArrayLisText first element is a number between 1 and 8
		assertTrue(altnbr >=1 && altnbr <=8);

	}
	
	@Test
	public void testHighScores(){
		HighScores hs = new HighScores();
		
		// fills the hs with random records
		for(int i = 0; i < 10; i++){
			hs.checkNewRecord("Something" + i, i+20);
		}
		ArrayList<String> list = new ArrayList<String>(hs.getList());
		
		/* TEST 3: if list size is at least 2, than the first element's
		   score is same or lower than the second element's*/
		assertTrue(list.size() >= 2);
		
		String[] str1 = list.get(0).toString().split("\\s+");
		String[] str2 = list.get(1).toString().split("\\s+");
		assertTrue(Integer.parseInt(str1[0]) <= Integer.parseInt(str2[0]));
		
		/* TEST 4: if I get a new score, but the list is full,
		   it will have the same length(10)*/
		assertTrue(list.size() == 10);
		
		hs.checkNewRecord("Test 4", 22); // the real score is 22/2 = 11
		assertTrue(list.size() == 10);
		
		/* TEST 5: if my new score is lower or equal than the first one, 
		   I will be the first*/
		String[] str3 = list.get(0).toString().split("\\s+");
		int intStr3 = Integer.parseInt(str3[0]);
		int myScore = 8;
		
		assertTrue(myScore <= intStr3);
		hs.checkNewRecord("MyScore", 16); //8 = 16/2
		list = new ArrayList<String>(hs.getList());
		
		String[] newHsFirst = list.get(0).toString().split("\\s+");
		assertEquals("MyScore", newHsFirst[3]);
		
	}
}
