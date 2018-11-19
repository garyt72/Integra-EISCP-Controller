package com.taylor.integra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.taylor.utilities.Util;

public class Response {

	//private static final boolean DEBUGGING = Util.DEBUGGING;
	private static final boolean DEBUGGING = EISCP.DEBUG;

	public class ResponseHeader {
		private String start;
		private int headerSize;
		private int dataSize;
		private int version;
		private String reserved;

		/**
		 * @return the start
		 */
		public String getStart() {
			return start;
		}

		/**
		 * @param start the start to set
		 */
		public void setStart(char[] start) {
			setStart(new String(start));
		}

		/**
		 * @param start the start to set
		 */
		public void setStart(String start) {
			this.start = start;
		}

		/**
		 * @return the headerSize
		 */
		public int getHeaderSize() {
			return headerSize;
		}

		/**
		 * @param headerSize the headerSize to set
		 */
		public void setHeaderSize(char[] headerSize) {			
			setHeaderSize(Util.convertBigEndianHexStringToInteger(new String(headerSize)));
		}

		/**
		 * @param headerSize the headerSize to set
		 */
		public void setHeaderSize(int headerSize) {
			this.headerSize = headerSize;
		}

		/**
		 * @return the dataSize
		 */
		public int getDataSize() {
			return dataSize;
		}

		/**
		 * @param dataSize the dataSize to set
		 */
		public void setDataSize(char[] dataSize) {
			setDataSize(Util.convertBigEndianHexStringToInteger(new String(dataSize)));
		}

		/**
		 * @param dataSize the dataSize to set
		 */
		public void setDataSize(int dataSize) {
			this.dataSize = dataSize;
		}

		/**
		 * @return the version
		 */
		public int getVersion() {
			return version;
		}

		/**
		 * @param version the version to set
		 */
		public void setVersion(char[] version) {
			setVersion(Util.convertBigEndianHexStringToInteger(new String(version)));
		}

		/**
		 * @param version the version to set
		 */
		public void setVersion(int version) {
			this.version = version;
		}

		/**
		 * @return the reserved
		 */
		public String getReserved() {
			return reserved;
		}

		/**
		 * @param reserved the reserved to set
		 */
		public void setReserved(char[] reserved) {
			setReserved(new String(reserved));
		}

		/**
		 * @param reserved the reserved to set
		 */
		public void setReserved(String reserved) {
			this.reserved = reserved;
		}

		ResponseHeader () {
			init("", 0, 0, 0, "");
		}
		
		ResponseHeader(String start, int headerSize, int dataSize, int version, String reserved) {
			init(start, headerSize, dataSize, version, reserved);
		}
		
		private void init(String start, int headerSize, int dataSize, int version, String reserved) {
			setStart(start);
			setHeaderSize(headerSize);
			setDataSize(dataSize);
			setVersion(version);
			setReserved(reserved);
		}
		
	}
	
	public class ResponseData {
		private String startChar;
		private String unitType;
		private String message;
		private String end;
		
		/**
		 * @return the startChar
		 */
		public String getStartChar() {
			return startChar;
		}

		/**
		 * @param startChar the startChar to set
		 */
		public void setStartChar(char startChar[]) {
			setStartChar(new String(startChar));
		}

		/**
		 * @param startChar the startChar to set
		 */
		public void setStartChar(String startChar) {
			this.startChar = startChar;
		}

		/**
		 * @return the destinationUnittype
		 */
		public String getUnitType() {
			return unitType;
		}

		/**
		 * @param destinationUnittype the destinationUnittype to set
		 */
		public void setUnitType(char[] destinationUnittype) {
			setUnitType(new String(destinationUnittype));
		}

		/**
		 * @param destinationUnittype the destinationUnittype to set
		 */
		public void setUnitType(String destinationUnittype) {
			this.unitType = destinationUnittype;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}
		
		/**
		 * @return message command (first 3 letters)
		 */
		public String getMessageCommand() {
			return getMessage().substring(0,3);
		}
		
		/**
		 * 
		 * @return message data (everything after the first 3 characters)
		 */
		public String getMessageData() {
			return getMessage().substring(3, getMessage().length());
		}
		
		/**
		 * Converts a MessageData HEX number String to its decimal equivalent.
		 * @return the decimal equivalent of the passed in HEX numberStr
		 */
		public int getMessageDataDecimal() {
			return Integer.parseInt(getMessageData(), 16);
		}
		

		/**
		 * @param message the message to set
		 */
		public void setMessage(char[] message) {
			setMessage(new String(message));
		}

