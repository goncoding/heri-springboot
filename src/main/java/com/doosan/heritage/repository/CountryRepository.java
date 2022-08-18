package com.doosan.heritage.repository;

import com.doosan.heritage.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {

	List<Country> findByCountryDisplayed(String displayed);
}
