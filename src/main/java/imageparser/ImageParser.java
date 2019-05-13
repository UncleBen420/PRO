package imageparser;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageParser {
	
	public int getPixel(String path,int x, int y) throws IOException{

	  File file = new File(path);
	  BufferedImage image = ImageIO.read(file);
	  // Getting pixel color by position x and y 
	  int clr=  image.getRGB(x,y); 
	  int  red   = (clr & 0x00ff0000) >> 16;
	  int  green = (clr & 0x0000ff00) >> 8;
	  int  blue  =  clr & 0x000000ff;
	  
	  System.out.println("Red Color value = "+ red);
	  System.out.println("Green Color value = "+ green);
	  System.out.println("Blue Color value = "+ blue);
	  
	  return clr;
	  
	}
	
	public byte[] getPixelBuffer(String path) throws IOException{
		
		File file = new File(path);
		BufferedImage image = ImageIO.read(file);		
		return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		
	}
	
	/*static int compareImageRatio(String path1, String path2) throws Exception {
		
		byte[] bufImage1 = ImageParser.getPixelBuffer(path1);
		byte[] bufImage2 = ImageParser.getPixelBuffer(path2);
		
		if(bufImage1.length != bufImage2.length) {
			throw new Exception("The images are not the same size");
		}
		
		double ratio = 100;
		int valUnsigned1, valUnsigned2, min, max;
		

		for(int i = 0; i < bufImage1.length; i++) {
			
			
			
			valUnsigned1 = Byte.toUnsignedInt(bufImage1[i]);
			valUnsigned2 = Byte.toUnsignedInt(bufImage2[i]);
			
			//System.out.println("val1: " + valUnsigned1 + " val2: "+ valUnsigned2);
			
			min = Math.min(valUnsigned1, valUnsigned2);
			max = Math.max(valUnsigned1, valUnsigned2);
			
			if(max != 0) {
			
				ratio = (((min / max) * 100) + ratio) / 2;
			
			}
		}
		
		return (int)ratio;
		
	}*/
	
	
public int compareImageRatioOpti(String path1, String path2, int hCut, int wCut, int correctness, int precision) throws Exception {
		
	File file = new File(path1);
	BufferedImage image1 = ImageIO.read(file);
	file = new File(path2);
	BufferedImage image2 = ImageIO.read(file);
	
	if(image1.getWidth() != image2.getTileWidth() || image1.getHeight() != image2.getHeight()) {
		throw new Exception("The images are not the same size");
	}
	
	double ratio = 0;
	
	System.out.println(image1.getHeight() + " " + image1.getWidth());
	
	int incrementI = image1.getHeight() / precision;
	int incrementJ = image1.getWidth() / precision;
	int counter = 0;	

	for(int i = 0; i < image1.getHeight() - hCut; i += incrementI) {
		for(int j = 0; j < image1.getWidth()  - wCut; j += incrementJ) {
		
			counter++;

			ratio += comparePixelTolerance(image1.getRGB(j, i), image2.getRGB(j, i),correctness);
		}
	}
	ratio = ratio / counter * 100;
	
	return (int)ratio;
		
	}


public int comparePixelTolerance(int pixel1, int pixel2, int correctness) {
	
	int match = 1;
	
	for(int i = 0; i <= 16; i += 8) {
		
		int  color1  =  pixel1 & 0x000000ff;
		int  color2  =  pixel2 & 0x000000ff;
		
		double min = Math.min(color1, color2);
		double max = Math.max(color1, color2);
		
		int percent = (int)(min / max * 100);
		
		if(percent < correctness) {
			match = 0;
		}
		
	}
	
	return match;

}


}
