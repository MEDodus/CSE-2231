import components.map.Map;
import components.map.Map1L;

/**
 * Implementation of {@code EmailAccount}.
 *
 * @author Put your name here
 *
 */
public final class EmailAccount1 implements EmailAccount {

    /*
     * Private members --------------------------------------------------------
     */

    private String firstName;
    private String lastName;
    private String emailAddress;
    static Map<String, Integer> container = new Map1L<>();

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Constructor.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public EmailAccount1(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
        if (!this.container.hasKey(lastName.toLowerCase())) {
            this.container.add(lastName.toLowerCase(), 1);
        } else {
            int num = this.container.value(lastName.toLowerCase());
            this.container.replaceValue(lastName.toLowerCase(), num + 1);
        }
        this.emailAddress = lastName.toLowerCase() + "."
                + this.container.value(lastName.toLowerCase()) + "@osu.edu";

    }

    /*
     * Methods ----------------------------------------------------------------
     */

    @Override
    public String name() {

        return this.firstName + " " + this.lastName;
    }

    @Override
    public String emailAddress() {

        return this.emailAddress;
    }

    @Override
    public String toString() {

        return "Name: " + this.name() + ", Email: " + this.emailAddress;
    }

}
