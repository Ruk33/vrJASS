package com.ruke.vrjassc.vrjassc.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.ruke.vrjassc.vrjassc.exception.CompileException;

import de.peeeq.jmpq.JmpqEditor;

public class vrjassc {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("You have to pass the path of the map containing vrJASS code");
			System.exit(0);
			
			return;
		}

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

			compile.setCommonPath("./common.j");
			compile.setBlizzardPath("./blizzard.j");
			
			writer.println(compile.runFromFile(defaultCode.getAbsolutePath()));
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
