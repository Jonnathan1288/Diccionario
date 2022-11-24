/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class Servidor {

    public static void main(String[] args) {
        try {
            int port = 6789;
            Socket conectionClient;

            DataOutputStream salida = null;
            DataOutputStream salidaDictionary = null;
            DataInputStream entrada = null;
            DataInputStream entradaValue = null;

            boolean keyFind = false;
            String keyValue = null;

            ServerSocket socketTCP = new ServerSocket(port);
            conectionClient = socketTCP.accept();

            System.out.println("Cliente: " + conectionClient.getInetAddress().getHostName() + " conectado.");

            salida = new DataOutputStream(conectionClient.getOutputStream());
            salida.writeUTF("La conexión se a establecido.");

            entrada = new DataInputStream(conectionClient.getInputStream());
            System.out.println(entrada.readUTF());

            boolean breakServer = true;
            while (breakServer) {

                entradaValue = new DataInputStream(conectionClient.getInputStream());
                String keyResult = entradaValue.readUTF();
                if (keyResult.equals("nullE")) {
                    breakServer = false;
                } else {
                    System.out.println("Tenemos como salida -> " + keyResult);

                    //El hasMap donde hacemos la creacion y al mismo tiempo el ingreso de su key and value
                    HashMap<String, String> mapBits = new HashMap<String, String>();
                    mapBits.put("SALUDO", "Un saludo puede catalogarse como un respeto.");
                    mapBits.put("IP", "protocolo de internet.");
                    mapBits.put("TCP", "Protocolo de transferencia comunicación");
                    mapBits.put("INTERNET", "Red de redes.");
                    mapBits.put("LAN", "Redes de area local.");
                    mapBits.put("MAN", "Redes de area metropolitana.");
                    mapBits.put("WAN", "Redes de area extensa.");
                    mapBits.put("HARDWARE", "Partes fisicas, tangibles");
                    mapBits.put("SOFTWARE", "Son las partes logicas, las partes intangibles.");
                    mapBits.put("SO", "Sistema operativo");

                    for (HashMap.Entry<String, String> each : mapBits.entrySet()) {
//                System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());        

                        if (each.getKey().equals(keyResult)) {
                            System.out.println("En el servidor tenemos: " + each.getValue());
                            keyValue = each.getValue();
                            keyFind = true;
                            break;
                        }
                    }

                    if (keyFind == true) {
                        salidaDictionary = new DataOutputStream(conectionClient.getOutputStream());
                        salidaDictionary.writeUTF(keyValue);

                    } else {
                        salidaDictionary = new DataOutputStream(conectionClient.getOutputStream());
                        salidaDictionary.writeUTF("null");
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
