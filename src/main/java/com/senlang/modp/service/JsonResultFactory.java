/**
 * 
 */
package com.senlang.modp.service;

import com.senlang.modp.model.CommonDataResult;
import com.senlang.modp.model.CommonResult;

import java.util.HashMap;


/**
 * @author Mc.D
 *
 */
public class JsonResultFactory {
	private static final HashMap<Integer, String> h = new HashMap<>();

	static {
		h.put(0, "操作成功");
		h.put(-1, "未知错误");
		h.put(1, "蓝信返回错误，请刷新页面重新尝试");
		h.put(2, "获取蓝信用户信息失败");
		h.put(3, "文件操作失败");
	}

	/**
	 * 根据给定的错误码返回标准返回格式
	 * 
	 * @param errorcode
	 * 
	 * @return
	 * @see #getCommonResult(int)
	 * @see #getCommonResult(int, Object)
	 * @see #getDataResult(int)
	 * @see #getDataResult(int, Object)
	 */
	@Deprecated
	public static HashMap<String, Object> getResult(int errorcode) {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("errorcode", errorcode);
		ret.put("errmsg", h.containsKey(errorcode) ? h.get(errorcode) : "未知错误");
		return ret;
	}

	/**
	 * 返回指定错误消息的标准返回格式，错误码固定为-1
	 * 
	 * @param errmsg
	 * @return
	 * @see #getCommonResult(String)
	 * @see #getCommonResult(String, Object)
	 * @see #getDataResult(String)
	 * @see #getDataResult(String, Object)
	 */
	@Deprecated
	public static HashMap<String, Object> getResult(String errmsg) {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("errorcode", -1);
		ret.put("errmsg", errmsg);
		return ret;
	}

	/**
	 * 返回操作成功的标准返回格式，错误码固定为0，错误消息为“操作成功”
	 * 
	 * @return
//	 * @see #getCommonResult()
//	 * @see #getCommonResult(Object)
//	 * @see #getDataResult()
//	 * @see #getDataResult(Object)
	 */
	@Deprecated
	public static HashMap<String, Object> getOkResult() {
		return getResult(0);
	}

	/**
	 * 根据给定的错误码和数据返回标准返回格式
	 * 
	 * @param errorcode
	 * @param data
	 * @return
	 */
	@Deprecated
	public static HashMap<String, Object> getResult(int errorcode, Object data) {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("errorcode", errorcode);
		ret.put("errmsg", h.containsKey(errorcode) ? h.get(errorcode) : "未知错误");
		ret.put("data", data);
		return ret;
	}

	/**
	 * 返回指定错误消息和数据的标准返回格式，错误码固定为-1
	 * 
	 * @param errmsg
	 * @param data
	 * @return
	 */
	@Deprecated
	public static HashMap<String, Object> getResult(String errmsg, Object data) {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("errorcode", -1);
		ret.put("errmsg", errmsg);
		ret.put("data", data);
		return ret;
	}

	/**
	 * 返回操作成功的标准返回格式，错误码固定为0，错误消息为“操作成功”，包含数据
	 * 
	 * @param data
	 * @return
	 */
	@Deprecated
	public static HashMap<String, Object> getOkResult(Object data) {
		return getResult(0, data);
	}

	/**
	 * 根据给定的错误码返回标准数据返回格式
	 * 
	 * @param errorcode
	 * @return
	 */
	public static CommonDataResult<Object> getDataResult(int errorcode) {
		CommonDataResult<Object> cdr = new CommonDataResult<Object>();
		cdr.setErrorcode(errorcode);
		cdr.setErrmsg(h.containsKey(errorcode) ? h.get(errorcode) : "未知错误");
		return cdr;
	}

	/**
	 * 返回指定错误消息的标准数据返回格式，错误码固定为-1
	 * 
	 * @param errmsg
	 * @return
	 */
	public static CommonDataResult<Object> getDataResult(String errmsg) {
		CommonDataResult<Object> cdr = new CommonDataResult<Object>();
		cdr.setErrorcode(-1);
		cdr.setErrmsg(errmsg);
		return cdr;
	}

