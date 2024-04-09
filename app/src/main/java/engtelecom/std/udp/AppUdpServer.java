package engtelecom.std.udp;

/**
 * Aplicação Servidor UDP.
 *
 * Inicia um servidor UDP na porta 9876.
 */
public class AppUdpServer {

    public static void main(String[] args) {

        int porta = 9876;
        // Verifica se o usuário informou a porta como argumento
        if (args.length == 1) {
            porta = Integer.parseInt(args[0]);
        }
     
        // Cria um socket UDP na porta 9876
        new Thread(new UdpServer(porta)).start();
    }
}
