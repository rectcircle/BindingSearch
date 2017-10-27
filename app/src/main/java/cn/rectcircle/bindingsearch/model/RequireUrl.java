package cn.rectcircle.bindingsearch.model;

import com.google.gson.annotations.SerializedName;

public class RequireUrl {
	private String name;
	private String url;
	private String loginUrl;
	private String logoUrl;
	private String prefix;
	private String suffix;

	private String isBind;
	private String noBind;


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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public String getNoBind() {
		return noBind;
	}

	public void setNoBind(String noBind) {
		this.noBind = noBind;
	}

	@Override
	public String toString() {
		return "RequireUrl{" +
				"name='" + name + '\'' +
				", url='" + url + '\'' +
				", loginUrl='" + loginUrl + '\'' +
				", logoUrl='" + logoUrl + '\'' +
				", prefix='" + prefix + '\'' +
				", suffix='" + suffix + '\'' +
				", isBind='" + isBind + '\'' +
				", noBind='" + noBind + '\'' +
				'}';
	}

}
