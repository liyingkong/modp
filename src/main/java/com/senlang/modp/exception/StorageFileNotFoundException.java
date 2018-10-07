/**
 * 
 */
package com.senlang.modp.exception;

/**
 * @author Mc.D
 *
 */
public class StorageFileNotFoundException extends RuntimeException  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4502288266322739534L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
