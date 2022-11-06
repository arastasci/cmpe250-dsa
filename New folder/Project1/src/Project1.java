import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.util.Scanner; // Import the Scanner class to read text files

public class Project1 {


    public static void main(String[] args) {
        FactoryImpl factory = new FactoryImpl();
        try {
            File input = new File(args[0]);
            FileWriter fileWriter = new FileWriter(args[1]);
            Scanner inputReader = new Scanner(input);
            while (inputReader.hasNextLine()) {
                String data = inputReader.nextLine();
                String output = process(factory, data.split(" "));
                if(!output.isEmpty()){
                    fileWriter.write(output + "\n");
                }
            }
            inputReader.close();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String process(FactoryImpl factory, String[] strings){
        switch (strings[0]){
            case "AF":
                factory.addFirst(new Product(Integer.parseInt(strings[1]),Integer.parseInt(strings[2])));
                break;
            case "AL":
                factory.addLast(new Product(Integer.parseInt(strings[1]),Integer.parseInt(strings[2])));
                break;
            case "A":
                try{
                    factory.add(Integer.parseInt(strings[1]),new Product(Integer.parseInt(strings[2]),Integer.parseInt(strings[3])));
                }
                catch (Exception e){
                    return "Index out of bounds.";
                }
                break;
            case "RF":
                try{
                    return factory.removeFirst().toString();
                }catch (Exception e){
                    return "Factory is empty.";
                }

            case "RL":
                try{
                    return factory.removeLast().toString();
                }catch (Exception e){
                    return "Factory is empty.";
                }

            case "RI":
                try{
                    return factory.removeIndex(Integer.parseInt(strings[1])).toString();
                }catch (Exception e){
                    return "Index out of bounds.";
                }

            case "RP":
                try{
                   return factory.removeProduct(Integer.parseInt(strings[1])).toString();
                }catch (Exception e){
                    return "Product not found.";
                }

            case "F":
                try{
                    return factory.find(Integer.parseInt(strings[1])).toString();
                }catch(Exception e){
                    return "Product not found.";
                }

            case "G":
                try{
                    return factory.get(Integer.parseInt(strings[1])).toString();
                }catch (Exception e){
                    return "Index out of bounds.";
                }

            case "U":
                try{
                    return factory.update(Integer.parseInt(strings[1]), Integer.parseInt(strings[2])).toString();
                }catch(Exception e){
                    return "Product not found.";
                }

            case "FD":
                return Integer.toString(factory.filterDuplicates());

            case "R":
                factory.reverse();
                return factory.toString();

            case "P":
                return factory.toString();

        }
        return "";
    }
}