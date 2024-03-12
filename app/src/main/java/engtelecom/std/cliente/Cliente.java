package engtelecom.std.cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        System.out.println("\n\n\u2591\u2592\u2592 Iniciando o cliente \u2592\u2592\u2591\n");

        String enderecoServidor = "localhost";
        int porta = 12345;

        // Verifica se o usuário informou o endereço e a porta do servidor como argumentos
        if (args.length == 2) {
            enderecoServidor = args[0];
            porta = Integer.parseInt(args[1]);
        }

        // Estabelecendo a conexão com o servidor
        try (Socket cliente = new Socket(enderecoServidor, porta)) {    
            System.out.println("Conectado ao servidor " + enderecoServidor + ":" + porta);

            // Estabelecendo os fluxos de entrada e saída
            BufferedReader entradaDoServidor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            // Somente bytes podem ser enviados e aqui estamos enviando uma string, por isso usamos um OutputStreamWriter com UTF-8
            OutputStreamWriter saidaParaOServidor = new OutputStreamWriter(cliente.getOutputStream(), "UTF-8");

            // Enviando a mensagem para o servidor
            saidaParaOServidor.write("Olá, servidor!\n");
            saidaParaOServidor.flush();

            // Lendo a resposta do servidor
            String respostaDoServidor = entradaDoServidor.readLine();
            System.out.println("Resposta do servidor: " + respostaDoServidor);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
