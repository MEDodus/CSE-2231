import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Michael Dodus, Ethan Tsou
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

    /**
     * Testing Map constructor for implementations extending MapTest.
     */
    @Test
    public final void testConstructor() {
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.constructorRef();

        // Assert object equality
        assertEquals(mExpected, m);
    }

    /**
     * Testing Map.add() with test and ref and empty constructor and adding an
     * element to test.
     */
    @Test
    public final void testAddEmpty() {
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing");

        m.add("michael", "climbing");

        // Assert object equality
        assertEquals(mExpected, m);
    }

    /**
     * Testing Map.add() with test and ref and adding multiple elements.
     */
    @Test
    public final void testAddNonEmpty() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing",
                "brandon", "reading");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading");

        // Assert object equality
        assertEquals(mExpected, m);
    }

    /**
     * Testing Map.add() with test and ref both having multiple elements. Add
     * after calling createFromArgs().
     */
    @Test
    public final void testAddNonEmptyAndAdd() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing",
                "brandon", "reading");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading", "chedo", "writing");

        m.add("chedo", "writing");

        // Assert object equality
        assertEquals(mExpected, m);
    }

    /**
     * Testing Map.add() with test and ref both having multiple elements and
     * adding multiple to test. Add multiple after calling createFromArgs().
     */
    @Test
    public final void testAddMultiple() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading", "chedo", "writing");

        m.add("brandon", "reading");
        m.add("chedo", "writing");

        // Assert object equality
        assertEquals(mExpected, m);
    }

    /**
     * Testing Map.remove() with test and ref both having exactly one element
     * and removing that element from both.
     */
    @Test
    public final void testRemoveFromMapWOneKey() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing");

        Map.Pair<String, String> p = m.remove("michael");
        Map.Pair<String, String> pExpected = mExpected.remove("michael");

        // Assert object method return equality and object equality
        assertEquals(mExpected, m);
        assertEquals(pExpected, p);
    }

    /**
     * Testing Map.remove() with test and ref both having multiple elements and
     * removing the same one from both.
     */
    @Test
    public final void testRemoveFromMapWMultipleKeys() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing",
                "brandon", "reading", "chedo", "writing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading", "chedo", "writing");

        Map.Pair<String, String> p = m.remove("chedo");
        Map.Pair<String, String> pExpected = mExpected.remove("chedo");

        // Assert object method return equality and object equality
        assertEquals(mExpected, m);
        assertEquals(pExpected, p);
    }

    /**
     * Testing Map.removeAny() with test and ref both having exactly one
     * element.
     */
    @Test
    public final void testRemoveAnyMapWOneKey() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing");

        Map.Pair<String, String> p = m.removeAny();

        // Check if the element was removed
        assertEquals(mExpected.size(), m.size() + 1);

        //Remove the element from the ref
        Map.Pair<String, String> pExpected = mExpected.remove(p.key());

        // Assert object method return equality and object equality
        assertEquals(mExpected, m);
        assertEquals(pExpected, p);
    }

    /**
     * Testing Map.removeAny() with test and ref both having multiple elements.
     */
    @Test
    public final void testRemoveAnyMapWMultipleKeys() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing",
                "brandon", "reading", "chedo", "writing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading", "chedo", "writing");

        Map.Pair<String, String> p = m.removeAny();

        // Check if an element was removed
        assertEquals(mExpected.size(), m.size() + 1);

        // Remove the element from the ref
        Map.Pair<String, String> pExpected = mExpected.remove(p.key());

        // Assert object method return equality and object equality
        assertEquals(mExpected, m);
        assertEquals(pExpected, p);
    }

    /**
     * Testing Map.remove() with the test and ref both having multiple elements
     * and removing multiple elements from both.
     */
    @Test
    public final void testRemoveMultiple() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing",
                "brandon", "reading", "chedo", "writing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading", "chedo", "writing");

        // Remove element from both objects
        Map.Pair<String, String> p1 = m.remove("brandon");
        Map.Pair<String, String> p1Expected = mExpected.remove("brandon");

        // Assert object method return equality and object equality
        assertEquals(p1Expected, p1);
        assertEquals(mExpected, m);

        // Remove element from both objects
        Map.Pair<String, String> p2 = m.remove("michael");
        Map.Pair<String, String> p2Expected = mExpected.remove("michael");

        // Assert object method return equality and object equality
        assertEquals(p2Expected, p2);
        assertEquals(mExpected, m);
    }

    /**
     * Testing Map.value() with the test and ref both having exactly one
     * element.
     */
    @Test
    public final void testValueWOneKey() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing");

        String v = m.value("michael");
        String vExpected = mExpected.value("michael");

        // Assert object method return equality and object equality
        assertEquals(mExpected, m);
        assertEquals(vExpected, v);
    }

    /**
     * Testing Map.value() with the test and ref both having multiple elements.
     */
    @Test
    public final void testValueWMultipleKeys() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing",
                "brandon", "reading", "chedo", "writing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading", "chedo", "writing");

        String v = m.value("brandon");
        String vExpected = mExpected.value("brandon");

        // Assert object method return equality and object equality
        assertEquals(mExpected, m);
        assertEquals(vExpected, v);
    }

    /**
     * Testing Map.hasKey() with both the test and ref having exactly one
     * element.
     */
    @Test
    public final void testHasKeyWOneKey() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing");

        boolean b = m.hasKey("michael");
        boolean bExpected = mExpected.hasKey("michael");

        // Assert object method return equality and object equality
        assertEquals(bExpected, b);
        assertEquals(mExpected, m);
    }

    /**
     * Testing Map.hasKey() with both the test and ref having multiple elements.
     */
    @Test
    public final void testHasKeyWMultipleKeys() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing",
                "brandon", "reading", "chedo", "writing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading", "chedo", "writing");

        boolean b = m.hasKey("brandon");
        boolean bExpected = mExpected.hasKey("brandon");

        // Assert object method return equality and object equality
        assertEquals(bExpected, b);
        assertEquals(mExpected, m);
    }

    /**
     * Testing Map.hasKey() with the test being an empty map and the ref being
     * non-empty.
     */
    @Test
    public final void testHasKeyOpposites1() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading");

        boolean b = m.hasKey("brandon");
        boolean bExpected = mExpected.hasKey("brandon");

        // Assert object method return equality
        assertEquals(bExpected, !b);
    }

    /**
     * Testing Map.hasKey() with the test being a non-empty map and the ref
     * being empty.
     */
    @Test
    public final void testHasKeyOpposites2() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing",
                "brandon", "reading");
        Map<String, String> mExpected = this.constructorRef();

        boolean b = m.hasKey("michael");
        boolean bExpected = mExpected.hasKey("michael");

        // Assert object method return equality
        assertEquals(bExpected, !b);
    }

    /**
     * Testing Map.size() with size = 0.
     */
    @Test
    public final void testSizeZero() {
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.constructorRef();

        // Assert object method return equality and object equality
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Testing Map.size() with size != 0.
     */
    @Test
    public final void testSizeNonZero() {
        Map<String, String> m = this.createFromArgsTest("michael", "climbing",
                "brandon", "reading", "chedo", "writing");
        Map<String, String> mExpected = this.createFromArgsRef("michael",
                "climbing", "brandon", "reading", "chedo", "writing");

        // Assert object method return equality and object equality
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }
}
