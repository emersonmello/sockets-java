package engtelecom.std.udp;

/**
 * Aplicação Servidor UDP.
 *
 * Inicia um servidor UDP na porta 9876.
 */
public class AppUdpServer {

    public static void main(String[] args) {
     
        // Cria um socket UDP na porta 9876
        new Thread(new UdpServer(9876)).start();
    }
}
