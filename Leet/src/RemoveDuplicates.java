import java.util.Arrays;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class RemoveDuplicates {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RemoveDuplicates() {
    }

    public static int removeDuplicates(int[] nums) {
        int max = 0;
        if (nums.length > 0) {
            max = nums[nums.length - 1];
        }
        int cnt = 0;
        int pointer = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] == nums[i] || pointer == nums[i]) {
                cnt++;
                pointer = nums[i];
                nums[i] = max + 1;
            }
        }
        cnt = nums.length - cnt;
        Arrays.sort(nums);
        return cnt;
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        String[] arr = { "panda", "pancreas", "palace", "pam" };
        in.close();
        out.close();
    }

}
