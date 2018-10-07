/**
 * 
 */
package com.senlang.modp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Mc.D
 *
 */
@ConfigurationProperties("storage")
public class StorageProperties {
	/**
     * Folder location for storing files
     */
	@Value("${file-path}")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
