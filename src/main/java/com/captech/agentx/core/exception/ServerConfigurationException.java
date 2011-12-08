/**
 * 
 */
package com.captech.agentx.core.exception;

/**
 * Unchecked exception that is raised when the server configuration provided is
 * not valid.
 * 
 * @author Chris Wash
 * 
 */
public class ServerConfigurationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public ServerConfigurationException() {
	super();
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ServerConfigurationException(String arg0, Throwable arg1) {
	super(arg0, arg1);
    }

    /**
     * @param arg0
     */
    public ServerConfigurationException(String arg0) {
	super(arg0);
    }

    /**
     * @param arg0
     */
    public ServerConfigurationException(Throwable arg0) {
	super(arg0);
    }

}
