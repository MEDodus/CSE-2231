import components.list.List;
import components.list.List1L;

/**
 * Customized JUnit test fixture for {@code List3}.
 */
public class List4Test extends ListTest {

    @Override
    protected final List<String> constructorTest() {
        return new List4<String>();
    }

    @Override
    protected final List<String> constructorRef() {
        return new List1L<String>();
    }

}
