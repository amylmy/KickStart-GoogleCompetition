import java.util.Scanner;

public class Solution {
    public static String solve(Scanner input) {
        int N = input.nextInt();

        String scores = input.next();
        int[] ss = new int[N];
        for (int i = 0; i < N; i++) ss[i] = scores.charAt(i) - '0';

        int d = N - N / 2;
        int[] dp = new int[N];
        int max = 0;

        for (int i = 0; i < d; i++) dp[0] += ss[i];
        for (int i = 1; i + d - 1 < N; i++) dp[i] = dp[i-1] - ss[i-1] + ss[i+d-1];
        for (int i = 0; i < N; i++) max = Math.max(max, dp[i]);

        return String.valueOf(max);
    }

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int caseNum = input.nextInt();
        for (int ks = 1; ks <= caseNum; ks++) {
            System.out.println("Case #" + ks + ": " + solve(input));
            System.out.println(String.format("Case #%d: %s", ks, solve(input)));
        }
    }
}
