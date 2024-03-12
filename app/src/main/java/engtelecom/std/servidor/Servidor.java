package engtelecom.std.servidor;

import java.net.ServerSocket;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("\u2591\u2592\u2592 Iniciando o servidor \u2592\u2592\u2591");

        int porta = 12345;
        
        try (ServerSocket servidor = new ServerSocket(porta);) {
            
            System.out.println("Servidor aguardando conexões em " + servidor.getInetAddress() +":" + porta);
            System.out.println("Pressione CTRL+C para encerrar o processo do servidor\n\n");
            
            while (true) {
                // Aceita a conexão de um cliente e cria uma nova thread para atendê-lo
                new Thread(new AtenderCliente(servidor.accept())).start();
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
