/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartphone;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.*;

/**
 *
 * @author Frederic
 */
public class SmartPhone {

    // Methode pour afficher la temperature courante
    private static void afficherTemperatureCourante() throws IOException 
    {
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        String url = "http://localhost:4567/thermometre";
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(false); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        InputStream response = connection.getInputStream();
        // Obtenir le resultat de la requete
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            JSONObject obj = new JSONObject(responseBody);
            int temperatureCourante = obj.getInt("temperatureCourante");
            System.out.println("Temperature courante:" + temperatureCourante);
        }
    }


    // Methode pour changer la temperature de chauffage
    private static void changerTemperatureChauffage(int temperatureChauffage) throws IOException 
    {        
        // Definir l'adresse URL de la requete
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        String url = "http://localhost:4567/chauffage";
        String query = String.format("temperature=%s", temperatureChauffage);
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        try (OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes(charset));
        }

        InputStream response = connection.getInputStream();
        // Obtenir le resultat de la requete
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            //System.out.println("Reponse de la requete HTTP POST:" + responseBody);
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Affiche un menu
        Scanner in = new Scanner(System.in);
        boolean doExit=false;
        do
        {
            System.out.println("Choisir une option:");
            System.out.println(" 1: Modifier la temperature de chauffage");
            System.out.println(" 2: Afficher la temperature courante");
            System.out.println(" 3: Arreter le programme");
            int choix=in.nextInt();
            switch(choix)
            {
                case 1:
                    System.out.println("Entrer la nouvelle temperature de chauffage");
                    int temperatureChauffage=in.nextInt();
                    changerTemperatureChauffage(temperatureChauffage);
                    break;
                case 2:
                    afficherTemperatureCourante();
                    break;
                case 3:
                    doExit=true;
                    break;
            }
        }
        while(doExit==false);
    }
    
}
