/*
 *  $URL: svn://svn/open/trunk/projects/WebARTS/ca/bc/webarts/tools/eiscp/IscpCommands.java $
 *  $Author: $
 *  $Revision: $
 *  $Date: $
 */
/*
 *
 *  Written by Tom Gutwin - WebARTS Design.
 *  Copyright (C) 2012-2014 WebARTS Design, North Vancouver Canada
 *  http://www.webarts.bc.ca
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without_ even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package com.taylor.integra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * A class that wraps the Onkyo/Integra Serial Control Protocol (eISCP) messages
 * that can be sent in a packet. This class uses class constants and commandMaps
 * to help handling of the many iscp Commands. <br />
 * <br />
 * The Message packet looks like:<br />
 * <img src=
 * "http://tom.webarts.ca/_/rsrc/1320209141605/Blog/new-blog-items/javaeiscp-integraserialcontrolprotocol/eISCP-Packet.png"
 * border="1"/> <br />
 * See also <a href=
 * "http://tom.webarts.ca/Blog/new-blog-items/javaeiscp-integraserialcontrolprotocol"
 * > tom.webarts.ca</a> writeup.
 *
 * @author Tom Gutwin P.Eng
 */
public class Commands {

	public class CommandList {

		public final static String POWER_OFF = "POWER_OFF";
		public final static String POWER_ON = "POWER_ON";
		public final static String POWER_QUERY = "POWER_QUERY";
		public final static String UNMUTE = "UNMUTE";
		public final static String MUTE = "MUTE";
		public final static String MUTE_TOGGLE = "MUTE_TOGGLE";
		public final static String MUTE_QUERY = "MUTE_QUERY";
		public final static String VOLUME_UP = "VOLUME_UP";
		public final static String VOLUME_DOWN = "VOLUME_DOWN";
		public final static String VOLUME_UP1 = "VOLUME_UP1";
		public final static String VOLUME_DOWN1 = "VOLUME_DOWN1";
		public final static String VOLUME_QUERY = "VOLUME_QUERY";
		public final static String VOLUME_SET = "VOLUME_SET";
		public final static String SPEAKER_SWITCH_LAYOUT = "SPEAKER_SWITCH_LAYOUT";
		public final static String SPEAKER_LAYOUT_QUERY = "SPEAKER_LAYOUT_QUERY";

		public final static String ALBUM_ART_LINK = "ART_LINK";
		public final static String ALBUM_ART_DISABLE = "ART_DISABLE";
		public final static String ALBUM_ART_REQUEST = "ART_REQUEST";
		public final static String ALBUM_ART_QUERY = "ART_QUERY";
		
			// Center
		public final static String CENTER_LEVEL_UP = "CENTER_LEVEL_UP";
		public final static String CENTER_LEVEL_DOWN = "CENTER_LEVEL_DOWN";
		public final static String CENTER_LEVEL_QUERY = "CENTER_LEVEL_QUERY";
		public final static String CENTER_LEVEL_SET = "CENTER_LEVEL_SET";
		
			// Front
		public final static String TONE_FRONT_TREBLE_UP = "TONE_FRONT_TREBLE_UP";
		public final static String TONE_FRONT_TREBLE_DOWN = "TONE_FRONT_TREBLE_DOWN";
		public final static String TONE_FRONT_BASS_UP = "TONE_FRONT_BASS_UP";
		public final static String TONE_FRONT_BASS_DOWN = "TONE_FRONT_BASS_DOWN";
		public final static String TONE_FRONT_QUERY = "TONE_FRONT_QUERY";
			// Front Wide
		public final static String TONE_FRONT_WIDE_TREBLE_UP = "TONE_FRONT_WIDE_TREBLE_UP";
		public final static String TONE_FRONT_WIDE_TREBLE_DOWN = "TONE_FRONT_WIDE_TREBLE_DOWN";
		public final static String TONE_FRONT_WIDE_BASS_UP = "TONE_FRONT_WIDE_BASS_UP";
		public final static String TONE_FRONT_WIDE_BASS_DOWN = "TONE_FRONT_WIDE_BASS_DOWN";
		public final static String TONE_FRONT_WIDE_QUERY = "TONE_FRONT_WIDE_QUERY";
			// Front High
		public final static String TONE_FRONT_HIGH_TREBLE_UP = "TONE_FRONT_HIGH_TREBLE_UP";
		public final static String TONE_FRONT_HIGH_TREBLE_DOWN = "TONE_FRONT_HIGH_TREBLE_DOWN";
		public final static String TONE_FRONT_HIGH_BASS_UP = "TONE_FRONT_HIGH_BASS_UP";
		public final static String TONE_FRONT_HIGH_BASS_DOWN = "TONE_FRONT_HIGH_BASS_DOWN";
		public final static String TONE_FRONT_HIGH_QUERY = "TONE_FRONT_HIGH_QUERY";
			// Center
		public final static String TONE_CENTER_TREBLE_UP = "TONE_CENTER_TREBLE_UP";
		public final static String TONE_CENTER_TREBLE_DOWN = "TONE_CENTER_TREBLE_DOWN";
		public final static String TONE_CENTER_BASS_UP = "TONE_CENTER_BASS_UP";
		public final static String TONE_CENTER_BASS_DOWN = "TONE_CENTER_BASS_DOWN";
		public final static String TONE_CENTER_QUERY = "TONE_CENTER_QUERY";
			// Surround
		public final static String TONE_SURROUND_TREBLE_UP = "TONE_SURROUND_TREBLE_UP";
		public final static String TONE_SURROUND_TREBLE_DOWN = "TONE_SURROUND_TREBLE_DOWN";
		public final static String TONE_SURROUND_BASS_UP = "TONE_SURROUND_BASS_UP";
		public final static String TONE_SURROUND_BASS_DOWN = "TONE_SURROUND_BASS_DOWN";
		public final static String TONE_SURROUND_QUERY = "TONE_SURROUND_QUERY";
			// Surround Back
		public final static String TONE_SURROUND_BACK_TREBLE_UP = "TONE_SURROUND_BACK_TREBLE_UP";
		public final static String TONE_SURROUND_BACK_TREBLE_DOWN = "TONE_SURROUND_BACK_TREBLE_DOWN";
		public final static String TONE_SURROUND_BACK_BASS_UP = "TONE_SURROUND_BACK_BASS_UP";
		public final static String TONE_SURROUND_BACK_BASS_DOWN = "TONE_SURROUND_BACK_BASS_DOWN";
		public final static String TONE_SURROUND_BACK_QUERY = "TONE_SURROUND_BACK_QUERY";
			// Subwoofer
		public final static String TONE_SUBWOOFER_BASS_UP = "TONE_SUBWOOFER_BASS_UP";
		public final static String TONE_SUBWOOFER_BASS_DOWN = "TONE_SUBWOOFER_BASS_DOWN";
		public final static String TONE_SUBWOOFER_QUERY = "TONE_SUBWOOFER_QUERY";

