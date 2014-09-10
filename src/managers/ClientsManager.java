package managers;

import java.util.ArrayList;
import java.util.List;

import core.Task;

public class ClientsManager {
	
	public static ClientsManager instance = new ClientsManager();
	
	private int 				clientsCount = 1;
	private List< Task >		tasks;
	
	private ClientsManager() {
		this.tasks = new ArrayList< Task >();
	}
	
	public void setClientsCount( int clientsCount ) {
		this.clientsCount = clientsCount;
	}
	
	public int getClientsCount() {
		return this.clientsCount;
	}
	
	public void addTask( Task task ) {
		this.tasks.add( task );
	}
	
	public void init() {
		this.tasks.clear();
	}
	
	public void update() {
		while( true ) {
			
			//is client empty
			
			//is exist task in list add to client
			
			//
		}
	}
	
}
