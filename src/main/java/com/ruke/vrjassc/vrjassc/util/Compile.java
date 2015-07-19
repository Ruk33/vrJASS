package com.ruke.vrjassc.vrjassc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassLexer;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.VrJassScope;
import com.ruke.vrjassc.vrjassc.visitor.CompilerVisitor;
import com.ruke.vrjassc.vrjassc.visitor.SymbolVisitor;

public class Compile {

	protected String commonPath;
	protected String blizzardPath;
	protected String compiled;

	public String getCompiled() {
		return this.compiled;
	}
	
	public Compile setCommonPath(String path) {
		this.commonPath = path;
		return this;
	}

	public Compile setBlizzardPath(String path) {
		this.blizzardPath = path;
		return this;
	}

	public String run(String code) throws CompileException {
		ANTLRInputStream is = null;
		vrjassLexer lexer = null;
		TokenStream token = null;
		vrjassParser parser = null;
		
		Scope scope = new VrJassScope();
		SymbolVisitor symbolVisitor = new SymbolVisitor(scope);
		CompilerVisitor compilerVisitor = new CompilerVisitor(scope);
				
		if (this.commonPath != null && this.blizzardPath != null) {
			String blizzCode = null;

			try {
				blizzCode = String.join(
					System.lineSeparator(),
					Files.readAllLines(Paths.get(this.commonPath))
				);
				
				blizzCode += System.lineSeparator();
								
				blizzCode += String.join(
					System.lineSeparator(),
					Files.readAllLines(Paths.get(this.blizzardPath))
				);
				
				blizzCode += System.lineSeparator();
			} catch (IOException e) {
				new ErrorWindow(e.getMessage(), "", 0);
				e.printStackTrace();
			}

			is = new ANTLRInputStream(blizzCode);
			lexer = new vrjassLexer(is);
			token = new CommonTokenStream(lexer);
			parser = new vrjassParser(token);
			
			symbolVisitor.visit(parser.init());
			parser.reset();
		}

		is = new ANTLRInputStream(code.replace("\t", "    ") + System.lineSeparator());
		lexer = new vrjassLexer(is);
		token = new CommonTokenStream(lexer);
		parser = new vrjassParser(token);
		
		symbolVisitor.visit(parser.init());
		parser.reset();

		compilerVisitor.visit(parser.init());
		
		this.compiled = "";
		
		return this.compiled;
	}

	public String runFromFile(String path) throws IOException {
		return this.run(
			String.join(
				System.lineSeparator(),
				Files.readAllLines(Paths.get(path))
			)
		);
	}

}
