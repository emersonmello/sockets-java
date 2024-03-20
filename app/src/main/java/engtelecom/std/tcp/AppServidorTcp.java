package engtelecom.std.tcp;

import java.net.ServerSocket;
import java.util.logging.Logger;

/**
 * Aplicação Servidor TCP.
 */
public class AppServidorTcp {

    private static final Logger logger = Logger.getLogger(AppServidorTcp.class.getName());

    // Variável para controlar a execução da thread principal
    // volatile garante que a variável running será sempre lida diretamente da memória principal e não de uma cópia em cache do processador
    private static volatile boolean running = true;

    public static void main(String[] args) {

        // Adiciona um tratador para encerrar o processo quando o usuário pressionar CTRL+C
        Runtime.getRuntime().addShutdownHook( new Thread(() -> running = false));

        System.out.println("\u2591\u2592\u2592 Iniciando o tcp \u2592\u2592\u2591");

        int porta = 12345;
        
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            
            System.out.println("Servidor aguardando conexões em " + serverSocket.getInetAddress() +":" + porta);
            System.out.println("Pressione CTRL+C para encerrar o processo do tcp\n\n");

            while (running) {
                // Aceita a conexão de um cliente e cria uma nova thread para atendê-lo
                new Thread(new ThreadServidor(serverSocket.accept())).start();
            }

        } catch (Exception e) {
            logger.severe("Erro: " + e.getMessage());
        }
    }
}