	/**
	 * 返回操作成功的标准数据返回格式，错误码固定为0，错误消息为“操作成功”
	 * 
	 * @return
	 */
	public static CommonDataResult<Object> getOkDataResult() {
		return getDataResult(0);
	}

	/**
	 * 根据给定的错误码返回标准通用返回格式
	 * 
	 * @param errorcode
	 * @return
	 */
	public static CommonResult getCommonResult(int errorcode) {
		CommonResult cdr = new CommonDataResult<Object>();
		cdr.setErrorcode(errorcode);
		cdr.setErrmsg(h.containsKey(errorcode) ? h.get(errorcode) : "未知错误");
		return cdr;
	}

	/**
	 * 返回指定错误消息的标准通用返回格式，错误码固定为-1
	 * 
	 * @param errmsg
	 * @return
	 */
	public static CommonResult getCommonResult(String errmsg) {
		CommonResult cdr = new CommonDataResult<Object>();
		cdr.setErrorcode(-1);
		cdr.setErrmsg(errmsg);
		return cdr;
	}

	/**
	 * 返回操作成功的标准通用返回格式，错误码固定为0，错误消息为“操作成功”
	 * 
	 * @return
	 */
	public static CommonResult getOkCommonResult() {
		return getCommonResult(0);
	}

	/**
	 * 根据给定的错误码和数据返回标准数据返回格式
	 * 
	 * @param errorcode
	 * @param data
	 * @return
	 */
	public static <T> CommonDataResult<T> getDataResult(int errorcode, T data) {
		CommonDataResult<T> cdr = new CommonDataResult<T>();
		cdr.setErrorcode(errorcode);
		cdr.setErrmsg(h.containsKey(errorcode) ? h.get(errorcode) : "未知错误");
		cdr.setData(data);
		return cdr;
	}

	/**
	 * 返回指定错误消息和数据的标准数据返回格式，错误码固定为-1
	 * 
	 * @param errmsg
	 * @param data
	 * @return
	 */
	public static <T> CommonDataResult<T> getDataResult(String errmsg, T data) {
		CommonDataResult<T> cdr = new CommonDataResult<T>();
		cdr.setErrorcode(-1);
		cdr.setErrmsg(errmsg);
		cdr.setData(data);
		return cdr;
	}

	/**
	 * 返回操作成功的标准数据返回格式，错误码固定为0，错误消息为“操作成功”，包含数据
	 * 
	 * @param data
	 * @return
	 */
	public static <T> CommonDataResult<T> getOkDataResult(T data) {
		return getDataResult(0, data);
	}
	
	/**
	 * 根据给定的错误码和数据返回标准通用返回格式
	 * 
	 * @param errorcode
	 * @param data
	 * @return
	 */
	public static <T> CommonResult getCommonResult(int errorcode,T data) {
		CommonDataResult<T> cdr = new CommonDataResult<T>();
		cdr.setErrorcode(errorcode);
		cdr.setErrmsg(h.containsKey(errorcode) ? h.get(errorcode) : "未知错误");
		cdr.setData(data);
		return cdr;
	}

	/**
	 * 返回指定错误消息和数据的标准通用返回格式，错误码固定为-1
	 * 
	 * @param errmsg
	 * @param data
	 * @return
	 */
	public static <T> CommonResult getCommonResult(String errmsg,T data) {
		CommonDataResult<T> cdr = new CommonDataResult<T>();
		cdr.setErrorcode(-1);
		cdr.setErrmsg(errmsg);
		cdr.setData(data);
		return cdr;
	}

	/**
	 * 返回操作成功的标准通用返回格式，错误码固定为0，错误消息为“操作成功”，包含数据
	 * 
	 * @param data
	 * @return
	 */
	public static <T> CommonResult getOkCommonResult(T data) {
		return getCommonResult(0,data);
	}
}
