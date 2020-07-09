
/**
 * Write a description of class someClass here.
 * 
 * @author Romanov Mykola
 * @version 1.0
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class WeatherParser{

    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldest = null;
        for(CSVRecord currentRow: parser){
            if (coldest == null){
                coldest = currentRow;
            }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldest.get("TemperatureF"));
                if ((currentTemp<coldestTemp)&&(currentTemp!=-9999)){
                    coldest = currentRow;
                }
            }
        }
        return coldest;
    }
    
    public String fileWithColdestTemperature(){
        DirectoryResource dr= new DirectoryResource();
        CSVRecord coldest = null;
        String coldestName = null;
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord current = coldestHourInFile(fr.getCSVParser());
            if (coldest == null){
                coldest = current;
            } else{
                double currentTemp = Double.parseDouble(current.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldest.get("TemperatureF"));
                if (currentTemp<coldestTemp){
                    coldest = current;
                    coldestName = f.getName();
                }
            }
        }
        return coldestName;
    }
    
    public void testFileWithColdestTemperature(){
        System.out.println(fileWithColdestTemperature());
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldest = coldestHourInFile(parser);
        System.out.println("Coldest temperature was "+coldest.get("TemperatureF")+" at "+coldest.get("DateUTC"));
        parser = fr.getCSVParser();
        for(CSVRecord record: parser){
            System.out.println(record.get("DateUTC")+": "+record.get("TemperatureF"));
        }
    }
    
    public CSVRecord lowestHumidityInFile (CSVParser parser){
        
        CSVRecord lowest = null;
        for(CSVRecord currentRow: parser){
            if ((lowest == null)&&(!((currentRow.get("Humidity")).equals("N/A")))){
                lowest = currentRow;
            }
            else {
                 if((!((currentRow.get("Humidity")).equals("N/A"))))   {
                    double currentTemp = Double.parseDouble(currentRow.get("Humidity"));
                    double lowestTemp = Double.parseDouble(lowest.get("Humidity"));
                    if ((currentTemp<lowestTemp)&&(!((currentRow.get("Humidity")).equals("N/A")))){
                        lowest = currentRow;
                    }
                }
            }
        }
        return lowest;
    }
    
    public CSVRecord lowestHumidityInManyFiles(){
        
        DirectoryResource dr= new DirectoryResource();
        CSVRecord lowest = null;
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord current = lowestHumidityInFile(fr.getCSVParser());
            if ((lowest == null)&&(!((current.get("Humidity")).equals("N/A")))){
                lowest = current;
            } else{
                if((!((current.get("Humidity")).equals("N/A"))))   {
                    double currentTemp = Double.parseDouble(current.get("Humidity"));
                    double lowestTemp = Double.parseDouble(lowest.get("Humidity"));
                    if ((currentTemp<lowestTemp)&&(!((current.get("Humidity")).equals("N/A")))){
                        lowest = current;               
                    }
                }
            }
        }
        return lowest;
    }
    
   public void testLowestHumidityInManyFiles(){
        CSVRecord lowest = lowestHumidityInManyFiles();
        System.out.println("Lowest humidity was "+lowest.get("Humidity")+" at "+lowest.get("DateUTC"));
    }
   
    
    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest humidity was "+csv.get("Humidity")+" at "+csv.get("DateUTC"));
        parser = fr.getCSVParser();
        for(CSVRecord record: parser){
            System.out.println(record.get("DateUTC")+": "+record.get("Humidity"));
        }
    }
    
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldest = coldestHourInFile(parser);
        System.out.println("Coldest temperature was "+coldest.get("TemperatureF")+" at "+coldest.get("DateUTC"));
    }
    
    public double averageTemperatureInFile (CSVParser parser){
        int amount =0;
        double allTemp = 0.0;
        for (CSVRecord record: parser){
            allTemp+=Double.parseDouble(record.get("TemperatureF"));
            amount++;
        }
        return allTemp/amount;
    }
    
    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.print("Average temperature in file is "+averageTemperatureInFile(parser));
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        int amount =0;
        double allTemp = 0.0;
        for (CSVRecord record: parser){
              if(!(record.get("Humidity")).equals("N/A")){
                double humidity = Double.parseDouble(record.get("Humidity"));
                if (humidity>=value){
                    allTemp+=Double.parseDouble(record.get("TemperatureF"));
                    amount++;
                }
            }
        }
        if (amount == 0) return 0.0;
        else return allTemp/amount;
    }
    
    public void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avHum = averageTemperatureWithHighHumidityInFile(parser, 80);
        if (avHum == 0.0){
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidity is "+avHum);
        }
    }
    
}
