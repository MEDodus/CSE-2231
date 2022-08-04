import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple program to exercise EmailAccount functionality.
 */
public final class EmailAccountMain {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private EmailAccountMain() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        EmailAccount myAccount = new EmailAccount1("Brutus", "Buckeye");
        /*
         * Should print: Brutus Buckeye
         */
        out.println(myAccount.name());
        /*
         * Should print: buckeye.1@osu.edu
         */
        out.println(myAccount.emailAddress());
        /*
         * Should print: Name: Brutus Buckeye, Email: buckeye.1@osu.edu
         */
        out.println(myAccount);

        EmailAccount test1 = new EmailAccount1("Michael", "Dodus");
        out.println(test1.name());
        out.println(test1.emailAddress());
        out.println(test1);

        EmailAccount test2 = new EmailAccount1("Ethan", "Tsou");
        out.println(test2.name());
        out.println(test2.emailAddress());
        out.println(test2);

        EmailAccount test3 = new EmailAccount1("Rob", "LaTour");
        out.println(test3.name());
        out.println(test3.emailAddress());
        out.println(test3);

        EmailAccount test4 = new EmailAccount1("Kyle", "Tsou");
        out.println(test4.name());
        out.println(test4.emailAddress());
        out.println(test4);

        EmailAccount test5 = new EmailAccount1("Chelsea", "Dodus");
        out.println(test5.name());
        out.println(test5.emailAddress());
        out.println(test5);

        in.close();
        out.close();
    }

}
