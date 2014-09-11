package net;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import managers.ProxyManager;
import managers.ProxyManager.ProxyData;

public class HTTPClient
{
	private String 				user_agent 		= "Mozilla/5.0";
	private List<String> 		coockies 		= new ArrayList<String>();
	private int 				responseCode 	= 200;
	private String 				url;
	private String 				response;
	private Proxy				proxy 			= null;
	private int					proxyIndex		= 0;
	 
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
	
	public void nextProxy( ) {
		proxyIndex = ProxyManager.instance.getProxyIndex();
		ProxyData proxyData = ProxyManager.instance.getProxy();
		
		
		this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress( proxyData.host, proxyData.port ));
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

		responseCode = 404;
		
		URL obj;
		try {
			obj = new URL( this.url );
		

			HttpURLConnection con;
			
			if( this.proxy != null )
				con = (HttpURLConnection) obj.openConnection( this.proxy );
			else
				con = (HttpURLConnection) obj.openConnection( );
	 
			con.setConnectTimeout( 5000 );

			con.setRequestMethod("GET");
	 
			if( coockies.size() > 0 ){
				String coockiesString = "";
	
				for (String s : coockies)
					coockiesString += s + "; ";
			
				con.setRequestProperty( "Cookie", coockiesString );
				
				System.out.println("Send Cookie "+coockiesString);
			}
	
			responseCode 			 = con.getResponseCode();

		    
			System.out.println("\nProxy : " + this.proxy.address().toString());
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			Map<String, List<String>> map = con.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				System.out.println("Key : " + entry.getKey() + 
		                 " ,Value : " + entry.getValue());
				
				if( entry.getKey() != null && entry.getKey().equals("Set-Cookie")){
					//List<String> newCoockies = Arrays.asList( entry.getValue()..split("\\s*;\\s*") );
					for( String coockie : entry.getValue() ){
						//System.out.println(coockie);
						
						List<String> newCoockies = Arrays.asList( coockie.split("\\s*;\\s*") );
						
						for( String newCoockie : newCoockies ){
							newCoockie = newCoockie.replaceAll("secure", "");
							newCoockie = newCoockie.replaceAll(" ", "");
							
							if( !coockies.contains(newCoockie) && !newCoockie.contains("path") && !newCoockie.contains("expires") && !newCoockie.contains("domain") && !newCoockie.isEmpty() ){
								coockies.add( newCoockie );
							}
						}
					}
				}
			}

	 
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
			responseCode = 404;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			responseCode = 404;
			e.printStackTrace();
		}
	}
}
