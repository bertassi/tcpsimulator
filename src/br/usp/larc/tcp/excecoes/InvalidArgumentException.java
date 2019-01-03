package br.usp.larc.tcp.excecoes;

/**
 * Exce��o que representa argumento(s) inv�lido(s)
 * 
 * @author Laborat�rio de Arquitetura e Redes de Computadores
 * @version 1.0 09 Maio 2003
 */
public class InvalidArgumentException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>InvalidArgumentException</code> without detail message.
     */
    public InvalidArgumentException() {
    }
    
    
    /**
     * Constructs an instance of <code>InvalidArgumentException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidArgumentException(String msg) {
        super(msg);
    }
}
