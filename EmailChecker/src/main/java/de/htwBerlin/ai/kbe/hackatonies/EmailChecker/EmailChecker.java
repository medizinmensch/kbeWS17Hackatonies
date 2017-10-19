package de.htwBerlin.ai.kbe.hackatonies.EmailChecker;

public class EmailChecker 
{
	public boolean checkEmail(String in) {
		ExternalEmailSyntaxChecker extVal = new ExternalEmailSyntaxChecker();
		return extVal.IsValid(in);
	}
}
