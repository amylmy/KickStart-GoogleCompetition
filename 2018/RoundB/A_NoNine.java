
import java.io.PrintStream;
import java.util.*;

public class A_NoNine {
    public static void main(String[] args) throws Exception{
       System.setOut(new PrintStream("A-output.txt"));
       Scanner scanner = new Scanner(System.in);
       int caseNum = scanner.nextInt();
       long start = System.currentTimeMillis();
       for (int i = 0; i < caseNum; i++) {
            try {
                System.out.println(String.format("Case #%d: %s", i + 1, new A_NoNine().solve(scanner)));
            } catch (Throwable e) {
                System.err.println("ERROR in case #" + i);
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.err.println(String.format("Time used: %.3fs", (end - start) / 1000.0));
    }

    private String solve(Scanner scanner) {
        long start = scanner.nextLong();
        long end = scanner.nextLong();
        long ans = get(end) - get(start) + 1;
        return String.valueOf(ans); 
    }

    private long get(long num) {
        long r = num % 10;
        num /= 10;
        long x = 0, v = 1, sum = 0;
        while (num != 0) {
            x += num % 10 * v;
            sum += num % 10;
            num /= 10;
            v *= 9;
        }
        long res = 8 * x;
        for (long i = 0; i <= r; i++) {
            if ((sum + i) % 9 != 0) res += 1;
        }
        return res;
    }

   // private String solveSmall(Scanner scanner) {
   //  	String s = scanner.next();
   //      String e = scanner.next();

   //      long start = preProcess(s);
   //      long end = preProcess(e);
   //      long count = 1;
   //      while (start < end) {
   //          count++;
   //          start++;
   //          String tmp = String.valueOf(start);
   //          while (tmp.contains("9") || start % 9 == 0) {
   //              start++;
   //              tmp = String.valueOf(start);
   //          }
   //          if (start < end) count++;
   //          else break;
   //          end--;
   //          String tmp2 = String.valueOf(end);
   //          while (tmp2.contains("9") || end % 9 == 0 || end < 0) {
   //              end--;
   //              tmp2 = String.valueOf(end);
   //          }
   //      }
   //      return String.valueOf(count);
   //  }

   //  private long preProcess(String s) {
   //      if (s.contains(".")) {
   //          double d = Double.parseDouble(s);
   //          if (d < 1) return 1;
   //          else return (int)Math.ceil(d);
   //      } else {
   //          long tmp = Long.parseLong(s);
   //          if (tmp < 1) return 1;
   //          else return tmp;
   //      }
   //  }

}
