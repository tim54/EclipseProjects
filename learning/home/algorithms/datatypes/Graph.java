package home.algorithms.datatypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.princeton.cs.algs4.In;

public class Graph {
	
	private static final String NEWLINE = System.getProperty("line.separator");
	
	private final int V;
	private int E;
	private LinkedList<Integer>[] adj;
	
	@SuppressWarnings("unchecked")
	public Graph(int v) {
		if (v < 0) throw new IllegalArgumentException("Number of vertices should be non 0");
		V = v;
		E = 0;
		
		adj = (LinkedList<Integer>[]) new LinkedList[v];
		for (int i = 0; i < V; i++) {
			adj[i] = new LinkedList<Integer>();
		}
	}
	
	public Graph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
}
	
	public Graph(Graph g) {
		this(g.V());
		this.E = g.E();
		
		for (int i = 0; i < g.V(); i++){
			this.adj[i] = new LinkedList<Integer>(g.adj[i]);
		}
	}
	
	public int V() { return this.V; }
	public int E() { return this.E; }
	
	public void validateVertex(int v){
		if ((v < 0) || (v >= this.V))
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (this.V-1));
	}
	
	public void addEdge(int v1, int v2){
		validateVertex(v1);
		validateVertex(v2);
		this.E++;
		adj[v1].add(v2);
		adj[v2].add(v1);
	}
	
	public Iterable<Integer> adj(int v){
		validateVertex(v);
		return adj[v];
	}
	
	public int degree(int v){
		validateVertex(v);
		return adj[v].size();
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("Graph is: " + NEWLINE);
		for (int i = 0; i < this.V; i++){
			str.append("Vertex " + i + ": ");
			for (int val : this.adj[i]){
				str.append(val + ", ");
			}
			str.append(NEWLINE);
		}
		
		return str.toString();
	}

	public static void main(String[] args) throws IOException {
		String userdir = System.getProperty("user.dir");
		System.out.println(userdir);

		File file = new File(userdir + "\\home\\algorithms\\datatypes\\GraphData.txt");
	        Graph G = new Graph(new In(file));
	        System.out.println(G);

	}

}