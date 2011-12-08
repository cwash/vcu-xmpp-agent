/**
 * 
 */
package com.captech.agentx;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.captech.agentx.core.exception.ServerConfigurationException;
import com.captech.agentx.core.util.Contract;

/**
 * XXX
 * 
 * @author Chris Wash
 *
 */
public class XmppAgentImpl implements XmppAgent {

    /* make fields final so that objects of this class are immutable */
    /* see http://code.google.com/p/google-guice/wiki/MinimizeMutability */
    private final Logger log;
    private final String username;
    private final String password; 
    private final String hostname;
    private final String port;
    private final String servicename;
    
    
    /**
     * @param log
     * @param username
     * @param password
     * @param hostname
     * @param port
     */
    @Inject
    public XmppAgentImpl(Logger log, String username, String password, String hostname, String port, String servicename) {
	super();
	this.log = log;
	this.username = username;
	this.password = password;
	this.hostname = hostname;
	this.port = port;
	this.servicename = servicename;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHostname() {
        return hostname;
    }

    public String getPort() {
        return port;
    }

    public String getServicename() {
	return servicename;
    }


    /* (non-Javadoc)
     * @see com.captech.agentx.XmppAgent#start(java.lang.String, java.lang.String, java.lang.String)
     */
    public void start() {
	log.log(Level.INFO, "Using configuration value for username -> {0}", username);
	log.log(Level.INFO, "Using configuration value for password -> {0}", password);
	log.log(Level.INFO, "Using configuration value for hostname -> {0}", hostname);
	log.log(Level.INFO, "Using configuration value for port -> {0}", port);
	log.log(Level.INFO, "Using configuration value for servicename -> {0}", servicename);
	
	/* TODO: it would be better to move this to the CONSTRUCTOR - that way you can NEVER create an invalid Agent. */
	validateConfiguration();
    }
    
    /**
     * Check each of the pieces of server configuration is valid (hasText)
     */
    public void validateConfiguration() {
	
	try {
	    
	    /* hasText == not null and contain at least one non whitespace character */
	    Contract.hasText(username);
	    Contract.hasText(password);
	    Contract.hasText(hostname);
	    Contract.hasText(port);
	    Contract.hasText(servicename);
	    
	    Integer.parseInt(port);
	    
	} catch (Exception e) {
	    
	    log.info("Problem found when validating server configuration");
	    throw new ServerConfigurationException(e);
	    
	}
    }
    
    /**
     * To be called on startup.
     * 
     * PRECONDITION: has called validateConfiguration already
     */
    public void connect() {
	
	/* TODO: change the member on this class to store the Integer after parsing, instead of a String value */
	Integer integerPort = Integer.parseInt(port);
	
	ConnectionConfiguration smackConfig = new ConnectionConfiguration(hostname, integerPort, servicename);
	smackConfig.setSASLAuthenticationEnabled(false);
	
	/* TODO: make this setting configurable so that it can be cut off in higher environments */
	smackConfig.setDebuggerEnabled(true);
	
	XMPPConnection connection = new XMPPConnection(smackConfig);
	
	try {
	    connection.connect();
	    
	    connection.login(username, password);
	    
	    log.info("Agent '" + username +"' started...");
	    
	    // say hi to buddies...
	    
	    ChatManager manager = connection.getChatManager();
	    MessageListener listener = new MessageListener() {
		public void processMessage(Chat chat, Message message) {
		    if (message.getError() != null) {
			log.info("Error received in " + chat.getParticipant() + "'s chat: " + message.getError().toString());
			return;
		    }
		    
		    if (message.getBody().equalsIgnoreCase("shutdown")) {
			// TODO: send an event to the bootstrapper to shutdown
		    }
		    
		    try {
			chat.sendMessage("[ACK]");
		    } catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    log.info("" + chat.getParticipant() + " requests " + message.getBody());
		    
		    // TODO: dispatch commands
		}
	    };
	} catch (XMPPException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }

}
