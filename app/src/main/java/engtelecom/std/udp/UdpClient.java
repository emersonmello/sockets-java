package engtelecom.std.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

/**
 * Cliente UDP.
 *
 * Envia uma mensagem para um servidor UDP e aguarda a resposta.
 */
public class UdpClient implements Runnable {

    private static final Logger logger = Logger.getLogger(UdpClient.class.getName());

    private final String servidor;
    private final int porta;

    public UdpClient(String servidor, int porta) {
        this.servidor = servidor;
        this.porta = porta;
    }

    public String comunicacao(String mensagem) {
        String resposta = "";
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            // Converte a mensagem para bytes
            byte[] buffer = mensagem.getBytes();

            // Cria um pacote com os dados da mensagem
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(this.servidor), this.porta);

            // Envia o pacote
            datagramSocket.send(datagramPacket);

            // Cria um pacote para receber os dados
            datagramPacket = new DatagramPacket(buffer, buffer.length);

            // Aguarda a chegada de um pacote
            datagramSocket.receive(datagramPacket);

            // Exibe os dados recebidos
            resposta = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        } catch (Exception e) {
            logger.severe("Erro: " + e.getMessage());
        }
        return resposta;
    }

    @Override
    public void run() {
        System.out.println("\u2591\u2592\u2592 Cliente UDP \u2592\u2592\u2591");
        String resposta = this.comunicacao("Olá, eu sou o cliente UDP!");
        logger.info("<<< " + resposta);
    }
}