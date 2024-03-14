package engtelecom.std;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import engtelecom.std.udp.UdpClient;
import engtelecom.std.udp.UdpServer;


public class UdpTest {

    private static UdpClient udpClient;

    @BeforeAll
    public static void setUp() {
        new Thread(new UdpServer(9876)).start();
        udpClient = new UdpClient("localhost", 9876);
    }

    @Test
    public void envioERecimentoComSucesso(){
        String mensagem = "Olá, eu sou o cliente UDP!";
        String resposta = udpClient.comunicacao(mensagem);
        assertEquals("Olá, eu sou o servidor UDP", resposta);
    }

    @AfterAll
    public static void finalizar(){
        udpClient.comunicacao("fim");
    }
}