
/**
 * Write a description of class BabyNames here.
 * 
 * @author Romanov Mykola 
 * @version 1.0
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.lang.*;


public class BabyNames {

    public void printNames(){
        FileResource fr= new FileResource();
        for (CSVRecord rec: fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <=100){
                System.out.println("Name: "+rec.get(0)+" Gender: "+
                rec.get(1)+" Num Born"+rec.get(2));
            }
        }
    } 
    
    
    public void totalBirths(FileResource fr){
        int totalBirths =0;
        int totalBoys = 0;
        int totalGirls = 0;
        int girlsNames =0;
        int boysNames =0;
        for (CSVRecord rec: fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths+=numBorn;
            if (rec.get(1).equals("M")){
                
                totalBoys+=numBorn;
                boysNames++;
            } else{
                girlsNames++;
                totalGirls += numBorn;
            }
        }
        System.out.println("Total births = "+totalBirths);
        System.out.println("Total girls = "+totalGirls);
        System.out.println("Total girls' names = "+girlsNames);
        System.out.println("Total boys = "+totalBoys);
        System.out.println("Total boys' names = "+boysNames);
    }
    
    public void testTotalBirths(){
        int year = 1905;
        FileResource fr= new FileResource("us_babynames/us_babynames_by_year/yob"+Integer.toString(year)+".csv");
        totalBirths(fr);
    }
    
    public int getRank(int year, String name, String gender){
        FileResource fr= new FileResource("us_babynames/us_babynames_by_year/yob"+Integer.toString(year)+".csv");
        int rank = 0;
        for (CSVRecord record : fr.getCSVParser(false)){
            
            if (record.get(1).equals(gender)){
                rank++;
                if (record.get(0).equals(name))
                return rank;
            }
        }
        return -1;
    }
    
    public void testGetRank(){
        int year = 1880;
        String name = "Mich";
        String gender ="M";
        int check = getRank(year, name, gender);
        if (check!=-1){
            System.out.println(name+" rank is "+check);
        }
        else
        {
            System.out.println(name+" does not appear in that file");
        }
    }
    
    public String getName(int year, int rank, String gender){
        int count = 0;
        FileResource fr= new FileResource("us_babynames/us_babynames_by_year/yob"+Integer.toString(year)+".csv");
        for (CSVRecord record : fr.getCSVParser(false)){
            if (record.get(1).equals(gender)){
                count++;
                if (count == rank)
                return record.get(0);
            }
        }
        return "NO NAME";
    }
    
    public void testGetName(){
        int year = 1980;
        int rank = 350;
        String gender ="F";
        System.out.println(getName(year, rank,gender));
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        FileResource first= new FileResource("us_babynames/us_babynames_by_year/yob"+Integer.toString(year)+".csv");
        FileResource second= new FileResource("us_babynames/us_babynames_by_year/yob"+Integer.toString(newYear)+".csv");
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        System.out.println(name+" born in "+Integer.toString(year)+
        " would be "+newName+" if she was born in "+Integer.toString(newYear));
    }
    
    public void testWhatIsNameYear(){
        int year = 1974;
        int newYear = 2014;
        String name = "Owen";
        String gender ="M";
        whatIsNameInYear(name, year, newYear, gender);
    }
    
    public int yearOfHighestRank(String name, String gender){
        DirectoryResource dr  = new DirectoryResource();
        int highestRank = 0;
        int resultYear = 0;
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            String fileName = f.getName();
            int year = Integer.parseInt(fileName.substring(3, 7));
            int currentRank = getRank(year, name, gender);
            if (currentRank == -1) continue;
            
            if ((currentRank<highestRank) || (highestRank == 0)){
                    highestRank = currentRank;
                    resultYear = year;
            }
            
        }
        int result = resultYear == 0 ? (-1) : (resultYear);
        return result;
    }
    
    public void testYearOfHighestRank(){
        String name = "Mich";
        String gender ="M";
        System.out.println(yearOfHighestRank(name,gender));
    }
    
    public double getAverageRank(String name, String gender){
        DirectoryResource dr  = new DirectoryResource();
        double sumRank = 0.0;
        int countYear = 0;
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            String fileName = f.getName();
            int year = Integer.parseInt(fileName.substring(3, 7));
            int currentRank = getRank(year, name, gender);
            if (currentRank != -1){
                sumRank+=currentRank;
                countYear++;
            }
            
        }
        double result = sumRank == 0.0 ? (-1.0) : (sumRank/countYear);
        return result;
    }
    
    public void testGetAverageRank(){
        String name = "Robert";
        String gender ="M";
        System.out.println(getAverageRank(name, gender));
    }
    
    public int getTotalBirthsRankedHigher (int year, String name, String gender){
        FileResource fr= new FileResource("us_babynames/us_babynames_by_year/yob"+Integer.toString(year)+".csv");
        int totalBirth = 0;
        for (CSVRecord record : fr.getCSVParser(false)){
            if (record.get(1).equals(gender)){
                if (record.get(0).equals(name)){
                   return totalBirth;
                }
                totalBirth+=Integer.parseInt(record.get(2));
            }
        }
        return totalBirth;
    }
    
    public void testGetTotalBirthsRankedHigher(){
        String name = "Emily";
        String gender ="F";
        int year = 1990;
        System.out.println(getTotalBirthsRankedHigher(year, name, gender));
    }
}
