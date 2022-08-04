import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value, hasKey, and size
    @Test
    public void testAdd1() {
        Map<String, String> mExpected = this.createFromArgsRef("Alec",
                "Noonan");
        Map<String, String> m = this.constructorTest();

        m.add("Alec", "Noonan");

        assertEquals(mExpected, m);
    }

    @Test
    public void testRemove1() {
        Map<String, String> mExpected = this.createFromArgsRef("Alec",
                "Noonan");
        Map<String, String> m = this.createFromArgsTest("Alec", "Noonan",
                "Ethan", "Tsou");

        m.remove("Ethan");

        assertEquals(mExpected, m);
    }

    @Test
    public void testHasKey1() {
        Map<String, String> mExpected = this.createFromArgsRef("Alec", "Noonan",
                "Ethan", "Tsou");
        Map<String, String> m = this.createFromArgsTest("Alec", "Noonan",
                "Ethan", "Tsou");

        assertEquals(mExpected.hasKey("Ethan"), m.hasKey("Ethan"));
    }

    @Test
    public void testHasKey2() {
        Map<String, String> mExpected = this.createFromArgsRef("Alec", "Noonan",
                "Ethan", "Tsou");
        Map<String, String> m = this.createFromArgsTest("Alec", "Noonan",
                "Ethan", "Tsou");

        assertEquals(mExpected.hasKey("Dodus"), m.hasKey("Dodus"));
    }

    @Test
    public void testSize1() {
        Map<String, String> mExpected = this.createFromArgsRef();
        Map<String, String> m = this.createFromArgsTest();

        assertEquals(mExpected.size(), m.size());
    }

    @Test
    public void testSize2() {
        Map<String, String> mExpected = this.createFromArgsRef("Alec", "Noonan",
                "Ethan", "Tsou");
        Map<String, String> m = this.createFromArgsTest("Alec", "Noonan",
                "Ethan", "Tsou");

        assertEquals(mExpected.size(), m.size());
    }

}
