import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Project3 {

    static BufferedWriter bufferedWriter;

    public static void main(String[] args) {
        try {
            getInput(args[0]);
            File file = new File(args[1]);
            FileWriter writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter( writer);
            simulate();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    static void simulate() throws IOException {

        for(ACC acc : ACC.accMap.values()){
            String output = "";
            int time = acc.simulate();
            output += (acc.accCode + " " + time);
            ArrayList<ATC> list = new ArrayList<>(acc.atcHashMap.values());
            list.sort(Comparator.comparingInt(a -> a.number));
            for(ATC atc : list){
                output += (" " + atc.airportName);
            }
            bufferedWriter.write(output + "\n");
        }

    }
    static void getInput(String inputFileName) throws FileNotFoundException {
        File inputFile = new File(inputFileName);
        Scanner reader = new Scanner(inputFile);
        String firstLine = reader.nextLine();
        String[] firstLineArr = firstLine.split(" ");

        int accCount = Integer.parseInt(firstLineArr[0]);
        int flightCount = Integer.parseInt(firstLineArr[1]);
        for(int i = 0; i < accCount; i++){
            String[] accInfos = reader.nextLine().split(" ");
//            System.out.println(accInfos[0]);
            ACC acc = new ACC(accInfos[0]);
            for(int j = 1; j < accInfos.length; j++){
                acc.insertAirport(accInfos[j]);
            }
        }
        for(int i = 0; i < flightCount; i++){
            String[] flightInfos = reader.nextLine().split(" ");
            Flight flight = new Flight(flightInfos[0],flightInfos[1],
                    flightInfos[2],flightInfos[3], flightInfos[4]);
            for(int j = 5; j < flightInfos.length; j++){
                flight.setOperation(Integer.parseInt(flightInfos[j]), j-5);
            }

        }
    }
}
