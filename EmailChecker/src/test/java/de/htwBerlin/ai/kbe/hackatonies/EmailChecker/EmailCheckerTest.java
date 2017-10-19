package de.htwBerlin.ai.kbe.hackatonies.EmailChecker;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailCheckerTest
{   
	
	EmailChecker emailChecker = new EmailChecker();
	
    @Test
    public void isNotNull(){
    	assertFalse("Must not be null", emailChecker.checkEmail(""));
    }
    
    @Test
    public void hasNoAt() {
    	assertFalse("Adress must contain an @", emailChecker.checkEmail("testExample.com"));
    }
    
    @Test
    public void onlyOneAtAllowed() {
    	assertFalse("Only one @ allowed.", emailChecker.checkEmail("test@Example@Example.com"));
    }
    
    @Test
    public void normalEmailAdress() {
    	assertTrue("Normal Email check failed.", emailChecker.checkEmail("peter@gmail.com"));
    }
    
    @Test
    public void specialCharacters() {
    	assertTrue("Did not accept special Characters", emailChecker.checkEmail("disposable.style.email.with+symbol@example.com"));
    }
    
    @Test
    public void superUnusualCharacters() {
    	assertTrue("Did not accept special Characters", emailChecker.checkEmail("\"very.(),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com"));
    }
    
}
