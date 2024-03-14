package engtelecom.std.udp;

public class AppUdpServer {

    public static void main(String[] args) {
     
        // Cria um socket UDP na porta 9876
        new Thread(new UdpServer(9876)).start();
    }
}
