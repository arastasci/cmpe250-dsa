import java.util.Comparator;

public class FlightComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight x, Flight y) {
        if (x.admissionTime < y.admissionTime )
            return -1;

        if (x.admissionTime > y.admissionTime)
            return 1;

        if((!x.isOldFlight  && y.isOldFlight)) return -1;

        if (x.isOldFlight && !y.isOldFlight) return 1;
        return x.flightCode.compareTo(y.flightCode);
    }
}
