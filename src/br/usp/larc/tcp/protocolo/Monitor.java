package br.usp.larc.tcp.protocolo;

import br.usp.larc.tcp.aplicacao.MonitorFrame;
import br.usp.larc.tcp.excecoes.CanalInexistenteException;
import br.usp.larc.tcp.excecoes.TimeOutException;
import br.usp.larc.tcp.ipsimulada.*;

import java.util.HashMap;
import java.util.Iterator;

/** 
 * Classe que representa o monitor do seu protocolo. Detalhes e dicas de
 * implementa��o podem ser consultadas nas Apostilas.
 *
 * Procure sempre usar o paradigma Orientado a Objeto, a simplicidade e a 
 * criatividade na implementa��o do seu projeto.
 *  
 *
 * @author	Laborat�rio de Arquitetura e Redes de Computadores
 * @version	1.0 Agosto 2003
 */
public class Monitor {
    
    /**
     * O Protocolo TCP que o monitor est� vinculado
     */
    private ProtocoloTCP protocoloTCP;

    /** 
     * Atributo que representa o frame associado ao ProtocoloTCP
     */
    private MonitorFrame monitorFrame;
    
    /**
     * Thread que vai monitorar o buffer de entrada da camdada IPSimulada e
     * entregar dos dados recebidos para o monitor
     */
    private MonitorThread monitorThread;
    
    /**
     * Cole��o com objetos MaquinaDeEstados
     */
    private MaquinasDeEstados maquinasDeEstados;

    /**
     * Objeto que representa a Tabela de Conexao
     */
    private TabelaDeConexoes tabelaDeConexoes;
    
    /** 
     * Atributo com o IpLocal do Monitor
     */
    private String ipSimuladoLocal;
    
    /**
     * Atributo que representa o contador de Id de Conex�es
     */
    private int countIdConexao;
    
    /** Construtor da classe Monitor */
    public Monitor() {
    }
    
    /** Construtor da classe Monitor */
    public Monitor(ProtocoloTCP _protocoloTCP) {
        this.protocoloTCP = _protocoloTCP;
        //inicia o Frame do Monitor
        this.monitorFrame = new MonitorFrame(_protocoloTCP);
        this.tabelaDeConexoes = new TabelaDeConexoes();
        this.maquinasDeEstados = new MaquinasDeEstados();

        //inicia o contador de id de conex�o em 0 (zero)
        this.countIdConexao = 0;
    }
    
    /**
    * M�todo utilizado para entregar o pr�ximo id do contador para cada conex�o.
    * 
    * @return O id da pr�xima Conex�o
    */
    public synchronized int getNextID() {
        return countIdConexao++;
    }
    
    /**
     * M�todo que procura uma maquina de estados dado a porta local associada
     * a essa m�quina de estados e retorna uma refer�ncia para a m�quina de 
     * estados encontrada. Caso n�o encontre a m�quina, retorna null.
     *
     * @param  porta  porta local da maquina de estados
     * @return MaquinaDeEstados associada a porta local dada 
     */
    public MaquinaDeEstados findMEPorPortaLocal(int _portaME) {
            for (Iterator i = (Iterator) 
                this.maquinasDeEstados.maquinas();
                    i.hasNext(); ) {
                MaquinaDeEstados meq = (MaquinaDeEstados) i.next();
                if (Integer.toString(meq.getPortaLocal()).equals( 
                        Integer.toString(_portaME))) {
                    return meq;
                }
            }
    return null;
    }

