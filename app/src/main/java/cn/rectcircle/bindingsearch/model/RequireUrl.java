package cn.rectcircle.bindingsearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class RequireUrl {
	public static final String PHONE_PARAM = "phone";
	public static final String PHONE_KEY_PARAM = "phoneKey";
	private String name;
	private String url;
	private String loginUrl;
	private String logoUrl;
	private String cookieUrl; //默认为loginUrl
	private String method="get";
	private String phoneKey="phone";
	private Map<String, String> headers = new HashMap<>();
	private Map<String, String> params = new HashMap<>();
	private String author="anonymous";

	private String bound;
	private String noBind;

	@Override
	public String toString() {
		return "RequireUrl{" +
				"name='" + name + '\'' +
				", url='" + url + '\'' +
				", loginUrl='" + loginUrl + '\'' +
				", logoUrl='" + logoUrl + '\'' +
				", cookieUrl='" + cookieUrl + '\'' +
				", method='" + method + '\'' +
				", phoneKey='" + phoneKey + '\'' +
				", headers=" + headers +
				", params=" + params +
				", author='" + author + '\'' +
				", bound='" + bound + '\'' +
				", noBind='" + noBind + '\'' +
				'}';
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

	public String getCookieUrl() {
		if(cookieUrl==null) {
			return loginUrl;
		}
		return cookieUrl;
	}

	public void setCookieUrl(String cookieUrl) {
		this.cookieUrl = cookieUrl;
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
