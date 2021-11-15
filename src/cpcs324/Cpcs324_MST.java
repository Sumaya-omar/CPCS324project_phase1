/*
 * CPCS324 project phase 1.
 * Task 3  implementation |comparing between prim using minheap and prim using priorty queue 
 *Task 2   implementation |comparing between prim using priorty queue and kruskal
 * Using Randomly generated graph then send it to primPQ() ,primMH(),kruskalMST
 * Instructor :DR.Mai Fadel 
 * Done by 
	*Sumaya Bamer 1911108
	*Sadeem Alhijiri 1913044
	*Maha ALtwiem 1905255
 * Refrence  
 *https://gist.github.com/thmain/77ec4ec91fa3b0a97a6279bdbbfa955f
 *https://gist.github.com/thmain/d29f274d2769974a8950b5e0436fda92
 * We use the code from the above and add methods
 */
package cpcs324;

import javafx.util.Pair;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
public class Cpcs324_MST {
    



    //main method

    /**
     *
     * @param void
     */
    public static void main(String[] args) {
        //veruces and edges numbers
        int edgeNum=0 ,verticesNum=0;
        Scanner in = new Scanner(System.in);
        System.out.println("*** Runtime of Different Minimum Spanning Tree Algorithms ***\n\t1- Kruskal's Algorithm& Prim's Algorithm (based on Priority Queue)"
                + "\n\t2- Prim's Algorithm (based on Min Heap)& Prim's Algorithm(based on Priority Queue)");
        System.out.print("> Enter your choice (1 or 2): ");
        int choice1 = in.nextInt();
        System.out.println("**--------------------------------------------------------------------**");
         System.out.println("> choose the number of graph  vertices and edges from the above list:");
         System.out.println("\t1) vertcies number =1000 edges number =10000");
         System.out.println("\t2) vertcies number =1000 edges number =15000");
         System.out.println("\t3) vertcies number =1000 edges number =25000");
         System.out.println("\t4) vertcies number =5000 edges number =15000");
         System.out.println("\t5) vertcies number =5000 edges number =25000");
         System.out.println("\t6) vertcies number =10000 edges number =15000");
         System.out.println("\t7) vertcies number =10000 edges number =25000");
         System.out.println("\t8) vertcies number =20000 edges number =200000");
         System.out.println("\t9) vertcies number =20000 edges number =300000");
         System.out.println("\t10) vertcies number =50000 edges number =1000000");
  
        int choice2 = in.nextInt();
        //each case contain the edges and vertices numbers
        System.out.println("**--------------------------------------------------------------------**");
        switch (choice2) {
            case 1: 
                 verticesNum=1000;
                 edgeNum =10000;
                 break;
            
            
            case 2: 
                verticesNum=1000;
                 edgeNum =15000;
            
                  break;
            case 3: 
                verticesNum=1000;
                 edgeNum =25000;
            
                  break;
            case 4: 
                 verticesNum=5000;
                 edgeNum =15000;
            
                  break;
            case 5: 
                verticesNum=5000;
                 edgeNum =25000;
            
                  break;
              case 6: 
                verticesNum=10000;
                 edgeNum =15000;
            
                  break;
            case 7: 
                verticesNum=10000;
                 edgeNum =25000;
            
                  break;
            case 8: 
                 verticesNum=20000;
                 edgeNum =200000;
            
                   break;
            case 9: 
               verticesNum=20000;
                 edgeNum =300000;
            
            break;
            case 10: 
                verticesNum=50000;
                 edgeNum =1000000;
            
                  break;
            default:
                System.out.println("Invalid input!");
        }
        //create graph according to edges and vertuces numbers selected
        Graph graph = new Graph(verticesNum,  edgeNum);
        //call make graph
        graph.make_graph(graph);
        // to perform Task 2 
        if (choice1 == 1) {
            graph.kruskalMST();
            graph.primPQ();
        } 
        // to perform Task 3
        else if (choice1 == 2) {
            graph.primMH();
            graph.primPQ();
        }
        else{
            System.out.println("Invalid input!");
        }
    }
    
    
    
    
    
    
    static class Edge {
        int source;
        int destination;
        int weight;
        // constructor 
        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
        public String toString(){
            return source+"-"+destination+": "+weight;
        }
    }
    static class HeapNode {
        int vertex;
        int key;
    }
    static class ResultSet {
        int parent;
        int weight;
    }
    // all methods were added in the graph class
    static class Graph {
        int vertices;
        int edges;
        LinkedList<Edge>[] adjacencylist;
        
