package cn.rectcircle.bindingsearch.model;

import java.util.List;

public class RequireUrls {
	private  String version;

	private List<RequireUrl> requireUrls;

	public String getVersion() {
		return version;
	}

	public List<RequireUrl> getRequireUrls() {
		return requireUrls;
	}

	@Override
	public String toString() {
		return "RequireUrls{" +
				"version='" + version + '\'' +
				", requireUrls=" + requireUrls +
				'}';
	}
}