		public final static String SOURCE_DVR = "SOURCE_DVR";
		public final static String SOURCE_TIVO = "SOURCE_TIVO";
		public final static String SOURCE_GAME = "SOURCE_GAME";
		public final static String SOURCE_AUX = "SOURCE_AUX";
		public final static String SOURCE_VIDEO5 = "SOURCE_VIDEO5";
		public final static String SOURCE_COMPUTER = "SOURCE_COMPUTER";
		public final static String SOURCE_BLURAY = "SOURCE_BLURAY";
		public final static String SOURCE_TAPE1 = "SOURCE_TAPE1";
		public final static String SOURCE_TAPE2 = "SOURCE_TAPE2";
		public final static String SOURCE_PHONO = "SOURCE_PHONO";
		public final static String SOURCE_CD = "SOURCE_CD";
		public final static String SOURCE_FM = "SOURCE_FM";
		public final static String SOURCE_AM = "SOURCE_AM";
		public final static String SOURCE_TUNER = "SOURCE_TUNER";
		public final static String SOURCE_MUSICSERVER = "SOURCE_MUSICSERVER";
		public final static String SOURCE_INTERETRADIO = "SOURCE_INTERETRADIO";
		public final static String SOURCE_USB = "SOURCE_USB";
		public final static String SOURCE_USB_BACK = "SOURCE_USB_BACK";
		public final static String SOURCE_NETWORK = "SOURCE_NETWORK";
		public final static String SOURCE_MULTICH = "SOURCE_MULTICH";
		public final static String SOURCE_SIRIUS = "SOURCE_SIRIUS";
		public final static String SOURCE_UP = "SOURCE_UP";
		public final static String SOURCE_DOWN = "SOURCE_DOWN";
		public final static String SOURCE_QUERY = "SOURCE_QUERY";
		public final static String VIDEO_WIDE_AUTO = "VIDEO_WIDE_AUTO";
		public final static String VIDEO_WIDE_43 = "VIDEO_WIDE_43";
		public final static String VIDEO_WIDE_FULL = "VIDEO_WIDE_FULL";
		public final static String VIDEO_WIDE_ZOOM = "VIDEO_WIDE_ZOOM";
		public final static String VIDEO_WIDE_WIDEZOOM = "VIDEO_WIDE_WIDEZOOM";
		public final static String VIDEO_WIDE_SMARTZOOM = "VIDEO_WIDE_SMARTZOOM";
		public final static String VIDEO_WIDE_NEXT = "VIDEO_WIDE_NEXT";
		public final static String VIDEO_WIDE_QUERY = "VIDEO_WIDE_QUERY";

		public final static String VIDEO_INFO_QUERY = "VIDEO_INFO_QUERY";

