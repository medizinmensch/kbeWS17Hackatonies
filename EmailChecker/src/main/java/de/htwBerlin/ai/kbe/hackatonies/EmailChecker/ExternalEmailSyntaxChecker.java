package de.htwBerlin.ai.kbe.hackatonies.EmailChecker;

import org.apache.commons.validator.routines.EmailValidator;

public class ExternalEmailSyntaxChecker {
	public boolean IsValid(String in) {	
		EmailValidator eva = EmailValidator.getInstance();
		return eva.isValid(in);
	}
}
