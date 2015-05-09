package com.ruke.vrjassc.vrjassc.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import com.ruke.vrjassc.vrjassc.exception.CompileException;

import de.peeeq.jmpq.JmpqEditor;
import de.peeeq.jmpq.JmpqError;

public class vrjassc {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("You have to pass the path of the map containing vrJASS code");
			System.exit(0);

			return;
		}

		File war3jcode = null;

		try {
			Compile compile = new Compile();

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
			new ErrorWindow(je.getMessage(), "", 0);
		} catch (CompileException ce) {
			try {
				new ErrorWindow(
					ce.getMessage(),
					String.join(
						System.lineSeparator(),
						Files.readAllLines(war3jcode.toPath())
					),
					ce.getLine()
				);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//System.exit(2);
		} catch (IOException io) {
			new ErrorWindow(io.getMessage(), "", 0);
			//System.exit(1);
		}

		//System.exit(0);
	}

}
