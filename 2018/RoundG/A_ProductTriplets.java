import java.io.PrintStream;
import java.util.*;
public class A_ProductTriplets {
    public static void main(String[] args) throws Exception{
        System.setOut(new PrintStream("A-output.txt"));
        Scanner scanner = new Scanner(System.in);
        int caseNum = scanner.nextInt();
        long start = System.currentTimeMillis();
        for (int i = 1; i <= caseNum; i++) {
            try {
                System.out.println(String.format("Case #%d: %s", i, new A_ProductTriplets().solve(scanner)));
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
        long[] A = new long[N];
        for (int i = 0; i < N; i++) A[i] = scanner.nextLong();
        long ans = 0;
        long zero = 0;
        HashMap<Long, Integer> map = new HashMap<>();
        for(int i = 0; i < N; i++) {
            if (A[i] == 0) {
                zero++;
                continue;
            }
            for(int j = i + 1; j < N; j++) {
                if(A[j] == 0) continue;
                long x = A[i];
                long y = A[j];
                long z = x * y;
                ans += map.getOrDefault(z, 0);
                if(x % y == 0) {
                    long p = x / y;
                    if(p != z) ans += map.getOrDefault(p, 0);
                }
                if(x == y) continue;
                if(y % x == 0) {
                    long q = y / x;
                    if(q != z) ans += map.getOrDefault(q, 0);
                }
            }
            map.put(A[i], map.getOrDefault(A[i], 0) + 1);
        }

        ans += ((zero * (zero - 1)) / 2) * (N - zero) ;
        ans += (zero * (zero - 1) * (zero - 2)) / 6;
        return String.valueOf(ans);
    }

    // private String solveSmall(Scanner scanner) {
    //     int N = scanner.nextInt();
    //     int[] A = new int[N];
    //     for (int i = 0; i < N; i++) A[i] = scanner.nextInt();
    //     long ans = 0;
    //     for (int i = 0; i < N; i++) {
    //         for (int j = i + 1; j < N; j++) {
    //             for (int k = j + 1; k < N; k++) {
    //                 if ((A[i] == 0 && A[j] == 0) || (A[i] == 0 && A[k] == 0) || (A[j] == 0 && A[k] == 0)) {
    //                     ans++;
    //                 } else if (A[j] != 0 && A[i] == A[k] / A[j] && A[k] % A[j] == 0) {
    //                     ans++;
    //                 } else if (A[k] != 0 && A[i] == A[j] / A[k] && A[j] % A[k] == 0) {
    //                     ans++;
    //                 } else if (A[k] != 0 && A[j] == A[i] / A[k] && A[i] % A[k] == 0) {
    //                     ans++;
    //                 }
    //             }
    //         }
    //     }
    //     return String.valueOf(ans);
    // }
}
