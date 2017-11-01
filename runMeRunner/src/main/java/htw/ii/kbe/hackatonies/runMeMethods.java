package htw.ii.kbe.hackatonies;

import de.htwBerlin.ai.kbe.hackatonies.EmailChecker.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class runMeMethods {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface RunMe {
        String input();
    }

    public boolean method11(String input) {
        System.out.println ("In method11");
        EmailChecker emailChecker = new EmailChecker();
        return emailChecker.checkEmail(input);
    }

    @RunMe(input = "myName@domain.com")
    public boolean method22(String input) {
        System.out.println ("In method22");
        EmailChecker emailChecker = new EmailChecker();
        return emailChecker.checkEmail(input);
    }

    public boolean method33(String input) {
        System.out.println ("In method33");
        EmailChecker emailChecker = new EmailChecker();
        return emailChecker.checkEmail(input);
    }

    @RunMe(input = "mySecondName@domain.com")
    public boolean method44(String input) {
        System.out.println ("In method44");
        EmailChecker emailChecker = new EmailChecker();
        return emailChecker.checkEmail(input);
    }
}
