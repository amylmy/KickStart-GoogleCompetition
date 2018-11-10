import java.io.FileInputStream;
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
                System.out.println(String.format("Case #%d: %s", i, new C_CaveEscape().solve(scanner)));
            } catch (Throwable e) {
                System.err.println("ERROR in case #" + i);
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.err.println(String.format("Time used: %.3fs", (end - start) / 1000.0));
    }

    private String solve(Scanner scanner) {
        int[][] move = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int INF = -100000;

        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int E = scanner.nextInt();
        int Sr = scanner.nextInt();
        int Sc = scanner.nextInt();
        int Tr = scanner.nextInt();
        int Tc = scanner.nextInt();

        int[][] V = new int[N+1][M+1];
        int[][] idx = new int[N+1][M+1];
        boolean[][] vis = new boolean[N+1][M+1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                V[i][j] = scanner.nextInt();
                idx[i][j] = -1;
                vis[i][j] = false;
            }
        }
        V[Sr][Sc] = E;
        int[] VV = new int[(N+1) * (M+1)];
        Arrays.fill(VV, 0);

        //Merge cells
        int counter = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (vis[i][j] || V[i][j] == INF) continue;

                if (V[i][j] < 0) { // A trap
                    idx[i][j] = counter;
                    VV[counter] = V[i][j];
                    vis[i][j] = true;
                    counter++;
                    continue;
                }

                Queue<Pair> q = new LinkedList<>();
                q.offer(new Pair(i, j));
                int total = V[i][j];
                vis[i][j] = true;
                idx[i][j] = counter;
                while (!q.isEmpty()) {
                    Pair p = q.poll();
                    int x = p.x;
                    int y = p.y;
                    for (int k = 0; k < 4; k++) {
                        int xx = x + move[k][0];
                        int yy = y + move[k][1];
                        if (xx < 1 || xx > N || yy < 1 || yy > M || V[xx][yy] < 0 || vis[xx][yy]) continue;
                        total += V[xx][yy];
                        vis[xx][yy] = true;
                        idx[xx][yy] = counter;
                        q.offer(new Pair(xx, yy));
                    }
                }
                VV[counter] = total;
                counter++;

            }
        }

        List<Integer>[] P = new ArrayList[counter];
        boolean[][] viss = new boolean[counter][counter];
        for (int c = 0; c < counter; c++) {
            P[c] = new ArrayList<>();
            Arrays.fill(viss[c], false);
        }
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (V[i][j] == INF) continue;
                for (int k = 0; k < 4; k++) {
                    int xx = i + move[k][0];
                    int yy = j + move[k][1];
                    if (!isValid(xx, yy, N, M) || V[xx][yy] == INF || idx[i][j] == idx[xx][yy]) continue;
                    int s = idx[i][j];
                    int ss = idx[xx][yy];
                    if (viss[s][ss]) continue;

                    P[s].add(ss);
                    P[ss].add(s);
                    viss[s][ss] = true;
                    viss[ss][s] = true;
                }
            }
        }

        int start = idx[Sr][Sc];
        int end = idx[Tr][Tc];
        int ans = -1;
        if (start == end) ans = VV[start];

        int[] traps = new int[counter];
        Arrays.fill(traps, -1);
        traps[start] = 0;
        int trap_count = 1;
        for (int i = 0; i < counter; i++) {
            if (VV[i] < 0) {
                traps[i] = trap_count;
                trap_count++;
            }
        }

        if (start != end) {
            traps[end] = trap_count;
            trap_count++;
        }

        int[][] dp = new int[1 << trap_count][counter];
        boolean[][] visit = new boolean[1 << trap_count][counter];

        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
            Arrays.fill(visit[i], false);
        }

        dp[1][start] = VV[start];
        visit[1][start] = true;

        for (int i = 0; i < (1 << trap_count); i++) {
            for (int curr = 0; curr < counter; curr++) {
                int current_id = traps[curr];
                if (current_id == -1 || !trap_visited(i, current_id)) continue;

                // Mark all reachable cells
                for (int j = 0; j < P[curr].size(); j++) {
                    int tmp_id = P[curr].get(j);
                    visit[i][tmp_id] = true;
                }
                visit[i][curr] = true;
            }

            Set<Integer> top_q = new TreeSet<>();
            for (int j = 0; j < counter; j++) top_q.add(j);

            while (!top_q.isEmpty()) {
                int curr = ((TreeSet<Integer>) top_q).pollFirst();
                // not a trap or haven't reached this trap
                int current_id = traps[curr];
                if (current_id == -1 || !trap_visited(i, current_id)) continue;
                if (dp[i][curr] == -1) continue;

                // Search all reachable yet-to-visit traps
                Queue<Integer> q = new LinkedList<>();
                for (int j = 0; j < P[curr].size(); j++) {
                    int tmp_id = P[curr].get(j);
                    if (traps[tmp_id] != -1) q.offer(tmp_id);
                    else { // not a trap
                        for (int k = 0; k < P[tmp_id].size(); k++) {
                            // push all traps
                            int trap = P[tmp_id].get(k);
                            if (curr != trap) q.offer(trap);
                        }
                    }
                }

                while (!q.isEmpty()) {
                    int trap = q.poll();
                    if (trap_visited(i, traps[trap])) {
                        if (dp[i][curr] > dp[i][trap]) {
                            dp[i][trap] = dp[i][curr];
                            top_q.add(trap);
                        }
                        continue;
                    }
                    // can't reach
                    if (dp[i][curr] + VV[trap] < 0) continue;
                    int trap_id = traps[trap];
                    int tmp = dp[i][curr] + VV[trap];
                    for (int k = 0; k < P[trap].size(); k++) {
                        int id = P[trap].get(k);
                        if (visit[i][id] || traps[id] != -1) continue;
                        if (VV[id] >= 0) tmp += VV[id];
                    }
                    dp[i | (1 << trap_id)][trap] = tmp;
                }
            }
        }

        int total = start == end ? trap_count : trap_count - 1;
        for (int i = 0; i < (1 << total); i++) {
            ans = Math.max(ans, dp[(1 << traps[end]) | i][start]);
        }

        return String.valueOf(ans);
    }

    private boolean isValid(int x, int y, int n, int m) {
        return x >= 1 && x <= n && y >= 1 && y <= m;
    }

    private boolean trap_visited(int num, int id) {
        return (num & (1 << id)) != 0;
    }

    private String solveSmall(Scanner scanner) {
        int[][] move = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int INF = -100000;

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