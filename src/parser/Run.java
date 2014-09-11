package parser;

import managers.ClientsManager;
import managers.ProxyManager;
import managers.UserAgentsManager;
import net.HTTPClient;

public class Run {
	
	
	public static void main(String[] args) throws Exception {
		
		
		
		UserAgentsManager.instance.loadUserAgentsList("/home/dima/workspace/java_parser/user_agents");
		
		
		ClientsManager.instance.setClientsCount( 1 );
		ClientsManager.instance.init();
		ClientsManager.instance.update();
	}
}
