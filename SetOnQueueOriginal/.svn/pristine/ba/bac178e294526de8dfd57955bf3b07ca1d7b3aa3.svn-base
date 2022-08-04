import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
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

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    @Test
    public final void testConstructor() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddEmpty() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        s.add("yellow");
        sExpected.add("yellow");
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddNone() {
        Set<String> s = this.createFromArgsTest("red", "blue", "green");
        Set<String> sExpected = this.createFromArgsRef("red", "blue", "green");
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddNonEmpty() {
        Set<String> s = this.createFromArgsTest("red", "blue", "green");
        Set<String> sExpected = this.createFromArgsRef("red", "blue", "green");
        s.add("yellow");
        sExpected.add("yellow");
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveOneElement() {
        Set<String> s = this.createFromArgsTest("blue");
        Set<String> sExpected = this.createFromArgsRef("blue");
        String str = s.remove("blue");
        String strExpected = sExpected.remove("blue");
        assertEquals(strExpected, str);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveMultipleElements() {
        Set<String> s = this.createFromArgsTest("blue", "green");
        Set<String> sExpected = this.createFromArgsRef("blue", "green");
        String str1 = s.remove("blue");
        String str2 = s.remove("green");
        String str1Expected = sExpected.remove("blue");
        String str2Expected = sExpected.remove("green");
        assertEquals(str1Expected, str1);
        assertEquals(str2Expected, str2);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAny() {
        Set<String> s = this.createFromArgsTest("blue");
        Set<String> sExpected = this.createFromArgsRef("blue");
        String str = s.removeAny();
        assertEquals(sExpected.contains(str), true);
        String strExpected = sExpected.remove(str);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAnyMultipleElements() {
        Set<String> s = this.createFromArgsTest("blue", "yellow", "green",
                "brown", "red");
        Set<String> sExpected = this.createFromArgsRef("blue", "yellow",
                "green", "brown", "red");
        String str1 = s.removeAny();
        assertEquals(sExpected.contains(str1), true);
        String str1Expected = sExpected.remove(str1);
        assertEquals(sExpected, s);
        String str2 = s.removeAny();
        assertEquals(sExpected.contains(str2), true);
        String str2Expected = sExpected.remove(str2);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContains() {
        Set<String> s = this.createFromArgsTest("blue", "yellow", "green",
                "brown", "red");
        Set<String> sExpected = this.createFromArgsRef("blue", "yellow",
                "green", "brown", "red");
        assertEquals(sExpected.contains("blue"), s.contains("blue"));
        assertEquals(sExpected.contains("green"), s.contains("green"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeZero() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeNonZero() {
        Set<String> s = this.createFromArgsTest("blue", "yellow", "green",
                "brown", "red");
        Set<String> sExpected = this.createFromArgsRef("blue", "yellow",
                "green", "brown", "red");
        assertEquals(sExpected.size(), s.size());
        assertEquals(sExpected, s);
    }
}
