/**
 * 
 */
package com.captech.agentx.guice;

import java.util.logging.Logger;

import com.captech.agentx.ConfigurationService;
import com.captech.agentx.ConfigurationServiceImpl;
import com.captech.agentx.XmppAgent;
import com.captech.agentx.XmppAgentImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * This class represents a Guice Module that defines binding configuration for
 * the core set of classes.
 * 
 * @author Chris Wash
 * 
 */
public class CoreAgentModule extends AbstractModule {

    /**
     * Setup the Guice Module by defining the binding configuration.
     */
    @Override
    protected void configure() {
	bind(ConfigurationService.class).to(ConfigurationServiceImpl.class);
    }
    
    @Provides
    XmppAgent provideAgent(Logger log, ConfigurationService config) {
	
	String username = config.loadUsername();
	String password = config.loadPassword();
	String hostname = config.loadHostname();
	String port = config.loadPort();
	String servicename = config.loadServicename();
	
	return new XmppAgentImpl(log, username, password, hostname, port, servicename);
    }

}