    /**
     * Cria uma m�quina de estados com a porta passada como par�metro (uma 
     * porta TCP local). Retorna false se j� existir uma m�quina de estados j�
     * associada �quela porta.
     *
     * @param _portaTCP a porta que ser� atribuida a nova M�quina de Estados
     */
    public boolean criaMaquinaDeEstados(int _portaTCP) {
        //verifica se j� existe uma m�quina de estados associada �quela porta
        if (this.findMEPorPortaLocal(_portaTCP) == null) {
            
            //sincroniza esse trecho do c�digo para evitar inconsist�ncia na
            //ordem de inser��o da cole�ao de m�quinas de estados e da tabela
            //de conex�es.
            synchronized (this) {
                int countIdConexao = this.getNextID();
                
                //cria o objeto com a nova m�quina de estados associando a porta
                // passada como par�metro e o id da conex�o
                MaquinaDeEstados maquinaME  = new MaquinaDeEstados(
                this, _portaTCP, countIdConexao);
                this.adicionaMaquina(Integer.toString(countIdConexao), maquinaME);

                //cria uma nova conex�o
                ConexaoTCP conexao = new ConexaoTCP();
                conexao.setIpSimuladoLocal(this.ipSimuladoLocal);
                conexao.setPortaLocal(Integer.toString(_portaTCP));
                conexao.setIdConexao(countIdConexao);

                //adiciona a nova conex�o na Tabela de Conex�es do Monitor
                this.abreConexao(conexao);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Fecha uma m�quina de estados com o id passado como par�metro 
     *
     * @param _id O id da M�quina de Estados que ser� fechada
     */
    public boolean fechaMaquinaDeEstados(int _idConexao) {
		MaquinaDeEstados maquina = null;
		
                //faz uma varredura na cole��o de chaves da cole��o de m�quina
                //de estados 
                for (Iterator i = 
                    this.maquinasDeEstados.maquinasKeySet(); i.hasNext();) {
                    
                    //pega chave por chave da cole��o de m�quina de estados    
                    int chaveIdConexao = Integer.parseInt((String) (i.next()));
                    
                    //verifica se chave recuperada da cole��o � igual a chave
                    //passada como par�metro
                    if (chaveIdConexao == _idConexao) {

                                //recupera a refer�ncia da m�quina de estados
                                //com o id da conex�o passada como par�metro
                                maquina = this.maquinasDeEstados.get(_idConexao);
				break;
			}
		}
		
                //se a refer�ncia recuperada n�o for nula a apaga a m�quina da
                //cole��o de m�quinas de estados do monitor e tamb�m a conex�o
                //com aquele id da Tabela de Conex�es do Monitor
                if (maquina != null) {
                        this.fechaMaquina(_idConexao);
			this.fechaConexao(_idConexao);
			this.fechaMaquinaDeEstadosFrame(maquina);
			return true;
		}
		return false;
    }        
    
    /**
     * Finaliza o frame da m�quina de estados passada com par�metro
     *
     * @param _id O id da M�quina de Estados que ser� fechada
     */
    public void fechaMaquinaDeEstadosFrame(MaquinaDeEstados _maquina) {
        if (_maquina.getMeFrame() != null) {
            _maquina.getMeFrame().dispose();
            _maquina.setMeFrame(null);
            _maquina = null;
        }
    }
    
    /**
     *  Fecha o monitor
     */
    public void fechar() {
        this.monitorFrame.dispose();
        this.monitorThread = null;
        this.tabelaDeConexoes = null;
        this.maquinasDeEstados = null;
    }
    
    /**
     *  Reinicia o monitor
     */
    public void reinicia() {
        //implemente aqui o m�todo que reinicia o monitor ao seu estado inicial
    	
    	// fecha todas as maquinas de estados
    	for(int i=0; i<countIdConexao; i++)
    	{
    		fechaMaquinaDeEstados(i);
    	}
    }
    
    /**
     * Monitora a camada IP Simulada
     */
    public synchronized void monitoraCamadaIP() {
	monitorThread = new MonitorThread(this,(IpSimulada) 
        this.protocoloTCP.getCamadaIpSimulada());
	monitorThread.start();
    }
	
    /**
     * Termina monitoramento da camada IP Simulada
     */
    public synchronized void terminaMonitoramentoCamadaIP() {
        monitorThread.paraThread();
    }
    
    /**
     * Analiza dados recebidos da camada IP simulada e faz an�lise
     */
    public void analisaDados(String _bufferEntrada) {
        //implemente aqui o tratamento dos segmentos que chegam.
    	

    	
    	
    	
    	
    	
    	PacoteTCP pacoteTCP = new PacoteTCP(_bufferEntrada);
    	
    	// procura uma m�quina de estados com a porta especificada
    	int portaLocal = Integer.parseInt(pacoteTCP.getPortaRemota());
    	MaquinaDeEstados _maquinaDeEstados = this.findMEPorPortaLocal(portaLocal);
    	// se encontrou, manda o pacote TCP � m�quina de estados
    	if( _maquinaDeEstados != null )
    	{
    		try {
    			_maquinaDeEstados.recebeSegmentoTCP(pacoteTCP);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
 
    /** 
     * M�todo que coloca uma nova Maquina na tabela de MaquinasDeEstados
     *
     * @param _idConexao        O Id da conexao da m�quina a ser registrada
     * @param _maquinaDeEstados Nova m�quina a ser registrada
     */
    public void adicionaMaquina(String _idConexao, 
        MaquinaDeEstados _maquinaDeEstados) {
        try {
            this.maquinasDeEstados.put(_idConexao, _maquinaDeEstados);
        } catch (Exception e) {
            System.out.println("Monitor.adicionaMaquina(): "  +
                e.getMessage());
        }         
    }
        
    /** 
     * M�todo que fecha uma m�quina de estados, dada a porta da conex�o
     *
     * @param _idConexao id da M�quina de Estado a ser exclu�da
     * @exception Exception exce��o jogada se a m�quina n�o existe
     */
    public void fechaMaquina(int _idConexao) { 
        try {
            this.maquinasDeEstados.remove(Integer.toString(_idConexao));
        } catch (Exception e) {
            System.out.println("Monitor.fechaMaquina(): "  +
                e.getMessage());
        }
    }
    
    /** 
     * M�todo que coloca uma nova conexaoTCP na tabela de conex�es
     *
     * @param conexaoTCP Nova conex�o a ser registrada
     */
    public void abreConexao(ConexaoTCP _conexaoTCP) {
        try {
            this.tabelaDeConexoes.put(_conexaoTCP);
        } catch (Exception e) {
            System.out.println("Monitor.abreConexao(): "  +
                e.getMessage());
        }         
    }
        
    /** 
     * M�todo que fecha uma conex�o TCP, dado o id da conex�o
     *
     * @param idConexaoTCP id da conexaoTCP a ser fechada
     * @exception Exception exce��o jogada se a conex�o n�o existe
     */
    public void fechaConexao(int _idConexaoTCP) { 
        try {
            this.tabelaDeConexoes.remove(_idConexaoTCP);
        } catch (Exception e) {
            System.out.println("Monitor.fechaConexao(): "  +
                e.getMessage());
        }
    }

    /** M�todo acessador para o atributo protocoloTCP.
     * @return A refer�ncia para o atributo protocoloTCP.
     *
     */
    public ProtocoloTCP getProtocoloTCP() {
        return protocoloTCP;
    }
    
    /** M�todo modificador para o atributo protocoloTCP.
     * @param protocoloTCP Novo valor para o atributo protocoloTCP.
     *
     */
    public void setProtocoloTCP(ProtocoloTCP _protocoloTCP) {
        this.protocoloTCP = _protocoloTCP;
    }
    
    /** M�todo acessador para o atributo ipSimuladoLocal.
     * @return A refer�ncia para o atributo ipSimuladoLocal.
     *
     */
    public String getIpSimuladoLocal() {
        return ipSimuladoLocal;
    }
    
    /** M�todo modificador para o atributo ipSimuladoLocal.
     * @param ipSimuladoLocal Novo valor para o atributo ipSimuladoLocal.
     *
     */
    public void setIpSimuladoLocal(String _ipSimuladoLocal) {
        this.ipSimuladoLocal = _ipSimuladoLocal;
    }

    /** M�todo acessador para o atributo maquinasDeEstados.
     * @return A refer�ncia para o atributo maquinasDeEstados.
     *
     */
    public MaquinasDeEstados getMaquinasDeEstados() {
        return maquinasDeEstados;
    }    
    
    /** M�todo modificador para o atributo maquinasDeEstados.
     * @param maquinasDeEstados Novo valor para o atributo maquinasDeEstados.
     *
     */
    public void setMaquinasDeEstados(MaquinasDeEstados _maquinasDeEstados) {
        this.maquinasDeEstados = _maquinasDeEstados;
    }
    
    /** M�todo acessador para o atributo tabelaDeConexoes.
     * @return A refer�ncia para o atributo tabelaDeConexoes.
     *
     */
    public TabelaDeConexoes getTabelaDeConexoes() {
        return tabelaDeConexoes;
    }
    
    /** M�todo modificador para o atributo tabelaDeConexoes.
     * @param tabelaDeConexoes Novo valor para o atributo tabelaDeConexoes.
     *
     */
    public void setTabelaDeConexoes(TabelaDeConexoes _tabelaDeConexoes) {
        this.tabelaDeConexoes = _tabelaDeConexoes;
    }

}//fim da classe Monitor 2006