/**
 * 
 */
package com.captech.agentx;

/**
 * Abstraction to retrieve configuration values.
 * 
 * @author Chris Wash
 *
 */
public interface ConfigurationService {

    public String loadUsername();
    public String loadPassword();
    public String loadHostname();
    public String loadPort();
    public String loadServicename();

}
