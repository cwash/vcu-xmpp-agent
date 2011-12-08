/**
 * 
 */
package com.captech.agentx;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.captech.agentx.guice.CoreAgentModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Gets an instance of the agent from Guice and starts.
 * 
 * @author Chris Wash
 *
 */
public class AgentBootstrapper {

    private final XmppAgent agent;
    
    private final Logger log;

    @Inject
    public AgentBootstrapper(Logger log, XmppAgent agent) {
	this.log = log;
	this.agent = agent;
    }

    public void bootstrap() {
	
	agent.start();
	
	log.info("Agent started.");
    }
    
    public static void main(String... args) {
	
	Injector injector = Guice.createInjector(new CoreAgentModule());
	AgentBootstrapper startup = injector.getInstance(AgentBootstrapper.class);
	
	startup.bootstrap();
    
    }

   
}
