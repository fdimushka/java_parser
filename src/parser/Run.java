package parser;

import managers.ClientsManager;
import net.HTTPClient;

public class Run {
	
	
	public static void main(String[] args) throws Exception {
		
		//ClientsManager.instance.setClientsCount( 10 );
		//ClientsManager.instance.init();
		//ClientsManager.instance.update();
		
		
		//while( true ){
			
			HTTPClient httpClient = new HTTPClient();
			httpClient.setProxy( "216.189.0.235", 3127 );
			httpClient.setUrl( "https://mobile.bet365.com" );
			httpClient.setUserAgent( "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36" );
			httpClient.sendGet();
			
			httpClient.setUrl( "https://mobile.bet365.com/sport/default.aspx?ID=200:0&ip=1" );
			httpClient.sendGet();
			
			httpClient.setUrl( "https://mobile.bet365.com/sport/splash/Default.aspx?Sport=1&key=1&L=2&ip=1" );
			httpClient.sendGet();
		
		//}
	}
}
