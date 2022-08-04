import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test fixture for {@code ExpressionEvaluator}'s {@code valueOfExpr}
 * static method.
 *
 * @author Put your name here
 *
 */
public final class ExpressionEvaluatorTest {

    @Test
    public void testExample() {
        StringBuilder exp = new StringBuilder(
                "281/7/2-1-5*(15-(14-1))+((1))+20=30!");
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(30, value);
        assertEquals("=30!", exp.toString());
    }

    @Test
    public void test1() {
        StringBuilder exp = new StringBuilder("(15*(10-3))+4-5END");
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(104, value);
        assertEquals("END", exp.toString());
    }

    @Test
    public void test2() {
        StringBuilder exp = new StringBuilder("3/3+(7*3)*2-10+(5/2-5)END");
        int value = ExpressionEvaluator.valueOfExpr(exp);
        assertEquals(30, value);
        assertEquals("END", exp.toString());
    }

}
