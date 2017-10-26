package de.htwBerlin.ai.kbe.hackatonies.EmailChecker;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailCheckerTest
{   
	
	EmailChecker emailChecker = new EmailChecker();
	
    @Test
    public void isNotNull(){
    	assertFalse("Must not be null", emailChecker.checkEmail());
    }

    @Test
    public void isEmpty(){
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
    public void validLocalPartSpecialCharacters() {
    	assertTrue("Did not accept special Characters", emailChecker.checkEmail("disposable.style.email.with+symbol@example.com"));
    }
    
    @Test
    public void validLocalPartSuperUnusualCharacters() {
    	assertTrue("Did not accept special Characters", emailChecker.checkEmail("\"very.(),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com"));
    }

    @Test
    public void invalidDomainHyphenAtBeginning() {
        assertFalse("if domain begins with hyphen, false should be returned", emailChecker.checkEmail("symbol@-example.com"));
    }

    @Test
    public void invalidDomainHyphenAtEnd() {
        assertFalse("if domain ends with hyphen, false should be returned", emailChecker.checkEmail("symbol@example.com-"));
    }

    @Test
    public void invalidDomainSpecialCharacters() {
        assertFalse("Domain cannot contain special Characters", emailChecker.checkEmail("symbol@$e%xa&mple.com"));
    }
    
}
