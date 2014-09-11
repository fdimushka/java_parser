package core;

import managers.UserAgentsManager;
import net.HTTPClient;

public class Client extends Thread  {
	private Task currentTask = null;
	
	private HTTPClient httpClient = new HTTPClient();

	public Client() {
		
	}
	
	public void setTask() {
		
	}
	
	public void run() {
		while( true ) {
			httpClient.nextProxy( );
			httpClient.setUrl( "https://mobile.bet365.com" );
			httpClient.setUserAgent( UserAgentsManager.instance.getUserAgent() );
			httpClient.sendGet();
			
			if( httpClient.getResponseCode() == 200 ){
				httpClient.setUrl( "https://mobile.bet365.com/sport/default.aspx?ID=200:0&ip=1" );
				httpClient.sendGet();
				
				if( httpClient.getResponseCode() == 200 ){
					httpClient.setUrl( "https://mobile.bet365.com/sport/splash/Default.aspx?Sport=1&key=1&L=2&ip=1" );
					httpClient.sendGet();
				}
			}
		}
	}
}
