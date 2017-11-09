package htw.ii.kbe.hackatonies;

import de.htwBerlin.ai.kbe.hackatonies.EmailChecker.*;

public class RunMeMethods {

    public boolean method11(String input) {
        System.out.println ("In method11");
        EmailChecker emailChecker = new EmailChecker();
        return emailChecker.checkEmail(input);
    }

    @RunMeAnnotation.RunMe(input = "myName@domain.com")
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

    @RunMeAnnotation.RunMe(input = "mySecondName@domain.com")
    public boolean method44(String input) {
        System.out.println ("In method44");
        EmailChecker emailChecker = new EmailChecker();
        return emailChecker.checkEmail(input);
    }

    public static RunMeMethods create() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String className = System.getProperty("classWithRunMeAnnos",
                "htw.ii.kbe.hackatonies.RunMeMethods");
        Class<?> c = Class.forName(className);
        return (RunMeMethods) c.newInstance();
    }
}
