package cn.rectcircle.bindingsearch.model;

import com.google.gson.annotations.SerializedName;

public class RequireUrl {
	public String name;
	public String url;
	public String loginUrl;
	public String logoUrl;
	public String prefix;
	public String suffix;

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

	public String isBind;
	public String noBind;
}
