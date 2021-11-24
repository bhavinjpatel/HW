import java.util.*;
import java.io.*;
public class Graph
{
    static int numVertex;
   static  int numEdges;
    ArrayList<ArrayList<Integer>> graph;
    boolean[] marked;
    int components;
    static ArrayList<Edge> edgeSet;
    int numTrees;
    HashSet<Integer> comp;
    int maxCCSize;
    
    
    public Graph () {
        numVertex =0;
        numEdges =0;
        graph = new ArrayList<>();
        components=0;
        edgeSet = new ArrayList<>();
        
    }
    
    public Graph(int vertexCount) {
        
        numVertex=vertexCount;
        graph = new ArrayList<>(numVertex);
        for (int i=0;i<numVertex;i++){
        graph.add(new ArrayList<>());
    }
        marked = new boolean[numVertex];
        components=0;
        numTrees=0;
        comp=new HashSet<>();
        maxCCSize=0;
        
        
    }
    
   public void depthFirstTraversal() {
       for (int i=0;i<numVertex;i++) 
       if (!marked[i]){
           components++;
           comp=new HashSet<>();
          
           searchDF (i);
           numTrees(comp);
        }
        System.out.println("number of connected components = "+components);
       
    }
    
    public void searchDF(int v){
       // System.out.println(v);
          comp.add(v);
         marked[v]=true;
        ArrayList<Integer> neighbors= getNeighbors(v);
        for (Integer u:neighbors)
        if (!marked[u]) searchDF(u);
    }
    
    public void breadthFirstTraversal() {
        components=0;
       marked = new boolean[numVertex];
       for (int i=0;i<numVertex;i++) {
       if (!marked[i]){
          // System.out.println(maxCCSize);
           components++;
           searchBF (i);
        }
    }
        System.out.println("number of connected components = "+components);
       
    }
    
   
    
    public void searchBF(int v){
      Queue<Integer> que = new LinkedList<>();
      que.add(v);
      marked[v]=true;
      int size=0;
        
        while (!que.isEmpty()) {
            int p =que.remove();
            size++;
            for (Integer u:getNeighbors(p))
            if (!marked[u]) { 
                que.add(u);
                marked[u]=true;
            }
        }
        if (size>maxCCSize) maxCCSize=size;
       // System.out.println(maxCCSize);
    }
    
    
    public ArrayList<Integer> getNeighbors(int u) {
        
        return graph.get(u);
    }
    
    public boolean edgePresent(int u, int v) {
      
        return (graph.get(u).contains(v));
        
    }
    
    public boolean isTree(HashSet<Integer> cc){
        int countEdges=0;
        if (cc.size()==1) return true; else
        
        for (Integer vertex: cc) {
            countEdges+= getNeighbors(vertex).size();
        }
        if (countEdges==cc.size()-1) return true;
        else return false;
        
    }
    
    public void numTrees(HashSet<Integer> cc) {
        if (isTree(cc)) numTrees++;
        
    }
    
    public void addEdge(int u, int v) {
        // lets assume that the vertices are already created.
       if (u>=0 && u<numVertex && v>=0 && v<numVertex) { 
         if (!edgePresent(u,v))
        graph.get(u).add(v);
        
         if (!edgePresent(v,u))
        graph.get(v).add(u);
        numEdges++;
    }
    else {
        
        throw new IndexOutOfBoundsException();
    }
    
    
        
    }
    
     public  static Graph readAndStoreGraph(String fileName) {
         
          int maxV=0;
          edgeSet = new ArrayList<>();
     
        try{
        Scanner sc =new Scanner(new File(fileName));
        String s;
       
       
      
      // graph.add(new ArrayList<Integer>());
       
     while (sc.hasNextLine()) {
           s = sc.nextLine();
           if (s.isEmpty()) continue;
           String[] line= s.split("\\s");
          // String[] line= s.split(",");
        int v1=  Integer.parseInt(line[0]);
        int v2=  Integer.parseInt(line[1]);
        int p= Math.max(v1,v2);
        if (p>maxV) maxV=p;
        edgeSet.add(new Edge(v1,v2));
        
    }
    
    
    
}
catch (FileNotFoundException e) {
}

Graph gr = new Graph(maxV+1);
//numEdges = edgeSet.size();
return gr;

    
}
public static void main(String[] args) {
    
   String file = "Email-Enron.txt";
  //String file = "Graph.txt";
 // String file = "artist_edges.txt";
    Graph gr = readAndStoreGraph(file);
    System.out.println("# vertices "+numVertex);
    
    for (Edge e: edgeSet) 
        gr.addEdge(e.v1,e.v2);
    System.out.println("# edges  "+ numEdges);
    long t1=System.currentTimeMillis();
     gr.depthFirstTraversal();
     long t2=System.currentTimeMillis();
    System.out.println("time taken =  "+ (t2-t1)*1.0/1000);
    
    System.out.println("number of Trees "+gr.numTrees);
    
     long t3=System.currentTimeMillis();
     gr.breadthFirstTraversal();
     long t4=System.currentTimeMillis();
    System.out.println("time taken =  "+ (t4-t3)*1.0/1000);
    
    System.out.println("max connected component size = "+ gr.maxCCSize);
        
    
    
}
    
    
}
