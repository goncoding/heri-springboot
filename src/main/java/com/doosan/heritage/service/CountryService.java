package com.doosan.heritage.service;

import com.doosan.heritage.constant.YesNoConstant;
import com.doosan.heritage.model.Country;
import com.doosan.heritage.model.CountryName;
import com.doosan.heritage.repository.CountryNameRepository;
import com.doosan.heritage.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private CountryNameRepository countryNameRepository;

	public List<Country> getLocalizedCountryList(String localeLanguage) {
		List<Country> countryList = countryRepository.findByCountryDisplayed(YesNoConstant.YES);
		List<CountryName> countryNameList = countryNameRepository.findByCountryNameLanguage(localeLanguage);

		countryList.forEach(country -> {
			countryNameList.stream()
					.filter(countryName -> Objects.equals(country.getCountryId(), countryName.getCountryId()))
					.findFirst()
					.ifPresent(countryName -> country.setCountryDisplayName(countryName.getCountryNameTranslated()));
			if (country.getCountryDisplayName() == null) {
				country.setCountryDisplayName(country.getCountryDefaultName());
			}
		});

		countryList.sort(Comparator.comparing(Country::getCountryDisplayName));

		return countryList;
	}

	public void applyLocalizedCountryDisplayName(Country country, String localeLanguage) {
		if (country == null) {
			return;
		}

		CountryName countryName = countryNameRepository.findByCountryIdAndCountryNameLanguage(country.getCountryId(), localeLanguage);

		if (countryName != null) {
			country.setCountryDisplayName(countryName.getCountryNameTranslated());
		} else {
			country.setCountryDisplayName(country.getCountryDefaultName());
		}
	}
}
