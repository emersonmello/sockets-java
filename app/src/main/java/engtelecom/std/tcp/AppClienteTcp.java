package engtelecom.std.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Aplicação Cliente TCP.
 */
public class AppClienteTcp {

    private static final Logger logger = Logger.getLogger(AppClienteTcp.class.getName());
    private String enderecoServidor;
    private int porta;

    public AppClienteTcp(String enderecoServidor, int porta) {
        this.enderecoServidor = enderecoServidor;
        this.porta = porta;
    }

    /**
     * Estabelece a comunicação com o servidor TCP.
     * @param mensagem Mensagem a ser enviada para o servidor TCP.
     * @return Resposta do servidor TCP.
     */
    public String comunicacao(String mensagem){
        String respostaDoServidor = "";
        // Estabelecendo a conexão com o tcp
        try (Socket socket = new Socket(enderecoServidor, porta)) {
            logger.info("Conectado ao servidor " + enderecoServidor + ":" + porta);

            // Estabelecendo os fluxos de entrada e saída
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Somente bytes podem ser enviados e aqui estamos enviando uma string, por isso usamos um OutputStreamWriter com UTF-8
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);

            // Enviando a mensagem para o servidor TCP
            outputStreamWriter.write(mensagem+ "\n");
            outputStreamWriter.flush();

            // Lendo a resposta do servidor TCP
            respostaDoServidor = bufferedReader.readLine();

        } catch (Exception e) {
            logger.severe("Erro: " + e.getMessage());
        }
        return respostaDoServidor;
    }

    public static void main(String[] args) {
        System.out.println("\n\n\u2591\u2592\u2592 Iniciando o cliente \u2592\u2592\u2591\n");

        String enderecoServidor = "localhost";
        int porta = 12345;
        // Verifica se o usuário informou o endereço e a porta do tcp como argumentos
        if (args.length == 2) {
            enderecoServidor = args[0];
            porta = Integer.parseInt(args[1]);
        }

        AppClienteTcp appClienteTcp = new AppClienteTcp(enderecoServidor, porta);
        String resposta = appClienteTcp.comunicacao("Olá, eu sou o cliente TCP!");
        logger.info("<<< " + resposta);
    }
}
