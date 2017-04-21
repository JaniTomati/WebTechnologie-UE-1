// source Benno Stein lecture unit-de-network-protocol3, p.31-32


package Exercise7;
import java.net.*;
import java.io.*;
import java.util.Scanner;



public class SiteLoader{

    public static String load(String urlString) throws IOException{

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage());

        InputStream in = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String curline;
        StringBuilder content = new StringBuilder();

        while( (curline = br.readLine()) != null ){

            content.append(curline + '\n');
        }



        br.close();
        connection.disconnect();

        return content.toString();
    }

    public static void main(String[] args){

        System.out.println("Type in your netspeak request: (e.g. waiting for ? response) ");

        Scanner input = new Scanner(System.in);
        String answer = input.nextLine();        
        
        
        try{   

            String req = URLEncoder.encode(answer, "UTF-8");
            String content = SiteLoader.load("http://api.netspeak.org/netspeak3/search?query="+req);
            System.out.println(content);

        }catch(MalformedURLException e) { 

            System.out.println("MalformedURLException:" + e.getMessage());
        }catch(IOException e) {
            
            System.out.println("IOException:" + e.getMessage());
        }
    }
}