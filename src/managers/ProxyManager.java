package managers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProxyManager {
	
	public static final String  serverListPath = "/home/dima/workspace/java_parser/proxy_list"; 
	
	public static ProxyManager 	instance = new ProxyManager();
	
	
	public class ProxyData {
		public String 	host;
		public int 		port;
		public boolean	status = true;
	}
	
	
	private List< ProxyData >	servers = new ArrayList< ProxyData >();
	private int					currentServerIndex = 0;
	
	private ProxyManager() {
		loadProxyList( serverListPath );
	}
	
	private void loadProxyList( String path ) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] array = line.split(" ");
				
				ProxyData proxy = new ProxyData();
				
				proxy.host = array[0];
				proxy.port = Integer.parseInt(array[1]);
				
				servers.add( proxy );
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public int getProxyIndex() {
		return currentServerIndex;
	}
	
	public ProxyData getProxy() {
		
		do{
			currentServerIndex++;
			if( currentServerIndex >= servers.size() )
				currentServerIndex = 0;
			
		}while( !servers.get( currentServerIndex ).status );
		
		return servers.get( currentServerIndex );
	}
	
	public void setBlockProxy() {
		servers.get( currentServerIndex ).status = false;
	}
	
	
}
