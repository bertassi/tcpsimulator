package br.usp.larc.tcp.protocolo;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Iterator;

/** 
 * Classe que representa a Tabela de Conex�o que vai ajudar o objeto Monitor no 
 * chaveamento de pacotes para as m�quinas de estados correspondentes.
 * 
 * Note que essa classe estende a classe java.util.Hashtable (ela herda todos
 * os atributos e m�todos) e sobrecarrega/sobrep�e alguns m�todos da mesma. 
 * Estamos usando o conceito de Polimorfismo.
 *
 * Detalhes e dicas de implementa��o dessa classe podem ser consultadas na API
 * da classe java.util.Hashtable.
 *
 * Procure sempre usar o paradigma Orientado a Objeto, a simplicidade e a 
 * criatividade na implementa��o do seu projeto.
 *  
 *
 * @author	Laborat�rio de Arquitetura e Redes de Computadores
 * @version	1.0 Agosto 2003
 */
public class TabelaDeConexoes extends java.util.Hashtable {
    
    /** Construtor da classe TabelaDeConexoes */
    public TabelaDeConexoes() {
        super();
    }
    
    /**
     * M�todo que adiciona na tabela uma conex�o. No caso de adi��o de conex�es
     * com o mesmo ID, o m�todo sobrep�e os objetos e permanece na tabela a
     * �ltima conex�o inserida.
     *
     * @param aConexaoTCP O objeto conex�o que ser� inserido
     */    
    public void put(ConexaoTCP _conexaoTCP) {
        super.put(Integer.toString(_conexaoTCP.getIdConexao()),  _conexaoTCP);
    }
    
    /**
     * M�todo que retorna uma conex�o da tabela com o ID (de conex�o) passado, 
     * caso n�o encontre o ID na tabela, retorna null.
     *
     * @param idConexao O ID da conex�o que voc� quer obter, nulo se n�o existir
     * a conex�o com o ID passado
     * @return ConexaoTCP A conex�o desejada
     */    
    public ConexaoTCP get(int _idConexao) {
        return (ConexaoTCP) super.get(Integer.toString(_idConexao));
    }
    
    /**
     * M�todo que remove uma conex�o da tabela com o ID (de conex�o) passado, 
     * caso n�o encontre o ID na tabela, gera exce��o.     
     * 
     * @param idConexao o ID da conex�o que se quer remover da tabela
     * @throws NullPointerException Caso n�o encontre o objeto com o ID passado
     */    
    public void remove(int _idConexao) throws NullPointerException {
        if (super.remove((Integer.toString(_idConexao))) == null) {
                throw new NullPointerException("Elemento n�o encontrado");
        }
    }
            
    /**
     * M�todo que retorna o tamanho atual da tabela de conex�es baseado no 
     * n�mero de conex�es inseridas.
     * 
     * @return int O tamanho da tabela de conex�es
     */    
    public int size() {
        return super.size();
    }
    
    /**
     *  M�todo que retorna um iterador com todos os objetos Conex�o da tabela
     *
     *  Exemplo:
     *  
     *  //cria um iterador para percorrer um objeto do tipo TabelaDeConexoes
     *  Iterator  iteratorTabela = (Iterator) tabelaDeConexoes.conexoes();
     *  ConexaoTCP conexao = new Conexao();
     *
     * 
     *  //percorre todo o iterador
     *  while (iteratorTabela.hasNext()) {
     *      //imprime todos ID's das conex�es que est�o na tabela
     *      System.out.println(((ConexaoTCP)iteratorTabela.next()).getIdConexao());
     *  }
     *   
     * @return Iterator O iterador com os objetos Conex�o da tabela
     */    
    public Iterator conexoes() {
        return (Iterator) (super.values()).iterator();
    }
    
    /**
     * M�todo que retorna um iterador com as chaves dos objetos ConexaoTCP
     * da HashTable
     *
     *   
     * @return Iterator O iterador com as chaves dos objetos da Hashtable
     */    
    public Iterator conexoesKeySet() {
        return (Iterator) (super.keySet()).iterator();
    }
    
    /**
     * M�todo para visualizar a tabela de conex�es
     * 
     * Imprime linha por linha o conte�do da tabela de conex�o com a seguinte
     * sintaxe:
     * 
     * ID: NumId [ IPSimuladoLocal : PortaTCP] [ IPSimuladoRemoto : PortaTCP]
     * Um exemplo:
     * ID: 1 [ 143.107.111.087:4593 : 1024 ] [ 143.107.111.087:4593 : 1025 ]
     * ID: 2 [ 143.107.111.087:4593 : 1026 ] [ 143.107.111.087:4593 : 1027 ]
     *
     * @return String O String com cada da tabela de conex�o
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        MessageFormat mf =
                new MessageFormat("ID: {0} [ {1} : {2} ] [ {3} : {4} ]\n");
        DecimalFormat df = new DecimalFormat("0");
        for (Iterator i = this.conexoes(); i.hasNext();) {
                ConexaoTCP conexao = (ConexaoTCP) i.next();
                Object[] argumentos = {df.format(conexao.getIdConexao()),
                    Decoder.ipSimuladoToBytePonto(conexao.getIpSimuladoLocal()),
                    conexao.getPortaLocal(),
                    Decoder.ipSimuladoToBytePonto(conexao.getIpSimuladoRemoto()),
                    conexao.getPortaRemota()};
                buffer.append(mf.format(argumentos));
        }
        return buffer.toString();
    }
    
}//fim da classe TabelaDeConex�es 2006