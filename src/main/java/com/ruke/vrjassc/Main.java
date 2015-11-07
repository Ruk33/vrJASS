package com.ruke.vrjassc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.util.Compile;

import de.peeeq.jmpq.JmpqEditor;

public class Main {

	protected static void displayHelp() {
		System.out.println("-result Choose which file to store the compiled source code result. Example: -result=foo.txt");
		System.out.println("-log Choose which file to store the errors. Example: -log=foo.txt");
		System.out.println("You can pass .j files (containing vrJASS code) or an entire map. Example: file1.j file2.j file3.w3x");
		
		System.exit(0);
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			Main.displayHelp();
		}
		
		Compile compile = new Compile();
		JmpqEditor editor;
		PrintWriter writer;
		File tmpFile;
		
		Collection<String> files = new ArrayList<String>();
		String toCompile = "";
		String resultPath = "compiled-vrjass.j";
		String logPath = "log-vrjass.txt";
		
		for (String arg : args) {
			if (arg.startsWith("-help")) {
				Main.displayHelp();
			} else if (arg.startsWith("-result")) {
				resultPath = arg.replace("-result=", "");
			} else if (arg.startsWith("-log")) {
				logPath = arg.replace("-log=", "");
			} else {
				files.add(arg);
			}
		}
		
		File logFile = new File(logPath);
		PrintWriter logWriter = null;
		
		try {
			logWriter = new PrintWriter(logFile);
		} catch (FileNotFoundException notFound) {
			notFound.printStackTrace();
		}
		
		for (String file : files) {
			try {
				if (file.endsWith(".j")) {
					toCompile += String.join("\n", Files.readAllLines(Paths.get(file))) + "\n";
				} else {
					editor = new JmpqEditor(new File(file));
					
					tmpFile = File.createTempFile("vrjass-temp", null);
					editor.extractFile("war3map.j", tmpFile);
					
					toCompile += String.join("\n", Files.readAllLines(tmpFile.toPath())) + "\n";
					
					editor.close();
				}
			} catch (IOException io) {
				logWriter.write(io.getMessage());
			}
		}
		
		try {
			tmpFile = new File(resultPath);
			writer = new PrintWriter(tmpFile, "UTF-8");
			
			writer.write(compile.run(toCompile));
			writer.close();
			
			if (resultPath.endsWith("w3x") || resultPath.endsWith("w3m")) {
				editor = new JmpqEditor(new File(resultPath));
				editor.injectFile(tmpFile, "war3map.j");
				editor.close();
			}
		} catch (CompileException ce) {
			logWriter.write(ce.getMessage());
		} catch (IOException e) {
			logWriter.write("Could not load blizzard.j or common.j");
		}
		
		logWriter.close();
	}

}
