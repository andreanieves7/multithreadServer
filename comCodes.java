//package edu.upr.cs.server;

public class comCodes {

	//Buffer size
	public static final int BUFFER_SIZE=1024;
	
	
	//Data transfer codes--------------------------------------------
	//OK 
	public static final String OK="HTTP/1.1 200 OK"+"\r\n";
	
	//Get
	public static final int GET=2;
	
	//Put
	public static final int PUT=3;
	
	//Close connection
	public static final int CLOSECONNECTION=4;
	
	
	//Error messages----------------------------------------------------
	//File not found
	public static final String FILENOTFOUND="HTTP/1.1 404 Not Found"+"\r\n"+"\r\n";
	
	public static final String SERVERERROR = "HTTP/1.1 500 Internal Server Error"+"\r\n";
	
	public static final String REQ_HTML = "Content-type: text/html"+"\r\n"+"\r\n";
	
	public static final String REQ_PNG = "Content-type: image/png"+"\r\n"+"\r\n";
	
	public static final String REQ_JPG = "Content-type: image/jpeg"+"\r\n"+"\r\n";
	
	public static final String REQ_GIF = "Content-type: image/gif"+"\r\n"+"\r\n";
	
	public static final String BAD_REQ = "HTTP/1.1 400 Bad Request"+"\r\n"+"\r\n";
	
	public static final String FILECREATED = "HTTP/1.1 201 Created"+"\r\n"+"\r\n";
	
	
	//The intended file already exists
	public static final int EXISTINGFILE=21;
	
	//No valid command
	public static final int WRONGCOMMAND=30;
	
}
