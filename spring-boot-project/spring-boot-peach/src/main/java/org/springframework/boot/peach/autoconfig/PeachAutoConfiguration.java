package org.springframework.boot.peach.autoconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PeachProperties.class)
public class PeachAutoConfiguration {

	private PeachProperties peachProperties;

	public PeachAutoConfiguration(PeachProperties peachProperties){
		this.peachProperties =peachProperties;
	}

	@Bean
	public PeachVersion version(){
		PeachVersion version =new PeachVersion();
		StringBuffer versionInfo =new StringBuffer();
		versionInfo.append("Version:").append(peachProperties.getVersion()).append("\n")
				.append("BuildDate:").append(peachProperties.getBuildDate()).append("\n")
				.append(peachProperties.getWelcomeWord());
		version.setVersionInfo(versionInfo.toString());
		return version;
	}
}
