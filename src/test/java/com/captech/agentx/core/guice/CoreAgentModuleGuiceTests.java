/**
 * 
 */
package com.captech.agentx.core.guice;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.captech.agentx.ConfigurationService;
import com.captech.agentx.XmppAgent;
import com.captech.agentx.guice.CoreAgentModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * This file centralizes a lot of the Guice dependency-injection related
 * configuration. Could consider refactoring this to use @ProvidedBy and @ImplementedBy
 * and get rid of the code here, but no clear advantage to that approach at this
 * time.
 * 
 * @author Chris Wash
 * 
 */
public class CoreAgentModuleGuiceTests {

    /**
     * Tests that the core module should inject configuration service for us.
     * 
     * @throws Exception
     */
    @Test
    public void module_should_inject_configuration_service() throws Exception {

	Injector injector = Guice.createInjector(new CoreAgentModule());

	// next line will throw an exception if dependencies missing
	injector.getProvider(ConfigurationService.class);

    }

    /**
     * Tests that the core module 'provides' an XmppAgent when we want one
     * injected - that is, it runs through the 'Provider' method in the module
     * to create/initialize the class using the ConfigurationService.
     * 
     * @throws Exception
     */
    @Test
    public void module_should_provide_xmppAgent_using_configuration_service()
	    throws Exception {

	/* given */
	Injector injector = Guice.createInjector(new CoreAgentModule());

	/* when */
	XmppAgent agent = injector.getInstance(XmppAgent.class);

	/* then */
	assertThat(agent.getUsername(), equalTo("test-cwash"));
	assertThat(agent.getPassword(), equalTo("test-passh"));
	assertThat(agent.getHostname(), equalTo("test-gtalk.com"));
	assertThat(agent.getPort(), equalTo("9090"));
    }

}
