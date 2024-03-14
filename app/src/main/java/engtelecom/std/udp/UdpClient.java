package engtelecom.std.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpClient implements Runnable {

    private final String host;
    private final int porta;

    public UdpClient(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public String comunicacao(String mensagem) {
        String resposta = "";
        try (DatagramSocket socket = new DatagramSocket()) {
            // Converte a mensagem para bytes
            byte[] buffer = mensagem.getBytes();

            // Cria um pacote com os dados da mensagem e o endereço do servidor
            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, java.net.InetAddress.getByName(this.host),
                    this.porta);

            // Envia o pacote
            socket.send(pacote);
            System.out.println("Mensagem enviada: " + mensagem);

            // Cria um pacote para receber os dados
            DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);

            // Aguarda a chegada de um pacote
            System.out.println("Aguardando resposta...");
            socket.receive(pacoteRecebido);

            // Exibe os dados recebidos
            resposta = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
            System.out.println("Resposta do servidor: " + resposta);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return resposta;
    }

    @Override
    public void run() {
        System.out.println("\u2591\u2592\u2592 Cliente UDP \u2592\u2592\u2591");
        this.comunicacao("Olá, eu sou o cliente UDP!");
    }

}
