import java.util.Arrays;
import java.util.Objects;

public class HashTable {
    String[] atcArray;
    static final int TABLE_SIZE = 1000;
    public HashTable(){
        atcArray = new String[TABLE_SIZE];
        Arrays.fill(atcArray, "");
    }
    int hash(String airportCode){
        int hashValue = 0;
        for(int i = 0; i < airportCode.length();i++){
            hashValue += (int)airportCode.charAt(i) * (int)Math.pow(31, i);
        }
        return hashValue;

    }
    public void insert(String airportCode){
        int initialHashVal = hash(airportCode);
        int i = 0;
        while(true){
            if(!Objects.equals(atcArray[(initialHashVal + i) % TABLE_SIZE], "")) i++;
            else {
                atcArray[(initialHashVal+i) % TABLE_SIZE] = airportCode;
                break;
            }
        }
    }
    public int find(String airportCode){
        int initialHashVal = hash(airportCode);
        int i = 0;
        while(i < 1000){
            if(!Objects.equals(atcArray[(initialHashVal + i) % TABLE_SIZE], airportCode)) i++;
            else return (initialHashVal + i) % TABLE_SIZE;
        }
        return -1;
    }
}
