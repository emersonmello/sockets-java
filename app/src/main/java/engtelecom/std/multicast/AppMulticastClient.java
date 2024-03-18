package engtelecom.std.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.net.Inet6Address;

/**
 * Aplicação Cliente de hora Multicast.
 * 
 * Recebe a hora atual de um grupo multicast.
 */
public class AppMulticastClient {

    // Para exibir mensagens de log
    private static final Logger logger = Logger.getLogger(AppMulticastClient.class.getName());

    private final int BUFFER_SIZE;
    private String enderecoMulticast;
    private int porta;
    private static InetAddress mcastaddr;
    private List<InetAddress> interfaces;

    public AppMulticastClient(String enderecoMulticast, int porta) {
        this.BUFFER_SIZE = 256;
        this.enderecoMulticast = enderecoMulticast;
        this.porta = porta;
        this.interfaces = new ArrayList<>();
    }

    /**
     * Obtém as interfaces de rede disponíveis. Irá excluir a interface de loopback
     * e as interfaces IPv6.
     * 
     * @return
     */
    public List<InetAddress> getNetworkInterfaces() {
        List<InetAddress> interfaces = new ArrayList<>();
        try {
            logger.info("Obtendo interfaces de rede...");
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                for (InetAddress inetAddress : Collections.list(netint.getInetAddresses())) {
                    if (inetAddress.isLoopbackAddress() || inetAddress instanceof Inet6Address) {
                        continue;
                    }
                    interfaces.add(inetAddress);
                    System.out.printf("Display name: %s\tInetAddress: %s\n", netint.getDisplayName(), inetAddress);
                }
            }
            logger.info("Interfaces de rede obtidas.\n");
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
        this.interfaces = getNetworkInterfaces();

        try (MulticastSocket s = new MulticastSocket(this.porta)) {

            // Cria o endereço multicast
            mcastaddr = InetAddress.getByName(this.enderecoMulticast);
            // Cria o grupo multicast
            InetSocketAddress group = new InetSocketAddress(mcastaddr, this.porta);

            // Entra no grupo multicast para todas as interfaces
            for (InetAddress inetAddress : this.interfaces) {
                s.joinGroup(group, NetworkInterface.getByInetAddress(inetAddress));
            }

            byte[] buffer = new byte[this.BUFFER_SIZE];
            
            // Cria o pacote para receber a mensagem
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // Vai receber 5 mensagens do grupo multicast e exibir na tela
            for (int i = 0; i < 5; i++) {
                s.receive(packet);
                String received = new String(packet.getData());
                System.out.printf("Servidor: %s enviou a mensagem: %s\n", packet.getAddress(), received.trim());
            }

            // Deixa o grupo multicast para todas as interfaces
            for (InetAddress inetAddress : this.interfaces) {
                s.leaveGroup(group, NetworkInterface.getByInetAddress(inetAddress));
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

        AppMulticastClient client = new AppMulticastClient(enderecoMulticast, porta);
        client.iniciar();
    }
}