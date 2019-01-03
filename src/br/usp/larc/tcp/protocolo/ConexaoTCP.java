package br.usp.larc.tcp.protocolo;

public class ConexaoTCP {
    
    /**
     * Atributo que identifica a conex�o de forma �nica.
     */
    private int idConexao;
    
    /**
     * Atributo que representa o IP Simulado (IP REAL Local+ porta UDP) Local.
     */
    private String ipSimuladoLocal;

    /**
     * Atributo que representa o IP Simulado (IP REAL Remoto + porta UDP) Remoto.
     */
    private String ipSimuladoRemoto;

    /**
     * Atributo que representa a Porta TCP Local.
     */
    private String portaLocal;
    
    /**
     * Atributo que representa a Porta TCP Remota.
     */
    private String portaRemota;
    
    /** Construtor default da classe Conex�oTCP */
    public ConexaoTCP() {
        this.idConexao = 0;
        this.ipSimuladoLocal = "";
        this.ipSimuladoRemoto = "";
        this.portaLocal = "";
        this.portaRemota = ""; 
    }
    
   /** Construtor com par�metros da classe Conex�oTCP */
    public ConexaoTCP(int oIdConexao, String oIpSimuladoLocal, String aPortaLocal, String oIpSimuladoRemoto, String aPortaRemota) {
        this.idConexao = oIdConexao;
        this.ipSimuladoLocal = oIpSimuladoLocal;
        this.portaLocal = aPortaLocal;
        this.ipSimuladoRemoto = oIpSimuladoRemoto;
        this.portaRemota = aPortaRemota; 
    }
    
    /** 
     * M�todo acessador para o atributo idConexao.
     * @return int Valor do atributo idConexao.
     *
     */
    public int getIdConexao() {
        return this.idConexao;
    }
    
    /** 
     * M�todo modificador para o atributo idConexao.
     * @param aIdConexao Novo valor para o atributo idConexao.
     *
     */
    public void setIdConexao(int _idConexao) {
        this.idConexao = _idConexao;
    }
    
    /**
     * M�todo acessador para o atributo ipSimuladoLocal.
     * @return String Valor do atributo ipSimuladoLocal.
     *
     */
    public String getIpSimuladoLocal() {
        return this.ipSimuladoLocal;
    }
    
    /** M�todo modificador para o atributo ipSimuladoLocal.
     * @param oIpSimuladoLocal Novo valor para o atributo ipSimuladoLocal.
     *
     */
    public void setIpSimuladoLocal(String _IPSimuladoLocal) {
        this.ipSimuladoLocal = _IPSimuladoLocal;
    }
    
    /** M�todo acessador para o atributo ipSimuladoRemoto
     * @return String Valor do atributo ipSimuladoLocal.
     *
     */
    public String getIpSimuladoRemoto() {
        return this.ipSimuladoRemoto;
    }
    
    /** M�todo modificador para o atributo ipSimuladoRemoto.
     * @param oIpSimuladoRemoto Novo valor para o atributo ipSimuladoRemoto.
     *
     */
    public void setIpSimuladoRemoto(String _IPSimuladoRemoto) {
        this.ipSimuladoRemoto = _IPSimuladoRemoto;
    }
    
    /** M�todo acessador para o atributo portaLocal.
     * @return String Valor do atributo portaLocal.
     *
     */
    public String getPortaLocal() {
        return this.portaLocal;
    }
    
    /** M�todo modificador para o atributo portaLocal.
     * @param aPortaLocal Novo valor para o atributo portaLocal.
     *
     */
    public void setPortaLocal(String _portaLocal) {
        this.portaLocal = _portaLocal;
    }
    
    /** M�todo acessador para o atributo portaRemota.
     * @return String Valor do atributo portaRemota.
     *
     */
    public String getPortaRemota() {
        return this.portaRemota;
    }
    
    /** M�todo modificador para o atributo portaRemota.
     * @param aPortaRemota Novo valor para o atributo portaRemota.
     *
     */
    public void setPortaRemota(String _portaRemota) {
        this.portaRemota = _portaRemota;
    }
    
}//fim da classe ConexaoTCP 2006