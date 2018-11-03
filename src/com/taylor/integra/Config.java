package com.taylor.integra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.taylor.utilities.AbstractConfig;

public class Config extends AbstractConfig {
	
	private static final String DEFAULT_FILENAME = "integra.xml";
	
	private static Map<String, Receiver> receiverMap;
	private static List<String> receiverList;
	private static Map<String, Command> commandMap;
	private static Map<String, String> labelMap;
	private static List<String> commandList;
	public static String defaultLocation;
	
	public Map<String, Receiver> getReceiverMap() {
		return receiverMap;
	}
	
	protected String getDefaultFilename() {
		return DEFAULT_FILENAME;
	}
	
	public List<String> getReceiverList() {
		if (receiverList == null) {
			receiverList = new ArrayList<String>();
			for (Iterator<String> iter = receiverMap.keySet().iterator(); iter.hasNext();) {
				receiverList.add(iter.next());
			}
			Collections.sort(receiverList);
		}
		
		return receiverList;
	}
	
	public static Map<String, Command> getCommandMap() {
		return commandMap;
	}

	public static Map<String, String> getLabelMap() {
		return labelMap;
	}
	
	public static List<String> getCommandList() {
		if (commandList == null) {
			commandList = new ArrayList<String>();
			for (Iterator<String> iter = commandMap.keySet().iterator(); iter.hasNext();) {
				commandList.add(iter.next());
			}
			Collections.sort(commandList);
		}
		
		return commandList;
	}
	

	Config() throws Exception {
		super();
	}
	
	Config(String configFilePath) throws Exception {
		super(configFilePath);
	}

	protected void init(String configFilePath) throws Exception {
		super.init(configFilePath);
		parseConfigFile();
	}
	
	private void parseConfigFile() {
		
		// get the commands
		commandMap = new HashMap<String, Command>();
		for (Iterator<String> iter = super.commands.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String command = super.commands.get(name);
			commandMap.put(name, new Command(name, command));
		}
		
		labelMap = super.labels;
		
		// get the controllers
		try {
			receiverMap = new HashMap<String, Receiver>();
			
			// get the controller nodes from the document
			NodeList nodes = getConfigDocument().getElementsByTagName("receiver");
			for(int i = 0; i < nodes.getLength(); i++) {
				
				// get the values from the controller entry
				Element element = (Element) nodes.item(i);
				NamedNodeMap elements = element.getAttributes();


				String name = null;
				String ipAddress = null;
				Integer port = null;
				Integer delay = null;
				
				// loop through them to allow for case-insensitive names
				for (int j=0; j < elements.getLength(); j++) {
					Node controllerNode =  elements.item(j);
					String controllerNodeName = controllerNode.getNodeName().toLowerCase();
					
					if (controllerNodeName.equals("ipaddress")) {
						ipAddress = controllerNode.getNodeValue();
					}
					else if (controllerNodeName.equals("name")) {
						name = controllerNode.getNodeValue();
					}
					else if (controllerNodeName.equals("port")) {
						port = Integer.parseInt(controllerNode.getNodeValue());
					}
					else if (controllerNodeName.equals("delay")) {
						delay = Integer.parseInt(controllerNode.getNodeValue());
					}

				}
				
				// build the controller & add it to the list
				Receiver controller = new Receiver(name, ipAddress, port, delay);
				receiverMap.put(name, controller);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
