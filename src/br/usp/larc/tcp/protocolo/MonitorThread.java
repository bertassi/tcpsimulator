package br.usp.larc.tcp.protocolo;

import br.usp.larc.tcp.ipsimulada.*;
import br.usp.larc.tcp.excecoes.*;

/** 
 * Classe que monitora o buffer a espera de pacotes e entrega para o Objeto
 * Monitor.
 *
 * Procure sempre usar o paradigma Orientado a Objeto, a simplicidade e a 
 * criatividade na implementa��o do seu projeto.
 *
 * @author	Laborat�rio de Arquitetura e Redes de Computadores.
 * @version	1.0 Agosto 2003.
 */

public class MonitorThread extends Thread {
	
    /*
     * Monitor associado ao thread
     */
    private Monitor monitor;

    /*
     * Camada IP Simulada que a thread vai monitorar
     */
    private IpSimulada camadaIPSimulada;

    /*
     * Atributo que controla se a a thread deve continuar a monitorar a
     * camada IP Simulada
     */
    private boolean isRunning;


    /** 
     * Construtor da classe InputThread
     *
     * @param _monitor Monitor que o thread vai servir
     * @param _camadaIPSimulada Camada IP Simulada que a thread vai 
     * monitorar o buffer de entrada
     */ 
    public MonitorThread(Monitor _monitor, IpSimulada _camadaIPSimulada) {
        this.monitor = _monitor;
        this.camadaIPSimulada = _camadaIPSimulada;
        this.isRunning = true;
        System.out.println("MonitorThread Iniciado...");
    }

    /*
     * M�todo de execu��o da thread
     */
    public void run() {
        String bufferEntrada = null;
        // indica se o buffer recebeu algo
        boolean bufferReceived = false;

        while(isRunning) {
            try {
                bufferReceived = true;
                //verifica se tem dados no buffer de entrada,
                //se n�o tiver, gera exce��o
                bufferEntrada = camadaIPSimulada.recebe(
                ProtocoloTCP.BUFFER_DEFAULT_IP_SIMULADA);
            } catch(Exception e) {
                // Nada recebido
                bufferReceived = false;
                //System.out.println(e.getMessage());
            }
            if(bufferReceived) {
                //recebe dados e entrega para o monitor analisar
                monitor.analisaDados(bufferEntrada);
            }
        }
    }

    /*
     * Indica se a thread est� rodando
     *
     * @return boolean O estado do thread (true = monitorando e
     * false = n�o monitorando
     */

    public boolean isRunning() {
        return this.isRunning;
    }

    /*
     * P�ra a thread
     */
    public void paraThread() {
        System.out.println("MonitorThread Parando...");
        this.isRunning = false;
    }
    
}//fim da classe MonitorThread 2006