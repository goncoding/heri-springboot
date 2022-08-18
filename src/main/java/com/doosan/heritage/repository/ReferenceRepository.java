package com.doosan.heritage.repository;

import com.doosan.heritage.model.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReferenceRepository extends JpaRepository<Reference, Long> {

	@Query("  SELECT" +
			"   a" +
			" FROM" +
			"   Reference a" +
			"   LEFT JOIN FETCH a.referenceCategory b" +
			"   LEFT JOIN FETCH a.referenceThumbnail c" +
			" WHERE" +
			"   b.referenceCategoryLanguage = :language" +
			"   AND a.referenceDisplayed = :displayed" +
			"   AND b.referenceCategoryDisplayed = :displayed" +
			" ORDER BY" +
			"   a.referenceDisplayOrder ASC")
	List<Reference> findByLanguageAndDisplayedOrderByDisplayOrderAsc(String language, String displayed);
}
