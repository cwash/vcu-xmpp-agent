/**
 * 
 */
package com.captech.agentx.core.specs;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

import com.captech.agentx.XmppAgent;
import com.captech.agentx.XmppAgentImpl;
import com.captech.agentx.core.exception.ServerConfigurationException;

/**
 * Spec and whitebox tests for the XmppAgentImpl class
 * 
 * @author Chris Wash
 *
 */
public class XmppAgentSpecs {

    @Mock Logger log;
    
    @InjectMocks XmppAgent validAgent = new XmppAgentImpl(log, "chris", "pass", "gtalk.com", "80", "google");
    @InjectMocks XmppAgent secretAgent = new XmppAgentImpl(log, "james", "bond", "mi7.mil", "007", "codespeak");
    @InjectMocks XmppAgent invalidAgent_nulls = new XmppAgentImpl(log, null, null, null, null, null);
    @InjectMocks XmppAgent invalidAgent_nonInteger_port = new XmppAgentImpl(log, "chris", "pass", "gtalk.com", "doh", "google");
    
    
    
    @Before
    public void initialize_mocks() throws Exception {

	MockitoAnnotations.initMocks(this);

    }
    
    /*
     * Given the agent is initialized
     * When the agent is started
     * Then the agent should validate the server info provided by the bootstrapper
     */
    @Test
    public void agent_should_validate_the_server_info_provided_on_initialization() throws Exception {
	
	XmppAgent doubleAgent = spy(secretAgent);
	doubleAgent.start();
	
	/* 
	 * uncomment the following line and watch the test fail...
	 *
	 * REMEMBER IT IS IMPORTANT TO ALWAYS WATCH YOUR TEST FAIL WHEN YOU ARE
	 * WRITING IT, EVEN IF IT PASSES AT THE TIME YOU WRITE IT!
	 */
//	verify(doubleAgent, times(0)).validateConfiguration();
	
	/* times(1) is the default and can be omitted */
	verify(doubleAgent).validateConfiguration();
    }
    
    /* Whitebox Testing - Private Methods */
    /* These tests use PowerMock to test private methods on the class */
    /* See 'Bypass Encapsulation' - http://code.google.com/p/powermock/wiki/BypassEncapsulation */
    
    @Test
    public void whitebox_test_validateConfiguration_with_invalid_config_nulls() throws Exception {
	
	try {

	    Whitebox.invokeMethod(invalidAgent_nulls, "validateConfiguration");
	    fail("Validation Exception should have been thrown");
	
	} catch (ServerConfigurationException caught) {

	    assertThat(caught.getMessage(), containsString("IllegalArgument"));
	    
	} catch (Throwable t) {
	    
	    fail("Wrong exception was thrown: " + t);
	}
	
    }

    @Test
    public void whitebox_test_validateConfiguration_with_invalid_config_nonInteger_port() throws Exception {
	
	try {
	    
	    Whitebox.invokeMethod(invalidAgent_nonInteger_port, "validateConfiguration");
	    fail("Validation Exception should have been thrown");
	    
	} catch (ServerConfigurationException caught) {
	    
	    assertThat(caught.getMessage(), containsString("NumberFormat"));
	    
	} catch (Throwable t) {
	    
	    fail("Wrong exception was thrown: " + t);
	}
	
    }
    
    @Test
    public void whitebox_test_validateConfiguration_with_valid_config() throws Exception {
	
	Whitebox.invokeMethod(validAgent, "validateConfiguration");
	
	/* nothing really to verify at this point... tautology time */
	assertTrue(true);
	
    }
}
