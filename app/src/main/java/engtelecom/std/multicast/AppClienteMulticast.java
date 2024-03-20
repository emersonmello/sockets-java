package engtelecom.std.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.net.Inet6Address;

/**
 * Aplicação Cliente de hora Multicast.
 * 
 * Recebe a hora atual de um grupo multicast.
 */
public class AppClienteMulticast {

    // Para exibir mensagens de log
    private static final Logger logger = Logger.getLogger(AppClienteMulticast.class.getName());

    private final int BUFFER_SIZE;
    private String endereco;
    private int porta;
    private List<InetAddress> interfacesDeRede;

    public AppClienteMulticast(String endereco, int porta) {
        this.BUFFER_SIZE = 256;
        this.endereco = endereco;
        this.porta = porta;
        this.interfacesDeRede = new ArrayList<>();
    }

    /**
     * Obtém as interfaces de rede disponíveis. Irá excluir a interface de loopback
     * e as interfaces IPv6.
     * 
     * @return
     */
    public List<InetAddress> obterInterfacesDeRede() {
        List<InetAddress> interfaces = new ArrayList<>();
        try {
            logger.info("Obtendo interfaces de rede...");
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress inetAddress : Collections.list(networkInterface.getInetAddresses())) {
                    // Não incluir loopback e IPv6ß
                    if (inetAddress.isLoopbackAddress() || inetAddress instanceof Inet6Address) {
                        continue;
                    }
                    interfaces.add(inetAddress);
                    String m = String.format("Nome: %s\tEndereço: %s\n", networkInterface.getDisplayName(), inetAddress);
                    logger.info(m);
                }
            }
        } catch (Exception e) {
            logger.severe("Erro: " + e.getMessage());
        }
        return interfaces;
    }

    /**
     * Inicia a aplicação cliente.
     */
    public void iniciar() {
        System.out.println("Cliente Multicast iniciado.");
        this.interfacesDeRede = obterInterfacesDeRede();

        try (MulticastSocket multicastSocket = new MulticastSocket(this.porta)) {

            // Cria o endereço multicast
            InetAddress enderecoMulticast = InetAddress.getByName(this.endereco);
            // Cria o grupo multicast
            InetSocketAddress grupo = new InetSocketAddress(enderecoMulticast, this.porta);

            // Entra no grupo multicast para todas as interfaces
            for (InetAddress inetAddress : this.interfacesDeRede) {
                multicastSocket.joinGroup(grupo, NetworkInterface.getByInetAddress(inetAddress));
            }

            byte[] buffer = new byte[this.BUFFER_SIZE];
            
            // Cria o pacote para receber a mensagem
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

            // Vai receber 5 mensagens do grupo multicast e exibir na tela
            for (int i = 0; i < 5; i++) {
                multicastSocket.receive(datagramPacket);
                String mensagemRecebida = new String(datagramPacket.getData());
                System.out.printf("Servidor: %s enviou a mensagem: %s\n", datagramPacket.getAddress(), mensagemRecebida.trim());
            }

            // Deixa o grupo multicast para todas as interfaces
            for (InetAddress inetAddress : this.interfacesDeRede) {
                multicastSocket.leaveGroup(grupo, NetworkInterface.getByInetAddress(inetAddress));
            }
        } catch (Exception e) {
            logger.severe("Erro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String enderecoMulticast = "231.0.0.0";
        int porta = 8888;

        if (args.length == 2) {
            enderecoMulticast = args[0];
            porta = Integer.parseInt(args[1]);
        }

        AppClienteMulticast appClienteMulticast = new AppClienteMulticast(enderecoMulticast, porta);
        appClienteMulticast.iniciar();
    }
}