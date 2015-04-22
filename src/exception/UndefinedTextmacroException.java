package exception;

public class UndefinedTextmacroException extends CompileException {

	protected String name;
	
	public UndefinedTextmacroException(String name) {
		super(null);
		this.name = name;
	}
	
	@Override
	public String getMessage() {
		return String.format("Textmacro <%s> is not defined", this.name);
	}

}
