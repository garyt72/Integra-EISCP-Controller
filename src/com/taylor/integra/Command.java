package com.taylor.integra;

public class Command {
	
	private String name;
	private String command;
	private String commandGroup;
	private String commandAction;
	private String eiscpMessage;
	
	/**
	 * @return the "Friendly" command name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the ISCP command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return the commandGroup
	 */
	public String getCommandGroup() {
		return commandGroup;
	}

	/**
	 * @param commandGroup the commandGroup to set
	 */
	public void setCommandGroup(String commandGroup) {
		this.commandGroup = commandGroup;
	}


	/**
	 * @param commandAction the commandAction to set
	 */
	public void setCommandAction(String commandAction) {
		
		// convert from decimal to hex for MVL & ZVL commands
		if (getCommandGroup().equals("MVL") || getCommandGroup().equals("ZVL")) {
			// get the hex value for the number that was passed (as a string)
			this.commandAction = getIntegraHexValue(commandAction);
		} else if (getCommandGroup().equals("CTL")) {
			this.commandAction = getCenterLevelValue(new String(commandAction));
		} else if (getCommandGroup().equals("TUN")) {
			this.commandAction = getRadioTunerValue(commandAction);
		} else {
			this.commandAction = commandAction;
		}
				
		this.command = getCommandGroup() + getCommandAction();
	}

	/**
	 * @return the commandAction
	 */
	public String getCommandAction() {
		return commandAction;
	}
		
	
	/**
	 * @param name
	 * @param command
	 * @param number
	 */
	public Command(String name, String command) {
		super();
		this.name = name;
		this.command = command;
		if (command.length() <= 3){
			this.commandGroup = command;
		}
		else {
			this.commandGroup = command.substring(0, 3);
			this.commandAction = command.replace(getCommandGroup(), "");
		}
	}

	
	/**
	 * Wraps a command in a eiscp data message (data characters).
	 *
	 * @param command
	 *            must be one of the Command Class Constants from the
	 *            eiscp.Eiscp.Command class.
	 * @return StringBuffer holing the full iscp message packet
	 **/
	public String getEiscpMessage() {
		
		if (eiscpMessage == null) {
			// data length - command length + start char + unit type char
			int eiscpDataSize = getCommand().length() + 2;

			/*
			 * This is where we construct the entire message character by character.
			 * Each char is represented by a 2 disgit hex value
			 */
			StringBuilder sb = new StringBuilder("ISCP");
					
			// the following are all in HEX representing one char

			// 4 char Big Endian Header
			sb.append((char) Integer.parseInt("00", 16));
			sb.append((char) Integer.parseInt("00", 16));
			sb.append((char) Integer.parseInt("00", 16));
			sb.append((char) Integer.parseInt("10", 16));

			// 4 char Big Endian data size
			sb.append((char) Integer.parseInt("00", 16));
			sb.append((char) Integer.parseInt("00", 16));
			sb.append((char) Integer.parseInt("00", 16));
			sb.append((char) Integer.parseInt(Integer.toHexString(eiscpDataSize), 16));

			// eiscp_version = "01";
			sb.append((char) Integer.parseInt("01", 16));

			// 3 chars reserved = "00"+"00"+"00";
			sb.append((char) Integer.parseInt("00", 16));
			sb.append((char) Integer.parseInt("00", 16));
			sb.append((char) Integer.parseInt("00", 16));

			// eISCP data Start Character
			sb.append("!");

			// eISCP data - unittype char '1' is receiver
			sb.append("1");

			// eISCP data - 3 char command and param ie PWR01
			sb.append(getCommand());

			// msg end - EOF
			// sb.append((char) Integer.parseInt("0D", 16));
			sb.append("\n");

			eiscpMessage = sb.toString();
		}

		return eiscpMessage;
	} 



	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Command [CommandName=" + getName() + 
				", CommandString=" + getCommand() + " (" + getCommandGroup() + 
				")(" + getCommandAction() + ") ]";
	}

	/**
	 * 
	 * @param input The String to convert
	 * @return a 2-character hex value representation of the value passed
	 */
	private String getIntegraHexValue(String input){

		String result;
		
		try {
			double doubleValue = Double.parseDouble(input);
			int intValue = (int) (doubleValue * 2);
		
			// convert the integer value to hex;
			String hex = Integer.toHexString(intValue);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			result = hex;

		} catch (Exception e) { 
			result = "";
		}
		return result;
		
	}
	
	/**
	 * 
	 * @param input Center level string value
	 * @return a valid 3 character CTL HEX value representation of the value passed
	 */
	private String getCenterLevelValue(String input) {
		
		// make a local copy of the passed value so we can manipulate it without causing upstream issues
		String value = new String(input); 
		String result = "";
		boolean negative = false;

		// detect and handle negatives
		if (value.contains("-")) {
			negative = true;
			value = value.replace("-", "");
		}
		
		// get the hex value for what we have
		result = getIntegraHexValue(value);
		
		if (result.equals("00")) {
			result = "0" + result;
			negative = false;
		}else if (negative == true){
			result = "-" + result;
		} else {
			result = "+" + result;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param input radio frequency value
	 * @return formatted integra frequency string
	 */
	private String getRadioTunerValue(String input) {
		String result;
		try {
			double doubleValue = Double.parseDouble(input);
			doubleValue = doubleValue * 100;
			int intValue = (int) doubleValue;
			result = Integer.toString(intValue);
			
			while (result.length() < 5) {
				result = "0" + result;
			}

		} catch (Exception e) { 
			result = "";
		}
		
		return result;
	}
	
}