		public final static String LISTEN_MODE_STEREO = "LISTEN_MODE_STEREO";
		public final static String LISTEN_MODE_DIRECT = "LISTEN_MODE_DIRECT";
		public final static String LISTEN_MODE_SURROUND = "LISTEN_MODE_SURROUND";
		public final static String LISTEN_MODE_FILM = "LISTEN_MODE_FILM";
		public final static String LISTEN_MODE_THX = "LISTEN_MODE_THX";
		public final static String LISTEN_MODE_ACTION = "LISTEN_MODE_ACTION";
		public final static String LISTEN_MODE_MUSICAL = "LISTEN_MODE_MUSICAL";
		public final static String LISTEN_MODE_ORCHESTRA = "LISTEN_MODE_ORCHESTRA";
		public final static String LISTEN_MODE_UNPLUGGED = "LISTEN_MODE_UNPLUGGED";
		public final static String LISTEN_MODE_STUDIOMIX = "LISTEN_MODE_STUDIOMIX";
		public final static String LISTEN_MODE_TVLOGIC = "LISTEN_MODE_TVLOGIC";
		public final static String LISTEN_MODE_ALLCHANSTEREO = "LISTEN_MODE_ALLCHANSTEREO";
		public final static String LISTEN_MODE_THEATER_DIMENSIONAL = "LISTEN_MODE_THEATER_DIMENSIONAL";
		public final static String LISTEN_MODE_SPORTS = "LISTEN_MODE_SPORTS";
		public final static String LISTEN_MODE_MONO = "LISTEN_MODE_MONO";
		public final static String LISTEN_MODE_PUREAUDIO = "LISTEN_MODE_PUREAUDIO";
		public final static String LISTEN_MODE_FULLMONO = "LISTEN_MODE_FULLMONO";
		public final static String LISTEN_MODE_AUDYSSEY_DSX = "LISTEN_MODE_AUDYSSEY_DSX";
		public final static String LISTEN_MODE_WHOLEHOUSE = "LISTEN_MODE_WHOLEHOUSE";
		public final static String LISTEN_MODE_PLII_MOVIE_DSX = "LISTEN_MODE_PLII_MOVIE_DSX";
		public final static String LISTEN_MODE_PLII_MUSIC_DSX = "LISTEN_MODE_PLII_MUSIC_DSX";
		public final static String LISTEN_MODE_PLII_GAME_DSX = "LISTEN_MODE_PLII_GAME_DSX";
		public final static String LISTEN_MODE_NEO_CINEMA_DSX = "LISTEN_MODE_NEO_CINEMA_DSX";
		public final static String LISTEN_MODE_NEO_MUSIC_DSX = "LISTEN_MODE_NEO_MUSIC_DSX";
		public final static String LISTEN_MODE_NEURAL_SURROUND_DSX = "LISTEN_MODE_NEURAL_SURROUND_DSX";
		public final static String LISTEN_MODE_NEURAL_DIGITAL_DSX = "LISTEN_MODE_NEURAL_DIGITAL_DSX";

		public final static String LISTEN_MODE_UP = "LISTEN_MODE_UP";
		public final static String LISTEN_MODE_DOWN = "LISTEN_MODE_DOWN";
		public final static String LISTEN_MODE_MOVIE = "LISTEN_MODE_MOVIE";
		public final static String LISTEN_MODE_MUSIC = "LISTEN_MODE_MUSIC";
		public final static String LISTEN_MODE_GAME = "LISTEN_MODE_GAME";
		public final static String LISTEN_MODE_QUERY = "LISTEN_MODE_QUERY";

		public final static String AUDIO_INFO_QUERY = "AUDIO_INFO_QUERY";

		public final static String ZONE2_POWER_ON = "ZONE2_POWER_ON";
		public final static String ZONE2_POWER_OFF = "ZONE2_POWER_OFF";
		public final static String ZONE2_POWER_QUERY = "ZONE2_POWER_QUERY";
		public final static String ZONE2_SOURCE_DVR = "ZONE2_SOURCE_DVR";
		public final static String ZONE2_SOURCE_SATELLITE = "ZONE2_SOURCE_SATELLITE";
		public final static String ZONE2_SOURCE_GAME = "ZONE2_SOURCE_GAME";
		public final static String ZONE2_SOURCE_AUX = "ZONE2_SOURCE_AUX";
		public final static String ZONE2_SOURCE_VIDEO5 = "ZONE2_SOURCE_VIDEO5";
		public final static String ZONE2_SOURCE_COMPUTER = "ZONE2_SOURCE_COMPUTER";
		public final static String ZONE2_SOURCE_BLURAY = "ZONE2_SOURCE_BLURAY";
		public final static String ZONE2_SOURCE_QUERY = "ZONE2_SOURCE_QUERY";
		public final static String ZONE2_VOLUME_UP = "ZONE2_VOLUME_UP";
		public final static String ZONE2_VOLUME_DOWN = "ZONE2_VOLUME_DOWN";
		public final static String ZONE2_VOLUME_QUERY = "ZONE2_VOLUME_QUERY";
		public final static String ZONE2_MUTE_ON = "ZONE2_MUTE_ON";
		public final static String ZONE2_MUTE_OFF = "ZONE2_MUTE_OFF";
		public final static String ZONE2_MUTE_TOGGLE = "ZONE2_MUTE_TOGGLE";
		public final static String ZONE2_MUTE_QUERY = "ZONE2_MUTE_QUERY";
			// commandMap_.put(ZONE2_SOURCE_OFF= "SLZ7F"; // not supported

