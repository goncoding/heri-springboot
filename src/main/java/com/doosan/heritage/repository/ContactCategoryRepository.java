package com.doosan.heritage.repository;

import com.doosan.heritage.model.ContactCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactCategoryRepository extends JpaRepository<ContactCategory, Long> {

	List<ContactCategory> findByContactCategoryLanguageAndContactCategoryDisplayed(String language, String displayed);
}
