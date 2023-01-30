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

public class Weather{

    static  String cityname,statename;
    static String temp_data;
    String weather_data;
    static void frame_latlong()
    {   

        JLabel state,city,msg,temp_lb,weather_data_lb;
        JTextField state_name,city_name;
        JFrame frame=new JFrame("City Weather");
        frame.setSize(400, 500);
        msg=new JLabel("City Weather In India");
        temp_lb=new JLabel();
        temp_lb.setBounds(100, 300, 200, 30);
        weather_data_lb=new JLabel();
        weather_data_lb.setBounds(100, 320, 200, 30);
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
        frame.add(msg);frame.add(find);frame.add(temp_lb);frame.add(weather_data_lb);
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
                    // lat_msg.setText("Latitude:- "+lat);
                    // long_msg.setText("Longitude:- "+lng);
                    URL url2=new URL("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lng+"&appid=b535fff314794c34cb57ab212fb13379");
                    HttpURLConnection url_data2=(HttpURLConnection)url2.openConnection();
                    url_data2.setRequestMethod("GET");
           
                    InputStreamReader in2=new InputStreamReader(url_data2.getInputStream());
                    BufferedReader in12=new BufferedReader(in2);
                    String st2="";         
                    st2=in12.readLine();    
                   //  System.out.println(st2);           
                    JSONParser par2=new JSONParser();
                    JSONObject arr2=(JSONObject)par2.parse(st2);             
                    JSONObject obj3=(JSONObject)arr2.get("main");
                    Object city_temp=obj3.get("temp");
                    Double city_tmp=(Double)obj3.get("temp");
                    city_tmp=city_tmp-273.15;         
                    temp_lb.setText("Today Temprature is "+String.format("%.2f", city_tmp)+"°C");
                   // System.out.println("Temprature is "+String.format("%.2f", city_tmp)+"°C");  
           
                    JSONArray wth = (JSONArray)arr2.get("weather");
                    JSONObject ob4 = (JSONObject)wth.get(0);
                   
                    Object weather=ob4.get("main");   
                   String wthr=(String)weather;   
                    weather_data_lb.setText("Today Weather is: "+wthr);
                  // System.out.println("Today Weather is: "+weather);
                    
                    // Object Visible=arr2.get("visibility");
                   // System.out.println("Visibility in "+city+" is "+Visible+" Meters");
                    // Object wind=arr2.get("wind");
                   
                    // JSONObject winddata=(JSONObject)wind;
                   // System.out.println("Air Speed is "+winddata.get("speed")+" km/h");
                                
                            
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