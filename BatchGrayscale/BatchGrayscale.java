
/**
 * Write a description of class BatchGrayscale here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.io.*;

public class BatchGrayscale {
    
    public ImageResource makeGray(ImageResource inImage){
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel: outImage.pixels()){
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            int average = (inPixel.getRed()+inPixel.getBlue()+inPixel.getGreen())/3;
            pixel.setRed(average);
            pixel.setBlue(average);
            pixel.setGreen(average);
        }
        return outImage;
    }
    
    public ImageResource makeInversion(ImageResource inImage){
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel: outImage.pixels()){
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            int red = 255 - inPixel.getRed();
            int blue = 255 - inPixel.getBlue();
            int green = 255 - inPixel.getGreen();
            pixel.setRed(red);
            pixel.setBlue(blue);
            pixel.setGreen(green);
        }
        return outImage;
    }
    
    public void testGray(){
        ImageResource ir = new ImageResource();
        ImageResource gray = makeGray(ir);
        gray.draw();
    }
    
    public void testInversion(){
        ImageResource ir = new ImageResource();
        ImageResource inversion = makeInversion(ir);
        inversion.draw();
    }
    
    public void selectAndConvert(){
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            ImageResource inImage = new ImageResource(f);
            ImageResource gray = makeGray(inImage);
            String fname = inImage.getFileName();
            String newName = "gray-"+fname;
            gray.setFileName(newName);
            gray.draw();
            gray.save();           
        }
    }
    
    public void makeInversion(){
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            ImageResource inImage = new ImageResource(f);
            ImageResource inversion = makeInversion(inImage);
            String fname = inImage.getFileName();
            String newName = "inverted-"+fname;
            inversion.setFileName(newName);
            inversion.draw();
            inversion.save();           
        }
    }   
}
