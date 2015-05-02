package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import de.peeeq.jmpq.JmpqEditor;
import exception.CompileException;

public class vrjassc {

	public static void main(String[] args) {
		try {
			Compile compile = new Compile();
			
			File map = new File(args[0]);
			JmpqEditor editor = new JmpqEditor(map);
			
			File code = File.createTempFile("war3map", ".wct");
			File defaultCode = File.createTempFile("war3map", ".j");
			File output = File.createTempFile("output", ".vrjass");
			PrintWriter writer = new PrintWriter(output, "UTF-8");
			
			editor.extractFile("war3map.wct", code);
			editor.extractFile("war3map.j", defaultCode);
			
			writer.println(compile.runFromFile(code.getAbsolutePath()));
			writer.close();
			
			editor.injectFile(output, "war3map.j");
			editor.close();			
		} catch (CompileException ce) {
			System.out.println(ce.getMessage());
			System.exit(2);
		} catch (IOException io) {
			System.out.println(io.getMessage());
			System.exit(1);
		}
		
		System.exit(0);
	}

}
