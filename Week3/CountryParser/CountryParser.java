
/**
 * Write a description of class CSV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;

public class CountryParser{

    public String countryInfo(CSVParser parser, String country){
        
        for(CSVRecord record: parser){
            String currCountry = record.get("Country");
            if (currCountry.equals(country)){
                return record.get("Country")+": "+record.get("Exports")+": "+record.get("Value (dollars)");
            }
        }
        return "NOT FOUND";
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        for (CSVRecord record: parser){
            String export = record.get("Exports");
            if (export.contains(exportItem1)&& export.contains(exportItem2))
            System.out.println(record.get("Country"));
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem){
        int count =0;
        for(CSVRecord record: parser){
            String export = record.get("Exports");
            if (export.contains(exportItem)) count++;
        }
        return count;
    }
    
    public void bigExporters(CSVParser parser, String amount){
        for (CSVRecord record: parser){
            String value = record.get("Value (dollars)");
            if (value.length()>amount.length())
            System.out.println(record.get("Country")+": "+value);
        }
    }
    
    public void tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        //System.out.print(countryInfo(parser, "Nauru"));
        //listExportersTwoProducts(parser, "cotton", "flowers");
        //System.out.println(numberOfExporters(parser, "cocoa"));
        bigExporters(parser, "$999,999,999,999");
    }
    
    
}
