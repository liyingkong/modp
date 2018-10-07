/**
 * 
 */
package com.senlang.modp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mc.D
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonDataResult<T> extends CommonResult {
	// private long errorcode;
	// private String errmsg;
	private T data;
}
