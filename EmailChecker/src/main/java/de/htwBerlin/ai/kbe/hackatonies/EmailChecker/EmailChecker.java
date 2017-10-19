package de.htwBerlin.ai.kbe.hackatonies.EmailChecker;

/**
 * Hello world!
 *
 */
public class EmailChecker 
{
	public boolean checkEmail(String in) {
		ExternalEmailSyntaxChecker extVal = new ExternalEmailSyntaxChecker();
		return extVal.IsValid(in);
	}
}
