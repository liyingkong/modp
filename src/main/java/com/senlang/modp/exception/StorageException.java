/**
 * 
 */
package com.senlang.modp.exception;

/**
 * @author Mc.D
 *
 */
public class StorageException extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615681626154538469L;
	
	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
