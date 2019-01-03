package br.usp.larc.tcp.excecoes;

public class ConnectionBindException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>BindException</code> without detail message.
     */
    public ConnectionBindException() {
    }
    
    
    /**
     * Constructs an instance of <code>BindException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ConnectionBindException(String msg) {
        super(msg);
    }
}
