/**
 * 
 */
package com.captech.agentx.core.state;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.captech.agentx.ConfigurationService;
import com.captech.agentx.ConfigurationServiceImpl;

/**
 * State-based unit testing for ConfigurationServiceImpl
 * 
 * @author Chris Wash
 *
 */
public class ConfigurationServiceImplStateTests {
    
    static ConfigurationService service;
    
    /* these need to be maintained alongside the test agentx-config.properties */
    private static final String EXPECTED_USERNAME = "test-cwash";
    private static final String EXPECTED_PASSWORD = "test-passh";
    private static final String EXPECTED_HOSTNAME = "test-gtalk.com";
    private static final String EXPECTED_PORT = "9090";
    private static final String EXPECTED_SERVICENAME = "service";
    
    @BeforeClass
    public static void initializeService() {
	service = new ConfigurationServiceImpl();
    }
    
    /* Tests can be "wet" but not soaking... */
    
    @Test
    public void configuration_service_should_load_username() {

	String expected = EXPECTED_USERNAME;
	String actual = service.loadUsername();
	assertThat(actual, equalTo(expected));
	
    }
    
    @Test
    public void configuration_service_should_load_password() throws Exception {
	
	String expected = EXPECTED_PASSWORD;
	String actual = service.loadPassword();
	assertThat(actual, equalTo(expected));
	
    }
    
    @Test
    public void configuration_service_should_load_hostname() throws Exception {
	
	String expected = EXPECTED_HOSTNAME;
	String actual = service.loadHostname();
	assertThat(actual, equalTo(expected));

    }
    
    @Test
    public void configuration_service_should_load_port() throws Exception {
	
	String expected = EXPECTED_PORT;
	String actual = service.loadPort();
	assertThat(actual, equalTo(expected));
	
    }
    
    @Test
    public void configuration_service_should_load_servicename() throws Exception {
	
	String expected = EXPECTED_SERVICENAME;
	String actual = service.loadServicename();
	assertThat(actual, equalTo(expected));
	
    }
    
    /* Whitebox Testing - Private Methods */
    /* These tests use PowerMock to test private methods on the class */
    /* See 'Bypass Encapsulation' - http://code.google.com/p/powermock/wiki/BypassEncapsulation */

    @Test
    public void whitebox_test_loadConfigurationProperties() throws Exception {
	String expected = "test-cwash";
	Whitebox.invokeMethod(service, "loadConfigurationProperties");
	Properties actualProperties = Whitebox.<Properties> getInternalState(service, "configurationProperties");
	assertThat(actualProperties.getProperty("agent.server_config.username"), equalTo(expected));
    }
    
    @Test
    public void whitebox_test_loadPropertiesFromClasspath() throws Exception {
	
	String expected = "test-cwash";
	String classpathResource = "com/captech/agentx/agentx-config.properties";
	Properties actualProperties = Whitebox.<Properties> invokeMethod(service, "loadPropertiesFromClasspath", classpathResource);
	assertThat(actualProperties.getProperty("agent.server_config.username"), equalTo(expected));
	
    }
    
    
}
