package com.doosan.heritage.repository;

import com.doosan.heritage.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface NewsRepository extends JpaRepository<News, Long> {

	Page<News> findByNewsLanguageAndNewsDisplayed(String language, String displayed, Pageable pageable);

	@Query("  SELECT" +
			"   a" +
			" FROM" +
			"   News a" +
			" WHERE" +
			"   a.newsLanguage = :language" +
			"   AND a.newsDisplayed = :displayed" +
			"   AND (" +
			"     a.newsTitle LIKE CONCAT('%', :query, '%')" +
			"     OR a.newsContent LIKE CONCAT('%', :query, '%')" +
			"   )")
	Page<News> findByNewsLanguageAndNewsDisplayedAndQuery(String language, String displayed, String query, Pageable pageable);

	News findFirstByNewsLanguageAndNewsDisplayedAndNewsTypeAndNewsPressDateGreaterThanOrderByNewsPressDateAsc(
			String language, String displayed, String newsType, Date newsPressDate);

	News findFirstByNewsLanguageAndNewsDisplayedAndNewsTypeAndNewsPressDateLessThanOrderByNewsPressDateDesc(
			String language, String displayed, String newsType, Date newsPressDate);
}
