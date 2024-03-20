package engtelecom.std.udp;

public class AppUdpClient {

    public static void main(String[] args) {
        String servidor = "localhost";
        int porta = 9876;

        // Verifica se o usuário informou o endereço e a porta do tcp como argumentos
        if (args.length == 2) {
            servidor = args[0];
            porta = Integer.parseInt(args[1]);
        }

        // Cria um cliente UDP
        new Thread(new UdpClient(servidor, porta)).start();
    }
}
