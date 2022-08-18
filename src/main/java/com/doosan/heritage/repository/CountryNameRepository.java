package com.doosan.heritage.repository;

import com.doosan.heritage.model.CountryName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryNameRepository extends JpaRepository<CountryName, Long> {

	List<CountryName> findByCountryNameLanguage(String language);

	CountryName findByCountryIdAndCountryNameLanguage(String countryId, String language);
}
