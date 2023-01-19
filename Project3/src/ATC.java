import java.util.Comparator;
import java.util.PriorityQueue;

public class ATC {
    String name;
    String airportName;
    Comparator<Flight> comparator;
    PriorityQueue<Flight> priorityQueue;
    int number;
    int time = 0;
    public ATC(String accCode, String airPort, int atcNumber){
        comparator = new FlightComparator();
        priorityQueue = new PriorityQueue<>(comparator);
        number = atcNumber;
        giveName(accCode, atcNumber);
        giveAirportName(airPort, atcNumber);
    }
    void giveName(String code, int number){
        String atcNumberString = Integer.toString(number);
        name = code;
        for(int i = 0; i < 3 - atcNumberString.length(); i++) name += "0";
        name += atcNumberString;
    }
    void giveAirportName(String code, int number){
        String atcNumberString = Integer.toString(number);
        airportName = code;
        for(int i = 0; i < 3 - atcNumberString.length(); i++) airportName += "0";
        airportName += atcNumberString;
    }
}
