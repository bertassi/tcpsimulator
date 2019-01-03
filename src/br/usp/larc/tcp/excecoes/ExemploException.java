package br.usp.larc.tcp.excecoes;

/**
 * Classe para exemplificar uma cria�ao de exce��o pr�pria.
 * Voc� pode seguir esse modelo para criar suas pr�prias exce��es para o
 * projeto (por exemplo, exce��es para timeouts)
 * 
 * @author Laborat�rio de Arquitetura e Redes de Computadores.
 * @version 1.0 01 Junho 2004.
 */
public class ExemploException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>ExemploException</code> without detail message.
     */
    public ExemploException() {
    }
    
    
    /**
     * Constructs an instance of <code>ExemploException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ExemploException(String msg) {
        super(msg);
    }
}
// fim da classe ExemploException 2006