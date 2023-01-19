import java.io.*;

import java.util.*;

public class project5 {
    static BufferedReader bufferedReader;
    static FileReader reader;
    static BufferedWriter bufferedWriter;
    static FileWriter writer;

    static class Edge {

        int from, to, flow, capacity;
        public Edge residualCP;

        public Edge(int from, int to,int capacity){
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }
        public void augmentPath(int minFlow) {
            flow += minFlow;
            residualCP.flow -= minFlow;
        }
        public int availableFlow(){
            return capacity - flow;
        }
    }
    static void addEdge(int from, int to, int capacity){
        Edge edge1 = new Edge(from,to,capacity);
        Edge edge2 = new Edge(to,from, 0);
        edge1.residualCP = edge2;
        edge2.residualCP = edge1;
        adjacencyList.get(from).add(edge1);
        adjacencyList.get(to).add(edge2);
    }


    static ArrayList<ArrayList<Edge>> adjacencyList;
    static int vertexCount;
    static int source;

    static int sink;

    static boolean[] isReachable;
    static int[] visited;
    static int countVisit = 1;
    static int maxFlow = 0;


    static void dfsMinCut(int node) {
        isReachable[node] = true;
        for (Edge edge : adjacencyList.get(node)) {
            if (edge.availableFlow() > 0 && !isReachable[edge.to]) {
      //          System.out.println(vocalize(edge.from, edge.to)
      //          + " " + edge.capacity);
               dfsMinCut(edge.to);
            }
        }
    }
    static void maxFlowMinCut() throws IOException {

        int flowMax;
        do{
            flowMax  = dfs(source, Integer.MAX_VALUE);
            countVisit++;
            maxFlow += flowMax;
        }while (flowMax != 0);


        bufferedWriter.write(maxFlow + "\n");
        isReachable = new boolean[vertexCount];
        Arrays.fill(isReachable, false);
        dfsMinCut(source);
        for(int i = 0;  i < vertexCount; i++){
            if(!isReachable[i]) continue;
            ArrayList<Edge> edgeList = adjacencyList.get(i);
            for(Edge edge : edgeList){
                if(isReachable[edge.to] || edge.capacity == 0) continue;
                bufferedWriter.write(vocalize(edge.from, edge.to) + "\n");
            }
        }
    }

    static String vocalize(int fromI, int toI){
        String from;
        String to;
        if(fromI == source)
            return ("r" + toI);
        else{
            if(fromI < 6) from = "r" + fromI;
            else from = "c" + (fromI -6);
            if(toI == sink) to = "KL";
            else if(toI < 6) to = "r" + toI;
            else to = "c" +(toI - 6);
        }
        return from + " " + to;
    }
    static boolean notVisited(int node){
        return visited[node] != countVisit;
    }
    static void visit(int node, Stack<Edge> stack){
        visited[node] = countVisit;
        for(Edge edge : adjacencyList.get(node)) {
            int availableFlow = edge.availableFlow();
            if(availableFlow > 0 && notVisited(edge.to)){
                stack.push(edge);
            }
        }
    }
    private static int dfs(int node, int flow) {
        if (node == sink) return flow;

        visited[node] = countVisit;

        for (Edge edge : adjacencyList.get(node)) {
            int availableFlow = edge.availableFlow();
            if (availableFlow > 0 && notVisited(edge.to)) {
                int minFlow = dfs(edge.to, Math.min(flow, availableFlow));
                if (minFlow > 0) {
                    edge.augmentPath(minFlow);
                    return minFlow;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
      //  long time = System.currentTimeMillis();
        try {
            getInput(args[0],args[1]);
            maxFlowMinCut();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    //    System.out.println(maxFlow);
     //   long lastTime = System.currentTimeMillis();
    //    System.out.println(lastTime-time);
    }

    private static void getInput(String arg0, String arg1) throws IOException {
        File inputFile = new File(arg0);
        reader = new FileReader(inputFile);
        bufferedReader = new BufferedReader(reader);
        File outputFile = new File(arg1);
        writer = new FileWriter(outputFile);
        bufferedWriter = new BufferedWriter(writer);

        String buffer;
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
