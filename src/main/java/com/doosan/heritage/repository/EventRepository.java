package com.doosan.heritage.repository;

import com.doosan.heritage.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {

	@Query("  SELECT" +
			"   a" +
			" FROM" +
			"   Event a" +
			" WHERE" +
			"   a.eventLanguage = :language" +
			"   AND a.eventDisplayed = :displayed" +
			"   AND (" +
			"     a.eventTitle LIKE CONCAT('%', :query, '%')" +
			"     OR a.eventContent LIKE CONCAT('%', :query, '%')" +
			"   )")
	Page<Event> findByEventLanguageAndEventDisplayedAndQuery(String language, String displayed, String query, Pageable pageable);

	Event findFirstByEventLanguageAndEventDisplayedAndEventDisplayOrderGreaterThanOrderByEventDisplayOrderAsc(String language, String displayed, Long eventDisplayOrder);

	Event findFirstByEventLanguageAndEventDisplayedAndEventDisplayOrderLessThanOrderByEventDisplayOrderDesc(String language, String displayed, Long eventDisplayOrder);
}
