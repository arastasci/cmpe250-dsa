import java.lang.reflect.Array;
import java.util.*;

public class ACC {
    final int TIME_SLICE = 30;
    public static HashMap<String, ACC> accMap = new HashMap<String, ACC>();
    public HashMap<String, ATC> atcHashMap;
    Comparator<Flight> comparator = new FlightComparator();
    PriorityQueue<Flight> priorityQueue = new PriorityQueue<>(comparator);
    LinkedList<Flight> flightList = new LinkedList<>();
    String accCode;
    HashTable airportTable;
    Flight activeFlight;
    int time;
    boolean isActiveFlightOnACC;
    public ACC(String accCode){
        time = 0;
        this.accCode = accCode;
        airportTable = new HashTable();
        accMap.put(accCode, this);
        atcHashMap = new HashMap<String, ATC>();
    }
    public void insertAirport(String airPortCode){
        airportTable.insert(airPortCode);
        int place = airportTable.find(airPortCode);
        ATC atc = new ATC(accCode, airPortCode, place);
        atcHashMap.put(airPortCode, atc);
    }
    public void insertFlight(Flight flight){
        flightList.add(flight);
        priorityQueue.add(flight);
    }


    public int simulate(){
        int lastTime = 0;
        while (!priorityQueue.isEmpty()){
            Flight currentFlight = priorityQueue.remove();
            int timeElapsed;
            switch(currentFlight.flightState){
                case ACC:
                    timeElapsed = Math.min(currentFlight.getCurrentOperationTime(), TIME_SLICE);
                    currentFlight.decrementTime(timeElapsed);
                    if(currentFlight.admissionTime >= time){
                        currentFlight.admissionTime += timeElapsed;
                        time = currentFlight.admissionTime;
                    }
                    else{
                        time += timeElapsed;
                        currentFlight.admissionTime = time;
                    }
                    if(currentFlight.getCurrentOperationTime() == 0){
                        currentFlight.nextOperation();
                        currentFlight.isOldFlight = false;
                    }
                    else{
                        currentFlight.isOldFlight = true;
                    }
                    if(currentFlight.currentOperation == 21)
                        lastTime = time;
                    else priorityQueue.add(currentFlight);
                    break;
                case ATC_ARRIVAL:
                     timeElapsed = currentFlight.getCurrentOperationTime();
                    if(currentFlight.admissionTime >= atcHashMap.get(currentFlight.arrivalAirport).time){
                        currentFlight.admissionTime += timeElapsed;
                        atcHashMap.get(currentFlight.arrivalAirport).time = currentFlight.admissionTime;
                    }
                    else {
                        atcHashMap.get(currentFlight.arrivalAirport).time += timeElapsed;
                        currentFlight.admissionTime = atcHashMap.get(currentFlight.arrivalAirport).time;
                    }
                    currentFlight.nextOperation();

                    priorityQueue.add(currentFlight);
                    break;

                case ATC_DEPARTURE:
                    timeElapsed = currentFlight.getCurrentOperationTime();
                    if(currentFlight.admissionTime >= atcHashMap.get(currentFlight.departureAirport).time){
                        currentFlight.admissionTime += timeElapsed;
                        atcHashMap.get(currentFlight.departureAirport).time = currentFlight.admissionTime;
                    }
                    else {
                        atcHashMap.get(currentFlight.departureAirport).time += timeElapsed;
                        currentFlight.admissionTime = atcHashMap.get(currentFlight.departureAirport).time;
                    }
                    currentFlight.nextOperation();

                    priorityQueue.add(currentFlight);
                    break;

                case WAIT:
                    currentFlight.admissionTime += currentFlight.getCurrentOperationTime();
                    currentFlight.decrementTime(currentFlight.getCurrentOperationTime());
                    currentFlight.nextOperation();

                    priorityQueue.add(currentFlight);
                    break;

            }
        }
        return lastTime;
    }


}
