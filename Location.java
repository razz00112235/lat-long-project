import java.util.*;
import java.net.*;
import java.io.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Location{

    static  String cityname,statename;
    static double latdata,longdata;
    static void frame_latlong()
    {   

        JLabel state,city,msg,lat_msg,long_msg;
        JTextField state_name,city_name;
        JFrame frame=new JFrame("Find LAT/LONG");
        frame.setSize(400, 500);
        msg=new JLabel("LAT/LONG Converter");
        lat_msg=new JLabel("Latidue:- ");
        lat_msg.setBounds(100, 300, 200, 30);
        long_msg=new JLabel("Longitude:- ");
        long_msg.setBounds(100, 320, 200, 30);
        msg.setBounds(120,30,220,40);
        msg.setFont(new Font("Serif", Font.ITALIC, 18));
        state=new JLabel("Enter State name");
        state.setBounds(30,100,200,40);
        state_name=new JTextField();
        state_name.setBounds(150, 100, 200, 40);

        city=new JLabel("Enter City name");
        city.setBounds(30,150,120,40);
        city_name=new JTextField();
        city_name.setBounds(150, 150, 200, 40);

        JButton find=new JButton("Find");
        find.setBounds(220, 220, 60, 40);
        frame.add(msg);frame.add(find);frame.add(lat_msg);frame.add(long_msg);
        frame.add(state);frame.add(state_name);
        frame.add(city);frame.add(city_name);
        frame.setLayout(null);
        frame.setVisible(true);
       
        
        
      
        ActionListener click=new ActionListener(){
            int count=0;
            public void actionPerformed(ActionEvent e)           
            {
                try {
                    cityname=city_name.getText();
                    statename=state_name.getText();
                    JSONParser par3=new JSONParser();
                    Object objdata = par3.parse(new FileReader("statedata.json"));
                    JSONObject obj2=(JSONObject)objdata;
                    
                    
                    String state_data = statename.substring(0, 1).toUpperCase() + statename.substring(1);
            
                    Object State_data=obj2.get(state_data);
                  
                     URL url=new URL("http://api.openweathermap.org/geo/1.0/direct?q="+cityname+","+State_data+",IN&limit=10&appid=b535fff314794c34cb57ab212fb13379");
                     HttpURLConnection url_data=(HttpURLConnection)url.openConnection();
                     url_data.setRequestMethod("GET");
            
                     InputStreamReader in=new InputStreamReader(url_data.getInputStream());
                     BufferedReader in1=new BufferedReader(in);
                     String st="";
                      st=in1.readLine();            
                             JSONParser par=new JSONParser();
                             JSONArray arr=(JSONArray)par.parse(st);
                                 JSONObject obj=(JSONObject)arr.get(0);                      
                                 Object lat,lng;
                             
                                 lat=obj.get("lat");
                                 lng=obj.get("lon");
                    lat_msg.setText("Latitude:- "+lat);
                    long_msg.setText("Longitude:- "+lng);
                                
                            
                }
                catch(Exception ee)
                {
                    System.out.println(ee);
                }
            }
        };
        find.addActionListener(click);
        

    }
    public static void main(String[] args) {
   
        frame_latlong();
         
        
     
     
    }
}