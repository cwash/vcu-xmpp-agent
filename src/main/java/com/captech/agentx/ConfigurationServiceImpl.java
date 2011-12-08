/**
 * 
 */
package com.captech.agentx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This class is responsible for loading the relevant Properties files in order to
 * initialize relevant configuration services.
 * 
 * @author Chris Wash
 * 
 */
@javax.inject.Singleton
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final String CONFIGURATION_PROPERTIES_LOCATION = "com/captech/agentx/agentx-config.properties";

    /* Configuration Keys */
    private static final String LOGGING_PROPERTIES_KEY = "config_file.agentx-logging";
    private static final String AGENT_USERNAME_KEY = "agent.server_config.username";
    private static final String AGENT_PASSWORD_KEY = "agent.server_config.password";
    private static final String AGENT_HOSTNAME_KEY = "agent.server_config.hostname";
    private static final String AGENT_PORT_KEY     = "agent.server_config.port";
    private static final String AGENT_SERVICENAME_KEY = "agent.server_config.servicename";
    
    private final Logger log = Logger.getLogger("com.captech.agentx");

    private Properties configurationProperties;
    
    public ConfigurationServiceImpl() {
	loadConfigurationProperties();
	loadLoggingProperties();
    }
    
    private void loadConfigurationProperties() {
	
	/* classpath location hard-coded for now; possibly make this configurable using a -D */
	/* should allow for different file to be switched out on test classpath versus real classpath */
	String configurationClasspathLocation = CONFIGURATION_PROPERTIES_LOCATION;
	
	try {
	    configurationProperties = loadPropertiesFromClasspath(configurationClasspathLocation);
	} catch (IOException e) {
	    System.out.println("WARNING: Could not open configuration file");
	    System.out.println("WARNING: Configuration not loaded");
	}
	
	/* logging not configured yet */
	System.out.println("Loaded main config properties file from: " + CONFIGURATION_PROPERTIES_LOCATION);
	
    }
    
    /**
     * Preconditions: Have initialized the overall config first
     * Postconditions: The application will then log using configuration provided
     */
    private void loadLoggingProperties() {

	String loggingClasspathLocation = getString(LOGGING_PROPERTIES_KEY);
	InputStream loggingConfigurationStream;
	
	try {

	    loggingConfigurationStream = inputStreamFromClasspath(loggingClasspathLocation);
	
	    LogManager.getLogManager().readConfiguration(loggingConfigurationStream);
	    
	} catch (IOException ex) {
	
	    System.out.println("WARNING: Could not open configuration file");
	    System.out.println("WARNING: Logging not configured (console output only)");
	
	}

	log.info("logging properties loaded.");
    }

    private Properties loadPropertiesFromClasspath(String classpathLocation) throws IOException {
	
	Properties properties = new Properties();
	InputStream stream = inputStreamFromClasspath(classpathLocation);
	properties.load(stream);
	
	return properties;
	
    }
    
    private InputStream inputStreamFromClasspath(String classpathLocation) {
	
	ClassLoader loader = ClassLoader.getSystemClassLoader();
	InputStream stream = loader.getResourceAsStream(classpathLocation);

	return stream; 
    }
    
    public String getString(String key) {
	return configurationProperties.getProperty(key);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.captech.agentx.ConfigurationService#loadUsername()
     */
    public String loadUsername() {
	return getString(AGENT_USERNAME_KEY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.captech.agentx.ConfigurationService#loadPassword()
     */
    public String loadPassword() {
	return getString(AGENT_PASSWORD_KEY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.captech.agentx.ConfigurationService#loadHostname()
     */
    public String loadHostname() {
	return getString(AGENT_HOSTNAME_KEY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.captech.agentx.ConfigurationService#loadPort()
     */
    public String loadPort() {
	return getString(AGENT_PORT_KEY);
    }

    /* (non-Javadoc)
     * @see com.captech.agentx.ConfigurationService#loadServicename()
     */
    public String loadServicename() {
	// TODO Auto-generated method stub
	return getString(AGENT_SERVICENAME_KEY);
    }

}
