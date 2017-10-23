package cn.rectcircle.bindingsearch.model;

public class Version {
	private String version;

	public Version() {
	}

	public Version(String version) {

		this.version = version;
	}

	@Override
	public String toString() {
		return "Version{" +
				"version='" + version + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Version version1 = (Version) o;

		return version != null ? version.equals(version1.version) : version1.version == null;
	}

	@Override
	public int hashCode() {
		return version != null ? version.hashCode() : 0;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {

		return version;
	}
}