        // constructor 
        Graph(int vertices, int edges) {
            this.vertices = vertices;
            this.edges = edges;
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i < vertices; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }
        /**
     *
     * @param source
     * @param destnation
     * @param weight
     * @return void ,add edges to the graph
     */
        public void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencylist[source].addFirst(edge);
            edge = new Edge(destination, source, weight);
            adjacencylist[destination].addFirst(edge); //for undirected graph
            
        }
        
        
        // Prim's Algorithm using Priprity Queue
        public void primPQ() {
            //start time
            double stime = System.currentTimeMillis();
            boolean[] mst = new boolean[vertices];
            ResultSet[] resultSet = new ResultSet[vertices];
            int[] key = new int[vertices];  //keys used to store the key to know whether priority queue update is required
            //Initialize all the keys to infinity and
            //initialize resultSet for all the vertices
            for (int i = 0; i < vertices; i++) {
                key[i] = Integer.MAX_VALUE;
                resultSet[i] = new ResultSet();
            }
            //Initialize priority queue
            //override the comparator to do the sorting based keys
            PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                    //sort using key values
                    int key1 = p1.getKey();
                    int key2 = p2.getKey();
                    return key1 - key2;
                }
            });
            //create the pair for for the first index, 0 key 0 index
            key[0] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(key[0], 0);
            //add it to pq
            pq.offer(p0);
            resultSet[0] = new ResultSet();
            resultSet[0].parent = -1;
            
            
            //while priority queue is not empty
            while (!pq.isEmpty()) {
                //extract the min
                Pair<Integer, Integer> extractedPair = pq.poll();
                //extracted vertex
                int extractedVertex = extractedPair.getValue();
                mst[extractedVertex] = true;
                //iterate through all the adjacent vertices and update the keys
                LinkedList<Edge> list = adjacencylist[extractedVertex];
                for (int i = 0; i < list.size(); i++) {
                    Edge edge = list.get(i);
                    //only if edge destination is not present in mst
                    if (mst[edge.destination] == false) {
                        int destination = edge.destination;
                        int newKey = edge.weight;
                        //check if updated key < existing key, if yes, update if
                        if (key[destination] > newKey) {
                            //add it to the priority queue
                            Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                            pq.offer(p);
                            //update the resultSet for destination vertex
                            resultSet[destination].parent = extractedVertex;
                            resultSet[destination].weight = newKey;
                            //update the key[]
                            key[destination] = newKey;
                        }
                    }
                }
            }
            //finish time of the algorithm
            double ftime = System.currentTimeMillis();
            //print the total time consumed by the algorithm
            System.out.println("Total runtime of Prim's Algorithm (Usin PQ): "+(ftime-stime)+" ms.");
            //print mst
            printMST(resultSet);
        }
        // Prim's Algorithm using Minheap
        public void primMH() {
            //start time
            double stime = System.currentTimeMillis();
            boolean[] inHeap = new boolean[vertices];
            ResultSet[] resultSet = new ResultSet[vertices];
            //keys[] used to store the key to know whether min hea update is required
            int[] key = new int[vertices];
            //create heapNode for all the vertices
            HeapNode[] heapNodes = new HeapNode[vertices];
            for (int i = 0; i < vertices; i++) {
                heapNodes[i] = new HeapNode();
                heapNodes[i].vertex = i;
                heapNodes[i].key = Integer.MAX_VALUE;
                resultSet[i] = new ResultSet();
                resultSet[i].parent = -1;
                inHeap[i] = true;
                key[i] = Integer.MAX_VALUE;
            }
            //decrease the key for the first index
            heapNodes[0].key = 0;
            //add all the vertices to the MinHeap
            MinHeap minHeap = new MinHeap(vertices);
            //add all the vertices to priority queue
            for (int i = 0; i < vertices; i++) {
                minHeap.addHeapNode(heapNodes[i]);
            }
            //while minHeap is not empty
            while (!minHeap.isEmpty()) {
                //extract the min
                HeapNode extractedNode = minHeap.extractMin();
                //extracted vertex
                int extractedVertex = extractedNode.vertex;
                inHeap[extractedVertex] = false;
                //iterate through all the adjacent vertices
                LinkedList<Edge> list = adjacencylist[extractedVertex];
                for (int i = 0; i < list.size(); i++) {
                    Edge edge = list.get(i);
                    //only if edge destination is present in heap
                    if (inHeap[edge.destination]) {
                        int destination = edge.destination;
                        int newKey = edge.weight;
                        //check if updated key < existing key, if yes, update if
                        if (key[destination] > newKey) {
                            decreaseKey(minHeap, newKey, destination);
                            //update the parent node for destination
                            resultSet[destination].parent = extractedVertex;
                            resultSet[destination].weight = newKey;
                            key[destination] = newKey;
                        }
                    }
                }
            }
            //finish time of the algorithm
            double ftime = System.currentTimeMillis();
            //print the total time consumed by the algorithm
            System.out.println("Total runtime of Prim's Algorithm (Usin Min Heap): "+(ftime-stime)+" ms.");
            //print mst
            printMST(resultSet);
        }
            /**
     *
     * @param minHeap
     * @param newKey
     * @param vertex
     * @return void 
     */
        public void decreaseKey(MinHeap minHeap, int newKey, int vertex) {
            //get the index which key's needs a decrease;
            int index = minHeap.nodesIndexes[vertex];
            //get the node and update its value
            HeapNode node = minHeap.node[index];
            node.key = newKey;
            minHeap.percolateup(index);
        }
        
        
        
     /**
     *
     * @param resultset
     * @return void 
     */
        
        public void printMST(ResultSet[] resultSet) {
            int total = 0;
            for (int i = 1; i < vertices; i++) {

                total += resultSet[i].weight;
            }
              System.out.println("Total  Minimum Spanning  Tree weights = " + total);
               System.out.println("**--------------------------------------------------------------------**");
        }
        // Kruskal's Algorithm
        public void kruskalMST() {
            // start time
            double stime = System.currentTimeMillis();
            String treeV=""; // this variable is used only in tracing
        
            LinkedList<Edge>[] allEdges = adjacencylist.clone(); // modified data type from ArrayList to LinkedList
            PriorityQueue<Edge> pq = new PriorityQueue<>(edges, Comparator.comparingInt(o -> o.weight));

            //add all the edges to priority queue
            for (int i = 0; i < allEdges.length; i++) {
                for (int j = 0; j < allEdges[i].size(); j++) {
                    pq.add(allEdges[i].get(j));
                }
            }
            

            int[] parent = new int[vertices];
            //makeset
            makeSet(parent);
            LinkedList<Edge> mst = new LinkedList<>();
            //process vertices - 1 edges
            int index = 0;
            while (index < vertices - 1 && !pq.isEmpty()) {
                Edge edge = pq.remove();
                //check if adding this edge creates a cycle
                int x_set = find(parent, edge.source);
                int y_set = find(parent, edge.destination);
                if (x_set == y_set) {
                    //ignore, will create cycle
                } else {
                    //add it to our final result
                    mst.add(edge);
                    treeV+=edge.toString()+"\n";
                    index++;
                    union(parent, x_set, y_set);
                }

            }
            
            //finish time of the algorithm
            double ftime = System.currentTimeMillis();
            //print the total time consumed by the algorithm
            System.out.println("Total runtime of Kruskal's Algorithm: "+(ftime-stime)+" ms.");
            //print MST
//            System.out.println("Minimum Spanning Tree: ");
            printGraph(mst);
        }
               /**
     *
     * @param int[] parent
   
     * @return void ,create new sets with a parent pointer to its self
     */
        public void makeSet(int[] parent) {
            //Make set- creating a new element with a parent pointer to itself.
            for (int i = 0; i < vertices; i++) {
                parent[i] = i;
            }
        }
               /**
     *
     * @param parent[]
     * @param vertex
     
     * @return parent vertex
     */
        public int find(int[] parent, int vertex) {
            //chain of parent pointers from x upwards through the tree
            // until an element is reached whose parent is itself
            if (parent[vertex] != vertex) {
                return find(parent, parent[vertex]);
            };
            return vertex;
        }
        
               /**
     *
     * @param parent[]
     * @param x
     * @param y
     * @return void 
     */
        public void union(int[] parent, int x, int y) {
            int x_set_parent = find(parent, x);
            int y_set_parent = find(parent, y);
            //make x as parent of y
            parent[y_set_parent] = x_set_parent;
        }
        
               /**
     *
     * @param edge  list
     * @return void 
     */
        public void printGraph(LinkedList<Edge> edgeList) {
            int total = 0;
            for (int i = 0; i < edgeList.size(); i++) {
                Edge edge = edgeList.get(i);
               total += edge.weight;
            }
            System.out.println("Total  Minimum Spanning  Tree weights = " + total);
            System.out.println("**--------------------------------------------------------------------**");
        }
        //----------------------------------------------------------------------
               /**
     *
     
     * @param graph
     * @return void 
     */
        public void make_graph(Graph graph) {
    
         //u =source ,v=destnation ,w=weight
      	 int  u,v,w; 
        //create graph according to #of vertices
        
	//to generate randomly vertex number
        Random random=new Random();
	//add the edges according to the number
        for(int i=0 ;i< graph.edges ;i++){
            //choose random number for source vertex
            u=random.nextInt(graph.vertices);
	  //  choose random number for destnation vertex
            v=random.nextInt(graph.vertices);
	    //choose random value for edge between 1 to 20
            w=1+(int)(Math.random() * 20);
	      // to insure all node is concted 
             if (i<graph.vertices){
			// to insure no loop edge 
                        if (i== v)  
                             v++ ;
                        //add edge
                       graph.addEdge( i, v, w);
                     
                    }
              else{
			// to insure no loop edge
                        if (u==v)
                           v++ ;
                        //add edge
                        graph.addEdge( u, v, w);
                       
                    }
           
            graph.addEdge(u, v, w);

        }
         
         
            }
        
        }
        // checks if the edge is already existed

    /**
     *
     * @param src
     * @param dest
     * @param allEdges
     * @return true if connected false otherwise
     */
        public boolean isConnected(int src, int dest, LinkedList<Edge>[] allEdges) {
        for (LinkedList<Edge> i : allEdges) {
            for (Edge edge : i) {
                if ((edge.source == src && edge.destination == dest) || (edge.source == dest && edge.destination == src)) {
                    return true;
                }
            }
        }
        return false;
    }
        
    
  static class MinHeap {
    
    int size;//heap capacity
    int currentSize;//current nodes in the heap
    HeapNode[] node;
    int[] nodesIndexes; 
    public MinHeap(int size)//constructer
    {
        this.size = size;
        node = new HeapNode[size + 1];
        
        nodesIndexes = new int[size];
        //root node initaliztion
        node[0] = new HeapNode();
        node[0].key = Integer.MIN_VALUE; //
        node[0].vertex = -1; //nodeIndex
        currentSize = 0;
    }
           /**
   
     * @param x
     * @param y
     * @return void 
     */
    public void swap(int x, int y)
    {
       HeapNode temporary = node[x];
        node[x] = node[y];
        node[y] = temporary;
    }
          /**
     *

     * @return true if empty
     */
    public boolean isEmpty()
    {
        return currentSize == 0;
    }
    
           /**
    
     * @return heap size
     */
    public int heapSize()
    {
        return currentSize;
    }
    
   /**
     * @param node index
     * @return void 
     */
    public void percolateup(int newAddedNodeIdx)
    {   //get the parent node for the new added nodw 
        int parentNodeIndex = newAddedNodeIdx / 2;
    //    int newAddedNodeIdx = position;
        //while the node in the parent is grater than children
        while (newAddedNodeIdx > 0 
               && node[parentNodeIndex].key > node[newAddedNodeIdx].key)
        {
            HeapNode currentNode = node[newAddedNodeIdx];
           HeapNode parentNode = node[parentNodeIndex];
            //swap the nodeIndexcies in the nodeIndex array
            nodesIndexes[currentNode.vertex] = parentNodeIndex;
            nodesIndexes[parentNode.vertex] = newAddedNodeIdx;
            //swap the nodeIndex
            swap(newAddedNodeIdx, parentNodeIndex);
            newAddedNodeIdx = parentNodeIndex;
            parentNodeIndex = parentNodeIndex / 2;
        }
    }
    //addHeapNode new node
           /**
    
     * @param x
     * @return void 
     */
    public void addHeapNode(HeapNode x)
    {
        //increament heap size
        currentSize++;
        
        int newNodeIndex = currentSize;
        //add the new node to the node array
        node[newNodeIndex] = x;
        //add the index to  nodesIndexes array
        nodesIndexes[x.vertex] = newNodeIndex;
        percolateup(newNodeIndex);
    }
    
           /**
     
     * @return min heap node  
     */
    public HeapNode extractMin()
    {
        
        HeapNode min = node[1];
        HeapNode lastNode = node[currentSize];
        //change the index of the last node
        nodesIndexes[lastNode.vertex] = 1;
        //add the node at the nodes arry at first
        node[1] = lastNode;
        //delete the last node
        node[currentSize] = null;
        //to re alocate the root node(leaf node previously)
        sinkDown(1);
        //reduce the size
        currentSize--;
        return min;
    }
           /**
    
     * @param x
     * @return void 
     */
    //replace elements (parents) that are grater to the child node
    public void sinkDown(int x)
    {
        int parent = x;
        // left child
        int leftChild = 2 *x;
        // right child
        int rightChild = 2 *x + 1;
        //left child  is smaller than parent
        if (leftChild < heapSize() 
            && node[parent].key > node[leftChild].key)
        {
            parent = leftChild;
        }
        //right child  is smaller than parent
        if (rightChild < heapSize() 
            && node[parent].key > node[rightChild].key)
        {
           parent = rightChild;
        }
        if (parent != x)
        {
            
           HeapNode smallestNode = node[parent];
           HeapNode xNode = node[x];
            nodesIndexes[smallestNode.vertex] = x;
            nodesIndexes[xNode.vertex] = parent;
            swap(x, parent);
            //keep the procee untill the sutable location found
            sinkDown(parent);
        }
    }}
    

}
