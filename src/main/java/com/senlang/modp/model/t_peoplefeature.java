package com.senlang.modp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class t_peoplefeature {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7281626228672177261L;

	@Id
	@GeneratedValue(
			strategy = GenerationType.AUTO
	)
	public long id;

	public byte[] Feature;

	public String name;
	public int sex;
	public String cardnumber;
	public String businessid;
	public int status;
	public int contorltype;
	public String picpath;
	public String remark;
	public Date createtime;

	@Override
	public String toString() {
//		return '{' +
//				"\"name\":\"" + name + "\",\"sex\":\"" + sex + "\",\"cardnumber\":\"" +
//				cardnumber + "\",\"businessid\":\"" + businessid + "\",\"status\":\"" +
//				status + "\",\"contorltype\":\"" + contorltype + "\",\"file\":\"" +
//				"%s" + "\",\"filename\":\"" + "%s" + "\",\"remark\":\"" + remark +
//				"\"}";
		return '{' +
				"\"name\":\"" + name + "\",\"sex\":\"" + sex + "\",\"cardnumber\":\"" +
				cardnumber + "\",\"businessid\":\"" + businessid + "\",\"status\":\"" +
				status + "\",\"contorltype\":\"" + contorltype + "\",\"file\":\"" +
				"%s" + "\",\"filename\":\"" + "%s" + "\",\"remark\":\"" + remark +
				"\"}";
	}

}
