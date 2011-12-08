/**
 * 
 */
package com.captech.agentx;

/**
 * XXX
 * 
 * @author Chris Wash
 *
 */
public interface XmppAgent {

    /**
     * Start an agent using the specified credentials.
     * 
     * @param username
     * @param password
     * @param hostname
     */
    public void start();
    
    public void validateConfiguration();

    public String getPort();

    public String getHostname();

    public String getPassword();

    public String getUsername();
    
    public String getServicename();

}
