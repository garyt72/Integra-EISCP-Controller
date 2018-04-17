package com.taylor.integra;

import java.util.Iterator;

/**
 * A class that wraps the comunication to Onkyo/Integra devices using the
 * ethernet Integra Serial Control Protocal (eISCP). This class uses class
 * constants and commandMaps to help handling of the many iscp Commands. <br />
 * @author Tom Gutwin P.Eng
 */
public class EISCP {


	/**
	 * Class main commandLine entry method.
	 **/
	public static void main(String[] args) {
		
		if (args.length == 0) {
			StringBuffer output = new StringBuffer();

			output.append("Must specify a command:\n");
			output.append(getCommandList());
			
			System.out.println(output.toString());
			return;
		}
		
		Receiver receiver = new Receiver();		

		// for now the command is the only argument
		String friendlyCommand = args[0].toUpperCase();
		
		// handle executing the command multiple times by appending * + count
		// example VOLUME_UP*5 executes VOLUME_UP 5 times
		int commandRepeat = 1;
		if (friendlyCommand.contains("*")) {
			String[] split = friendlyCommand.split("\\*");
			friendlyCommand = split[0];
			try {
				if (friendlyCommand.endsWith("UP") || friendlyCommand.endsWith("DOWN")) {
					commandRepeat = Integer.parseInt(split[1]);
				} else {
					System.out.println("Ignoring multiple for commands that are not Volume Up/Down");
				}
			} catch (Exception e) {
				// do nothing
			}
		}
		
		Command command = Commands.getCommandObjectFromName(friendlyCommand);
		
		if (command == null) {
			StringBuffer output = new StringBuffer();
			
			output.append("Command \"" + friendlyCommand + "\" not found\n");

			for (Iterator<String> iter = Commands.getIterator(); iter.hasNext();) {
				String cmd = iter.next();
				
				if (cmd.contains(friendlyCommand)) {
					output.append("\t" + cmd + "\n");
				}
			}
			
			System.out.println(output.toString());
			
			return;
		}

	
		String response = receiver.sendCommand(command, commandRepeat);
		System.out.println("EISCP response:    " + response);
		
		String friendlyResponse = Response.getFriendlyResponse(response);
		System.out.println("Friendly response: " + friendlyResponse);

		
	} // main


	public static String getCommandList() {
		StringBuffer output = new StringBuffer();

		for (Iterator<String> iter = Commands.getIterator(); iter.hasNext();) {
			output.append("\t" + iter.next() + "\n");
		}
		
		return output.toString();
	}


} // class
