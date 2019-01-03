package br.usp.larc.tcp.protocolo;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Iterator;

/** 
 * Classe que representa as M�quinas de Conex�es que receber�o pacotes do objeto
 * Monitor.
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

public class MaquinasDeEstados extends java.util.Hashtable {
    
    /** Construtor da classe MaquinasDeEstados */
    public MaquinasDeEstados() {
        super();
    }
    
    /**
     * M�todo que adiciona na tabela uma conex�o. No caso de adi��o de conex�es
     * com o mesmo ID, o m�todo sobrep�e os objetos e permanece na tabela a
     * �ltima conex�o inserida.
     *
     * @param _idConexao O id da Conexao que ser� chave do o objeto m�quina
     * @param _maquinaDeEstados O objeto m�quina que ser� inserido
     */    
    public void put(String _idConexao, MaquinaDeEstados _maquinaDeEstados) {
        super.put(_idConexao, _maquinaDeEstados);
    }
    
    /**
     * M�todo que retorna uma m�quina da tabela com a Porta TCP (local) passada, 
     * caso n�o encontre a porta na tabela, retorna null.
     *
     * @param _idConexao O idConexao da m�quina que voc� quer obter,  nulo se
     * n�o existir a m�quina com o id passado
     * @return MaquinaDeEstados A m�quina desejada
     */    
    public MaquinaDeEstados get(int _idConexao) {
        return (MaquinaDeEstados) super.get(Integer.toString(_idConexao));
    }
    
    /**
     * M�todo que remove uma m�quina da tabela com o id da conex�o passado, caso
     * n�o encontre o id na tabela, gera exce��o.     
     * 
     * @param _idConexao O id da conex�o da m�quina que se quer remover da tabela
     * @throws NullPointerException Caso n�o encontre o objeto com o id passado
     */    
    public void remove(int _idConexao) throws NullPointerException {
        if (super.remove((Integer.toString(_idConexao))) == null) {
                throw new NullPointerException("Elemento n�o encontrado");
        }
    }
            
    /**
     * M�todo que retorna o tamanho atual da tabela de m�quinas baseado no 
     * n�mero de objetos inseridos.
     * 
     * @return int O tamanho da tabela de m�quinas
     */    
    public int size() {
        return super.size();
    }
    
    /**
     * M�todo que retorna um iterador com todos os objetos MaquinaDeEstados
     * da HashTable
     *
     * Exemplo:
     *  
     * //cria um iterador para percorrer um objeto do tipo TabelaDeConexoes
     * Iterator  iteratorTabela = (Iterator) tabelaDeMaquinas.maquinas();
     * MaquinaDeEstados me = new MaquinaDeEstados();
     *
     * 
     * //percorre todo o iterador
     * while (iteratorTabela.hasNext()) {
     *      //imprime todos os ID's das conex�es que est�o na tabela
     *      System.out.println((
                (MaquinaDeEstados)iteratorTabela.next()).getIdConexao());
     *  }
     *   
     * @return Iterator O iterador com os objetos M�quinas da tabela
     */    
    public Iterator maquinas() {
        return (Iterator) (super.values()).iterator();
    }
    
    /**
     * M�todo que retorna um iterador com as chaves dos objetos MaquinaDeEstados
     * da HashTable
     *
     *   
     * @return Iterator O iterador com as chaves dos objetos da Hashtable
     */    
    public Iterator maquinasKeySet() {
        return (Iterator) (super.keySet()).iterator();
    }

}//fim da classe MaquinasDeEstados 2006