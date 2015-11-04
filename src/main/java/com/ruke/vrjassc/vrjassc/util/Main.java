package com.ruke.vrjassc.vrjassc.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.ruke.vrjassc.vrjassc.exception.CompileException;

import de.peeeq.jmpq.JmpqEditor;
import de.peeeq.jmpq.JmpqError;

public class Main {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("You have to pass the path to common.j, blizzard.j and the map containing vrJASS code");
			System.exit(0);

			return;
		}

		Compile compile = new Compile();
		File war3jcode = null;

		try {
			File map = new File(args[2]);
			JmpqEditor editor = new JmpqEditor(map);

			war3jcode = File.createTempFile("war3map-j", null);

			File output = File.createTempFile("output", ".vrjass");
			PrintWriter writer = new PrintWriter(output, "UTF-8");

			editor.extractFile("war3map.j", war3jcode);

			compile.setCommonPath(args[0]);
			compile.setBlizzardPath(args[1]);

			writer.println(compile.runFromFile(war3jcode.getAbsolutePath()));
			writer.close();

			editor.injectFile(output, "war3map.j");

			editor.close();
		} catch (JmpqError je) {
			System.out.println(je.getMessage());
		} catch (CompileException ce) {
			System.out.println("Compile error: " + ce.getMessage());
			System.out.println("Line: " + ce.getLine());
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

}
