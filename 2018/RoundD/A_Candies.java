import java.io.PrintStream;
import java.util.*;

public class A_Candies {
    public static void main(String[] args) throws Exception{
        System.setOut(new PrintStream("A-output.txt"));
        Scanner scanner = new Scanner(System.in);
        int caseNum = scanner.nextInt();
        long start = System.currentTimeMillis();
        for (int i = 1; i <= caseNum; i++) {
            try {
                System.out.println(String.format("Case #%d: %s", i, new A_Candies().smallSolve(scanner)));
            } catch (Throwable e) {
                System.err.println("ERROR in case #" + i);
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.err.println(String.format("Time used: %.3fs", (end - start) / 1000.0));
    }

    private String smallSolve(Scanner scanner) {
        // The first line contains three integers N, O, and D
        // The second line contains seven integers X1, X2, A, B, C, M, L;
        int N = scanner.nextInt();
        int O = scanner.nextInt();
        long D = scanner.nextLong();

        long X1 = scanner.nextLong();
        long X2 = scanner.nextLong();
        long A = scanner.nextLong();
        long B = scanner.nextLong();
        long C = scanner.nextLong();
        long M = scanner.nextLong();
        long L = scanner.nextLong();

        long[] Sweet = generateSi(N, X1, X2, A, B, C, M, L);
        //for (int i = 0; i < N; i++) System.out.println(Sweet[i]);

        int lo = 0;
        int hi = 0;
        int oddNum = 0;

        long[] dp = new long[N];
        Arrays.fill(dp, Long.MIN_VALUE);

        long ans = Long.MIN_VALUE; 

        while (hi < N && lo < N && lo <= hi) {
            boolean is_odd = (Sweet[hi] % 2) != 0;
            if (lo == hi) {
                oddNum = is_odd ? 1 : 0;
                if (oddNum <= O && Sweet[hi] <= D) {
                    dp[lo] = Sweet[hi];
                    hi++;
                } else {
                    lo++;
                    hi++;
                }
            } else {
                if ((!is_odd && oddNum <= O && dp[lo] + Sweet[hi] <= D) 
                    || (is_odd && oddNum + 1 <= O && dp[lo] + Sweet[hi] <= D)) {
                    dp[lo] += Sweet[hi];
                    hi++;
                    if (is_odd) oddNum++;
                } else {
                    lo++;
                    dp[lo] = dp[lo-1] - Sweet[lo-1];
                    if (Sweet[lo-1] % 2 != 0) oddNum--;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            ans = Math.max(ans, dp[i]);
           // System.err.println(String.valueOf(dp[i]));
        }

        if (ans != Long.MIN_VALUE) return String.valueOf(ans);

        return "IMPOSSIBLE";
    }

    //  length, X1, X2, A, B, C, M, L
    private long[] generateSi(int len, long X1, long X2, long A, long B,long C, long M, long L) {
        // Xi = (A * Xi-1 + B * Xi-2 + C)% M
        // Xi = (A × Xi - 1 + B × Xi - 2 + C) modulo M, for i = 3 to N.
        // Si = Xi + L, for i = 1 to N.
        long[] Si = new long[len];
        long[] Xi = new long[len];
        Xi[0] = X1;
        Xi[1] = X2;
        Si[0] = X1 + L;
        Si[1] = X2 + L;
        for (int i = 2; i < len; i++) {
            Xi[i] = (A * Xi[i-1] + B * Xi[i-2] + C) % M;
            Si[i] = Xi[i] + L;
        }
        return Si;
    }

}
