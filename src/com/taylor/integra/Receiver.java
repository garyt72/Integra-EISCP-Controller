package com.taylor.integra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.taylor.utilities.CommunicationDevice;

public class Receiver extends CommunicationDevice{
	
	/** default receiver IP Address & Port  **/
	private static final String			DEFAULT_IP				= "192.168.1.40";
	private static final int			DEFAULT_PORT			= 60128;
	private static final int			SOCKET_SEND_WAIT		= 200;
	private static final int            SOCKET_RECV_TIMEOUT		= 200;
	private static final int            SOCKET_RECV_WAIT		= 200;
	private static final boolean        DEBUGGING				= EISCP.DEBUG;
	private static final boolean        INFO					= EISCP.INFO;

	private String name;
	
	@Override
	public String getDefaultIp() {
		return DEFAULT_IP;
	}

	@Override
	public int getDefaultPort() {
		return DEFAULT_PORT;
	}

	@Override
	public int getSocketSendWait() {
		return SOCKET_SEND_WAIT;
	}

	@Override
	public int getSocketReceiveWait() {
		return SOCKET_RECV_WAIT;
	}

	@Override
	public int getSocketReceiveTimeout() {
		return SOCKET_RECV_TIMEOUT;
	}	
	
	@Override
	public boolean getDebug() {
		return DEBUGGING;
	}

	@Override
	public boolean getInfo() {
		return INFO;
	}

	public String getName() {
		return name;
	}
	
	public Receiver() {
		super();
	}
	
	public Receiver(String ipAddress) {
		super(ipAddress);
	}
	
	public Receiver(String ipAddress, int port) {
		super(ipAddress, port);
	}
	
	public Receiver(String name, String ipAddress, int port, int delay) {
		super(ipAddress, port, delay);
		this.name = name;	
	}
		

	/**
	 * Sends to command to the receiver and does not wait for a reply.
	 *
	 * @param command
	 *            must be one of the Command Class Constants from the
	 *            eiscp.Eiscp.Command class.
	 **/
	public String sendCommand(Command command) {
		return sendCommand (command, 1);
	}

	public String sendCommand(Command command, int commandrepeat) {
		return sendCommand(command, commandrepeat, false).get(0);
	}
	
	/**
	 * Sends to command to the receiver and does not wait for a reply.
	 *
	 * @param command
	 *            must be one of the Command Class Constants from the
	 *            eiscp.Eiscp.Command class.
	 * @param closeSocket
	 *            flag to close the connection when done or leave it open.
	 **/
	public List<String> sendCommand(Command command, int commandRepeat, boolean allMessages) {
		List<String> result = new ArrayList<String>();
		
		String fullResponse = super.sendCommand(command.getEiscpMessage(), commandRepeat);

		if (fullResponse.length() == 0) {
			result.add("No Reponse");
		}
		else {

			// parse the complete response string out into individual responses
			List<Response> responses = Response.parseResponses(fullResponse);
			
			List<String> messages = new ArrayList<String>();
			for (Iterator<Response> iter = responses.iterator(); iter.hasNext(); ) {
				Response response = iter.next();
				messages.add(response.getData().getMessage());
				if (DEBUGGING) System.out.println("Response: " + response.getData().getMessage());
			}

			
			// only get the message that matches the command
			if (allMessages == false) {  
				String prefix = command.getCommandGroup();
				
				// loop through the resposes to look for the one for the command that was sent
				for (Iterator<String> iter = messages.iterator(); iter.hasNext(); ){
					
					// if the message starts with the command prefix
					String message = iter.next();
					if (message.startsWith(prefix)) {
						// we found the message we're looking for - clear the list & add back the just this message
						// this also ensures for "Multiple" commands (VOLUME_UP*5) that the last message is the one we end up with
						result.clear();
						result.add(message);
					}
				}
				
				// make sure there is at least one message in the list
				if (result.size() == 0) {
					result.add("Message not found");
				}

			} // all messages == false 
			else {
				result = messages;
			}
		}
		
		
		return result;
	}


}
