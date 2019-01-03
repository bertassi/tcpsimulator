package br.usp.larc.tcp.excecoes;

/**
 * Exce��o que representa expira��o de um algum timeout.
 * 
 * @author Laborat�rio de Arquitetura e Redes de Computadores.
 * @version 1.0 09 Maio 2003.
 */
public class TimeOutException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>TimeOutException</code> without detail message.
     */
    public TimeOutException() {
    }
    
    
    /**
     * Constructs an instance of <code>TimeOutException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TimeOutException(String msg) {
        super(msg);
    }
}
