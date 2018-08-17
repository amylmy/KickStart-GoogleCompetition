
import java.io.PrintStream;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{
        System.setOut(new PrintStream("ans.txt"));
        Scanner scanner = new Scanner(System.in);
        int caseNum = scanner.nextInt();
        long start = System.currentTimeMillis();
        for (int i = 0; i < caseNum; i++) {
            try {
                System.out.println(String.format("Case #%d: %s", i + 1, new Main().solve1(scanner)));
            } catch (Throwable e) {
                System.err.println("ERROR in case #" + i);
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.err.println(String.format("Time used: %.3fs", (end - start) / 1000.0));
    }


    // find circle via degree of nodes.
    private String solve1(Scanner scanner) {
        int N = scanner.nextInt();
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            if (!graph.containsKey(p)) {
                List<Integer> list = new ArrayList<>();
                list.add(q);
                graph.put(p, list);
            } else graph.get(p).add(q);
            if (!graph.containsKey(q)) {
                List<Integer> list = new ArrayList<>();
                list.add(p);
                graph.put(q, list);
            } else graph.get(q).add(p);
        }

        Map<Integer, List<Integer>> copy = new HashMap<>();
        for (int key : graph.keySet()) {
            List<Integer> list = new ArrayList<>(graph.get(key));
            copy.put(key, list);
        }

        boolean found = false;
        while (!found) {
            found = true;
            for (int i = 1; i < N+1; i++) {
                if (!copy.containsKey(i)) continue;
                if (copy.get(i).size() == 1) {
                    for (int v : copy.get(i)) {
                        copy.get(v).remove((Object)i);
                    }
                    copy.remove(i);
                    found = false;
                } else if (copy.get(i).size() == 0) copy.remove(i);
            }
        }

        Queue<Integer> q = new LinkedList<>();
        int[] dis = new int[N+1];
        boolean[] visited = new boolean[N+1];

        for (int v : copy.keySet()) {
            q.offer(v);
            visited[v] = true;
        }

        while (!q.isEmpty()) {
            int v = q.poll();
            for (int t : graph.get(v)) {
                if (visited[t]) continue;
                dis[t] = dis[v] + 1;
                q.offer(t);
                visited[t] = true;
            }
        }

        String ans = String.valueOf(dis[1]);
        for (int i = 2; i < N+1; i++) ans = ans + " " + dis[i];
        return ans;
    }

    // Method 2 ： DFS find circle
    private String solve2(Scanner scanner) {
        int N = scanner.nextInt();
        //List<Integer>[] graph = new ArrayList<>()[N];
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            if (!graph.containsKey(p)) {
                List<Integer> list = new ArrayList<>();
                list.add(q);
                graph.put(p, list);
            } else graph.get(p).add(q);

            if (!graph.containsKey(q)) {
                List<Integer> list = new ArrayList<>();
                list.add(p);
                graph.put(q, list);
            } else graph.get(q).add(p);
        }

        int[] visited = new int[N+1];
        int[] father = new int[N+1];
        boolean[] found = dfsVisited(graph, 1, 0, father, visited);
        int[] dis = new int[N+1];

        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i < N+1; i++) {
            if (found[i]) q.offer(i);
        }
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int v : graph.get(node)) {
                if (!found[v]) {
                    dis[v] = dis[node] + 1;
                    q.offer(v);
                    found[v] = true;
                }
            }
        }

        String s = String.valueOf(dis[1]);
        for (int i = 2; i < N+1; i++) s = s + " " + String.valueOf(dis[i]);
        return s;
    }

    private boolean[] dfsVisited(Map<Integer, List<Integer>> graph, int node, int prev, int[] father, int[] visited) {
        for (int v : graph.get(node)) {
            if (prev == v) continue;

            if (visited[v] == 1) {
                boolean[] circle = new boolean[visited.length];
                circle[v] = true;
                while (node != v) {
                    circle[node] = true;
                    node = father[node];
                }
                return circle;
            }

            father[v] = node;

            if (visited[v] == 0) {
                visited[v] = 1;
                boolean[] found = dfsVisited(graph, v, node, father, visited);
                if(found != null) return found;
                visited[v] = 2;
            }
        }
        return null;
    }

}
