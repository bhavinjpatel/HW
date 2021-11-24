import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {



    private static int [] parent;
    private static int [] size;


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner1 = new Scanner(new File("artist_edges.txt"));
        Scanner scanner2 = new Scanner(new File("Weights.txt"));
        int mx = 0;
        ArrayList<WeightedEdge> weightedEdges = new ArrayList<>();
        while (scanner1.hasNext()) {
            String [] lines = scanner1.nextLine().replaceAll("\\s+", " ").split(" ");
            double wt = scanner2.nextDouble();
            int edge1 = Integer.parseInt(lines[0]), edge2 = Integer.parseInt(lines[1]);
            mx = Math.max(mx, Math.max(edge1, edge2));
            WeightedEdge weightedEdge = new WeightedEdge(edge1, edge2, wt);
            weightedEdges.add(weightedEdge);
        }
        ++mx;
        kruskalSimple(weightedEdges, mx, weightedEdges.size());
        kruskalOptimised(weightedEdges, mx, weightedEdges.size());
    }


    private static ArrayList<WeightedEdge> kruskalSimple(ArrayList<WeightedEdge> allEdges, int n, int m) {
        ArrayList<WeightedEdge> result = new ArrayList<>();
        ConnectedComponents connectedComponents = new ConnectedComponents(n, m);
        PriorityQueue<WeightedEdge> weightedEdges = new PriorityQueue<>();
        weightedEdges.addAll(allEdges);
        double wt = 0;
        while (result.size() != n - 1) {
            WeightedEdge weightedEdge = weightedEdges.poll();
            int a = weightedEdge.v1, b = weightedEdge.v2;
            if (connectedComponents.findComponent(a) != connectedComponents.findComponent(b)) {
                result.add(weightedEdge);
                connectedComponents.mergeComponents(a, b);
                wt += weightedEdge.weight;
            }
        }
        System.out.println("The total weight from simple kruskal is : " + wt);
        return result;
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

    private static ArrayList<WeightedEdge> kruskalOptimised(ArrayList<WeightedEdge> allEdges, int n, int m) {
        ArrayList<WeightedEdge> result = new ArrayList<>();
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; ++i) {
            make_set(i);
        }

        PriorityQueue<WeightedEdge> weightedEdges = new PriorityQueue<>();
        weightedEdges.addAll(allEdges);
        double wt = 0;
        while (result.size() != n - 1) {
            WeightedEdge weightedEdge = weightedEdges.poll();
            int a = weightedEdge.v1, b = weightedEdge.v2;
            if (find_set(a) != find_set(b)) {
                result.add(weightedEdge);
                merge(a, b);
                wt += weightedEdge.weight;
            }
        }
        System.out.println("The total weight from simple kruskal is : " + wt);
        return result;
    }
}
