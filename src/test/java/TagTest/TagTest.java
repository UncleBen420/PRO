package TagTest;

import Shapes.Cercle;
import Shapes.Point;
import Shapes.Rectangle;
import Tag.CsvParser;
import Tag.Parser;
import Tag.Tag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TagTest {

	@Test
	void shouldFormatATag(){
            Tag format = new Tag();
            ArrayList<String> array = new ArrayList<String>();
            array.add("grenouille");
            array.add("2.5");
            array.add("true");
            array.add("false");
            String expected = "heigViewer;grenouille;2.5;true;false;";
            
            assertEquals(format.formatTag(array), expected);
        }
        
        @Test
        void shouldFormatACircle(){
            Tag format = new Tag();
            Cercle c = new Cercle(1.2,3.4,5);
            String expected = "circle;1.2;3.4;5.0;";
            
            assertEquals(format.formatShape(c), expected);
        }
        
        @Test
        void shouldFormatAPoint(){
            Tag format = new Tag();
            Point c = new Point(1.2,3.4);
            String expected = "point;1.2;3.4;";
            
            assertEquals(format.formatShape(c), expected);
        }
        
        @Test
        void shouldFormatARectangle(){
            Tag format = new Tag();
            Rectangle c = new Rectangle(1.2,3.4,5,5);
            String expected = "rectangle;1.2;3.4;5.0;5.0;";
            
            assertEquals(format.formatShape(c), expected);
        }
        
        @Test
        void shouldWriteAndReadImageTag(){
            Parser parser = new Parser();
            ArrayList<String> array = new ArrayList<String>();
            String str = "heigViewer;grenouille;2.5;true;false;";
            String path = "src/test/java/TagTest/1553444168110s.jpg";
            array.add(str);
            array.add(str);
            
            parser.setTags(array, path);
            try {
                ArrayList<String> test = parser.getTag(path);
                assertEquals(array,test);
            } catch (IOException ex) {
                Logger.getLogger(TagTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Test
        void shouldParseCsv(){
            ArrayList<String> array = new ArrayList<String>();
            String str = "heigViewer;grenouille;2.5;true;false;";
            array.add(str);
            array.add(str);
            
            ArrayList<String> row = new ArrayList<String>();
            row.add("heigViewer");
            row.add("grenouille");
            row.add("2.5");
            row.add("true");
            row.add("false");
            
            ArrayList<ArrayList<String>> test = new ArrayList<ArrayList<String>>();
            test.add(row);
            test.add(row);
            
            assertEquals(test, CsvParser.getTag(array));
            
            
        }

}
