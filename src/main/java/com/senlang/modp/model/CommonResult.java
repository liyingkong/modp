/**
 * 
 */
package com.senlang.modp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author Mc.D
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class CommonResult {
	private int errorcode;
	private String errmsg;
}
