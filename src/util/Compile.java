package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.TokenStream;

import exception.CompileException;
import visitor.MainVisitor;
import antlr4.vrjassLexer;
import antlr4.vrjassParser;

public class Compile {
	
	public String run(String code) throws CompileException {
		TextMacro textMacro = new TextMacro(code);
		code = textMacro.getOutput().replace("\t", "    ");
		
		ANTLRInputStream is = new ANTLRInputStream(code);
		
		vrjassLexer lexer = new vrjassLexer(is);
		TokenStream token = new CommonTokenStream(lexer);
		vrjassParser parser = new vrjassParser(token);
		
		return new MainVisitor(parser).getOutput();
	}
	
	public String runFromFile(String pathname) throws CompileException, IOException {
		File file = new File(pathname);
		Reader reader = new FileReader(file);
		BufferedReader buf = new BufferedReader(reader);
		Stack<String> lines = new Stack<String>();
		
		while (lines.push(buf.readLine()) != null) {
		}
		
		lines.pop(); //last line is null
		
		reader.close();
		buf.close();
		
		return this.run(String.join(System.lineSeparator(), lines));
	}

}
