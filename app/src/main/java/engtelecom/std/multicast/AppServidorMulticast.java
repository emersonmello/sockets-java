package engtelecom.std.multicast;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Aplicação Servidor de hora Multicast.
 * 
 * Envia a hora atual em intervalos regulares para um grupo multicast.
 */
public class AppServidorMulticast {

    // Para exibir mensagens de log
    private static Logger logger = Logger.getLogger(AppServidorMulticast.class.getName());

    public static void main(String[] args) {
        String enderecoMulticast = "231.0.0.0";
        int porta = 8888;

        // Verifica se o usuário informou o endereço e a porta como argumentos
        if (args.length == 2) {
            enderecoMulticast = args[0];
            porta = Integer.parseInt(args[1]);
        }

        try {
            ServidorMulticast servidorMulticast = new ServidorMulticast(enderecoMulticast, porta);
            // Inicia a thread do tcp de hora multicast
            new Thread(servidorMulticast).start();

        } catch (UnknownHostException | SocketException e) {
            logger.severe("Erro: " + e.getMessage());
        }
    }
}