
import java.util.*;
import java.io.*;
public class DirectedGraph  {
    ArrayList<DirectedNodeList> dGraph;
    int numVertex;
   boolean [] marked;
    
    
    public DirectedGraph() {
        dGraph = new ArrayList<>();
        numVertex=0;
        
    }
    
    public DirectedGraph(int n) {
      numVertex =n;
      dGraph = new ArrayList<>(n);
      marked= new boolean[n];
      for (int i=0;i<numVertex;i++)
       dGraph.add(new DirectedNodeList());
    }

    public void addEdge(int u, int v) {
        // assume all vertices are created
        // directed edge u to v will cause outdegree of u to go up and indegree of v to go up.

        if (u>=0 && u<numVertex && v>=0 && v<numVertex) {
            if (u!=v) {
                getNeighborList(u).addToOutList(v);
                getNeighborList(v).addToInList(u);
            }
        }
        else throw new IndexOutOfBoundsException();
    }
    
    public DirectedNodeList getNeighborList(int u) {
        return dGraph.get(u);
    }
    
    public void printAdjacency(int u) {
       DirectedNodeList dnl = getNeighborList(u);
       System.out.println ("vertices going into "+u+"  "+dnl.getInList());
       System.out.println ("vertices going out of "+u+"  "+dnl.getOutList());
       System.out.println();
    }
    
    public int [] postOrderDepthFirstTraversal() {
        int [] finishing = new int[numVertex];
        int curr = 0;
       for (int i=0;i<numVertex;i++) 
       if (!marked[i])
           curr = postOrderDFT (i, finishing, curr);
       return finishing;
    }
    public int postOrderDFT(int v, int [] finishing, int curr) {
        
        marked[v]=true;
        
        for (Integer u:dGraph.get(v).getOutList())
        if (!marked[u])
            curr = postOrderDFT(u, finishing, curr);
        finishing[curr] = v;
        ++curr;
        return curr;
        // System.out.println(v);
    }

    public void depthFirstTraversalOnFinishingTime(int[] finishing, HashMap<Integer, ArrayList<Integer>> leaders, int [] leader) {
        for (int i = 0; i < numVertex; ++i)
            marked[i] = false;
        for(int i = numVertex - 1; i >= 0; --i) {
            if (!marked[finishing[i]]){
                ArrayList<Integer> arrayList = new ArrayList<>();
                //System.out.println("Leader " + finishing[i]);
                dFT(finishing[i], arrayList);
                for (int x : arrayList) {
                    leader[x] = finishing[i];
                }
                leaders.put(finishing[i], arrayList);
                //System.out.println(arrayList.size());
            }
        }
    }
    public void depthFirstTraversal() {
       for (int i=0;i<numVertex;i++)
           if (!marked[i])
               dFT (i, new ArrayList<>());
       
    }
    public void dFT(int v, ArrayList<Integer> res){
        //System.out.println(v);
        marked[v]=true;
        res.add(v);
        for (Integer u:dGraph.get(v).getOutList())
        if (!marked[u]) dFT(u, res);
       
    }

    public DirectedGraph scc() {
        DirectedGraph reversed = new DirectedGraph(numVertex);
        for (int i = 0; i < numVertex; ++i) {
            for(int u : dGraph.get(i).getOutList()) {
                reversed.addEdge(u, i);
            }
            for (int v : dGraph.get(i).getInList()) {
                reversed.addEdge(i, v);
            }
        }
        HashMap<Integer, ArrayList<Integer>> leaders = new HashMap<>();
        int [] leader = new int[numVertex];
        depthFirstTraversalOnFinishingTime(reversed.postOrderDepthFirstTraversal(), leaders, leader);
        System.out.println("No. of scc: " + leaders.size());
        int v = 0;
        for (ArrayList<Integer> arr : leaders.values()) {
            v = Math.max(v, arr.size());
        }
        System.out.println("Max Size SCC: " + v);
        HashMap<Integer, Integer> mapLeadersToIdx = new HashMap<>();
        int i = 0;
        for (int a : leaders.keySet()) {
            mapLeadersToIdx.put(a, i);
            ++i;
        }
        DirectedGraph reduced = new DirectedGraph(leaders.size());
        for (Map.Entry<Integer, ArrayList<Integer>> a : leaders.entrySet()) {
                for (int nodes1 : a.getValue()) {
                    for (int x : dGraph.get(nodes1).getInList()) {
                        if (x == nodes1)
                            continue;
                        reduced.addEdge(mapLeadersToIdx.get(leader[x]), mapLeadersToIdx.get(a.getKey()));
                    }
                }
        }
        return reduced;
    }
    
    
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Slashdot0902.txt"));
        scanner.nextLine();
        scanner.nextLine();
        String w = scanner.nextLine();
        int n = Integer.parseInt(w.split(" ")[2]);
        scanner.nextLine();
        DirectedGraph directedGraph = new DirectedGraph(n);
        while (scanner.hasNext()) {
            int u = scanner.nextInt(), v = scanner.nextInt();
            if (u != v)
                directedGraph.addEdge(u, v);
        }
        DirectedGraph reduced = directedGraph.scc();
        System.out.println("Total Vertices: " + reduced.numVertex);
        int totalEdges = 0;
        for (int i = 0; i < reduced.numVertex; ++i) {
            totalEdges += reduced.getNeighborList(i).getOutList().size();
        }
        System.out.println("Total Edges: " + totalEdges);
//        int n =6;
//        DirectedGraph dg = new DirectedGraph(n);
//        dg.addEdge(0,1);
//        dg.addEdge(0,2);
//        dg.addEdge(2,4);
//        dg.addEdge(1,4);
//        dg.addEdge(2,3);
//        dg.addEdge(3,5);
//        dg.addEdge(4,5);
//
//
//
//
//      /*
//        dg.addEdge(0,1);
//        dg.addEdge(0,2);
//        dg.addEdge(1,2);
//        dg.addEdge(1,3);
//        dg.addEdge(2,4);
//        dg.addEdge(2,3);
//        dg.addEdge(3,1);
//        dg.addEdge(4,1);
//        dg.addEdge(4,3);
//        dg.addEdge(4,0);
//        dg.addEdge(3,0);
//        */
//
//       // for (int i=0;i<dg.numVertex;i++)
//       //  dg.printAdjacency(i);
//        System.out.println ("regular depth first traversal");
//        dg.depthFirstTraversal();
//        System.out.println ();
//        dg.marked= new boolean[n];
//        System.out.println("post order depth first traversal");
//        dg.postOrderDepthFirstTraversal();
//
//
//
    }
    

   
}
