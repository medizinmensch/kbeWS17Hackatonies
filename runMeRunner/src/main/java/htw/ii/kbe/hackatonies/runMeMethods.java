package htw.ii.kbe.hackatonies;

import de.htwBerlin.ai.kbe.hackatonies.EmailChecker.*;

public class RunMeMethods {

    public boolean method11(String input) {
        System.out.print ("Called in method11: ");
        EmailChecker emailChecker = new EmailChecker();
        if (emailChecker.checkEmail(input)) {
            System.out.println(input + " - Email is valid.");
            return true;
        }
        else {
            System.out.println(input + " - Email is invalid.");
            return false;
        }
    }

    @RunMeAnnotation.RunMe(input = "myName@domain.com")
    public boolean method22(String input) {
        System.out.print ("Called in method22: ");
        EmailChecker emailChecker = new EmailChecker();
        if (emailChecker.checkEmail(input)) {
            System.out.println(input + " - Email is valid.");
            return true;
        }
        else {
            System.out.println(input + " - Email is invalid.");
            return false;
        }
    }

    public boolean method33(String input) {
        System.out.print ("Called in method33: ");
        EmailChecker emailChecker = new EmailChecker();
        if (emailChecker.checkEmail(input)) {
            System.out.println(input + " - Email is valid.");
            return true;
        }
        else {
            System.out.println(input + " - Email is invalid.");
            return false;
        }
    }

    @RunMeAnnotation.RunMe(input = "mySecondName@domain.com_")
    public boolean method44(String input) {
        System.out.print ("Called in method44: ");
        EmailChecker emailChecker = new EmailChecker();
        if (emailChecker.checkEmail(input)) {
            System.out.println(input + " - Email is valid.");
            return true;
        }
        else {
            System.out.println(input + " - Email is invalid.");
            return false;
        }
    }

    //not used
    public static RunMeMethods create() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String className = System.getProperty("classWithRunMeAnnos",
                "htw.ii.kbe.hackatonies.RunMeMethods");
        Class<?> c = Class.forName(className);
        return (RunMeMethods) c.newInstance();
    }
}
