/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;

import spark.Request;
import spark.Response;
import static spark.Spark.*;

// Site web:
// http://localhost:4567/


public class WebServer {

    static int temperatureChauffe=20;        // Temperature de chauffage par defaut
    static int temperatureCourante=20;
    static boolean radiateurMarche=false;
    
    public static void main(String[] args) 
    {    
        // Traitement des requetes HTTP POST venant du thermometre
        post("/thermometre", (Request request, Response response) -> {
            // Recupere la temperature
            String val=request.queryParams("temperature");
            temperatureCourante=Integer.parseInt(val);
            // Fait fonctionner le radiateur suivant la temperature
            radiateurMarche= temperatureCourante<temperatureChauffe;
            System.out.println("Temperature recue: "+val);
            return "OK";
            });
        // Traitement des requetes HTTP GET venant du radiateur
        get("/radiateur", (request, response) -> {
            // La reponse est un string au format JSON
            if (radiateurMarche)
                return "{\"radiateurMarche\": \"true\"}";
            else
                return "{\"radiateurMarche\": \"false\"}";
            });
        // Traitement des requetes HTTP GET venant du SmartPhone pour recuperer la temperature courante
        get("/thermometre", (request, response) -> {
            // La reponse est un string au format JSON
            return "{\"temperatureCourante\":"+temperatureCourante+"}";
            });
        // Traitement des requetes HTTP POST venant du SmartPhone pour fixer la temperature chauffage
        post("/chauffage", (Request request, Response response) -> {
            // Recupere la temperature
            String val=request.queryParams("temperature");
            temperatureChauffe=Integer.parseInt(val);
            // Fait fonctionner le radiateur suivant la temperature
            radiateurMarche= temperatureCourante<temperatureChauffe;
            System.out.println("Nouvelle temperature de chauffage: "+temperatureChauffe);
            return "OK";
            });
    }
    
}