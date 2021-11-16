import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class GraphSolution {

    private static int [] parent;
    private static int [] size;
    private static int trees;


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Email-Enron.txt"));
        int mx = 0;
        ArrayList<Integer> edges_a = new ArrayList<>();
        ArrayList<Integer> edges_b = new ArrayList<>();
        while (scanner.hasNext()) {
            String [] strings = scanner.nextLine().replaceAll("\\s+"," ").split(" ");
            int a = Integer.parseInt(strings[0]), b = Integer.parseInt(strings[1]);
            mx = Math.max(a, Math.max(mx, b));
            edges_a.add(a);
            edges_b.add(b);
        }
        System.out.println("Email-Enron.txt");
        solver(edges_a, edges_b, mx);
        scanner = new Scanner(new File("Graph.txt"));
        mx = 0;
        edges_a = new ArrayList<>();
        edges_b = new ArrayList<>();
        while (scanner.hasNext()) {
            String [] strings = scanner.nextLine().split(",");
            int a = Integer.parseInt(strings[0]), b = Integer.parseInt(strings[1]);
            mx = Math.max(a, Math.max(mx, b));
            edges_a.add(a);
            edges_b.add(b);
        }
        System.out.println("Graph.txt");
        solver(edges_a, edges_b, mx);
    }

    private static void bfs(boolean[] visited, ArrayList<Integer>[] adjacencyList) {
        LinkedList<Integer> queue;
        for (int i = 0; i < visited.length; ++i) {
            if (visited[i]) {
                continue;
            }
            queue = new LinkedList<>();
            queue.add(i);
            visited[i] = true;
            while (queue.size() > 0) {
                int top = queue.poll();
                for (Integer j : adjacencyList[top]) {
                    if (visited[j])
                        continue;
                    queue.add(j);
                    visited[j] = true;
                }
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> dfs(boolean[] visited, ArrayList<Integer>[] adjacencyList) {
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        trees = 0;
        for (int i = 0; i < visited.length; ++i) {
            if (visited[i]) {
                continue;
            }
            ArrayList<Integer> component = new ArrayList<>();
            if (dfsHelper(visited, adjacencyList, i, component, -1)) {
                ++trees;
            }
            components.add(component);
        }
        return components;
    }

    private static boolean dfsHelper(boolean[] visited, ArrayList<Integer>[] adjacencyList, int i, ArrayList<Integer> component, int par) {
        visited[i] = true;
        component.add(i);
        boolean ok = true;
        for (Integer j : adjacencyList[i]) {
            if (visited[j]) {
                if (par != j) {
                    ok = false;
                }
                continue;
            }
            ok &= dfsHelper(visited, adjacencyList, j, component, i);
        }
        return ok;
    }

    private static void make_set(int v) {
        parent[v] = v;
        size[v] = 1;
    }

    private static int find_set(int v) {
        if (v == parent[v])
            return v;
        return parent[v] = find_set(parent[v]);
    }

    private static void merge(int a, int b) {
        a = find_set(a);
        b = find_set(b);
        if (a != b) {
            if (size[a] < size[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            parent[b] = a;
            size[a] += size[b];
        }
    }

    @SuppressWarnings("unchecked")
    private static void solver(ArrayList<Integer> edges_a, ArrayList<Integer> edges_b, int mx) {
        ++mx;
        parent = new int[mx];
        size = new int[mx];
        for (int i = 0; i < edges_a.size(); ++i) {
            make_set(edges_a.get(i));
            make_set(edges_b.get(i));
        }

        Instant start = Instant.now();
        for (int i = 0; i < edges_a.size(); ++i) {
            merge(edges_a.get(i), edges_b.get(i));
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        double mili = timeElapsed.toMillis() * 1.00;
        System.out.println("find/merge: " + mili / 1000 + " seconds");
        ArrayList<Integer> [] adjacencyList = new ArrayList[mx];
        for (int i = 0; i < mx; ++i) {
            adjacencyList[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges_a.size(); ++i) {
            adjacencyList[edges_a.get(i)].add(edges_b.get(i));
            adjacencyList[edges_b.get(i)].add(edges_a.get(i));
        }

        start = Instant.now();
        boolean [] visited = new boolean[mx];
        for (int i = 0; i < mx; ++i) {
            visited[i] = false;
        }
        ArrayList<ArrayList<Integer>> components = dfs(visited, adjacencyList);
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        mili = timeElapsed.toMillis() * 1.00;
        System.out.println("DFS: " + mili / 1000 + " seconds");
        visited = new boolean[mx];
        for (int i = 0; i < mx; ++i) {
            visited[i] = false;
        }
        start = Instant.now();
        bfs(visited, adjacencyList);
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        mili = timeElapsed.toMillis() * 1.00;
        System.out.println("BFS: " + mili / 1000 + " seconds");
        System.out.println("Total Components: " + components.size());
        mx = 0;
        for (ArrayList<Integer> comp : components) {
            mx = Math.max(comp.size(), mx);
        }
        System.out.println("Size of the largest component = " + mx);
        System.out.println("No of tress are: " + trees);
    }
}