		/**
		 * @param message the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}

		/**
		 * @return the end
		 */
		public String getEnd() {
			return end;
		}

		/**
		 * @param end the end to set
		 */
		public void setEnd(char[] end) {
			setEnd(new String(end));
		}

		/**
		 * @param end the end to set
		 */
		public void setEnd(String end) {
			this.end = end;
		}

		ResponseData () {
			init("", "", "", "");
		}
		
		private void init(String startChar, String unittype, String message, String end) {
			setStartChar(startChar);
			setUnitType(unittype);
			setMessage(message);
			setEnd(end);
		}
	}
	
	ResponseHeader header;
	ResponseData data;
	
	
	/**
	 * @return the header
	 */
	public ResponseHeader getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(ResponseHeader header) {
		this.header = header;
	}

	/**
	 * @return the data
	 */
	public ResponseData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ResponseData data) {
		this.data = data;
	}

	/**
	 * New empty response
	 */
	Response() {
		init(new ResponseHeader(), new ResponseData());
	}
	
	/**
	 * Build and parse a response from a string
	 * @param response the response string to parse
	 */	
	Response(String response) {
		parseResponseString(response);
	}
	
	private void init() {
		setHeader(new ResponseHeader());
		setData(new ResponseData());
	}
	
	private void init(ResponseHeader header, ResponseData data) {
		setHeader(header);
		setData(data);
	}
	
	private boolean parseResponseString(String response) {
		
		// initialize the header and response objects
		init();
		
		boolean success = false;
		
		try {
			
			// parse the response string it into dataMessages */
			
			if (DEBUGGING) System.out.println("Processing the header...");
			
			char[] responseChars = response.toCharArray();
			int responseByteCnt = 0;
			
	
			/* read Header */
			// 1st 4 chars are the leadIn
			char[] headerStart = new char[4];
			headerStart[0] = responseChars[responseByteCnt++];
			headerStart[1] = responseChars[responseByteCnt++];
			headerStart[2] = responseChars[responseByteCnt++];
			headerStart[3] = responseChars[responseByteCnt++];
			getHeader().setStart(headerStart);
	
			// read headerSize
			char[] headerSizeBytes = new char[4];
			headerSizeBytes[0] = responseChars[responseByteCnt++];
			headerSizeBytes[1] = responseChars[responseByteCnt++];
			headerSizeBytes[2] = responseChars[responseByteCnt++];
			headerSizeBytes[3] = responseChars[responseByteCnt++];
			getHeader().setHeaderSize(headerSizeBytes);
	
			// 4 char Big Endian data size;
			char[] dataSizeBytes = new char[4];
			dataSizeBytes[0] = responseChars[responseByteCnt++];
			dataSizeBytes[1] = responseChars[responseByteCnt++];
			dataSizeBytes[2] = responseChars[responseByteCnt++];
			dataSizeBytes[3] = responseChars[responseByteCnt++];
			getHeader().setDataSize(dataSizeBytes);
			
			// 1 char Version 
			char[] versionByte = new char[1];
			versionByte[0] = responseChars[responseByteCnt++];
			getHeader().setVersion(versionByte);
			
			// 3 char reserved data
			char[] reservedBytes = new char[3];
			reservedBytes[0] = responseChars[responseByteCnt++];
			reservedBytes[1] = responseChars[responseByteCnt++];
			reservedBytes[2] = responseChars[responseByteCnt++];
			getHeader().setReserved(reservedBytes);
	
			if (DEBUGGING) {
				System.out.println("   Header Start: " + getHeader().getStart());
				System.out.println("   Header Size:  " + getHeader().getHeaderSize());
				System.out.println("   Data Size:    " + getHeader().getDataSize());
				System.out.println("   Version:      " + getHeader().getVersion());
				System.out.println("Processing the message...");
			}
	
			// *****
			// *****
			// *****
			// ***** we are now at the beginning of the message
			// *****
			// *****
			// *****
			
			String dataString = response.substring(getHeader().getHeaderSize(), response.length());
			char[] dataChars = dataString.toCharArray(); // init dynamically
			int dataByteCnt = 0;
			
			// 1 char - data start
			char[] dataStart = new char[1];
			dataStart[0] = dataChars[dataByteCnt++];
			getData().setStartChar(dataStart);
			
			// 1 char - device unit type
			char[] dataDevice = new char[1];
			dataDevice[0] = dataChars[dataByteCnt++];
			getData().setUnitType(dataStart);
			
			// char array (data size in length) - message
			char[] dataMessage = new char[getHeader().getDataSize() - 5]; // data size - 2 (start + device) - 3 (end chars)
			for (int i = 0; i < (getHeader().getDataSize() - 5); i++) {
				dataMessage[i] = dataChars[dataByteCnt++];
			}
			getData().setMessage(dataMessage);
		
			// 3 char - end data end
			char[] dataEnd = new char[3];
			dataEnd[0] = dataChars[dataByteCnt++];
			dataEnd[1] = dataChars[dataByteCnt++];
			dataEnd[2] = dataChars[dataByteCnt++];
			getData().setEnd(dataEnd);
						
			if (DEBUGGING) {
				System.out.println("   Message Start: " + new String(dataStart));
				System.out.println("   Device:        " + new String(dataDevice));
				System.out.println("   Message:       " + new String(dataMessage));
				System.out.println("   "
						+ " End:   " + Util.displayAsciiCodes(new String(dataEnd)).trim());
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		
		

		return success;
	}
	
	/**
	 * retrieves the packet end string from a string containing one or more response packets
	 * @param responseString the string conaining all responses received
	 * @return List<String> of strings each containing a single response packet
	 */
	public static List<String> splitResponses (String responseString) {

		// initialize the list we will be returning
		List<String> responses = new ArrayList<String>();
		
		// parse the first packet to get the end string that we will use to split the responseString
		String endString = new Response(responseString).getData().getEnd();

		// split the responseString using the end string
		String[] splitStrings = responseString.split(endString);
		
		// loop through all responses to add the end back onto each packet
		for (int i = 0; i < splitStrings.length; i++) {
			responses.add(splitStrings[i] + endString);
		}
		
		return responses;
	}

	/**
	 * Parses responseString into Response objects
	 * @param responseString String containing one or more response packets
	 * @return List<Response> of Response objects, one response for each packet in the responseString
	 */
	public static List<Response> parseResponses (String responseString) {
		List<Response> responses = new ArrayList<Response>();
		
		List<String> responseStrings = Response.splitResponses(responseString);
		
		for (Iterator<String> iter = responseStrings.iterator(); iter.hasNext(); ) {
			responses.add(new Response(iter.next()));
		}
				
		return responses;
	}

	public static String getResponse (String responseMessage) {
		return getResponse(responseMessage, false, false);
	}
	
	public static String getResponse (String responseMessage, boolean verbose, boolean friendly) {
				
		// get the command prefix (command) from the response string
		String prefix = responseMessage.substring(0,3);
		String data = responseMessage.substring(3, responseMessage.length());
		
		String responseLabel = new String();
		String responseValue = responseMessage;
		
		// switch to prefix specifc code
		switch (prefix) {
			case "CTL":	responseLabel = "Center Level";
						responseValue = data;
						break;
			case "MVL":	responseLabel = "Main Volume";
			            try{
							responseValue = new Double((double)Integer.parseInt(data, 16) / 2).toString();
			            }catch (Exception e) {
			            	responseValue = data;
			            }
						break;
			case "PWR":	responseLabel = "Main Power";
						responseValue = Config.getLabelMap().get(responseMessage);
						break;
			case "ZPW":	responseLabel = "Zone2 Power";
						responseValue = Config.getLabelMap().get(responseMessage);
						break;
			case "ZVL":	responseLabel = "Zone2 Volume";
						responseValue = new Double((double)Integer.parseInt(data, 16) / 2).toString();
						break;
			case "AMT":	responseLabel = "Main Mute";
						try{
							responseValue = Config.getLabelMap().get(responseMessage);
						}catch (Exception e) {
							responseValue = data;
						}
						break;
			case "ZMT":	responseLabel = "Zone2 Mute";
						responseValue = Config.getLabelMap().get(responseMessage);
						break;
			case "SLI": responseLabel = "Main Input";
						responseValue = Config.getLabelMap().get(responseMessage);
						break;
			case "SLZ": responseLabel = "Zone2 Input";
						responseValue = Config.getLabelMap().get(responseMessage);
						break;
			default:	responseLabel = responseMessage;
			            try{
			            	responseValue = Config.getLabelMap().get(responseMessage);
			            }
			            catch(Exception e) {
							responseValue = "";
			            }
		}
		
		if (friendly == false) {
			responseValue = responseMessage;
		}
		
		String response = new String();
		if (verbose == true) {
			response = responseLabel + " " + responseValue;
		} else {
			response = responseValue;
		}
		
		return response.trim();
	}
	
}
