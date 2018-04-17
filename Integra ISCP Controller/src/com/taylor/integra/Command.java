package com.taylor.integra;

import com.taylor.utilities.Util;

public class Command {

	@SuppressWarnings("unused")
	private static final boolean DEBUGGING = Util.DEBUGGING;
	
	private String name;
	private String command;
	private String commandGroup;
	private String commandAction;
	private int number;
	private String eiscpMessage;
	
	/**
	 * @return the "Friendly" command name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * @param command the command to set
	 */
	private void setCommand(String command) {
		this.command = command;
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
		this.commandAction = commandAction;
	}

	/**
	 * @return the commandAction
	 */
	public String getCommandAction() {
		return commandAction;
	}
	
	
	/**
	 * @param number the number to set
	 */
	private void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the commandNumber
	 */
	public int getNumber() {
		return number;
	}

	
	
	/**
	 * @param name
	 * @param command
	 * @param number
	 */
	public Command(String name, String command, int number) {
		super();
		setName(name);
		setCommand(command);
		setCommandGroup(command.substring(0, 3));
		setCommandAction(command.replace(getCommandGroup(), ""));
		setNumber(number);
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
				")(" + getCommandAction() + ") CommandNumber=" + getNumber() + "]";
	}

	
	
}
