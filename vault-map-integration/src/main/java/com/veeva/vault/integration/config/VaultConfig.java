package com.veeva.vault.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class VaultConfig {

	@Value("${vault.api.url}")
	private String vaultApiUrl;

	@Value("${vault.api.version}")
	private String vaultApiVersion;

	@Value("${vault.client.id:study-map-integration}")
	private String vaultClientId;

	@Value("${vault.session.timeout:3600}")
	private Integer sessionTimeout;

	@Bean
	public RestTemplate vaultRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(60000);
		factory.setConnectTimeout(30000);
		restTemplate.setRequestFactory(factory);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return restTemplate;
	}

	public String getVaultApiUrl() {
		return vaultApiUrl;
	}

	public String getVaultApiVersion() {
		return vaultApiVersion;
	}

	public String getVaultClientId() {
		return vaultClientId;
	}

	public Integer getSessionTimeout() {
		return sessionTimeout;
	}

	public String getFullApiUrl() {
		return vaultApiUrl + "/" + vaultApiVersion;
	}
}