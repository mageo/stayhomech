package com.pavax.stayhome.syncservice.service;

import com.pavax.stayhome.syncservice.domain.BusinessRequest;
import com.pavax.stayhome.syncservice.domain.BusinessRequestRepository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@EnableConfigurationProperties(SyncServiceProperties.class)
public class BusinessRequestSyncService {

	private final BusinessRequestRepository businessRequestRepository;

	private final SyncServiceProperties syncServiceProperties;

	private final LanguageDetectionService languageDetectionService;

	public BusinessRequestSyncService(
			BusinessRequestRepository businessRequestRepository,
			SyncServiceProperties syncServiceProperties,
			LanguageDetectionService languageDetectionService) {
		this.businessRequestRepository = businessRequestRepository;
		this.syncServiceProperties = syncServiceProperties;
		this.languageDetectionService = languageDetectionService;
	}

	public void sync(BusinessEntryDto businessEntryDto) {
		if (businessEntryDto.getTtl() == null) {
			businessEntryDto.setTtl(this.syncServiceProperties.getDefaultTimeToLive().getSeconds());
		}
		final String correlationId = extractCorrelationId(businessEntryDto);
		final BusinessRequest businessRequest = this.businessRequestRepository.findByCorrelationId(correlationId)
				.orElse(new BusinessRequest())
				.setSourceUUid(correlationId)
				.setName(businessEntryDto.getName())
				.setDescription(businessEntryDto.getDescription())
				.setLocation(businessEntryDto.getLocation())
				.setWebsite(businessEntryDto.getWebsite())
				.setPhone(businessEntryDto.getPhone())
				.setEmail(businessEntryDto.getEmail())
				.setContact(businessEntryDto.getContact())
				.setCategory(businessEntryDto.getCategories())
				.setTtl(businessEntryDto.getTtl())
				.setDelivery(businessEntryDto.getDelivery());

		businessRequest.setLang(businessEntryDto.getLanguage() != null ? businessEntryDto.getLanguage().getKey() : this.determineLanguage(businessRequest));

		businessRequest.setChecksum(this.calculateChecksum(businessRequest));
		if (isNew(businessRequest)) {
			this.businessRequestRepository.save(businessRequest);
		} else {
			this.businessRequestRepository.update(businessRequest);
		}
	}

	private String determineLanguage(BusinessRequest businessRequest) {
		return this.languageDetectionService.detect(businessRequest)
				.map(Language::getKey)
				.orElse(null);
	}

	private boolean isNew(BusinessRequest businessRequest) {
		return businessRequest.getUuid() == null;
	}

	private String calculateChecksum(BusinessRequest businessRequest) {
		final String checkSumString = businessRequest.getName() +
				businessRequest.getDescription() +
				businessRequest.getLocation() +
				businessRequest.getWebsite() +
				businessRequest.getPhone() +
				businessRequest.getEmail() +
				businessRequest.getCategory() +
				businessRequest.getContact() +
				businessRequest.getLang() +
				businessRequest.getDelivery();
		return DigestUtils.md5DigestAsHex(checkSumString.getBytes());
	}

	private String extractCorrelationId(BusinessEntryDto businessEntryDto) {
		return String.format("%s-%s", businessEntryDto.getProviderName(), businessEntryDto.getId());
	}

}