		public final static String NETUSB_OP_PLAY = "NETUSB_OP_PLAY";
		public final static String NETUSB_OP_STOP = "NETUSB_OP_STOP";
		public final static String NETUSB_OP_PAUSE = "NETUSB_OP_PAUSE";
		public final static String NETUSB_OP_TRACKUP = "NETUSB_OP_TRACKUP";
		public final static String NETUSB_OP_TRACKDWN = "NETUSB_OP_TRACKDWN";
		public final static String NETUSB_OP_FF = "NETUSB_OP_FF";
		public final static String NETUSB_OP_REW = "NETUSB_OP_REW";
		public final static String NETUSB_OP_REPEAT = "NETUSB_OP_REPEAT";
		public final static String NETUSB_OP_RANDOM = "NETUSB_OP_RANDOM";
		public final static String NETUSB_OP_DISPLAY = "NETUSB_OP_DISPLAY";
		public final static String NETUSB_OP_RIGHT = "NETUSB_OP_RIGHT";
		public final static String NETUSB_OP_LEFT = "NETUSB_OP_LEFT";
		public final static String NETUSB_OP_UP = "NETUSB_OP_UP";
		public final static String NETUSB_OP_DOWN = "NETUSB_OP_DOWN";
		public final static String NETUSB_OP_SELECT = "NETUSB_OP_SELECT";
		public final static String NETUSB_OP_1 = "NETUSB_OP_1";
		public final static String NETUSB_OP_2 = "NETUSB_OP_2";
		public final static String NETUSB_OP_3 = "NETUSB_OP_3";
		public final static String NETUSB_OP_4 = "NETUSB_OP_4";
		public final static String NETUSB_OP_5 = "NETUSB_OP_5";
		public final static String NETUSB_OP_6 = "NETUSB_OP_6";
		public final static String NETUSB_OP_7 = "NETUSB_OP_7";
		public final static String NETUSB_OP_8 = "NETUSB_OP_8";
		public final static String NETUSB_OP_9 = "NETUSB_OP_9";
		public final static String NETUSB_OP_0 = "NETUSB_OP_0";
		public final static String NETUSB_OP_DELETE = "NETUSB_OP_DELETE";
		public final static String NETUSB_OP_CAPS = "NETUSB_OP_CAPS";
		public final static String NETUSB_OP_SETUP = "NETUSB_OP_SETUP";
		public final static String NETUSB_OP_RETURN = "NETUSB_OP_RETURN";
		public final static String NETUSB_OP_CHANUP = "NETUSB_OP_CHANUP";
		public final static String NETUSB_OP_CHANDWN = "NETUSB_OP_CHANDWN";
		public final static String NETUSB_OP_MENU = "NETUSB_OP_MENU";
		public final static String NETUSB_OP_TOPMENU = "NETUSB_OP_TOPMENU";
		public final static String NETUSB_SONG_ARTIST_QUERY = "NETUSB_SONG_ARTIST_QUERY";
		public final static String NETUSB_SONG_ALBUM_QUERY = "NETUSB_SONG_ALBUM_QUERY";
		public final static String NETUSB_SONG_TITLE_QUERY = "NETUSB_SONG_TITLE_QUERY";
		public final static String NETUSB_SONG_ELAPSEDTIME_QUERY = "NETUSB_SONG_ELAPSEDTIME_QUERY";
		public final static String NETUSB_SONG_TRACK_QUERY = "NETUSB_SONG_TRACK_QUERY";
		public final static String NETUSB_PLAY_STATUS_QUERY = "NETUSB_PLAY_STATUS_QUERY";

		public final static String MEMORY_SETUP_STORE = "MEMORY_SETUP_STORE";
		public final static String MEMORY_SETUP_RECALL = "MEMORY_SETUP_RECALL";
		public final static String MEMORY_SETUP_LOCK = "MEMORY_SETUP_LOCK";
		public final static String MEMORY_SETUP_UNLOCK = "MEMORY_SETUP_UNLOCK";

		public final static String OSD_MENU_COMMAND = "OSD_MENU_COMMAND";
		public final static String OSD_MENU_CONTROL_KEY_HOME = "OSD_MENU_CONTROL_KEY_HOME";
		public final static String OSD_MENU_CONTROL_KEY_QUICKSETUP = "OSD_MENU_CONTROL_KEY_QUICKSETUP";
		public final static String OSD_MENU_CONTROL_KEY_UP = "OSD_MENU_CONTROL_KEY_UP";
		public final static String OSD_MENU_CONTROL_KEY_DOWN = "OSD_MENU_CONTROL_KEY_DOWN";
		public final static String OSD_MENU_CONTROL_KEY_LEFT = "OSD_MENU_CONTROL_KEY_LEFT";
		public final static String OSD_MENU_CONTROL_KEY_RIGHT = "OSD_MENU_CONTROL_KEY_RIGHT";
		public final static String OSD_MENU_CONTROL_KEY_ENTER = "OSD_MENU_CONTROL_KEY_ENTER";
		public final static String OSD_MENU_CONTROL_KEY_EXIT = "OSD_MENU_CONTROL_KEY_EXIT";
		public final static String OSD_MENU_AUDIO_ADJUST = "OSD_MENU_AUDIO_ADJUST";
		public final static String OSD_MENU_VIDEO_ADJUST = "OSD_MENU_VIDEO_ADJUST";

