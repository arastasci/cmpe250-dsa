import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;
import java.util.Set;
enum FlightState{
    ACC,
    ATC_DEPARTURE,
    ATC_ARRIVAL,
    WAIT
}
public class Flight {
    public static HashSet<Integer> waitingSet = new HashSet<Integer>(){{
        add(1);
        add(4);
        add(6);
        add(8);
        add(11);
        add(14);
        add(16);
        add(18);
    }};
    public static HashSet<Integer> accSet = new HashSet<Integer>(){{
        add(0);
        add(1);
        add(2);
        add(10);
        add(11);
        add(12);
        add(20);
    }};
    public FlightState flightState;

    public int admissionTime;
    public int accTime;
    public int atcTime;
    public String arrivalAirport;
    public String departureAirport;
    private int[] operations = new int[21];
    public int currentOperation = 0;
    public boolean isOldFlight = false;
    String flightCode;
        public int[] getOperations() {
            return operations;
        }

        public int getCurrentOperationTime(){
           return operations[currentOperation];
        }
        public void nextOperation(){

            currentOperation++;
            setFlightState();


        }
        public void setOperation(int operation, int index) {
            operations[index] = operation;
        }
        public void decrementTime(int time){
            operations[currentOperation] -= time;
        }

        public void setOperations(int[] operations){
            this.operations = operations;
        }

        public Flight(String admissionTime, String flightCode, String accCode,
                      String departureAirport, String arrivalAirport){
            this.flightCode = flightCode;
            this.admissionTime = Integer.parseInt(admissionTime);
            this.departureAirport = departureAirport;
            this.arrivalAirport = arrivalAirport;
            ACC.accMap.get(accCode).insertFlight(this);
            flightState = FlightState.ACC;
        }

        void setFlightState(){
            if(waitingSet.contains(currentOperation)) flightState = FlightState.WAIT;
            else if(currentOperation <=2 ||
                    (currentOperation >= 10 && currentOperation <=12) || currentOperation == 20) flightState = FlightState.ACC;
            else if( currentOperation < 10) flightState = FlightState.ATC_DEPARTURE;
            else flightState = FlightState.ATC_ARRIVAL;
        }

}
