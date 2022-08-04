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
    public void testConstructor() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.constructorRef();
        assertEquals(sExpected, s);
    }

    @Test
    public void testPush() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.createFromArgsRef("Jack");
        s.push("Jack");
        assertEquals(sExpected, s);
    }

    @Test
    public void testPushMultiple() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.createFromArgsRef("Jack", "James");
        s.push("James");
        s.push("Jack");
        assertEquals(sExpected, s);
    }

    @Test
    public void testLength() {
        Stack<String> s = this.createFromArgsTest("Jack", "James");
        Stack<String> sExpected = this.createFromArgsRef("Jack", "James");
        assertEquals(sExpected.length(), s.length());
    }

    @Test
    public void testPop() {
        Stack<String> s = this.createFromArgsTest("Jack");
        Stack<String> sExpected = this.createFromArgsRef("Jack");
        assertEquals(sExpected.pop(), s.pop());
    }

    @Test
    public void testLengthZero() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.constructorRef();
        assertEquals(sExpected.length(), s.length());
    }

    @Test
    public void testPopMultiple() {
        Stack<String> s = this.createFromArgsTest("Jack", "James", "Jill");
        Stack<String> sExpected = this.createFromArgsRef("Jack", "James",
                "Jill");
        assertEquals(sExpected.pop(), s.pop());
        assertEquals(sExpected.pop(), s.pop());
    }
}
