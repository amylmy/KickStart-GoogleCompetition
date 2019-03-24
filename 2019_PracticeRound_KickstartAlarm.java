import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args)
        Scanner scanner = new Scanner(System.in);
        int caseNum = scanner.nextInt();
        for (int i = 1; i <= caseNum; i++) {
            System.out.println(String.format("Case #%d: %s", i, new Solution().solveSmall(scanner)));
        }
    }
    private String solveSmall(Scanner scanner) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        int C = scanner.nextInt();
        int D = scanner.nextInt();
        int E1 = scanner.nextInt();
        int E2 = scanner.nextInt();
        int F = scanner.nextInt();

        long[] A = new long[N];
        long[] x = new long[N];
        long[] y = new long[N];
        x[0] = x1;
        y[0] = y1;
        A[0] = (x[0] + y[0]) % F;
        for (int i = 1; i < N; i++) {
            x[i] = (C * x[i-1] + D * y[i-1] + E1) % F;
            y[i] = (D * x[i-1] + C * y[i-1] + E2) % F;
            A[i] = (x[i] + y[i]) % F;
        }

        final int mod = 1000000007;
        long[][] expo = new long[N+1][K+1];
        for (int i = 1; i <= N; i++) expo[i][0] = 1;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= K; j++) {
                expo[i][j] = (expo[i][j-1] * i) % mod;
            }
        }
        long power_sum = 0;
        for (int i = 1; i <= K; i++) {
            long[][] dp = new long[N+1][N+1];
            for (int start = 0; start < N; start++) {
                for (int len = 1; len <= N-start; len++) {
                    dp[start][len] = (dp[start][len-1] + (A[start+len-1] * expo[len][i]) % mod) % mod;
                    power_sum = (power_sum + dp[start][len]) % mod;
                }
            }
        }

        return String.valueOf(power_sum % mod);

    }
}
