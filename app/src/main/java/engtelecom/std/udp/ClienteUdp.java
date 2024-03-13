package engtelecom.std.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClienteUdp {

    private String host;
    private int porta;

    public ClienteUdp(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void comunicacao(String mensagem) {
        try (DatagramSocket socket = new DatagramSocket()) {
            // Converte a mensagem para bytes
            byte[] buffer = mensagem.getBytes();

            // Cria um pacote com os dados da mensagem e o endereço do servidor
            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, java.net.InetAddress.getByName(this.host), this.porta);

            // Envia o pacote
            socket.send(pacote);
            System.out.println("Mensagem enviada: " + mensagem);

            // Cria um pacote para receber os dados
            DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);

            // Aguarda a chegada de um pacote    
            System.out.println("Aguardando resposta...");
            socket.receive(pacoteRecebido);

            // Exibe os dados recebidos
            String resposta = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
            System.out.println("Resposta do servidor: " + resposta);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        System.out.println("\u2591\u2592\u2592 Cliente UDP \u2592\u2592\u2591");

        String host = "localhost";
        int porta = 9876;

        // Verifica se o usuário informou o endereço e a porta do servidor como argumentos
        if (args.length == 2) {
            host = args[0];
            porta = Integer.parseInt(args[1]);
        }
    
        // Cria um cliente UDP
        ClienteUdp cliente = new ClienteUdp(host, porta);

        // Envia uma mensagem para o servidor
        cliente.comunicacao("Olá, eu sou o cliente UDP!");        
    }
}
