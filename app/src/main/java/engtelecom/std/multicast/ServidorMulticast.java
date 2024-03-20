package engtelecom.std.multicast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servidor de hora Multicast.
 * 
 * Envia a hora atual em intervalos regulares para um grupo multicast.
 */
public class ServidorMulticast implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ServidorMulticast.class);
    private final int INTERVALO = 1000;
    private final int BUFFER_SIZE;
    private int porta;
    private InetAddress enderecoMulticast;

    public ServidorMulticast(String enderecoMulticast, int porta) throws UnknownHostException, SocketException {
        this.enderecoMulticast = InetAddress.getByName(enderecoMulticast);
        this.porta = porta;
        this.BUFFER_SIZE = 256;
    }

    @Override
    public void run() {

        logger.info("Servidor de hora Multicast iniciado.");
        try (DatagramSocket datagramSocket = new DatagramSocket()) {

            while (true) {
                String dataHora = LocalDateTime.now().toString();
                byte[] buffer =  dataHora.getBytes();

                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, enderecoMulticast, porta);
                datagramSocket.send(datagramPacket);
                System.out.println(">>> " + dataHora);

                Thread.sleep(INTERVALO);
            }
        } catch (Exception e) {
            logger.error("Erro: " + e.getMessage());
        }
    }
}