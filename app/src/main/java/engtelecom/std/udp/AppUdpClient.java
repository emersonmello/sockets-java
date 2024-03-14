package engtelecom.std.udp;

public class AppUdpClient {

    public static void main(String[] args) {
        String host = "localhost";
        int porta = 9876;

        // Verifica se o usuário informou o endereço e a porta do servidor como argumentos
        if (args.length == 2) {
            host = args[0];
            porta = Integer.parseInt(args[1]);
        }

        // Cria um cliente UDP
        new Thread(new UdpClient(host, porta)).start();
    }
}
