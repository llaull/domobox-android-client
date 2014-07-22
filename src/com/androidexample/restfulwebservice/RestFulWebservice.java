package com.androidexample.restfulwebservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
//import java.net.URLEncoder;
//import org.apache.http.client.HttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
/*import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;*/
import android.widget.TextView;

public class RestFulWebservice extends Activity {

    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
     
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_ful_webservice);  
        
        String serverURL = "http://10.119.33.10/labo/webservice.json";

        new LongOperation().execute(serverURL);
         
    }
     
     
    // Class with extends AsyncTask class
    private class LongOperation  extends AsyncTask<String, Void, Void> {
         
        // Required initialization
        
        //private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(RestFulWebservice.this);
        String data =""; 
        
        TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
        //int sizeData = 0;  
 
        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            
            /************ Make Post Call To Web Server ***********/
            BufferedReader reader=null;
   
                 // Send data 
                try
                { 
                  
                   // Defined URL  where to send data
                   URL url = new URL(urls[0]);
                     
                  // Send POST data request
       
                  URLConnection conn = url.openConnection(); 
                  conn.setDoOutput(true); 
                  OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
                  wr.write( data ); 
                  wr.flush(); 
              
                  // Get the server response 
                   
                  reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                        {
                               // Append server response in string
                               sb.append(line + "\n");
                        }
                    
                    // Append Server Response To Content String 
                   Content = sb.toString();
                }
                catch(Exception ex)
                {
                    Error = ex.getMessage();
                }
                finally
                {
                    try
                    {
         
                        reader.close();
                    }
       
                    catch(Exception ex) {}
                }
            
            /*****************************************************/
            return null;
        }
         
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
             
            // Close progress dialog
            Dialog.dismiss();
             
            if (Error != null) {
                 
                jsonParsed.setText("Output : "+Error);
                 
            } else {
              
                // affiche le json brut
                //uiUpdate.setText( Content );
                
             /****************** Start Parse Response JSON Data *************/
                
                String OutputData = "";
                                      
                try {
                      
                     /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                     //jsonResponse = new JSONObject(Content);
                      
                     /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                     /*******  Returns null otherwise.  *******/
                     //JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
                     JSONArray jsonMainNode = new JSONArray(Content);
                     /*********** Process each JSON Node ************/
  
                      
  
                     for(int i=0; i < jsonMainNode.length(); i++) 
                     {
                         /****** Get Object for each JSON node.***********/
                         JSONObject c = jsonMainNode.getJSONObject(i);
                          
                         /******* Fetch node values **********/
                         String sonde_type       = c.optString("nom_sensor");
                         String module_lieu     = c.optString("module_emplacement");
                         String sonde_valeur = c.optString("sonde_valeur");
                          
                        
                         OutputData += "Type de sonde       : "+ sonde_type +" \n "
                                     + "Emplacement         : "+ module_lieu +" \n "
                                     + "Valeur              : "+ sonde_valeur +" \n " 
                                     +"--------------------------------------------\n";
                        
                         //Log.i("JSON parse", song_name);
                    }
                 /****************** End Parse Response JSON Data *************/     
                     
                     //Show Parsed Output on screen (activity)
                     jsonParsed.setText( OutputData );
                     
                      
                 } catch (JSONException e) {
          
                     e.printStackTrace();
                 }
  
                 
             }
        }
         
    }
    
}
