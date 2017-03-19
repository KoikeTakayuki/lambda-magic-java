package jp.lambdamagic.io;

import java.io.IOException;

public class EndOfStreamException extends IOException {

    private static final long serialVersionUID = 794560722649741158L;

    public EndOfStreamException() {
        super("Attempted to read past the end of the stream");
    }
    
}
