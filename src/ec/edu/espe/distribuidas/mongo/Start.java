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
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;


public class Start {


    public static void main(String[] args) {
        
        //leer();
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
             
                separar(linea);
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
        ArrayList<String> tempo=new ArrayList<String>();
        
         for(int i=0; i<parts.length;i++)
        { 
            tempo.add(parts[i]);
        }
        System.out.println(tempo);
        
        //for(int i=0; i<1;i++)
        //{
        //    documents.add(new Document(tempo.toString(),i));
        //}
        //System.out.println(documents);
    }
    
    public static MongoClient Conexion(String lo,Integer puerto)
    {
        MongoClient mongoClient = new MongoClient( lo , puerto );
        return mongoClient;
    }
    
    public static void Insercion() {
        
        MongoClient mongoClient=Conexion("localhost",27017);
       
        // Creating Credentials 
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

        
        //List<Document> documents = new ArrayList<Document>();        
        Document document = new Document()
                .append("cedula", 17972)
                .append("nombre", "database")
                .append("apellido","fdkfkf")
                .append("fechaNacimiento", "15466");
        
        // Para insertar una coleecion
            collection.insertOne(document);
        // Para insertar varias coleeciones
        //collection.insertMany(documents);
        System.out.println("Document inserted successfully");
    }
    
}
