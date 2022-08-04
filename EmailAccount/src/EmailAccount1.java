import components.map.Map;
import components.map.Map1L;

/**
 * Implementation of {@code EmailAccount}.
 *
 * @author Michael Dodus
 *
 */
public final class EmailAccount1 implements EmailAccount {

    /*
     * Private members --------------------------------------------------------
     */

    private String firstName;

    private String lastName;

    private String emailAddress;

    private Map<String, Integer> container = new Map1L<>();

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
        int lastNameNum = this.container.hasKey(lastName)
                ? this.container.replaceValue(lastName,
                        this.container.value(lastName) + 1) + 1
                : 1;
        if (!this.container.hasKey(lastName)) {
            this.container.add(lastName, 1);
        }
        this.emailAddress = lastName.toLowerCase() + "." + lastNameNum
                + "@osu.edu";
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

        return this.firstName + " " + this.lastName + ", Email Address: "
                + this.emailAddress;

    }

}
