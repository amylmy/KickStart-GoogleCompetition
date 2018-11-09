import java.io.PrintStream;
import java.util.*;

public class C_CaveEscape {
    public static void main(String[] args) throws Exception{
        System.setOut(new PrintStream("C-output.txt"));
        Scanner scanner = new Scanner(System.in);
        int caseNum = scanner.nextInt();
        long start = System.currentTimeMillis();
        for (int i = 1; i <= caseNum; i++) {
            try {
                System.out.println(String.format("Case #%d: %s", i, new C_CaveEscape().solveSmall(scanner)));
            } catch (Throwable e) {
                System.err.println("ERROR in case #" + i);
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.err.println(String.format("Time used: %.3fs", (end - start) / 1000.0));
    }

    private String solveSmall(Scanner scanner) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int E = scanner.nextInt();
        int Sr = scanner.nextInt();
        int Sc = scanner.nextInt();
        int Tr = scanner.nextInt();
        int Tc = scanner.nextInt();

        int[][] V = new int[N+1][M+1];
        int[][] dp = new int[N+1][M+1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                V[i][j] = scanner.nextInt();
                dp[i][j] = -1;
            }
        }
        dp[Sr][Sc] = E;

        int[][] move = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int INF = -100000;
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(Sr, Sc));
        while (!q.isEmpty()) {
            Pair pair = q.poll();
            int x = pair.x;
            int y = pair.y;
            for (int i = 0; i < 4; i++) {
                int xx = x + move[i][0];
                int yy = y + move[i][1];
                if (xx < 1 || xx > N || yy < 1 || yy > M || V[xx][yy] == INF) continue;
                if (dp[x][y] + V[xx][yy] > dp[xx][yy]) {
                    dp[xx][yy] = dp[x][y] + V[xx][yy];
                    q.offer(new Pair(xx, yy));
                }
            }
        }
        return String.valueOf(dp[Tr][Tc]);
    }
    
}

class Pair {
    int x;
    int y;
    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}