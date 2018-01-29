/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.bson.Document;


public class Start {

    
    static ArrayList<String[]> datos=new ArrayList<>();
    
    public static void main(String[] args) {
        
        leer();
     //mostrar();
        Insercion();
          
    }
    
    //Para leer el documento
    public static void leer() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            archivo = new File("final.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            System.out.println("Leyendo el contendio del archivo.txt");
            String linea;
            
            while ((linea = br.readLine()) != null) {
             String separador = Pattern.quote("|");
             String[] parts = linea.split(separador);
             datos.add(parts);
         
            }    
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
           
    }
    //Separar y guardar en una Array
    public static void separar(String str)
    {
        String separador = Pattern.quote("|");
        String[] parts = str.split(separador);
        datos.add(parts);
    }
    
    public static void mostrar(){
        for (int i = 0;i < datos.size(); i++) {
            System.out.println(""+datos.get(i)[0]);
        }
    }
    
    public static MongoClient Conexion(String lo,Integer puerto)
    {
        MongoClient mongoClient = new MongoClient( lo , puerto );
        return mongoClient;
    }
    
    public static void Insercion() {
        
        long tiempo_inicio, tiempo_final;
        MongoClient mongoClient=Conexion("localhost",27017);
       
        // Creating Credentials // cambia en nombre de la base
        MongoCredential credential; 
        credential = MongoCredential.createCredential("sampleUser", "prueba", 
                "password".toCharArray());
        System.out.println("Conexion exitosa");

        // Accessing the database 
        MongoDatabase database = mongoClient.getDatabase("prueba");
        System.out.println("Credentials ::" + credential);

        //Creating a collection 
        database.createCollection("persona");
        System.out.println("Colección creada");
        MongoCollection<Document> collection = database.getCollection("persona");
        System.out.println("Coleccion persona creada");

        tiempo_inicio  = System.currentTimeMillis();
        //List<Document> documents = new ArrayList<Document>(); 
        for (int i = 0; i < datos.size(); i++) {
                Document document = new Document()
                .append("cedula", datos.get(i)[0])
                .append("nombre", datos.get(i)[1])
                .append("apellido",datos.get(i)[2])
                .append("fechaNacimiento",datos.get(i)[3]); 
        // Para insertar una coleecion
            collection.insertOne(document);
        }
        tiempo_final  = System.currentTimeMillis();
        System.out.println ( "El tiempo transcurrido fue de:"+ (tiempo_final - tiempo_inicio ) +" millisegundos");
        // Para insertar varias coleeciones
        //collection.insertMany(documents);
        System.out.println("Documentos insertados");
    }
    
}
