import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.waitingline.WaitingLine;
import components.waitingline.WaitingLine1;

public class TestClass {

    public static void main(String[] args) {
        WaitingLine<String> w = new WaitingLine1<>();
        SimpleWriter out = new SimpleWriter1L();
        w.addToLine("Michael");
        w.addToLine("Ethan");
        w.addToLine("Rob");
        w.addToLine("Paige");
        w.addToLine("Mustafa");
        out.println(w);
        out.println(w.removeFromLine());
        out.println(w);
        out.println(w.findInLine("Paige"));
        out.println(w);
        WaitingLine<String> y = w.newInstance();
        out.println(y);
        y.transferFrom(w);
        out.println(y);
        out.println(w);
    }
}
