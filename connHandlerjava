//package edu.upr.cs.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.File;
import java.net.Socket;
import java.util.*;


public class connHandler extends Thread{
	// Data stream and output streams for data transfer
	private 	DataInputStream 	clientInputStream;
    private 	DataOutputStream 	clientOutputStream;
    private 	PrintStream  		ps;
    private 	BufferedReader		in;
    
    // Base Directory for files
    private final static String dir = System.getProperty("user.dir") + "\\src\\httpserver\\";
    
    //Client socket for maintaining connection with the client
    private 	Socket 				clientSocket;
    
    //Client Id
    private		int					clientId;
    
    /***************************************************************************
     * Constructor
     * @param clientSocket: client socket created when the client connects to
     * the server
     */
    
    public connHandler(Socket clientSocket, int clientId)
    {
    	try{
    		 this.clientSocket = clientSocket;
    		 this.clientId = clientId;
    	     clientInputStream =  new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
    	     clientOutputStream =  new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}    
    }
    
    public void run()
    {
    	dataTransfer();
    }
    
    /*****************************************************************************
     * Data transfer method
     * Tasks: Manage connection with the client.
     * 		Receive commands from the client
     * 		Invoke the appropriate method depending on the received command
     * 
     *****************************************************************************/
	public void dataTransfer()
	{
		try{
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			ps = new PrintStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			String readCommand = in.readLine();
			
	        	System.out.println("Received Command from client " + this.clientId);
	        	
	        	//parse command
	        	if(readCommand.startsWith("GET"))
	        		getCommand(readCommand);
	        	else if(readCommand.startsWith("PUT"))
	        		putCommand(readCommand);
	        	else if(readCommand.startsWith("EXIT"))
	        		exitCommand();
					
					
	        //}while(!exit);
	        
	        //Close connection and socket
	        clientInputStream.close();
	        clientOutputStream.close();
	        clientSocket.close();
	        //exitCommand();
	        
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/***************************************************************************
	 * exitCommand Method
	 * Tasks: Sends OK confirmation code to the client for closing connection 
	 * 
	 **************************************************************************/
	
	private void exitCommand()
	{
		try{
			//clientOutputStream.writeInt(1); //FIX LATER
			clientOutputStream.flush();
			System.out.println("The connection has been closed! \n client: " + clientId );
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/***************************************************************************
	 * getCommand Method
	 * Tasks: Performs get command
	 * 		  Acknowledges to the client for the received command
	 * 		  Receives the file name
	 * 		  Acknowledges to the client for the received file name
	 * 		  Checks for the correctness of the file name
	 * 				if not found, send the FILENOTFOUND error code the client
	 * 				if found, transfer the file to the client
	 * 
	 **************************************************************************/	
	private void getCommand(String readCommand)
	{	
		byte[] buffer = new byte[comCodes.BUFFER_SIZE];
		int totalRead = 0;
		int read;
		long startTime;
		long endTime;
		
		
		
		System.out.println("Get Command");
		
		try{ 
			File file;
			
        	String[] splitString = readCommand.split(" ");  	
        	//THIS SHOULD BE STR[0] = GET, STR[1] = FILENAME, STR[2] = HTTP/1.1
        	
        	//file name
        	String fileName = splitString[1];
        	
        	//print the file name
        	System.out.println("GET " + fileName);
        	
        	//to protect data
        	if(fileName.contains(".."))
			{
				ps.print(comCodes.SERVERERROR);
				ps.flush();
				return;
			}
        	
        	//Parse file type
        	if(fileName.equals("/")){
        		//IF no file name. return index
        		file = new File("index.html");
        		//if file doesnt exist
        		if(!file.getAbsoluteFile().exists()) {
        			ps.print(comCodes.FILENOTFOUND);
        			ps.flush();
					return;
        		}
        		//send codes
        		ps.print(comCodes.OK);
        		ps.flush();
        		ps.print(comCodes.REQ_HTML);
        		ps.flush();
        		//prepare file input stream to send file
        		FileInputStream fis = new FileInputStream(file);
        		System.out.println("Sending " + "index.html" + "(" + file.length() + " bytes)");

        		
        		startTime = System.currentTimeMillis();
        		//send file
        		while((read = fis.read(buffer)) > 0)
				{
					ps.write(buffer, 0, read);
					totalRead += read;
				}
				fis.close();
				ps.flush();
				
				endTime = System.currentTimeMillis();
    	        System.out.println(totalRead + " bytes read in " + (endTime - startTime) + " ms.");
    	    	System.out.println("Successful Data transfer");
        	}
    	    	
        	else if(fileName.endsWith(".html")||fileName.endsWith("/")) {
        		//IF request is html file
        		fileName = fileName.substring(1);
        		System.out.println(dir + fileName);
				file = new File(fileName);
				//if file doesnt exist
				if(!file.getAbsoluteFile().exists())
				{
					ps.print(comCodes.FILENOTFOUND);
					ps.flush();
					return;
				}
				//send codes
				ps.print(comCodes.OK);
				ps.flush();
				ps.print(comCodes.REQ_HTML);
				ps.flush();
				//prepare file input stream to send file
				FileInputStream fis = new FileInputStream(file);
				System.out.println("Sending " + fileName + "(" + file.length() + " bytes)");
				
				startTime = System.currentTimeMillis();
				//send file
        		while((read = fis.read(buffer)) > 0)
				{
					ps.write(buffer, 0, read);
					totalRead += read;
				}
				fis.close();
				ps.flush();
				endTime = System.currentTimeMillis();
    	        System.out.println(totalRead + " bytes read in " + (endTime - startTime) + " ms.");
    	    	System.out.println("Successful Data transfer");
        	}
        	else if(fileName.endsWith(".png")||fileName.endsWith(".jpg")||fileName.endsWith(".jpeg")||fileName.endsWith(".gif")){
        		//IF request is image file. look in image folder
        		fileName = fileName.substring(1);
        		System.out.println("img\\" + fileName);
				file = new File("img\\" + fileName);
				//if file doesnt exist
				if(!file.getAbsoluteFile().exists())
				{
					ps.print(comCodes.FILENOTFOUND);
					ps.flush();
					return;
				}
				//send ok code
				ps.print(comCodes.OK);
				ps.flush();
				//type of image file
				if(fileName.endsWith(".jpg")||fileName.endsWith(".jpeg"))
					ps.print(comCodes.REQ_JPG);
				else if(fileName.endsWith(".png"))
					ps.print(comCodes.REQ_PNG);
				else
					ps.print(comCodes.REQ_GIF);
				//prepare file input stream to send file
				ps.flush();
				FileInputStream fis = new FileInputStream(file);
				System.out.println("Sending " + fileName + "(" + file.length() + " bytes)");
				
				startTime = System.currentTimeMillis();
				//send file
        		while((read = fis.read(buffer)) > 0)
				{
					ps.write(buffer, 0, read);
					totalRead += read;
				}
				fis.close();
				ps.flush();
				endTime = System.currentTimeMillis();
    	        System.out.println(totalRead + " bytes read in " + (endTime - startTime) + " ms.");
    	    	System.out.println("Successful Data transfer");
        	}
        	else {
        		ps.print(comCodes.SERVERERROR);
        		ps.flush();
        	}
		        
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/***************************************************************************
	 * putCommand Method
	 * Tasks: Performs put command
	 * 		  Acknowledges to the client for the received command
	 * 		  Receives the file name
	 * 		  Acknowledges to the client for the received file name
	 * 		  Checks for the correctness of the file name
	 * 				if found, send the EXISTINGFILE error code the client
	 * 				if found, receive file from the client
	 * 
	 **************************************************************************/
	private void putCommand(String readCommand)
	{
		byte[] buffer = new byte[comCodes.BUFFER_SIZE];
		int totalRead = 0;
		int read;
		
		System.out.println("Put Command");
		
		try{
			
			//PUT /new.html HTTP/1.1
			//Host: example.com
			//Content-type: text/html
			//Content-length: 16
			
			//Command with filename
			String[] splitString = readCommand.split(" ");
			String fileName = splitString[1];
			String filePath;
			System.out.println("PUT " + fileName);
        	
        	//set directory to images if its an image
        	fileName = fileName.substring(1);
        	if(fileName.endsWith(".html"))
        		filePath = fileName;
        	else if(fileName.endsWith(".jpeg")||fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".gif"))
        		filePath = "img\\" +fileName;
        	else {
        		ps.print(comCodes.BAD_REQ);
        		ps.flush();
        		return;
        	}
        	//ACKNOWLEDGE COMMAND
        	ps.print(comCodes.OK);
        	ps.flush();
        	
        	long startTime = System.currentTimeMillis();
        	
        	FileOutputStream fos = new FileOutputStream(filePath);
        			
            while ((read = clientInputStream.read(buffer)) > 0) {
                 fos.write(buffer, 0, read);
                 totalRead += read;      		
             }
        	
        	long endTime = System.currentTimeMillis();
	        System.out.println(totalRead + " bytes read in " + (endTime - startTime) + " ms.");
        	System.out.println("Successful Data transfer");
        	ps.print(comCodes.FILECREATED);
        	ps.flush();
        	fos.close();
	        
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
