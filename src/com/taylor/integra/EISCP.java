package com.taylor.integra;

import java.util.Iterator;

/**
 * A class that wraps the comunication to Onkyo/Integra devices using the
 * ethernet Integra Serial Control Protocal (eISCP). This class uses class
 * constants and commandMaps to help handling of the many iscp Commands. <br />
 * @author Tom Gutwin P.Eng
 */
public class EISCP {

	public static boolean DEBUG = false;
	public static boolean INFO = false;
	public static Config config;

	/**
	 * Class main commandLine entry method.
	 **/
	public static void main(String[] args) {

		int returnValue = processCommand(args);
		
		System.exit(returnValue);

	}
	
	private static int processCommand(String[] args) {
		
		// check for environment vaiable DEBUG set to true
		String env = System.getenv("DEBUG");
		if (env != null && env.toLowerCase().equals("true")) {
			System.out.println("Debugging enabled by ENV variable \"DEBUG\"");
			DEBUG = true;
		}

		// check for environment vaiable DEBUG set to true
		env = System.getenv("INFO");
		if (env != null && env.toLowerCase().equals("true")) {
			if (DEBUG) System.out.println("Info output enabled by ENV variable \"INFO\"");
			INFO = true;
		} 

		// try to load configuration settings from file
		
		try {
			config = new Config();
		} catch (Exception e) {
			if (DEBUG || INFO) {
				System.out.println("Error loading config file");
				e.printStackTrace();
			}
			else {
				System.out.println("Error loading config file: " + e.getMessage());
			}
			return 1;
		}
				
		// config was loaded - check debug settings
		if (config.getDebug()) {
			System.out.println("Debug enabled by config file");
			DEBUG = true;
		}

		// config was loaded - check info settings
		if (config.getInfo()) {
			System.out.println("Info enabled by config file");
			INFO = true;
		}

		
		// we've loaded the config file - lets get deal with the arguments that were passed
		String receiverArg = new String();
		String commandArg = new String();
		Boolean verbose = false;
		Boolean friendly = false;

		try {
			for (int i=0; i < args.length; i++) {

				String argument = args[i];
				
				// argument is a switch
				if (argument.startsWith("-")) {
					String parts[] = argument.split(":");
					switch (parts[0]) {
						case "-r":
							receiverArg = parts[1].toUpperCase();
							break;
						case "-v": 
							verbose = true;
							break;
						case "-f": 
							friendly = true;
							break;
						default:
							System.out.println("Invalid Argument: " + argument);
							System.out.println(getUsage(false));
							System.exit(1);
							return args.length;
					}
				} else { // argument is the command
					commandArg = argument.toUpperCase();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(getUsage());
			System.exit(1);
			return args.length;
		}

		// if receiver was not specified, there must be only one receiver
		if (config.getReceiverList().size() == 1) {
			// only one receiver - we will use it's name
			receiverArg = config.getReceiverList().get(0);
		}
		else {
			System.out.println("Receiver not specified");
			System.out.println(getUsage());
			return 2;
		}
		
		
		// get the receiver
		Receiver receiver = config.getReceiverMap().get(receiverArg);

		// if the receiver wasn't found display a list of available receivers and exit
		if (receiver == null) {
			StringBuffer output = new StringBuffer();
			output.append("Controller \"" + receiverArg + "\" not found\n");
			for (Iterator<String> iter = config.getReceiverList().iterator(); iter.hasNext(); ){
				output.append("\t" + iter.next() + "\n");
			}
			
			System.out.println(output.toString());
			return 4;
		}
		
		// handle executing the command multiple times by appending * + count
		// example VOLUME_UP*5 executes VOLUME_UP 5 times
		int commandRepeat = 1;
		if (commandArg.contains("*")) {
			String[] split = commandArg.split("\\*");
			commandArg = split[0];
			try {
				commandRepeat = Integer.parseInt(split[1]);
			} catch (Exception e) {
				// do nothing, if we couldn't parse the repeat value it was already set to 1
			}
		}
		
		// handle set commands by appending : + value
		// example VOLUME_SET:55 executes VOLUME_SET (MVLxx) 
		String commandSetValue = null;
		if (commandArg.contains("SET:")) {
			String[] split = commandArg.split(":");
			commandArg = split[0];
			commandSetValue = split[1];
		}

		// get the command
		Command command = Config.getCommandMap().get(commandArg);
		
		// if there was a "SET" command passed - update the command with the value passed
		if (commandSetValue != null){
			command.setCommandAction(commandSetValue);
		}
		
		// if there was no command, display a list of available commands & exit
		if (command == null) {
			StringBuffer output = new StringBuffer();
			
			output.append("Command \"" + commandArg + "\" not found\n");
			boolean similarCommandFound = false;

			for (Iterator<String> iter = Config.getCommandList().iterator(); iter.hasNext();) {
				String cmd = iter.next();
				
				if (cmd.contains(commandArg)) {
					output.append("\t" + cmd + "\n");
					similarCommandFound = true;
				}
			}
			
			if (similarCommandFound == false) {
				output.append(getCommandList());
			}
			
			System.out.println(output.toString());
			return 5;
		}

		String response = receiver.sendCommand(command, commandRepeat);
		String friendlyResponse = Response.getResponse(response, verbose, friendly);

		if (DEBUG || INFO) {
			System.out.println("EISCP response:    " + response);
			System.out.println("Friendly response: " + friendlyResponse);
		}
		else {
			System.out.println(friendlyResponse);
		}

		return 0;
	} // main

	private static String getReceiverList() {
		StringBuffer output = new StringBuffer();
		
		for (Iterator<String> iter = config.getReceiverList().iterator(); iter.hasNext();) {
			output.append("\t" + iter.next() + "\n");
		}
		return output.toString();
	}

	private static String getCommandList() {
		StringBuffer output = new StringBuffer();

		for (Iterator<String> iter = Config.getCommandList().iterator(); iter.hasNext();) {
			output.append("\t" + iter.next() + "\n");
		}
		
		return output.toString();
	}

	public static String getUsage() {
		return getUsage(true);
	}
	
	public static String getUsage(boolean displayValues) {
		StringBuffer output = new StringBuffer();

		output.append("Usage:\n\tintegra [-v] [-f] [-d:devicename] command \n");
		output.append("where:\n");
		output.append("  -v = verbose  (display label)\n");;
		output.append("  -f = friendly (display friendly repsonse value if available)\n");;
		
		output.append("  -r:receiver = receiver if more than one");
		if (displayValues == true) {
			output.append("Valid devices:\n");
			output.append(getReceiverList());
		} else {output.append("\n");}
		
		output.append("  command = command to execute");
		if (displayValues == true) {
			output.append(" Valid commands:\n");
			output.append(getCommandList());
		} else {output.append("\n");}
		
		return output.toString();
	}


} // class
