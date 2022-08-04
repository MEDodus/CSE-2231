import java.util.HashMap;

public class StackTest {

    public static int climbStairs(int n) {
        int perm = 0;
        if (n > 2) {
            perm = climbStairs(n - 1) + climbStairs(n - 2);
        } else if (n == 2) {
            perm = 2;
        } else if (n == 1) {
            perm = 1;
        } else {
            return 0;
        }
        return perm;
    }

    int num = 0;

    while(n>0)
    {
        int sqr = (int) Math.floor(Math.sqrt(n));
        n = n - (sqr * sqr);
        this.num++;
    }

    if(mod<num)
    {
        return mod;
    }return num;
    }

    public static void main(String[] args) {
        System.out.println(numSquares(12));
    }
}
