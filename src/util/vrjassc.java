package util;

import java.io.IOException;
import java.io.PrintWriter;

import exception.CompileException;

public class vrjassc {

	public static void main(String[] args) {
		try {
			Compile compile = new Compile();
			PrintWriter writer = new PrintWriter("output.j", "UTF-8");
			
			for (String arg : args) {
				writer.println(compile.runFromFile(arg));
			}
			
			writer.close();
		} catch (CompileException ce) {
			System.out.println(ce.getMessage());
			System.exit(2);
		} catch (IOException io) {
			System.out.println(io.getMessage());
			System.exit(1);
		}
	}

}
