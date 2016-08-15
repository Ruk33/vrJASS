package com.ruke.vrjassc;

import com.ruke.vrjassc.compiler.Compiler;
import com.ruke.vrjassc.vrjassc.exception.CompileException;
import de.peeeq.jmpq.JmpqEditor;
import de.peeeq.jmpq.JmpqError;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Main {
	
	static final int COMPILE_ERROR_STATUS_CODE = 1;
	static final int JMPQ_ERROR_STATUS_CODE = 2;
	static final int IO_ERROR_STATUS_CODE = 3;
	static final int OTHER_ERROR_STATUS_CODE = 4;

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
		
		JmpqEditor editor;
		File tmpFile;
		
		String code = "";
		HashSet<File> files = new HashSet<File>();
		
		String resultPath = null;
		String logPath = null;
		
		for (String arg : args) {
			if (arg.startsWith("-help")) {
				Main.displayHelp();
			} else if (arg.startsWith("-result")) {
				resultPath = arg.replace("-result=", "");
			} else if (arg.startsWith("-log")) {
				logPath = arg.replace("-log=", "");
			} else if (arg.startsWith("-c")) {
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
					String line;
					while ((line = r.readLine()) != null) {
						code += line;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				files.add(new File(arg));
			}
		}
		
		File logFile = null;
		PrintWriter logWriter = null;
		
		if (logPath != null) {
			logFile = new File(logPath);
			
			try {
				logWriter = new PrintWriter(logFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Compiler common = null;
		Compiler blizzard = null;
		ArrayList<Compiler> compilers = new ArrayList<Compiler>();
		
		for (File file : files) {
			try {
				if (file.getPath().toLowerCase().endsWith("common.j")) {
					common = new Compiler(file);
				} else if (file.getPath().toLowerCase().endsWith("blizzard.j")) {
					blizzard = new Compiler(file);
				} else if (file.getPath().toLowerCase().endsWith(".j")) {
					compilers.add(new Compiler(file));
				} else {
					editor = new JmpqEditor(file);
					
					tmpFile = File.createTempFile("vrjass-temp", null);
					editor.extractFile("war3map.j", tmpFile);
					
					compilers.add(new Compiler(tmpFile));
					
					editor.close();
				}
			} catch (Exception e) {
				if (logWriter != null) {
					logWriter.write(e.getMessage());
					logWriter.close();
				}
				
				System.exit(OTHER_ERROR_STATUS_CODE);
			}
		}
		
		try {
			if (common == null || blizzard == null) {
				throw new IOException();
			}
			
			common.compile(false);
			
			blizzard.injectSymbol(common.getSymbols());
			blizzard.compile(false);
			
			for (Compiler compiler : compilers) {
				compiler.injectSymbol(blizzard.getSymbols());
				compiler.compile(false);
				
				if (resultPath != null) {
					code += String.join(
						"\n",
						Files.readAllLines(compiler.getFile().toPath())
					);
				}
			}
			
			if (resultPath != null) {
				Compiler _final = new Compiler(code + "\n");
				_final.injectSymbol(blizzard.getSymbols());
				
				Compiler compiled = new Compiler(_final.compile(true));
				
				if (resultPath.endsWith("w3x") || resultPath.endsWith("w3m")) {
					editor = new JmpqEditor(new File(resultPath));
					editor.injectFile(compiled.getFile(), "war3map.j");
					editor.close();
				} else {
					Files.copy(compiled.getFile().toPath(), new File(resultPath).toPath());
				}
			}
		} catch (CompileException ce) {
			System.out.println(ce.getMessage());
			
			if (logWriter != null) {
				List<String> lines = new LinkedList<String>();
				
				try {
					lines.addAll(Files.readAllLines(ce.getFile().toPath()));
				} catch (Exception e) {
					
				}
				
				logWriter.write(ce.getMessage() + System.lineSeparator());
				
				for (int i = Math.max(0, ce.getLine() - 10), max = Math.min(lines.size(), ce.getLine() + 10); i < max; i++) {
					if (i == ce.getLine()) {
						String s = System.lineSeparator();
						for (int p = 0; p < ce.getCharPos(); p++) {
							s+= "-";
						}
						s += "^";
						logWriter.write(s);
					}
					
					logWriter.write(System.lineSeparator() + lines.get(i).replace("\t", "    "));
				}
				
				logWriter.close();
			}
			
			System.exit(COMPILE_ERROR_STATUS_CODE);
		} catch (JmpqError jmpqe) {
			if (logWriter != null) {
				logWriter.write(jmpqe.getMessage());
				logWriter.close();
			}
			
			System.exit(JMPQ_ERROR_STATUS_CODE);
		} catch (IOException e) {
			if (logWriter != null) {
				logWriter.write("Could not load blizzard.j or common.j");
				logWriter.close();
			}
			
			System.exit(IO_ERROR_STATUS_CODE);
		} catch (Exception e) {
			if (logWriter != null) {
				String msg = e.getMessage();

				if (msg != null) {
					logWriter.write(msg);
				} else {
					e.printStackTrace(logWriter);
				}
				
				logWriter.close();
			}
			
			System.exit(OTHER_ERROR_STATUS_CODE);
		}
	}

}
