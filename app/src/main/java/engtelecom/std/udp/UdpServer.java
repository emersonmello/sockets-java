package engtelecom.std.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer implements Runnable{

    private final int porta;
    private final int bufferSize;

    public UdpServer(int porta) {
        this.porta = porta;
        this.bufferSize = 1024;
    }

    private String aguardarMensagem(DatagramSocket socket, byte[] buffer) throws IOException {
        // Cria um pacote para receber os dados
        DatagramPacket pacoteRecebido = new DatagramPacket(buffer, buffer.length);

        // Aguarda a chegada de um pacote
        System.out.println("Aguardando pacote...");
        socket.receive(pacoteRecebido);

        // Exibe os dados recebidos
        String mensagem = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
        System.out.println("Mensagem recebida: " + mensagem);

        // Envia a resposta
        String r = "Olá, eu sou o servidor UDP!";
        byte[] resposta = r.getBytes();
        DatagramPacket pacoteResposta = new DatagramPacket(resposta, resposta.length, pacoteRecebido.getAddress(), pacoteRecebido.getPort());
        socket.send(pacoteResposta);
        return mensagem;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(this.porta)) {
            // Cria um buffer de bytes para receber os dados
            byte[] buffer = new byte[bufferSize];

            System.out.println("\u2591\u2592\u2592 Servidor UDP iniciado na porta " + this.porta);
            System.out.println("Pressione CTRL+C para encerrar...\n");
            String mensagem = "";
            while(!mensagem.equals("fim")) {
                mensagem = aguardarMensagem(socket, buffer);
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}