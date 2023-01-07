import java.io.*;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.*;

public class project5 {
    static BufferedReader bufferedReader;
    static FileReader reader;
    static BufferedWriter bufferedWriter;
    static FileWriter writer;

    static class Edge {

        int from, to, flow, capacity;
        public Edge residual;

        public Edge(int from, int to,int capacity){
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }
        public Edge(int from, int to,int capacity,int flow) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = flow;
        }

        public void augmentPath(int minFlow) {
            flow += minFlow;
            residual.flow -= minFlow;
        }
        public int remainingCapacity(){
            return capacity - flow;
        }
    }
    static ArrayList<ArrayList<Edge>> adjacencyList;
    static int vertexCount;
    static int source;
    static int sink;

    static void addEdge(int from, int to, int capacity){
        Edge edge1 = new Edge(from,to,capacity);
        Edge edge2 = new Edge(to,from, 0);
        edge1.residual = edge2;
        edge2.residual = edge1;
        adjacencyList.get(from).add(edge1);
        adjacencyList.get(to).add(edge2);
    }

    static int[] visited;
    static int visitToken = 1;
    static int maxFlow = 0;


    static void dfsMinCut(int node) {
        // At sink node, return augmented path flow.
        if (node == sink) return;

        ArrayList<Edge> edges = adjacencyList.get(node);
        isReachable[node] = true;

        for (Edge edge : edges) {
            if (edge.remainingCapacity() > 0 && !isReachable[edge.to] && edge.capacity != 0) {
               dfsMinCut(edge.to);

            }
        }
        return;
    }
    static boolean[] isReachable;
    static void maxFlowMinCut() {

        // Find max flow by adding all augmenting path flows.
        for (int flow  = dfs(source, Integer.MAX_VALUE); flow != 0; flow = dfs(source, Integer.MAX_VALUE)) {
            visitToken++;
            maxFlow += flow;
        }

        isReachable = new boolean[vertexCount];
        Arrays.fill(isReachable, false);
        dfsMinCut(source);
        for(int i = 0;  i < vertexCount; i++){
            if(!isReachable[i]) continue;
            ArrayList<Edge> edgeList = adjacencyList.get(i);
            for(Edge edge : edgeList){
                if(isReachable[edge.to]) continue;
//                System.out.print(edge.remainingCapacity() + " " + edge.capacity + " " );
                if(edge.from == source)
                    System.out.println("r" + edge.to);
                else{
                    String from;
                    String to;
                    if(edge.from < 6) from = "r" + edge.from;
                    else from = "c" + (edge.from -6);
                    if(edge.to == sink) to = "KL";
                    else if(edge.to < 6) to = "r" + edge.to;
                    else to = "c" +(edge.to - 6);
                    System.out.println(from + " " + to);
                }
            }
        }

    }
    static boolean visited(int node){
        return visited[node] == visitToken;
    }
    static void visit(int node, Stack<Edge> stack){
        visited[node] = visitToken;
        for(Edge edge : adjacencyList.get(node)) {
            int remainingCapacity = edge.remainingCapacity();
            if(remainingCapacity > 0 && !visited(edge.to)){
                stack.push(edge);
            }
        }
    }
    private static int iterativeDFS(int node){
        Stack<Edge> stack = new Stack<>();
        int currentNode = node;
        visit(currentNode,stack);
        int flow = Integer.MAX_VALUE;

        while (!stack.empty()){
            currentNode = stack.pop().to;

            if (visited(currentNode)) continue;
            visit(currentNode, stack);

        }
    }
    private static int dfs(int node, int flow) {
        // At sink node, return augmented path flow.
        if (node == sink) return flow;

        ArrayList<Edge> edges = adjacencyList.get(node);
        visited[node] = visitToken;

        for (Edge edge : edges) {
            int remainingCapacity = edge.remainingCapacity();
            if (remainingCapacity > 0 && !visited(edge.to)) {
                int bottleNeck = dfs(edge.to, Math.min(flow, remainingCapacity));

                // Augment flow with bottle neck value
                if (bottleNeck > 0) {
                    edge.augmentPath(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        return 0;
    }



    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        try {
            getInput(args[0],args[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        maxFlowMinCut();
        System.out.println(maxFlow);
        long lastTime = System.currentTimeMillis();
        System.out.println(lastTime-time);
    }

    private static void getInput(String arg0, String arg1) throws IOException {
        File inputFile = new File(arg0);
        reader = new FileReader(inputFile);
        bufferedReader = new BufferedReader(reader);
        writer = new FileWriter(arg1);
        bufferedWriter = new BufferedWriter(writer);

        String buffer = "";
        int cityCount = Integer.parseInt(bufferedReader.readLine());
        vertexCount = cityCount + 8;
        source = vertexCount - 1;
        sink = vertexCount - 2;
        String[] troopCount = bufferedReader.readLine().split(" ");
        adjacencyList = new ArrayList<>(vertexCount);
        visited = new int[vertexCount];
        for(int i = 0; i < vertexCount; i++){
            adjacencyList.add(new ArrayList<>());
        }
        for(int i = 0; i < 6; i ++){
            addEdge(source, i, Integer.parseInt(troopCount[i]));
        }

        while (!Objects.equals(buffer = bufferedReader.readLine(), null)){
            String[] line = buffer.split(" ");
            for(int i = 1; i < line.length; i +=2)
            addEdge(findIndex(line[0]),
                    findIndex(line[i]),
                    Integer.parseInt(line[i+1]));
        }

    }
    static int findIndex(String str){
        if(Objects.equals(str, "KL")) return sink;
        char preFix = str.charAt(0);
        String integerPart = str.substring(1, str.length());
        int index = Integer.parseInt(integerPart);
        if(preFix == 'c'){
            return 6 + index;
        }
        return index;
    }
}
