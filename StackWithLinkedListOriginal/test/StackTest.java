import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    // TODO - add test cases for constructor, push, pop, and length
    @Test
    public final void testConstructor() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.constructorRef();
        assertEquals(sExpected, s);
    }

    @Test
    public final void testPush() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.constructorRef();
        s.push("1");
        sExpected.push("1");
        assertEquals(sExpected, s);
    }

    @Test
    public final void testPushes2() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.constructorRef();
        s.push("1");
        sExpected.push("1");
        assertEquals(sExpected, s);
        s.push("2");
        sExpected.push("2");
        assertEquals(sExpected, s);
        s.push("3");
        sExpected.push("3");
        assertEquals(sExpected, s);
    }

    @Test
    public final void testPushes() {
        Stack<String> s = this.createFromArgsTest("1", "2", "3");
        Stack<String> sExpected = this.createFromArgsRef("1", "2", "3");
        assertEquals(sExpected, s);
    }

    @Test
    public final void testPop() {
        Stack<String> s = this.createFromArgsTest("1", "2", "3");
        Stack<String> sExpected = this.createFromArgsRef("1", "2", "3");
        assertEquals(sExpected.pop(), s.pop());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testLength() {
        Stack<String> s = this.createFromArgsTest("1", "2", "3");
        Stack<String> sExpected = this.createFromArgsRef("1", "2", "3");
        assertEquals(sExpected.length(), s.length());
        assertEquals(sExpected, s);
    }
}
