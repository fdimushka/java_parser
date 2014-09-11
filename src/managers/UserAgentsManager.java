package managers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserAgentsManager {
	
	public static UserAgentsManager 	instance = new UserAgentsManager();
	
	
	
	private List< String >		userAgents = new ArrayList< String >();
	private int					currentUserAgent = 0;
	
	private UserAgentsManager() {

	}
	
	public void loadUserAgentsList( String path ) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				userAgents.add( line );
				
				//System.out.printf("%s\n", line);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public String getUserAgent() {
		
		currentUserAgent++;
		if( currentUserAgent >= userAgents.size() )
			currentUserAgent = 0;

		return userAgents.get( currentUserAgent );
	}
	
}
