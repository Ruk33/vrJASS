package com.ruke.vrjassc;

import com.ruke.vrjassc.autocomplete.AutoComplete;
import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.util.Compile;
import de.peeeq.jmpq.JmpqEditor;
import de.peeeq.jmpq.JmpqError;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class Main {

	protected static void displayHelp() {
		System.out.println("-help Display this help!");
		System.out.println("-result Choose which file to store the compiled source code result. Example: -result=foo.txt");
		System.out.println("-log Choose which file to store the errors. Example: -log=foo.txt");
		System.out.println("-c Write code in console, when finish press Ctrl+Z+Enter");
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
		ArrayList<String> toCompile = new ArrayList<String>();
		String resultPath = null;
		String logPath = null;
		boolean error = false;
		boolean onlySuggestions = false;
		String[] suggestionOptions = null;
		
		for (String arg : args) {
			if (arg.startsWith("-help")) {
				Main.displayHelp();
			} else if (arg.startsWith("-result")) {
				resultPath = arg.replace("-result=", "");
			} else if (arg.startsWith("-log")) {
				logPath = arg.replace("-log=", "");
			} else if (arg.startsWith("-s")) {
				onlySuggestions = true;
				suggestionOptions = arg.replace("-s=", "").split(",");
			} else if (arg.startsWith("-c")) {
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
					String s;
					while ((s=r.readLine()) != null) {
						toCompile.add(s);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				files.add(arg);
			}
		}
		
		File logFile = null;
		PrintWriter logWriter = null;
		
		if (logPath != null) {
			logFile = new File(logPath);
			
			try {
				logWriter = new PrintWriter(logFile);
			} catch (FileNotFoundException notFound) {
				notFound.printStackTrace();
			}
		}
		
		for (String file : files) {
			try {
				if (file.toLowerCase().endsWith("blizzard.j")) {
					compile.setBlizzardPath(file);
				} else if (file.toLowerCase().endsWith("common.j")) {
					compile.setCommonPath(file);
				} else if (file.endsWith(".j")) {
					toCompile.addAll(Files.readAllLines(Paths.get(file)));
				} else {
					editor = new JmpqEditor(new File(file));
					
					tmpFile = File.createTempFile("vrjass-temp", null);
					editor.extractFile("war3map.j", tmpFile);
					
					toCompile.addAll(Files.readAllLines(tmpFile.toPath()));
					
					editor.close();
				}
			} catch (Exception e) {
				error = true;
				if (logWriter != null) {
					logWriter.write(e.getMessage());
				}
			}
		}
		
		try {
			if (onlySuggestions) {
				ArrayList<String> result = new ArrayList<String>();
				String code = String.join("\n", toCompile);
				AutoComplete autocomplete = new AutoComplete(
					compile.getCompiler(),
					code,
					Integer.valueOf(suggestionOptions[0]),
					Integer.valueOf(suggestionOptions[1])
				);

				if (suggestionOptions.length == 3) {
					autocomplete.setLimit(Integer.valueOf(suggestionOptions[2]));
				}

				ArrayList<Symbol> suggestions = autocomplete.get();

				for (Symbol s : suggestions) {
					result.add(s.getName());
				}

				System.out.println(result);
			} else if (resultPath == null) {
				compile.run(String.join("\n", toCompile), false);
			} else {
				tmpFile = File.createTempFile("vrjass-compiled", null);
				writer = new PrintWriter(tmpFile, "UTF-8");
				
				writer.write(compile.run(String.join("\n", toCompile)));
				
				writer.close();
				
				if (resultPath.endsWith("w3x") || resultPath.endsWith("w3m")) {
					editor = new JmpqEditor(new File(resultPath));
					editor.injectFile(tmpFile, "war3map.j");
					editor.close();
				} else {
					Files.copy(tmpFile.toPath(), new File(resultPath).toPath());
				}
			}
		} catch (CompileException ce) {
			error = true;
			System.out.println(ce.getMessage());
			
			if (logWriter != null) {
				logWriter.write(ce.getMessage()+System.lineSeparator());
				
				for (int i = Math.max(0, ce.getLine() - 10), max = Math.min(toCompile.size(), ce.getLine() + 10); i < max; i++) {
					if (i == ce.getLine()) {
						String s = System.lineSeparator();
						for (int p = 0; p < ce.getCharPos(); p++) {
							s+= "-";
						}
						s += "^";
						logWriter.write(s);
					}
					
					logWriter.write(System.lineSeparator() + toCompile.get(i).replace("\t", "    "));
				}
			}
		} catch (JmpqError jmpqe) {
			error = true;
			if (logWriter != null) {
				logWriter.write(jmpqe.getMessage());
			}
		} catch (IOException e) {
			error = true;
			if (logWriter != null) {
				logWriter.write("Could not load blizzard.j or common.j");
			}
		} catch (Exception e) {
			error = true;
			if (logWriter != null) {
				String msg = e.getMessage();

				if (msg != null) {
					logWriter.write(msg);
				} else {
					e.printStackTrace(logWriter);
				}
			}
		}
		
		if (logWriter != null) {
			logWriter.close();
			
			try {
				if (error) {
					Desktop.getDesktop().edit(logFile);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
