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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class Cliente {

    public static void main(String[] args) {

        //Declaración de una lectura de datos para cadenas..
        Scanner lec = new Scanner(System.in);
        
        //Declaración de una lectura de datos para enteros..
        Scanner lei = new Scanner(System.in);
        try {
            int port = 6789;
            String host = "localhost";
            DataInputStream entrada = null;
            DataOutputStream salida = null;
            DataOutputStream salidaKey = null;
            DataInputStream entradaValue = null;

            boolean breakLoop = true;

            Socket socketTCP = new Socket(host, port);
            entrada = new DataInputStream(socketTCP.getInputStream());
            System.out.println(entrada.readUTF());

            salida = new DataOutputStream(socketTCP.getOutputStream());
            salida.writeUTF("Muchas Gracias");

            //Bucle de repeción mientras el usuario ponga true..
            while (breakLoop) {
                //Ingreso de la información..
                System.out.print("Ingrese una palabra: ");
                String key = lec.nextLine().toUpperCase();

                //Envio de la key..
                salidaKey = new DataOutputStream(socketTCP.getOutputStream());
                salidaKey.writeUTF(key);

                //En este apartado obtenemos la respuesta del server..
                entradaValue = new DataInputStream(socketTCP.getInputStream());
                String valueKey = entradaValue.readUTF();
                
                //Vericate if the server responded with result or no
                if (valueKey.equals("null")) {
                    System.out.println("No se ha encontrado resultados, de la clave: " + key + "\n");
                    Thread.sleep(1000);
                } else {
                    System.out.println("\nEl valor de: " + key + " : " + valueKey);
                    Thread.sleep(1000);
                }

                //If resalizar o no de nuevo la buscqueda de otro elemento
                System.out.print("\nDesea realizar otra busqueda: 1 : SI - 2 : NO > ");
                int breakL = lei.nextInt();

                if (breakL == 2) {
                    salidaKey = new DataOutputStream(socketTCP.getOutputStream());
                    salidaKey.writeUTF("nullE");
                    breakLoop = false;
                } else {
                    breakLoop = true;
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
