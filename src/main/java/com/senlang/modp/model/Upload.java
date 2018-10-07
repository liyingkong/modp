/**
 * 
 */
package com.senlang.modp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mc.D
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Upload {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7695809381489865874L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String refCode;

	private String code;

	private String type;

	@JsonIgnore
	private String path;

	private String name;

	private String mime;

	private Date uploadDate;

	@JsonIgnore
	private String videoImgPath;

	private String videoTrackPath;

	private String videoMaskPath;

	private String cutVideoPath;
}
