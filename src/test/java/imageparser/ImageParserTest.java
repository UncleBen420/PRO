package imageparser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ImageParserTest {

	@Test
	void getPixelBufferShouldReturnTheRightArray() {
		
		ImageParser temp = new ImageParser();
		
		String expected = "84 96 78 -65 -80 -76 49 45 50 -84 -82 -88 -1 -1 -6 -95 -104 -1 -19 -1 -2 56 58 -1 -18 -6 -1 -10 -1 -1 -3 -2 -1 -21 -88 -43 -1 -8 -1 -1 -1 -8 -94 -29 -114 -4 -1 -5 -31 -91 -31 -2 -1 -13 -24 -15 -42 -5 -3 -1 -1 -2 -14 -1 -9 -1 -123 -23 -35 -1 -1 -4 -1 -1 -12 75";
		String actual = "";
		
		try {
			byte[] buffer = temp.getPixelBuffer("src/test/java/imageparser/imageTestParser.jpg");
			for(byte b : buffer) {
				System.out.print(b + " ");
				actual += b + " ";
			}
			System.out.print(buffer.length);
			actual += buffer.length;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expected, actual);

	}
	
	
	@Test
	void compareImageRatioShouldGiveACentPourCentRatio() {
		
		
		ImageParser temp = new ImageParser();
		
		int ratio = 0;
		
		try {
			
			ratio = temp.compareImageRatioOpti("src/test/java/imageparser/ImageDeTest1.jpg","src/test/java/imageparser/ImageDeTest1.jpg",100,0,100,500);
			
			System.out.println(ratio);
			System.out.println(ratio );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(100, ratio);
		
	}
	
	@Test
	void comparePixelShouldBeRight() {
		
		ImageParser temp = new ImageParser();
		
		int response = temp.comparePixelTolerance(0x00FEFEFE, 0x00FEFEFE, 100);
		System.out.println(response);
		assertEquals(1, response);
		
		response = temp.comparePixelTolerance(0x00AEAEAE, 0x00FEFEFE, 100);
		System.out.println(response);
		assertNotEquals(1, response);
		
		response = temp.comparePixelTolerance(0x00AEAEAE, 0x00FEFEFE, 60);
		System.out.println(response);
		assertEquals(1, response);
		
	}
	

}
