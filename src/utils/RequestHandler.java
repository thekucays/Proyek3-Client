package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RequestHandler {
	public String sendGetRequest(String requestURL){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while ((s=bufferedReader.readLine()) != null){
                sb.append(s+"\n");
            }
        } catch (Exception e){
        	e.printStackTrace();
        }

        return sb.toString();
    }
	
	public String tesReturn(String urls) throws IOException{
		// data yang dikirim ke server -  getDataUser.php?nama='edo'
		//String data = URLEncoder.encode("nama", "UTF-8") + "=" + URLEncoder.encode(nama, "UTF-8");
		String data = "nama=dummy";
		
		URL url = new URL(urls);
		URLConnection conn = url.openConnection();
		
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
            
        wr.write(data); 
        wr.flush();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        StringBuilder sb = new StringBuilder();
        String line = null;
        
        while((line = reader.readLine()) != null){
           sb.append(line);
           break;
        }
        return sb.toString();
	}
}
