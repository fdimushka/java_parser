package managers;

import java.util.ArrayList;
import java.util.List;

import core.Client;
import core.Task;

public class ClientsManager {
	
	public static ClientsManager instance = new ClientsManager();
	
	private int 				clientsCount = 1;
	
	private List< Task >		tasks;
	private List< Client >		clients;
	
	private ClientsManager() {
		this.tasks = new ArrayList< Task >();
		this.clients = new ArrayList< Client >();
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
		this.clients.clear();
		
		for( int i = 0; i<clientsCount; i++ ){
			this.clients.add( new Client() );
		}
	}
	
	public void update() {
		
		for( int i = 0; i<clientsCount; i++ ){
			this.clients.get( i ).start();
		}
		
		while( true ) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//is client empty
			
			//is exist task in list add to client
			
			//
		}
		
	}
	
}