		public final static String HDMI_CEC_ON = "HDMI_CEC_ON";
		public final static String HDMI_CEC_OFF = "HDMI_CEC_OFF";
		public final static String HDMI_CEC_QUERY = "HDMI_CEC_QUERY";
	}
		
	/** Maps the class contant vars to the eiscp command string. **/
	private static HashMap<String, Command> commandMessageMap = null;

	/** Maps a Readable string to a corresponding class var. **/
	private static HashMap<String, Command> commandNameMap = null;
	
	/**
	 * @return the commandMessageMap
	 */
	private static HashMap<String, Command> getCommandMessageMap() {
		
		if (commandMessageMap == null) {
			initCommandMaps();
		}
		
		return commandMessageMap;
	}

	/**
	 * @return the commandNameMap
	 */
	private static HashMap<String, Command> getCommandNameMap() {
		
		if (commandNameMap == null) {
			initCommandMaps();
		}
		
		return commandNameMap;
	}

	
	/**
	 * Gets an iterator of all commandNames.
	 * 
	 * @return the commandNames iterator as Strings
	 **/
	public static Iterator<String> getIterator() {
		TreeSet<String> ts = new TreeSet<String>(commandNameMap.keySet());
		Iterator<String> it = ts.tailSet("").iterator();
		return it;
	}

	/**
	 * searches for the commandName that is associated with the passed command.
	 * 
	 * @param commandStr
	 *            the iscp command to get a commandName key
	 * @return the commandNameMap_ key
	 **/
	public String getCommandName(String commandStr) {
		
		String result;
		Command command = getCommandMessageMap().get(commandStr);
		
		if (command == null) {
			result = "Not Found";
		}
		else {
			result = command.getName();
		}
		
		return result;
	}

	/**
	 * searches for the command constant that is associated with the passed
	 * command String. Pass in "SLI01" and get back the int value
	 * 
	 * @param commandStr
	 *            a string representation of command referencing the iscp
	 *            command
	 * @return the iscp command constant reference or -1 if not found
	 **/
	public String getCommandNameFromResponseMessage(String responseMessage) {
		
		String result;
		Command command = commandMessageMap.get(responseMessage);
		
		if (command == null) {
			result = "Not Found";
		}
		else {
			result = command.getName();
		}
		
		return result;
	}

	/**
	 * searches for the command constant that is associated with the passed
	 * command name (ie. LISTEN_MODE_THEATER_DIMENSIONAL)
	 * 
	 * @param commandName
	 *            a string representation of command referencing the iscp
	 *            command
	 * @return the iscp command constant null if not found
	 **/
	public static String getCommandMessageFromName(String commandName) {
		String result;
		
		Command command = commandNameMap.get(commandName);
		
		if (command == null) {
			result = null;
		}
		else {
			result = command.getCommand();
		}

		return result;
	}

	/**
	 * searches for the command constant that is associated with the passed
	 * command name (ie. LISTEN_MODE_THEATER_DIMENSIONAL)
	 * 
	 * @param commandName
	 *            a string representation of command referencing the iscp
	 *            command
	 * @return the iscp command constant null if not found
	 **/
	public static Command getCommandObjectFromName(String commandName) {
		Command result;
		
		Command command = getCommandNameMap().get(commandName);
		
		if (command == null) {
			result = null;
		}
		else {
			result = command;
		}

		return result;
	}

	/**
	 * This method creates the set volume command based on the passed value.
	 **/
	public static String getVolumeCmdStr(int volume) {
		return "MVL" + Integer.toHexString(volume);
	}

