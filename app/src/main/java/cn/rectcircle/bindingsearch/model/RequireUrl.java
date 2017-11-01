package cn.rectcircle.bindingsearch.model;

import java.util.HashMap;
import java.util.Map;

public class RequireUrl {
	/**
	 * 时间戳单位：秒
	 */
	public static final String TIMESTAMP_UNIT_S = "s";
	/**
	 * 时间戳单位：毫秒
	 */
	public static final String TIMESTAMP_UNIT_MS = "ms";

	/**
	 * 当网站是否可以查询
	 */
	private Boolean disable=false;
	/**
	 * 不能使用的原因

	 */
	private String disableDescription;

	/**
	 * 网站名
	 */
	private String name;
	/**
	 * 查询手机是否已注册的url
	 */
	private String url;
	/**
	 * 该网站登录的url
	 */
	private String loginUrl;
	/**
	 * 该网站logo
	 */
	private String logoUrl;
	/**
	 * 该网站注册页面url，用于请求cookies和设置请求头的Referer
	 */
	private String registerUrl;
	/**
	 * url的请求方式
	 */
	private String method="get";
	/**
	 * 请求参数的手机号码对应的参数名，默认为phone
	 */
	private String phoneKey="phone";
	/**
	 * 手机号参数的的位置
	 */
	private Integer phonePosition=null;
	/**

	 * 必须设置的请求头信息
	 */
	private Map<String, String> headers = new HashMap<>();
	/**
	 * 固定的参数
	 */
	private Map<String, String> params = new HashMap<>();
	/**
	 * 作者
	 */
	private String author="anonymous";
	/**
	 * 手机号参数拼接的前缀
	 */
	private String phoneParamPrefix="";
	/**
	 * 手机号参数需要拼接的后缀
	 */
	private String phoneParamSuffix="";
	/**
	 * 某些网站需要使用时间戳信息，配置参数名，选填
	 */
	private String timestampKey=null;
	/**
	 * 时间戳单位选填
	 * s 秒级时间戳（默认）
	 * ms 毫秒级时间戳
	 * 选填
	 */
	private String timestampUnit=TIMESTAMP_UNIT_MS;

	/**
	 * 获取cookies后延时请求
	 */
	private Integer delayMs = 0;

	/**
	 * url返回的字符串包含的此字符串则说明手机号被绑定
	 */
	private String bound;
	/**
	 * url返回的字符串包含的此字符串则说明手机号未被绑定
	 */
	private String noBind;



	public Integer getPhonePosition() {
		return phonePosition;
	}

	public void setPhonePosition(Integer phonePosition) {
		this.phonePosition = phonePosition;
	}

	public Integer getDelayMs() {
		return delayMs;
	}

	public void setDelayMs(Integer delayMs) {
		this.delayMs = delayMs;
	}

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public String getDisableDescription() {
		return disableDescription;
	}

	public void setDisableDescription(String disableDescription) {
		this.disableDescription = disableDescription;
	}

	@Override
	public String toString() {
		return "RequireUrl{" +
				"disable=" + disable +
				", disableDescription='" + disableDescription + '\'' +
				", name='" + name + '\'' +
				", url='" + url + '\'' +
				", loginUrl='" + loginUrl + '\'' +
				", logoUrl='" + logoUrl + '\'' +
				", registerUrl='" + registerUrl + '\'' +
				", method='" + method + '\'' +
				", phoneKey='" + phoneKey + '\'' +
				", phonePosition=" + phonePosition +
				", headers=" + headers +
				", params=" + params +
				", author='" + author + '\'' +
				", phoneParamPrefix='" + phoneParamPrefix + '\'' +
				", phoneParamSuffix='" + phoneParamSuffix + '\'' +
				", timestampKey='" + timestampKey + '\'' +
				", timestampUnit='" + timestampUnit + '\'' +
				", delayMs=" + delayMs +
				", bound='" + bound + '\'' +
				", noBind='" + noBind + '\'' +
				'}';
	}

	public String getPhoneParamPrefix() {
		return phoneParamPrefix;
	}

	public void setPhoneParamPrefix(String phoneParamPrefix) {
		this.phoneParamPrefix = phoneParamPrefix;
	}

	public String getPhoneParamSuffix() {
		return phoneParamSuffix;
	}

	public void setPhoneParamSuffix(String phoneParamSuffix) {
		this.phoneParamSuffix = phoneParamSuffix;
	}

	public String getTimestampKey() {
		return timestampKey;
	}

	public void setTimestampKey(String timestampKey) {
		this.timestampKey = timestampKey;
	}

	public String getTimestampUnit() {
		return timestampUnit;
	}

	public void setTimestampUnit(String timestampUnit) {
		this.timestampUnit = timestampUnit;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBound() {
		return bound;
	}

	public void setBound(String bound) {
		this.bound = bound;
	}

	public String getRegisterUrl() {
		if(registerUrl ==null) {
			return loginUrl;
		}
		return registerUrl;
	}

	public void setRegisterUrl(String registerUrl) {
		this.registerUrl = registerUrl;
	}

	public String getPhoneKey() {
		return phoneKey;
	}

	public void setPhoneKey(String phoneKey) {
		this.phoneKey = phoneKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getNoBind() {
		return noBind;
	}

	public void setNoBind(String noBind) {
		this.noBind = noBind;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
