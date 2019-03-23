import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class B_Mural {
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream("H-B-output.txt"));
        Scanner scanner = new Scanner(new FileInputStream("B-large-practice.in"));
//        Scanner scanner = new Scanner(System.in);
        int caseNum = scanner.nextInt();
        long start = System.currentTimeMillis();
        for (int i = 1; i <= caseNum; i++) {
            try {
                System.out.println(String.format("Case #%d: %s", i, new B_Mural().solve(scanner)));
            } catch (Throwable e) {
                System.err.println("ERROR in case #" + i);
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.err.println(String.format("Time used: %.3fs", (end - start) / 1000.0));
    }

    private String solve(Scanner scanner) {
        int N = scanner.nextInt();
        String scores = scanner.next();

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
}
