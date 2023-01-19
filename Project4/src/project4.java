import java.io.*;
import java.util.*;

public class project4 {
    static BufferedWriter bufferedWriter;
    static int numberOfPoints;
    static int numberOfFlags;
    static ArrayList<ArrayList<int[]>> vertices;
    static int[] flagVertices;
    static int currentArrayIndex = 0;
    static int currentFlagIndex = 0;
    static HashMap<String, Integer> vertexMap;
    static HashMap<Integer,String> vertexReverseMap;

    static int start;
    static int end;
    static boolean flagNotReachable = false;
    public static void main(String[] args) {
        try {
       //     long startTime = System.currentTimeMillis();


            int[] amounts = getInput(args[0]);
             File file = new File(args[1]);
             FileWriter writer = new FileWriter(file);
             bufferedWriter = new BufferedWriter( writer);
     //       printAdjacencyLists();
            bufferedWriter.write(findShortestPath(start, end) + "\n");
            bufferedWriter.write(findMST() +"\n");

         //   long endTime = System.currentTimeMillis();
            bufferedWriter.close();
          //  System.out.println(endTime-startTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int[] getInput(String inputFileName) throws IOException {
        File inputFile = new File(inputFileName);
        FileReader rdr = new FileReader(inputFile);
        BufferedReader reader = new BufferedReader(rdr);
        numberOfPoints = Integer.parseInt(reader.readLine());
        vertices = new ArrayList<ArrayList<int[]>>(numberOfPoints);

        vertexMap = new HashMap<String, Integer>(numberOfPoints);
        vertexReverseMap = new HashMap<>(numberOfPoints);
        numberOfFlags = Integer.parseInt(reader.readLine());
        flagVertices = new int[numberOfFlags];
        String[] endPoints = reader.readLine().split(" ");

        String[] flags = reader.readLine().split(" ");
        for (String flag : flags) {
            createFlag(flag);
        }
        String str;
        while ((str = reader.readLine()) != null) {
            String[] input = str.split(" ");
            if (!vertexMap.containsKey(input[0])) {
                createVertex(input[0]);
            }

            for (int i = 1; i < input.length; i += 2) {
                if (!vertexMap.containsKey(input[i])) {
                    createVertex(input[i]);
                }
                int rootIndex = vertexMap.get(input[0]);
                int curIndex = vertexMap.get(input[i]);
                int weight = Integer.parseInt(input[i + 1]);
                vertices.get(curIndex).add(new int[]{rootIndex, weight});
                vertices.get(rootIndex).add(new int[]{curIndex, weight});

            }
        }
        start = vertexMap.get(endPoints[0]);
        end = vertexMap.get(endPoints[1]);
        return new int[]{numberOfFlags, numberOfPoints};
    }

    static void createVertex(String s) {
        vertexMap.put(s, currentArrayIndex);
        vertexReverseMap.put(currentArrayIndex, s);
        vertices.add(new ArrayList<>());
        currentArrayIndex++;
    }

    static void createFlag(String s) {
        flagVertices[currentFlagIndex] = currentArrayIndex;
        currentFlagIndex++;
        createVertex(s);
    }


    public static void printAdjacencyLists() {
        for (String key : vertexMap.keySet()) {

            System.out.print(key);
            for (int[] x : vertices.get(vertexMap.get(key))) {
                System.out.print(" " + vertexReverseMap.get(x[0]) + " " + x[1]);
            }

            System.out.println();
        }
    }


    static int findMST(){
        int[][] flagGraph = makeCompleteGraph();
        if(flagNotReachable) return -1;
        int[] cost = new int[numberOfFlags];
        Arrays.fill(cost, Integer.MAX_VALUE);
        boolean[] known = new boolean[numberOfFlags];
        Arrays.fill(known, false);

        PriorityQueue<Node> pq = new PriorityQueue<Node>(new VertexComparator());
        pq.add(new Node(0,0));
        cost[0] = 0;
        while(!pq.isEmpty()){
            Node cur = pq.remove();
            if(!known[cur.node]){
                known[cur.node] = true;
                for(int i = 0; i < numberOfFlags; i++){
                    if(known[i]) continue;
                    int dist = flagGraph[cur.node][i];
                    if (dist < cost[i]){
                        cost[i]= dist;
                        pq.add(new Node(i,cost[i]));
                    }

                }
            }
        }
        int sum = 0;
        for(int c : cost){
            sum += c;
        }
        return sum;
    }

    static int[][] makeCompleteGraph(){
        int[][] newGraph = new int[numberOfFlags][numberOfFlags];
        for(int i = 0; i < numberOfFlags; i++){
            newGraph[i] = findShortestPathFlags(i);
            if(flagNotReachable) return null;
        }
//        for(int i = 0; i <numberOfFlags; i++){
//            for(int j = 0 ; j < numberOfFlags; j++){
//                System.out.print(newGraph[i][j] + " ");
//            }
//            System.out.println();
//        }
        return newGraph;
    }


    static int findShortestPath(int v1, int v2){
        int[] d = new int[numberOfPoints];
        int[] changeAmounts = new int[numberOfPoints];
        HashSet<Integer> set = new HashSet<>();
        Arrays.fill(d, Integer.MAX_VALUE);
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new VertexComparator());

        pq.add(new Node(v1, 0));
        d[v1] = 0;
        Node cur = null;
        while (set.size() != numberOfPoints){
            if(pq.isEmpty()) return -1;
            cur = pq.remove();
            if(set.contains(cur.node))
                continue;
            set.add(cur.node);

            for(int[] adjacencyVertex : vertices.get(cur.node)){
                if(set.contains(adjacencyVertex[0])) continue;
                int dist = cur.weight + adjacencyVertex[1];
                if (dist < d[adjacencyVertex[0]]){
                    d[adjacencyVertex[0]] = dist;
                    pq.add(new Node(adjacencyVertex[0],d[adjacencyVertex[0]]));
                }
            }

        }
        return d[v2];

    }
    static int[] findShortestPathFlags(int v1){
        int[] d = new int[numberOfPoints];
        int[] changeAmounts = new int[numberOfPoints];
        HashSet<Integer> set = new HashSet<>();
        Arrays.fill(d, Integer.MAX_VALUE);
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new VertexComparator());

        pq.add(new Node(v1, 0));
        d[v1] = 0;
        Node cur = null;
        while (set.size() != numberOfPoints){
            if(pq.isEmpty()) {
                flagNotReachable = true;
                break;
            }
            cur = pq.remove();
            if(set.contains(cur.node))
                continue;
            set.add(cur.node);

            for(int[] adjacencyVertex : vertices.get(cur.node)){
                if(set.contains(adjacencyVertex[0])) continue;
                int dist = cur.weight + adjacencyVertex[1];
                if (dist < d[adjacencyVertex[0]]){
                    d[adjacencyVertex[0]] = dist;
                    pq.add(new Node(adjacencyVertex[0],d[adjacencyVertex[0]]));
                }
            }

        }
        return d;

    }
    static void findShortestPath(String v1, String v2){
        findShortestPath(vertexMap.get(v1),vertexMap.get(v2));
    }
}
