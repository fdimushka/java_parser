package net;


import java.io.*;
import java.net.*;

public class ProxyServer implements Server.Service  {

  public static void main(String[] args) {
    try {
      // Check number of args.  Must be a multiple of 3 and > 0.
      if ((args.length == 0) || (args.length % 3 != 0))
        throw new IllegalArgumentException("Wrong number of arguments");

      // Create the Server object
      Server s = new Server(null, 12); // log stream, max connections

      // Loop through the arguments parsing (host, remoteport, localport)
      // tuples.  Create an appropriate Proxy object, and add it to the server
      int i = 0;
      while(i < args.length) {
        String host = args[i++];
        int remoteport = Integer.parseInt(args[i++]);
        int localport = Integer.parseInt(args[i++]);
        s.addService(new Proxy(host, remoteport), localport);
      }
    }
    catch (Exception e) {  // Print an error message if anything goes wrong.
      System.err.println(e);
      System.err.println("Usage: java ProxyServer " +
                         "<host> <remoteport> <localport> ...");
      System.exit(1);
    }
  }

	
	public static class Proxy implements Server.Service {

  
    String host;
    int port;

    public Proxy(String host, int port) {
      this.host = host;
      this.port = port;
    }

    @SuppressWarnings("resource")
	public void serve(InputStream in, OutputStream out) {

      final InputStream from_client = in;
      final OutputStream to_client = out;
      final InputStream from_server;
      final OutputStream to_server;


      Socket server;
      try { 
        server = new Socket(host, port); 
        from_server = server.getInputStream();
        to_server = server.getOutputStream();
      }
      catch (Exception e) {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
        pw.println("Proxy server could not connect to " + host + ":" + port);
        pw.flush();
        pw.close();
        try { in.close(); } catch (IOException ex) {}
        return;
      }


      final Thread[] threads = new Thread[2];


      Thread c2s = new Thread() {
        public void run() {
          byte[] buffer = new byte[2048];
          int bytes_read;
          try {
            while((bytes_read = from_client.read(buffer)) != -1) {
              to_server.write(buffer, 0, bytes_read);
              to_server.flush();
            }
          }
          catch (IOException e) {}


          threads[1].stop();
          try { to_server.close(); } catch (IOException e) {}
        }
      };


      Thread s2c = new Thread() {
        public void run() {
          byte[] buffer = new byte[2048];
          int bytes_read;
          try {
            while((bytes_read = from_server.read(buffer)) != -1) {
              to_client.write(buffer, 0, bytes_read);
              to_client.flush();
            }
          }
          catch (IOException e) {}


          threads[0].stop();
          try { to_client.close(); } catch (IOException e) {}
        }
      };


      threads[0] = c2s; threads[1] = s2c;


      c2s.start(); s2c.start();


      try { c2s.join(); s2c.join(); } catch (InterruptedException e) {}
    }
	}

	@Override
	public void serve(InputStream in, OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
