/**
 * 
 */
package com.captech.agentx.core.specs;

import static org.mockito.Mockito.*;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.captech.agentx.AgentBootstrapper;
import com.captech.agentx.XmppAgent;

/**
 * This specification verifies assumptions and common behavior of collaborators
 * to the AgentBootstrapper
 * 
 * @author Chris Wash
 * 
 */
public class AgentBootstrapperSpecs {

    /**
     * Use a Mockito 'mock' object to record the bootstrapper's
     * interaction with ConfigurationService
     */
    @Mock Logger mock_log;
    @Mock XmppAgent mock_bot;
    
    /**
     * Class Under Test, @InjectMocks will pass mocks to matching setters/constructors
     */
    @InjectMocks AgentBootstrapper boot_with_mocks = new AgentBootstrapper(mock_log, mock_bot);
    

    /**
     * Reinitialize the Mocks for each @Test
     * @throws Exception
     */
    @Before
    public void initialize_mocks() throws Exception {

	MockitoAnnotations.initMocks(this);

    }


    /*
     * Given the bootstrapper has retrieved the server information from configuration 
     * When bootstrap is called 
     * Then the bootstrapper should start the bot with the retrieved credentials
     */
    @Test
    public void bootstrapper_should_retrieve_login_credentials_and_start_bot()
	    throws Exception {

	// given
	// boot_with_mocks initialized already

	// when
	boot_with_mocks.bootstrap();

	// then
	verify(mock_bot).start();	

    }

}
