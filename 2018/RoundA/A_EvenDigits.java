
import java.io.PrintStream;
import java.util.*;

public class A_EvenDigits {
    public static void main(String[] args) throws Exception{
       System.setOut(new PrintStream("A-output.txt"));
       Scanner scanner = new Scanner(System.in);
       int caseNum = scanner.nextInt();
       long start = System.currentTimeMillis();
       for (int i = 0; i < caseNum; i++) {
            try {
                System.out.println(String.format("Case #%d: %s", i + 1, new A_EvenDigits().solve(scanner)));
            } catch (Throwable e) {
                System.err.println("ERROR in case #" + i);
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.err.println(String.format("Time used: %.3fs", (end - start) / 1000.0));
    }

   private String solve(Scanner scanner) {
    	String s = scanner.next();
        int len = s.length();
        long num = Long.parseLong(s);
        long[] plus_count = new long[len];
        long ans = 0;
        if (len == 1) {
            if (num % 2 == 0) return "0";
            else return "1";
        }
        for (int i = 0; i < len; i++) {
            if ((s.charAt(i) - '0') % 2 == 0) {
               plus_count[i] = Long.MAX_VALUE;
                continue;
            }
            if (i == len - 1) {
                ans += 1;
                continue;
            }
            String sub = s.substring(i+1);
            long low = Long.parseLong(sub);
            long high = 1;
            for (int j = 1; j < len - i; j++) high *= 10;
            long minusC = low + 1;
            long plusC = high - low;
            plus_count[i] = s.charAt(i) == '9' ? Long.MAX_VALUE : ans + plusC;
            if (minusC < plusC || s.charAt(i) == '9') {
                ans += minusC;
                if (i != 0 && ans > plus_count[i-1]) return String.valueOf(plus_count[i-1]);
                num = num - minusC;
            } else {
                return String.valueOf(plus_count[i]);
            }
            String tmp = String.valueOf(num);
            if (tmp.length() == len) s = tmp;
            else s = "0" + tmp;
        }
        return String.valueOf(ans);
    }
}
