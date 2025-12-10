package Ecoembes.gateway;

import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;

public class ContSocketGateway implements PlantaGatewayInterface {
	
	@Override
    public int getCapacidad(String url, LocalDate fecha) {
        // La URL viene como "localhost:9000". Hay que separarla.
        String[] partes = url.split(":");
        String host = partes[0];
        int puerto = Integer.parseInt(partes[1]);

        System.out.println("u001F4E1 [ContSocket Gateway] Conectando a " + host + ":" + puerto);

        try (Socket socket = new Socket(host, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Protocolo: GET_CAPACITY:YYYY-MM-DD
            String comando = "GET_CAPACITY:" + fecha.toString();
            out.println(comando);
            
            // Respuesta esperada: OK:150 o ERR:NO_DATA
            String respuesta = in.readLine();
            System.out.println("u001F4E5 [ContSocket Gateway] Respuesta: " + respuesta);

            if (respuesta != null && respuesta.startsWith("OK:")) {
                return Integer.parseInt(respuesta.split(":")[1]);
            }

        } catch (Exception e) {
            System.err.println("❌ Error socket ContSocket: " + e.getMessage());
        }

        throw new RuntimeException("No se pudo obtener capacidad de ContSocket");
    }
}