	/**
	 * Initializes all the class constants (commandNameMap_ & commandMap_ ) that
	 * help with processing the commands.
	 **/
	private static void initCommandMaps() {

		List<Command> list = new ArrayList<Command>();
		int count = 0;

		list.add(new Command(Commands.CommandList.POWER_OFF, "PWR00", count++));
		list.add(new Command(Commands.CommandList.POWER_ON, "PWR01", count++));
		list.add(new Command(Commands.CommandList.POWER_QUERY, "PWRQSTN", count++));
		list.add(new Command(Commands.CommandList.UNMUTE, "AMT00", count++));
		list.add(new Command(Commands.CommandList.MUTE, "AMT01", count++));
		list.add(new Command(Commands.CommandList.MUTE_TOGGLE, "AMTTG", count++));
		list.add(new Command(Commands.CommandList.MUTE_QUERY, "AMTQSTN", count++));
		list.add(new Command(Commands.CommandList.VOLUME_UP, "MVLUP", count++));
		list.add(new Command(Commands.CommandList.VOLUME_DOWN, "MVLDOWN", count++));
		list.add(new Command(Commands.CommandList.VOLUME_UP1, "MVLUP1", count++));
		list.add(new Command(Commands.CommandList.VOLUME_DOWN1, "MVLDOWN1", count++));
		list.add(new Command(Commands.CommandList.VOLUME_QUERY, "MVLQSTN", count++));
		list.add(new Command(Commands.CommandList.VOLUME_SET, "MVL", count++));
		
		list.add(new Command(Commands.CommandList.ALBUM_ART_LINK, "NJALINK", count++));
		list.add(new Command(Commands.CommandList.ALBUM_ART_DISABLE, "NJADIS", count++));
		list.add(new Command(Commands.CommandList.ALBUM_ART_REQUEST, "NJAREQ", count++));
		list.add(new Command(Commands.CommandList.ALBUM_ART_QUERY, "NJAQSTN", count++));
		

		list.add(new Command(Commands.CommandList.CENTER_LEVEL_UP, "CTLUP", count++));
		list.add(new Command(Commands.CommandList.CENTER_LEVEL_DOWN, "CTLDOWN", count++));
		list.add(new Command(Commands.CommandList.CENTER_LEVEL_QUERY, "CTLQSTN", count++));
		list.add(new Command(Commands.CommandList.CENTER_LEVEL_SET, "CTL", count++));

		list.add(new Command(Commands.CommandList.SPEAKER_SWITCH_LAYOUT, "SPLUP", count++));
		list.add(new Command(Commands.CommandList.SPEAKER_LAYOUT_QUERY, "SPLQSTN", count++));

		//Front
		list.add(new Command(Commands.CommandList.TONE_FRONT_TREBLE_UP, "TFRTUP", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_TREBLE_DOWN, "TFRTDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_BASS_UP, "TFRBUP", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_BASS_DOWN, "TFRBDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_QUERY, "TFRQSTN", count++));
		//Front Wide
		list.add(new Command(Commands.CommandList.TONE_FRONT_WIDE_TREBLE_UP, "TFWTUP", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_WIDE_TREBLE_DOWN, "TFWTDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_WIDE_BASS_UP, "TFWBUP", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_WIDE_BASS_DOWN, "TFWBDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_WIDE_QUERY, "TFWQSTN", count++));
		//Front High
		list.add(new Command(Commands.CommandList.TONE_FRONT_HIGH_TREBLE_UP, "TFHTUP", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_HIGH_TREBLE_DOWN, "TFHTDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_HIGH_BASS_UP, "TFHBUP", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_HIGH_BASS_DOWN, "TFHBDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_FRONT_HIGH_QUERY, "TFHQSTN", count++));
		//Center
		list.add(new Command(Commands.CommandList.TONE_CENTER_TREBLE_UP, "TCTTUP", count++));
		list.add(new Command(Commands.CommandList.TONE_CENTER_TREBLE_DOWN, "TCTTDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_CENTER_BASS_UP, "TCTBUP", count++));
		list.add(new Command(Commands.CommandList.TONE_CENTER_BASS_DOWN, "TCTBDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_CENTER_QUERY, "TCTQSTN", count++));

		//Surround
		list.add(new Command(Commands.CommandList.TONE_SURROUND_TREBLE_UP, "TSRTUP", count++));
		list.add(new Command(Commands.CommandList.TONE_SURROUND_TREBLE_DOWN, "TSRTDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_SURROUND_BASS_UP, "TSRBUP", count++));
		list.add(new Command(Commands.CommandList.TONE_SURROUND_BASS_DOWN, "TSRBDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_SURROUND_QUERY, "TSRQSTN", count++));
		list.add(new Command(Commands.CommandList.TONE_SURROUND_BACK_TREBLE_UP, "TSBTUP", count++));
		list.add(new Command(Commands.CommandList.TONE_SURROUND_BACK_TREBLE_DOWN, "TSBTDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_SURROUND_BACK_BASS_UP, "TSBBUP", count++));
		list.add(new Command(Commands.CommandList.TONE_SURROUND_BACK_BASS_DOWN, "TSBBDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_SURROUND_BACK_QUERY, "TSBQSTN", count++));
		
		//Subwoofer
		list.add(new Command(Commands.CommandList.TONE_SUBWOOFER_BASS_UP, "TSWBUP", count++));
		list.add(new Command(Commands.CommandList.TONE_SUBWOOFER_BASS_DOWN, "TSWBDOWN", count++));
		list.add(new Command(Commands.CommandList.TONE_SUBWOOFER_QUERY, "TSWQSTN", count++));

		// Source
		list.add(new Command(Commands.CommandList.SOURCE_DVR, "SLI00", count++));
		list.add(new Command(Commands.CommandList.SOURCE_TIVO, "SLI01", count++));
		list.add(new Command(Commands.CommandList.SOURCE_GAME, "SLI02", count++));
		list.add(new Command(Commands.CommandList.SOURCE_AUX, "SLI03", count++));
		list.add(new Command(Commands.CommandList.SOURCE_VIDEO5, "SLI04", count++));
		list.add(new Command(Commands.CommandList.SOURCE_COMPUTER, "SLI05", count++));
		list.add(new Command(Commands.CommandList.SOURCE_BLURAY, "SLI10", count++));
		list.add(new Command(Commands.CommandList.SOURCE_TAPE1, "SLI20", count++));
		list.add(new Command(Commands.CommandList.SOURCE_TAPE2, "SLI21", count++));
		list.add(new Command(Commands.CommandList.SOURCE_PHONO, "SLI22", count++));
		list.add(new Command(Commands.CommandList.SOURCE_CD, "SLI23", count++));
		list.add(new Command(Commands.CommandList.SOURCE_FM, "SLI24", count++));
		list.add(new Command(Commands.CommandList.SOURCE_AM, "SLI25", count++));
		list.add(new Command(Commands.CommandList.SOURCE_TUNER, "SLI26", count++));
		list.add(new Command(Commands.CommandList.SOURCE_MUSICSERVER, "SLI27", count++));
		list.add(new Command(Commands.CommandList.SOURCE_INTERETRADIO, "SLI28", count++));
		list.add(new Command(Commands.CommandList.SOURCE_USB, "SLI29", count++));
		list.add(new Command(Commands.CommandList.SOURCE_USB_BACK, "SLI2A", count++));
		list.add(new Command(Commands.CommandList.SOURCE_NETWORK, "SLI2C", count++));
		list.add(new Command(Commands.CommandList.SOURCE_MULTICH, "SLI30", count++));
		list.add(new Command(Commands.CommandList.SOURCE_SIRIUS, "SLI32", count++));
		list.add(new Command(Commands.CommandList.SOURCE_UP, "SLIUP", count++));
		list.add(new Command(Commands.CommandList.SOURCE_DOWN, "SLIDOWN", count++));
		list.add(new Command(Commands.CommandList.SOURCE_QUERY, "SLIQSTN", count++));
		list.add(new Command(Commands.CommandList.VIDEO_WIDE_AUTO, "VWM00", count++));
		list.add(new Command(Commands.CommandList.VIDEO_WIDE_43, "VWM01", count++));
		list.add(new Command(Commands.CommandList.VIDEO_WIDE_FULL, "VWM02", count++));
		list.add(new Command(Commands.CommandList.VIDEO_WIDE_ZOOM, "VWM03", count++));
		list.add(new Command(Commands.CommandList.VIDEO_WIDE_WIDEZOOM, "VWM04", count++));
		list.add(new Command(Commands.CommandList.VIDEO_WIDE_SMARTZOOM, "VWM05", count++));
		list.add(new Command(Commands.CommandList.VIDEO_WIDE_NEXT, "VWMUP", count++));
		list.add(new Command(Commands.CommandList.VIDEO_WIDE_QUERY, "VWMQSTN", count++));

		list.add(new Command(Commands.CommandList.VIDEO_INFO_QUERY, "IFVQSTN", count++));

		list.add(new Command(Commands.CommandList.LISTEN_MODE_STEREO, "LMD00", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_DIRECT, "LMD01", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_SURROUND, "LMD02", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_FILM, "LMD03", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_THX, "LMD04", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_ACTION, "LMD05", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_MUSICAL, "LMD06", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_ORCHESTRA, "LMD08", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_UNPLUGGED, "LMD09", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_STUDIOMIX, "LMD0A", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_TVLOGIC, "LMD0B", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_ALLCHANSTEREO, "LMD0C", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_THEATER_DIMENSIONAL, "LMD0D", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_SPORTS, "LMD0E", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_MONO, "LMD0F", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_PUREAUDIO, "LMD11", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_FULLMONO, "LMD13", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_AUDYSSEY_DSX, "LMD16", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_WHOLEHOUSE, "LMD1F", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_PLII_MOVIE_DSX, "LMDA0", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_PLII_MUSIC_DSX, "LMDA1", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_PLII_GAME_DSX, "LMDA2", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_NEO_CINEMA_DSX, "LMDA3", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_NEO_MUSIC_DSX, "LMDA4", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_NEURAL_SURROUND_DSX, "LMDA5", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_NEURAL_DIGITAL_DSX, "LMDA6", count++));

		list.add(new Command(Commands.CommandList.LISTEN_MODE_UP, "LMDUP", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_DOWN, "LMDDOWN", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_MOVIE, "LMDMOVIE", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_MUSIC, "LMDMUSIC", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_GAME, "LMDGAME", count++));
		list.add(new Command(Commands.CommandList.LISTEN_MODE_QUERY, "LMDQSTN", count++));

		list.add(new Command(Commands.CommandList.AUDIO_INFO_QUERY, "IFAQSTN", count++));

		list.add(new Command(Commands.CommandList.ZONE2_POWER_ON, "ZPW01", count++));
		list.add(new Command(Commands.CommandList.ZONE2_POWER_OFF, "ZPW00", count++));
		list.add(new Command(Commands.CommandList.ZONE2_POWER_QUERY, "ZPWQSTN", count++));
		list.add(new Command(Commands.CommandList.ZONE2_VOLUME_UP, "ZVLUP", count++));
		list.add(new Command(Commands.CommandList.ZONE2_VOLUME_DOWN, "ZVLDOWN", count++));
		list.add(new Command(Commands.CommandList.ZONE2_VOLUME_QUERY, "ZVLQSTN", count++));
		list.add(new Command(Commands.CommandList.ZONE2_MUTE_ON, "ZMT01", count++));
		list.add(new Command(Commands.CommandList.ZONE2_MUTE_OFF, "ZMT00", count++));
		list.add(new Command(Commands.CommandList.ZONE2_MUTE_TOGGLE, "ZMTTG", count++));
		list.add(new Command(Commands.CommandList.ZONE2_MUTE_QUERY, "ZVLQSTN", count++));
		
		list.add(new Command(Commands.CommandList.ZONE2_SOURCE_DVR, "SLZ00", count++));
		list.add(new Command(Commands.CommandList.ZONE2_SOURCE_SATELLITE, "SLZ01", count++));
		list.add(new Command(Commands.CommandList.ZONE2_SOURCE_GAME, "SLZ02", count++));
		list.add(new Command(Commands.CommandList.ZONE2_SOURCE_AUX, "SLZ03", count++));
		list.add(new Command(Commands.CommandList.ZONE2_SOURCE_VIDEO5, "SLZ04", count++));
		list.add(new Command(Commands.CommandList.ZONE2_SOURCE_COMPUTER, "SLZ05", count++));
		list.add(new Command(Commands.CommandList.ZONE2_SOURCE_BLURAY, "SLZ10", count++));
		list.add(new Command(Commands.CommandList.ZONE2_SOURCE_QUERY, "SLZQSTN", count++));
		//list.add(new IscpCommand(Commands.CommandList.ZONE2_SOURCE_OFF, "SLZ7F", count++); // not supported);

		list.add(new Command(Commands.CommandList.NETUSB_OP_PLAY, "NTCPLAY", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_STOP, "NTCSTOP", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_PAUSE, "NTCPAUSE", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_TRACKUP, "NTCTRUP", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_TRACKDWN, "NTCTRDN", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_FF, "NTCFF", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_REW, "NTCREW", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_REPEAT, "NTCREPEAT", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_RANDOM, "NTCRANDOM", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_DISPLAY, "NTCDISPLAY", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_RIGHT, "NTCRIGHT", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_LEFT, "NTCLEFT", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_UP, "NTCUP", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_DOWN, "NTCDOWN", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_SELECT, "NTCSELECT", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_1, "NTC1", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_2, "NTC2", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_3, "NTC3", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_4, "NTC4", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_5, "NTC5", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_6, "NTC6", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_7, "NTC7", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_8, "NTC8", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_9, "NTC9", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_0, "NTC0", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_DELETE, "NTCDELETE", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_CAPS, "NTCCAPS", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_SETUP, "NTCSETUP", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_RETURN, "NTCRETURN", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_CHANUP, "NTCCHUP", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_CHANDWN, "NTCCHDN", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_MENU, "NTCMENU", count++));
		list.add(new Command(Commands.CommandList.NETUSB_OP_TOPMENU, "NTCTOP", count++));
		list.add(new Command(Commands.CommandList.NETUSB_SONG_ARTIST_QUERY, "NATQSTN", count++));
		list.add(new Command(Commands.CommandList.NETUSB_SONG_ALBUM_QUERY, "NALQSTN", count++));
		list.add(new Command(Commands.CommandList.NETUSB_SONG_TITLE_QUERY, "NTIQSTN", count++));
		list.add(new Command(Commands.CommandList.NETUSB_SONG_ELAPSEDTIME_QUERY, "NTMQSTN", count++));
		list.add(new Command(Commands.CommandList.NETUSB_SONG_TRACK_QUERY, "NTRQSTN", count++));
		list.add(new Command(Commands.CommandList.NETUSB_PLAY_STATUS_QUERY, "NSTQSTN", count++));

		list.add(new Command(Commands.CommandList.MEMORY_SETUP_STORE, "MEMSTR", count++));
		list.add(new Command(Commands.CommandList.MEMORY_SETUP_RECALL, "MEMRCL", count++));
		list.add(new Command(Commands.CommandList.MEMORY_SETUP_LOCK, "MEMLOCK", count++));
		list.add(new Command(Commands.CommandList.MEMORY_SETUP_UNLOCK, "MEMUNLK", count++));

		list.add(new Command(Commands.CommandList.OSD_MENU_COMMAND, "OSDMEMU", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_CONTROL_KEY_HOME, "OSDHOME", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_CONTROL_KEY_QUICKSETUP, "OSDQUICK", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_CONTROL_KEY_UP, "OSDUP", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_CONTROL_KEY_DOWN, "OSDDOWN", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_CONTROL_KEY_LEFT, "OSDLEFT", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_CONTROL_KEY_RIGHT, "OSDRIGHT", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_CONTROL_KEY_ENTER, "OSDENTER", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_CONTROL_KEY_EXIT, "OSDEXIT", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_AUDIO_ADJUST, "OSDAUDIO", count++));
		list.add(new Command(Commands.CommandList.OSD_MENU_VIDEO_ADJUST, "OSDVIDEO", count++));

		list.add(new Command(Commands.CommandList.HDMI_CEC_ON, "CEC01", count++));
		list.add(new Command(Commands.CommandList.HDMI_CEC_OFF, "CEC00", count++));
		list.add(new Command(Commands.CommandList.HDMI_CEC_QUERY, "CECQSTN", count++));
		
		commandNameMap = new HashMap<String, Command>();
		commandMessageMap = new HashMap<String, Command>();

		for(Iterator<Command> iter = list.iterator();iter.hasNext(); )
		{
			// get the current command & add them to the maps
			Command command = iter.next();
			commandNameMap.put(command.getName(), command);
			commandMessageMap.put(command.getCommand(), command);
			
			// print the commands as they get added
			// System.out.println("added: " + command.toString());
			
		}

	}


} // class
