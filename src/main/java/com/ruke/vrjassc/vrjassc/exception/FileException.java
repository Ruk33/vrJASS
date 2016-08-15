package com.ruke.vrjassc.vrjassc.exception;

import java.io.File;

public class FileException extends CompileException {
    
    File file;
    CompileException e;
    
    public FileException(File file, CompileException e) {
        super(null);
        
        this.file = file;
        this.e = e;
    }
    
    public int getLine() {
        return this.e.line;
    }
    
    public int getCharPos() {
        return this.e.charPos;
    }
    
    @Override
    public String getErrorMessage() {
        return null;
    }
    
    @Override
    public String getMessage() {
        return "File '" + this.file + "':\n" + this.e.getMessage();
    }
    
}
