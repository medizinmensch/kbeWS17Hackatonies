package htw.ii.kbe.hackatonies.runMeRunner;

import de.htwBerlin.ai.kbe.hackatonies.EmailChecker.EmailChecker;
import htw.ii.kbe.hackatonies.runMeRunner.App.*;

public class runMeMethods {
	
	EmailChecker emailchecker = new EmailChecker();
	
	@RunMe(input = "myName@domain.com")
	public boolean method22(String input) {
	System.out.println ("In method22");
	return emailchecker.checkEmail(input);
	}
	
	@RunMe(input = "my_Name@domain.com")
	public boolean method11(String input) {
	System.out.println ("In method11");
	return emailchecker.checkEmail(input);
	}
	
	public boolean method12(String input) {
	System.out.println ("In method12");
	return emailchecker.checkEmail(input);
	}
	
	
	
	
}
