package com.ruke.vrjassc.vrjassc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassLexer;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.phase.ReferencePhase;
import com.ruke.vrjassc.vrjassc.phase.DefinitionPhase;
import com.ruke.vrjassc.vrjassc.phase.TranslationPhase;
import com.ruke.vrjassc.vrjassc.symbol.Scope;
import com.ruke.vrjassc.vrjassc.symbol.VrJassScope;

public class CompilerFacade {
	
	protected vrjassParser getParser(ANTLRInputStream is) {
		vrjassLexer lexer = new vrjassLexer(is);
		TokenStream token = new CommonTokenStream(lexer);
		
		return new vrjassParser(token);
	}
	
	protected String getCommonJ() throws IOException {
		return String.join("\n", Files.readAllLines(Paths.get("./resources/common.j")));
	}
	
	protected String getBlizzardJ() throws IOException {
		return String.join("\n", Files.readAllLines(Paths.get("./resources/blizzard.j")));
	}
	
	protected Scope getNatives() throws IOException {
		String natives = this.getCommonJ() + "\n" + this.getBlizzardJ();
		vrjassParser parser = this.getParser(new ANTLRInputStream(natives));
		
		TokenSymbolBag symbols = new TokenSymbolBag();
		Scope scope = new VrJassScope();
		
		DefinitionPhase defPhase = new DefinitionPhase(symbols, scope);
		defPhase.visit(parser.init());
		
		return scope;
	}
	
	public String compile(ANTLRInputStream is) throws IOException {
		vrjassParser parser = this.getParser(is);
		
		TokenSymbolBag symbols = new TokenSymbolBag();
		Scope scope = this.getNatives();
		
		DefinitionPhase defPhase = new DefinitionPhase(symbols, scope);
		ReferencePhase refPhase = new ReferencePhase(symbols, scope);
		TranslationPhase tranPhase = new TranslationPhase(symbols);
		
		defPhase.visit(parser.init());
		parser.reset();

		refPhase.visit(parser.init());
		parser.reset();
		
		return tranPhase.visit(parser.init()).translate();
	}
	
}
