package net;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HTTPClient
{
	private String 				user_agent 		= "Mozilla/5.0";
	private List<String> 		coockies 		= new ArrayList<String>();
	private int 				responseCode 	= 200;
	private String 				url;
	private String 				response;
	private Proxy				proxy 			= null;
	
	 
	public HTTPClient( ) {
		coockies.add( "session=processform=0" );
		coockies.add( "lng=1" );
	}
	
	public void setUrl( String url ) {
		this.url = url;
	}
	
	public void setUserAgent( String user_agent ) {
		this.user_agent = user_agent;
	}
	
	public void setProxy( String host, int port ) {
		this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress( host, port ));
	}
	
	public int getResponseCode() {
		return this.responseCode;
	}
	
	public String getResponse() {
		return this.response;
	}
	
	public void clearCookie() {
		coockies.clear();
	}
 
	// HTTP GET request
	public void sendGet() {

		System.out.println("Send Http GET request");
		
		URL obj;
		try {
			obj = new URL( this.url );
		

			HttpURLConnection con;
			
			if( this.proxy != null )
				con = (HttpURLConnection) obj.openConnection( this.proxy );
			else
				con = (HttpURLConnection) obj.openConnection( );
	 
			//con = new URL(urlString)
			//103.11.116.46 	8080
			// optional default is GET
			con.setRequestMethod("GET");
	 
	
			if( coockies.size() > 0 ){
				String coockiesString = "";
	
				for (String s : coockies)
					coockiesString += s + "; ";
			
				con.setRequestProperty( "Cookie", coockiesString );
			}
	
			responseCode 			 = con.getResponseCode();
			String coockiesString 	 = con.getHeaderField("Set-Cookie");
			
			List<String> newCoockies = new ArrayList<String>();
			
			if( coockiesString !=  null && !coockiesString.isEmpty() ){
				newCoockies = Arrays.asList( coockiesString.split("\\s*;\\s*") );
			}
			
			for( String coockie : newCoockies ){
				coockie = coockie.replaceAll(" ", "");
				if(!coockies.contains( coockie ) 
						&& !coockie.equals("secure")
						//&& !coockie.contains("path")
						//&& !coockie.contains("expires")
						//&& !coockie.contains("domain") 
				  ){
					coockies.add( coockie );
				}
			}
		    
			
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			System.out.println("Cookie : " + coockies);
			
			
	 
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();
	
			System.out.println(responseBuffer.toString());
			
			response = responseBuffer.toString();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
