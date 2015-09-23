/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.pserver;

import gr.demokritos.iit.pserver.api.Admin;
import gr.demokritos.iit.pserver.api.Personal;
import gr.demokritos.iit.pserver.ontologies.Client;
import gr.demokritos.iit.pserver.ontologies.User;
import gr.demokritos.iit.pserver.storage.PServerHBase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import org.apache.hadoop.hbase.exceptions.DeserializationException;

/**
 *
 * @author Panagiotis Giotis <giotis.p@gmail.com>
 */
public class PServerTest {

    public static void main(String[] args) throws IOException, DeserializationException {

        String clientInfo = "{"
                + "\"mail\":\"info@mail.com\","
                + "\"info1\":\"info1value\""
                + "}";

        System.out.println("===============================================");
        Client cl =new Client("root", "root");
        cl.setAuthenticatedTimestamp(new Date().getTime());
         Admin admin= new Admin(new PServerHBase(),cl );
         HashMap<String, String> info = new HashMap<>();
         
         admin.addClient("root", "root", info);
//        String userJSON ="{\"attributes\":{\"gender\":\"male\",\"age\": \"18\"},\"features\": {\"ftr1\": \"34\",\"ftr3\": \"3\",\"ftr5\": \"4\"}}";


//        User user = new User("panagiotis");
//        user.userCreator(userJSON);
//        System.out.println(user.getFeatures());
//        System.out.println(user.getAttributes());
//        HBase db = new HBase("Admin");
//        PServerHBase db = new PServerHBase("1ec1caa1-3fd9-3afb-8933-79a17f82e7a8");
//        Admin ad =new Admin(db);
//        System.out.println(ad.addClient("pnewDlient", "123", clientInfo));
//        System.out.println(ad.getClients());
//        System.out.println(ad.deleteClient("newClient"));

//        Personal ps = new Personal(db);
//        System.out.println(ps.addUsers("{\"test1\":{\"attributes\":{\"gender\": \"male\",\"age\": \"18\"},\"features\": {\"ftr1\": \"34\",\"ftr33\": \"3\",\"ftr5\": \"4\"}}}"));
//        System.out.println(ps.addUsers("{\"testko\":{},\"testko2\":{}ZZ,\"testko3\":{}}"));
//        System.out.println(ps.getUsers(null,null));
//        System.out.println(ps.getUsers("newsumUI", null));
//        System.out.println(ps.getUserAttributes("user1UIDps",null, null));
//        System.out.println(ps.getUserProfile("user1",null, null));
//        System.out.println(ps.getUserProfile("test1",null, 2));
//        System.out.println(ps.deleteUsers(null));
//        System.out.println(ps.setUsersAttributes("{\"user1\":{\"gender\":\"male\"}}"));
//        System.out.println(ps.setUsersFeatures("{\"user1\":{\"category1\":\"top\",\"ftr56\":\"0\"}}"));
//        System.out.println(ps.modifyUsersFeatures("{\"user1\":{\"ftr1\":\"5\"}}"));
        //Crete new client
//        Client cl = new Client("root", "root");
//        //Set client auth time
//        cl.setAuthenticatedTimestamp(new Date().getTime());
////        //Create new personal instance
//        Personal instance = new Personal(new PServerHBase(), cl);
////        instance.deleteUsers(null);
//
//        Random r = new Random(50);
//        ArrayList<User> usersList = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//
//            //Create new User
//            String username = "TestUser" + i;
//            User user = new User(username);
//
//            HashMap<String, String> attributes = new HashMap<>();
//
//            if (r.nextBoolean()) {
//                attributes.put("gender", "male");
//            } else {
//                attributes.put("gender", "female");
//            }
//            attributes.put("age", Integer.toString(r.nextInt(45)));
//            if (r.nextBoolean()) {
//                attributes.put("country", "en");
//            } else {
//                attributes.put("country", "gr");
//            }
//            
//            HashMap<String, String> info = new HashMap<>();
//            info.put("Username", username);
//            user.setInfo(info);
//
//            attributes.put("oc", Integer.toString(r.nextInt(3)));
//
//            user.setAttributes(attributes);
//
//            HashMap<String, String> features = new HashMap<>();
//            features.put("category.sport", Integer.toString(r.nextInt(30)));
//            features.put("category.eco", Integer.toString(r.nextInt(30)));
//            features.put("txt.basket", Integer.toString(r.nextInt(50)));
//            features.put("txt.hellas", Integer.toString(r.nextInt(50)));
//            user.setFeatures(features);
//            System.out.println(username + " --> " + attributes);
//            usersList.add(user);
//        }
//            instance.addUsers(usersList);

        System.out.println("===============================================");
    }

}



//TestUser2 --> {country=en, gender=male, oc=1, age=2}
//TestUser4 --> {country=gr, gender=male, oc=2, age=24} -
//TestUser0 --> {country=en, gender=male, oc=1, age=33} -
//TestUser1 --> {country=en, gender=male, oc=2, age=43} -
//TestUser3 --> {country=en, gender=female, oc=2, age=44}