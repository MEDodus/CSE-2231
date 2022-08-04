import java.util.ArrayList;
import java.util.List;

class MinStack {

    public List<Integer> rep;

    public List<Integer> minVal;

    public MinStack() {
        this.rep = new ArrayList<>();
        this.minVal = new ArrayList<>();
    }

    public void push(int val) {
        this.rep.add(0, val);
        this.minVal.add(0, val);
        this.minVal.sort(null);
    }

    public void pop() {
        Integer val = this.rep.remove(0);
        this.minVal.remove(val);
    }

    public int top() {
        return this.rep.get(0);
    }

    public int getMin() {
        return this.minVal.get(0);
    }
}