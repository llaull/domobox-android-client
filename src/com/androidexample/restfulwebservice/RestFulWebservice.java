package com.androidexample.restfulwebservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
//import java.net.URLEncoder;
//import org.apache.http.client.HttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
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
        
        //String serverURL = "http://10.119.33.10/labo/webservice.json";
        //String serverURL = "http://domobox.llovem.eu/resources.json";
        String serverURL = "http://stockage.llovem.eu/api/domobox/modules.php";

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
        String OutputData = "";
        
        
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
            
            /**
             * @param  ***************************************************/
            return null;
        }
        
        
        //fonction 
        protected String SauvegardeLocal(String contenuJson)  {

           	
           	// faire une fonction avec en param 3 trucs, path, nom fichier, content
                
                
                try {
               	 File sdCard = Environment.getExternalStorageDirectory();
               	 
               	 File dir = new File (sdCard.getAbsolutePath() + "/json/");
               	 dir.mkdirs();
               	 File file = new File(dir, "test.json");
               	 //OutputData += file.getAbsolutePath() + "\n";
                 //OutputData += contenuJson;
        			OutputStreamWriter wr = new OutputStreamWriter(new FileOutputStream(file));
        			try {
        				wr.write(contenuJson);//ecrit le fichier
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        				OutputData += e.getLocalizedMessage() + "\n";
        			}
        			wr.flush();
        			wr.close();
        		} catch (FileNotFoundException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        			OutputData += e.getLocalizedMessage() + "\n";
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        			OutputData += e.getLocalizedMessage() + "\n";
        		}
                
                return contenuJson;
            }        
        
        @SuppressLint("NewApi")
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
                
                
                                      
                try {
                                            
                     /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                     /*******  Returns null otherwise.  *******/
                     
                	
                	// faire une fonction avec en param 3 trucs, path, nom fichier, content
                     JSONArray jsonMainNode = new JSONArray(SauvegardeLocal(Content));
                     /*********** Process each JSON Node ************/
                     
                     /*try {
                    	 File sdCard = Environment.getExternalStorageDirectory();
                    	 //File sdCard = Environment.getExternalStoragePublicDirectory(DOWNLOAD_SERVICE);
                    	 File dir = new File (sdCard.getAbsolutePath() + "/json/");
                    	 dir.mkdirs();
                    	 File file = new File(dir, "test.json");
                    	 OutputData += file.getAbsolutePath() + "\n";
						OutputStreamWriter wr = new OutputStreamWriter(new FileOutputStream(file));
						try {
							wr.write(Content);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							OutputData += e.getLocalizedMessage() + "\n";
						}
						wr.flush();
						wr.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						OutputData += e.getLocalizedMessage() + "\n";
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						OutputData += e.getLocalizedMessage() + "\n";
					}*/
                     
                      
                     List<String> emplacement = new ArrayList<String>();
                     for(int i=0; i < jsonMainNode.length(); i++) 
                     {
                         /****** Get Object for each JSON node.***********/
                         JSONObject c = jsonMainNode.getJSONObject(i);
                          
                         /******* Fetch node values **********/
                         
                         String module_emplacement = c.optString("module_emplacement");
                         emplacement.add(module_emplacement);
                        
                         //Log.i("JSON parse", song_name);
                    }
                     
                     Set<String> set = new TreeSet<String>();
                     set.addAll(emplacement);
                     Object[] tt = set.toArray();
                     
                     //boucle qui liste les emplacements
                     for(int i=0; i<tt.length; i++)
                     {
                    	 OutputData += "-- "+ tt[i] +" \n ";
                    	 
                    	//boucle qui liste les modules
                    	 for(int j=0; j < jsonMainNode.length(); j++) 
                         {
                             /****** Get Object for each JSON node.***********/
                             JSONObject c = jsonMainNode.getJSONObject(j);
                              
                             /******* Fetch node values **********/
                             String sonde_type			= c.optString("nom_sensor");
                             String sonde_valeur 		= c.optString("sonde_valeur");
                             String module_emplacement  = c.optString("module_emplacement");
                             
                            //si emplacement est l'emplacement du modules 
                             //OutputData += Normalizer.normalize((String)tt[i],Form.NFC) + " ";
                             //OutputData += Normalizer.normalize((String)module_emplacement,Form.NFC) + "\n";
                            if(Normalizer.normalize((String)tt[i],Form.NFC).compareTo(Normalizer.normalize((String)module_emplacement,Form.NFC)) == 0  ){
                            	
                             OutputData += "Type de sonde       : "+ sonde_type +" \n "
                                         + "Emplacement         : "+ module_emplacement +" \n "
                                         + "Valeur              : "+ sonde_valeur +" \n " 
                                         +"--------------------------------------------\n";
                            }
                        }
                    	 
                    	 OutputData += " \n ";  	 
                    
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