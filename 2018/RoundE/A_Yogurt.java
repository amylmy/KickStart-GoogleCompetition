import java.io.PrintStream;
import java.util.*;

public class A_Yogurt {
    public static void main(String[] args) throws Exception{
        System.setOut(new PrintStream("A-output.txt"));
        Scanner scanner = new Scanner(System.in);
        int caseNum = scanner.nextInt();
        long start = System.currentTimeMillis();
        for (int i = 1; i <= caseNum; i++) {
            try {
                System.out.println(String.format("Case #%d: %s", i, new A_Yogurt().solve(scanner)));
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
        int K = scanner.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) A[i] = scanner.nextInt();
        Arrays.sort(A);
        int end = A[N-1];
        int cur = 0;
        int ans = 0;
        for (int i = 0; i < end; i++) {
            while (A[cur] <= i) cur++;
            for (int j = 0; j < K; j++) {
                if (cur + j < N && A[cur + j] > i) ans++;
            }
            cur += K;
            if (cur >= N) break;
        }
        return String.valueOf(ans);
    }
    
}