package suibianxiexie;

import java.net.HttpURLConnection;
import java.net.URL;

public class FindBackFile {
	public static boolean exists(String URLName) {  
        try {  
            HttpURLConnection.setFollowRedirects(false);  
            HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();  
            con.setRequestMethod("HEAD");  
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }
	public static void main(String[] args) {
		System.out.println(exists("http://47.93.61.29:8086/TSmjbackFilePath/20171213172124-634847-2.txt"));
	}
}
