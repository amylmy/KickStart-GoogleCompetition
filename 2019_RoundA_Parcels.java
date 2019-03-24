import java.util.*;

public class Solution {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int caseNum = input.nextInt();
        for (int ks = 1; ks <= caseNum; ks++) {
            System.out.println(String.format("Case #%d: %s", ks, new Solution().solveLarge(input)));
        }
    }
    
    public String solveLarge(Scanner scanner) {
        int r = scanner.nextInt();
        int c = scanner.nextInt();

        boolean[][] off = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            String s = scanner.next();
            for (int j = 0; j < s.length(); j++) {
                off[i][j] = s.charAt(j) == '1';
            }
        }
        boolean[][] used = new boolean[r][c];
        int[][] len = new int[r][c];
        Queue<Pair> q = new ArrayDeque<>();
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (off[i][j]) {
                    used[i][j] = true;
                    q.add(new Pair(i, j));
                }
            }
        }
        while (!q.isEmpty()) {
            Pair th = q.poll();
            int x = th.x;
            int y = th.y;
            for (int i = 0; i < dx.length; i++) {
                int x1 = x + dx[i];
                int y1 = y + dy[i];
                if (check(x1, y1, r, c, used)) {
                    used[x1][y1] = true;
                    len[x1][y1] = len[x][y] + 1;
                    q.add(new Pair(x1, y1));
                }
            }
        }
        int max = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                max = Math.max(max, len[i][j]);
            }
        }
        int min = -1;
        while (min + 1 < max) {
            int m = (min + max) / 2;
            int min1 = Integer.MAX_VALUE;
            int max1 = Integer.MIN_VALUE;
            int min2 = Integer.MAX_VALUE;
            int max2 = Integer.MIN_VALUE;
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (len[i][j] > m) {
                        min1 = Math.min(min1, i + j);
                        max1 = Math.max(max1, i + j);
                        min2 = Math.min(min2, i - j);
                        max2 = Math.max(max2, i - j);
                    }
                }
            }
            int d1 = Math.max(max1 - min1, max2 - min2);
            int d2 = Math.min(max1 - min1, max2 - min2);
            int l = d1 / 2;
            if (d1 % 2 == 1) {
                l++;
            }
            if (d1 % 2 == 0 && d2 % 2 == 0 && d1 == d2 && (min1 + min2) % 2 == 1) {
                l++;
            }
            if (l <= m) {
                max = m;
            } else {
                min = m;
            }
        }
        long ans = max;
        return String.valueOf(ans);
    }

    public class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public boolean check(int x, int y, int r, int c, boolean[][] used) {
        return 0 <= x && x < r && 0 <= y && y < c && !used[x][y];
    }

//    public static String solveSmall(Scanner scanner) {
//        int R = scanner.nextInt();
//        int C = scanner.nextInt();
//
//        char[][] grid = new char[R][C];
//        for (int i = 0; i < R; i++) {
//            String line = scanner.next();
//            grid[i] = line.toCharArray();
//        }
//
//        List<int[]> office_pos = new ArrayList<>();
//        List<int[]> candidates = new ArrayList<>();
//
//        for (int i = 0; i < R; i++) {
//            for (int j = 0; j < C; j++) {
//                if (grid[i][j] == '1') office_pos.add(new int[]{i, j});
//                else candidates.add(new int[]{i, j});
//            }
//        }
//
//        if (candidates.size() == 0) return "0";
//
//        int ans = R + C;
//        for (int[] cc : candidates) {
//            grid[cc[0]][cc[1]] = '1';
//            int[][] dis = new int[R][C];
//            int time = 0;
//            for (int i = 0; i < R; i++) {
//                for (int j = 0; j < C; j++) {
//                    if (grid[i][j] == '1') dis[i][j] = 0;
//                    else {
//                        dis[i][j] = Integer.MAX_VALUE;
//                        for (int k = 0; k < office_pos.size(); k++) {
//                            dis[i][j] = Math.min(dis[i][j], Math.abs(i - office_pos.get(k)[0]) + Math.abs(j - office_pos.get(k)[1]));
//                        }
//                        dis[i][j] = Math.min(dis[i][j], Math.abs(i - cc[0]) + Math.abs(j - cc[1]));
//                        time = Math.max(time, dis[i][j]);
//                    }
//                }
//            }
//            ans = Math.min(ans, time);
//            grid[cc[0]][cc[1]] = '0';
//        }
//
//        return String.valueOf(ans);
//    }
}
