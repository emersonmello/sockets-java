package engtelecom.std.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class AtenderCliente implements Runnable{

    private Socket cliente;

    public AtenderCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        

        if (cliente != null) {
            System.out.println("Cliente conectado: " + cliente.getInetAddress()+":"+cliente.getPort());
            try {
                // Estabelecendo os fluxos de entrada e saída
                // Somente bytes podem ser enviados e aqui o cliente vai enviar uma string, por isso usamos com UTF-8
                // Isso é um acordo entre o cliente e o servidor
                BufferedReader entradaDoCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream(), "UTF-8"));
                DataOutputStream saidaParaOCliente = new DataOutputStream(cliente.getOutputStream());

                // Lendo a mensagem do cliente
                String mensagemDoCliente = entradaDoCliente.readLine();
                System.out.println("Mensagem recebida do cliente: " + mensagemDoCliente+"\n");

                // Enviando a resposta para o cliente
                saidaParaOCliente.writeBytes("Mensagem recebida com sucesso!\n");

            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }else{
            System.out.println("Erro: cliente não conectado");
        }
    }
    
}
