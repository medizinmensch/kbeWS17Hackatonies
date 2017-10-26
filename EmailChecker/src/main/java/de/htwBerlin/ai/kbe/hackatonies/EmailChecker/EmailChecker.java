package de.htwBerlin.ai.kbe.hackatonies.EmailChecker;

public class EmailChecker 
{
	public boolean checkEmail(String in) {
		ExternalEmailSyntaxChecker extVal = new ExternalEmailSyntaxChecker();
		return extVal.IsValid(in);
	}

	//null based initialisation
	public boolean checkEmail() {
		return false;
	}


}
