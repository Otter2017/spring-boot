package org.springframework.boot.peach.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "peach")
public class PeachProperties {
	private String applicationName ="Peach";
	private String version ="1.0.0";
	private String buildDate ="20181026";
	private String welcomeWord ="Welcome to Peach,a fruite company.";

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getWelcomeWord() {
		return welcomeWord;
	}

	public void setWelcomeWord(String welcomeWord) {
		this.welcomeWord = welcomeWord;
	}
}
