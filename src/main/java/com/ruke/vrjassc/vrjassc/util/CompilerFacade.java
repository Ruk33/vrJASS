package com.ruke.vrjassc.vrjassc.util;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassLexer;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.exception.SyntaxErrorException;
import com.ruke.vrjassc.vrjassc.phase.DefinitionPhase;
import com.ruke.vrjassc.vrjassc.phase.PreprocessorPhase;
import com.ruke.vrjassc.vrjassc.phase.ReferencePhase;
import com.ruke.vrjassc.vrjassc.phase.TranslationPhase;
import com.ruke.vrjassc.vrjassc.symbol.ScopeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;
import com.ruke.vrjassc.vrjassc.symbol.VrJassScope;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

/**
 * @deprecated Use Compiler.Compiler instead
 */
public class CompilerFacade {
	
	static VrJassScope nativeScope = null;

	public String commonPath = "./resources/common.j";
	public String blizzardPath = "./resources/blizzard.j";

	private HashSet<String> compiled = new HashSet<String>();

	public boolean translateCode = true;

	private int line;
	private int col;
	private ScopeSymbol catchedScope;
	private Symbol catchedSymbol;
	private Class catchedType;
	private Type catchedTypeCompatible;
	
	protected vrjassParser getParser(ANTLRInputStream is) {
		vrjassLexer lexer = new vrjassLexer(is);
		TokenStream token = new CommonTokenStream(lexer);
		
		return new vrjassParser(token);
	}
	
	protected String getCommonJ() throws IOException {
		return String.join("\n", Files.readAllLines(Paths.get(this.commonPath)));
	}
	
	protected String getBlizzardJ() throws IOException {
		return String.join("\n", Files.readAllLines(Paths.get(this.blizzardPath)));
	}
	
	protected VrJassScope getNatives() throws IOException {		
		if (nativeScope == null) {
			String natives = this.getCommonJ() + "\n" + this.getBlizzardJ();
			vrjassParser parser = this.getParser(new ANTLRInputStream(natives));
			
			TokenSymbolBag symbols = new TokenSymbolBag();
			nativeScope = new VrJassScope();
			
			DefinitionPhase defPhase = new DefinitionPhase(symbols, nativeScope);
			defPhase.setValidator(null);
			
			defPhase.visit(parser.init());
		}
		
		return nativeScope;
	}

	public String compile(ANTLRInputStream is) throws CompileException, IOException {
		return this.compile(new VrJassScope(), is, false, false, false);
	}

	public void catchSymbolIn(int line, int col) {
		this.line = line;
		this.col = col;
	}

	public Symbol getCatchedSymbol() {
		return this.catchedSymbol;
	}

	public ScopeSymbol getCatchedScope() {
		return this.catchedScope;
	}

	public Class getCatchedSymbolType() {
		return this.catchedType;
	}

	public Type getCatchedTypeCompatible() {
		return this.catchedTypeCompatible;
	}

	public String compile(VrJassScope symbols, ANTLRInputStream is, boolean beingImported, boolean ignoreImports, boolean ignoreSyntaxErrors) throws CompileException, IOException {
		if (this.compiled.contains(is.toString())) {
			return "";
		}

		this.compiled.add(is.toString());

		vrjassParser parser = this.getParser(is);
		TokenSymbolBag tokenBag = new TokenSymbolBag();

		symbols.define(this.getNatives());

		PreprocessorPhase procPhase = new PreprocessorPhase(null);
		DefinitionPhase defPhase = new DefinitionPhase(tokenBag, symbols);
		ReferencePhase refPhase = new ReferencePhase(tokenBag, symbols);

		if (this.line > 0 && this.col > 0 && !beingImported) {
			refPhase.catchSymbolIn(this.line, this.col);
		}

		parser.removeErrorListeners();

		if (!ignoreSyntaxErrors) {
			parser.addErrorListener(new BaseErrorListener() {
				@Override
				public void syntaxError(Recognizer<?, ?> recognizer,
										Object offendingSymbol,
										int line,
										int charPositionInLine,
										String msg,
										RecognitionException e) {
					throw new SyntaxErrorException(
						line,
						charPositionInLine,
						msg
							.replaceAll("ID", "identifier")
							.replaceAll("<EOF>", "end of file")
							.replace("NL", "new line")
					);
				}
			});
		}

		if (!ignoreImports) {
			procPhase.visit(parser.init());
			parser.reset();
		}

		defPhase.visit(parser.init());
		parser.reset();

		refPhase.visit(parser.init());
		parser.reset();

		this.catchedScope = refPhase.getCatchedScope();
		this.catchedSymbol = refPhase.getCatchedSymbol();
		this.catchedType = refPhase.getCatchSymbolType();
		this.catchedTypeCompatible = refPhase.getCatchTypeCompatible();

		if (!this.translateCode || beingImported) {
			return "";
		}

		if (this.compiled.size() > 1) {
			String code = String.join("\n", this.compiled);
			this.compiled.clear();

			return this.compile(
				new VrJassScope(),
				new ANTLRInputStream(code),
				false,
				true,
				false
			);
		}

		TranslationPhase tranPhase = new TranslationPhase(tokenBag, symbols);
		return tranPhase.visit(parser.init()).translate();
	}
	
}
