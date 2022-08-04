import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Michael Dodus, Ethan Tsou
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Testing Set with an empty constructor.
     */
    @Test
    public final void testConstructor() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();

        // Assert object equality
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set with adds only from createFromArgs(). only to right side of
     * tree.
     */
    @Test
    public final void testAddRight() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d");

        // Assert object equality
        assertEquals(sExpected, s);

        s.add("e");
        sExpected.add("e");

        // Assert object equality after add
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set with adds only from createFromArgs(). only to left side of
     * tree.
     */
    @Test
    public final void testAddLeft() {
        Set<String> s = this.createFromArgsTest("d", "c", "b", "a");
        Set<String> sExpected = this.createFromArgsRef("d", "c", "b", "a");

        // Assert object equality
        assertEquals(sExpected, s);

        s.add("e");
        sExpected.add("e");

        // Assert object equality after add
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set with adds only from createFromArgs(). to both sides of the
     * tree.
     */
    @Test
    public final void testAddBoth() {
        Set<String> s = this.createFromArgsTest("d", "c", "f", "b", "g");
        Set<String> sExpected = this.createFromArgsRef("d", "c", "f", "b", "g");

        // Assert object equality
        assertEquals(sExpected, s);

        s.add("e");
        sExpected.add("e");

        // Assert object equality after add
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set with adds from createFromArgs() as well as an additional
     * deliberate add.
     */
    @Test
    public final void testAddAndAdd() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d");

        // Assert object equality
        assertEquals(sExpected, s);

        s.add("e");
        sExpected.add("e");

        // Assert object equality after add
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set with add on an initially empty Set.
     */
    @Test
    public final void testAddEmpty() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.createFromArgsRef("a");

        // Add element to both ref and test
        s.add("a");

        // Assert object equality
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set with remove on a Set with one element.
     */
    @Test
    public final void testRemoveOneElement() {
        Set<String> s = this.createFromArgsTest("a");
        Set<String> sExpected = this.constructorRef();

        // Remove element from test
        String r = s.remove("a");

        // Assert object instance method return value and object equality.
        assertEquals(sExpected, s);
        assertEquals("a", r);
    }

    /**
     * Testing Set with remove on a Set with multiple elements. only to right
     * side of tree.
     */
    @Test
    public final void testRemoveOneElementMultipleRight() {
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        Set<String> sExpected = this.createFromArgsRef("a", "c", "d");

        // Remove element from test
        String r = s.remove("b");

        // Assert object instance method return value and object equality.
        assertEquals(sExpected, s);
        assertEquals("b", r);
    }

    /**
     * Testing Set with remove on a Set with multiple elements. only to left
     * side of tree.
     */
    @Test
    public final void testRemoveOneElementMultipleLeft() {
        Set<String> s = this.createFromArgsTest("d", "c", "b", "a");
        Set<String> sExpected = this.createFromArgsRef("d", "c", "a");

        // Remove element from test
        String r = s.remove("b");

        // Assert object instance method return value and object equality.
        assertEquals(sExpected, s);
        assertEquals("b", r);
    }

    /**
     * Testing Set with remove on a Set with multiple elements and multiple
     * removes. One on the left the other at the root. removes from both sides
     * of the tree.
     */
    @Test
    public final void testRemoveMultipleElementsBoth() {
        Set<String> s = this.createFromArgsTest("d", "c", "f", "b", "g");
        Set<String> sExpected = this.createFromArgsRef("d", "f", "b");

        // Remove left element from test
        String r1 = s.remove("c");
        String r2 = s.remove("g");

        // Assert object instance method return value an object equality.
        assertEquals(sExpected, s);
        assertEquals("c", r1);
        assertEquals("g", r2);
    }

    /**
     * Testing Set with removeAny on a Set with one element.
     */
    @Test
    public final void testRemoveAnyOne() {
        Set<String> s = this.createFromArgsTest("a");
        Set<String> sExpected = this.createFromArgsRef("a");

        // Remove any from test
        String r = s.removeAny();

        // Check size of s
        assertEquals(sExpected.size(), s.size() + 1);

        String rExpected = sExpected.remove(r);

        // Assert object instance method return value an object equality.
        assertEquals(rExpected, r);
    }

    /**
     * Testing Set with removeAny on a Set with multiple elements.
     */
    @Test
    public final void testRemoveAnyMultiple() {
        Set<String> s = this.createFromArgsTest("d", "c", "f", "b", "g");
        Set<String> sExpected = this.createFromArgsRef("d", "c", "f", "b", "g");

        // Remove any from test
        String r = s.removeAny();

        // Check size of s
        assertEquals(sExpected.size(), s.size() + 1);

        String rExpected = sExpected.remove(r);

        // Assert object instance method return value and object equality.
        assertEquals(rExpected, r);
    }

    /**
     * Testing Set with contains on a Set with one element. i.e. the root.
     */
    @Test
    public final void testContainsOneElement() {
        Set<String> s = this.createFromArgsTest("a");
        Set<String> sExpected = this.createFromArgsRef("a");

        boolean b = s.contains("a");
        boolean bExpected = sExpected.contains("a");

        // Assert object instance method return value and object equality.
        assertEquals(bExpected, b);
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set contains on a Set on an element in the left tree.
     */
    @Test
    public final void testContainsLeft() {
        Set<String> s = this.createFromArgsTest("d", "c", "f", "b", "g");
        Set<String> sExpected = this.createFromArgsRef("d", "c", "f", "b", "g");

        boolean b = s.contains("b");
        boolean bExpected = sExpected.contains("b");

        // Assert object instance method return value and object equality.
        assertEquals(bExpected, b);
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set contains on a Set on an element in the right tree.
     */
    @Test
    public final void testContainsRight() {
        Set<String> s = this.createFromArgsTest("d", "c", "f", "b", "g");
        Set<String> sExpected = this.createFromArgsRef("d", "c", "f", "b", "g");

        boolean b = s.contains("f");
        boolean bExpected = sExpected.contains("f");

        // Assert object instance method return value and object equality.
        assertEquals(bExpected, b);
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set contains with a Set with no elements and comparing the
     * opposite boolean result with a Set with elements.
     */
    @Test
    public final void testContainsOpposite1() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.createFromArgsRef("d", "c", "f", "b", "g");

        boolean b = s.contains("g");
        boolean bExpected = sExpected.contains("g");

        // Assert object instance method return value opposite equality.
        assertEquals(bExpected, !b);
    }

    /**
     * Testing Set contains with a Set with elements and comparing the opposite
     * boolean result with a Set with no elements.
     */
    @Test
    public final void testContainsOpposite2() {
        Set<String> s = this.createFromArgsTest("d", "c", "f", "b", "g");
        Set<String> sExpected = this.constructorRef();

        boolean b = s.contains("g");
        boolean bExpected = sExpected.contains("g");

        // Assert object instance method return value opposite equality.
        assertEquals(bExpected, !b);
    }

    /**
     * Testing Set contains with a Set that does not contain the element.
     */
    @Test
    public final void testContainsNot() {
        Set<String> s = this.createFromArgsTest("d", "c", "f", "b", "g");
        Set<String> sExpected = this.createFromArgsRef("d", "c", "f", "b", "g");

        boolean b = s.contains("z");
        boolean bExpected = sExpected.contains("z");

        // Assert object instance method return value and object equality.
        assertEquals(bExpected, b);
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set size with a Set with no elements.
     */
    @Test
    public final void testSizeEmpty() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();

        // Assert object instance method return value and object equality.
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set size with a Set with multiple elements.
     */
    @Test
    public final void testSizeNonEmpty() {
        Set<String> s = this.createFromArgsTest("d", "c", "f", "b", "g");
        Set<String> sExpected = this.createFromArgsRef("d", "c", "f", "b", "g");

        // Assert object instance method return value and object equality.
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

    /**
     * Testing Set size with Set with difference amounts of elements in them.
     */
    @Test
    public final void testSizeDifference() {
        Set<String> s = this.createFromArgsTest("a");
        Set<String> sExpected = this.createFromArgsRef("d", "c", "f", "b", "g");

        int size = s.size();
        int sizeExpected = sExpected.size();
        int difference = sizeExpected - size;

        // Assert object instance method return value and object equality.
        assertEquals(sizeExpected, size + difference);
    }
}
