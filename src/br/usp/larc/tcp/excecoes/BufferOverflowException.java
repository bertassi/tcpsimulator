package br.usp.larc.tcp.excecoes;

/**
 * Exce��o que representa estouro de Buffer
 * 
 * @author Laborat�rio de Arquitetura e Redes de Computadores
 * @version 1.0 09 Maio 2003
 */
public class BufferOverflowException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>BufferOverflowException</code> without detail message.
     */
    public BufferOverflowException() {
    }
    
    
    /**
     * Constructs an instance of <code>BufferOverflowException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BufferOverflowException(String msg) {
        super(msg);
    }
}
