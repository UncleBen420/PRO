package exceptionHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * Classe qui gère les exceptions
 * @author Groupe PRO B-9
 */
public class LogFileWritingHandler {

	public static void handleException(String m, StackTraceElement[] s) {

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(new File("ErrorLog.txt")), StandardCharsets.UTF_16));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pw.println(m);
		System.out.println(m);
		
		for(StackTraceElement ste : s) {
			pw.println(ste.toString());
			System.out.println(ste.toString());
		}
		

		pw.close();
		System.exit(1);
	}

}
