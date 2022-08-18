package com.doosan.heritage.repository;

import com.doosan.heritage.model.IrAnnounce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IrAnnounceRepository extends JpaRepository<IrAnnounce, Long> {

	@Query("  SELECT" +
			"   a" +
			" FROM" +
			"   IrAnnounce a" +
			" WHERE" +
			"   a.irAnnounceLanguage = :language" +
			"   AND a.irAnnounceDisplayed = :displayed" +
			"	AND (a.irAnnounceReleaseStartTime <= :currentDate OR a.irAnnounceReleaseStartTime IS NULL)" +
			"	AND (year(a.irAnnounceDate) = :irAnnounceYear OR :irAnnounceYear IS NULL)" +
			"   AND (" +
			"     a.irAnnounceTitle LIKE CONCAT('%', :query, '%')" +
			"     OR a.irAnnounceContent LIKE CONCAT('%', :query, '%')" +
			"   )")
	Page<IrAnnounce> findByLanguageAndDisplayedAndReleaseStartTimeAfterAndYearAndQuery(
			String language, String displayed, Integer irAnnounceYear, Date currentDate, String query, Pageable pageable);

	IrAnnounce findFirstByIrAnnounceLanguageAndIrAnnounceDisplayedOrderByIrAnnounceDateAsc(
			String irAnnounceLanguage, String irAnnounceDisplayed);

	IrAnnounce findFirstByIrAnnounceLanguageAndIrAnnounceDisplayedOrderByIrAnnounceDateDesc(
			String irAnnounceLanguage, String irAnnounceDisplayed);

	@Query("  SELECT" +
			"   a" +
			" FROM" +
			"   IrAnnounce a" +
			" WHERE" +
			"   a.irAnnounceLanguage = :language" +
			"   AND a.irAnnounceDisplayed = :displayed" +
			"	AND (a.irAnnounceReleaseStartTime <= :currentDate OR a.irAnnounceReleaseStartTime IS NULL)" +
			"	AND a.irAnnounceDate > :irAnnounceDate")
	List<IrAnnounce> findDisplayablePreviousNewsByLanguage(String language, String displayed, Date currentDate, Date irAnnounceDate, Pageable pageable);

	@Query("  SELECT" +
			"   a" +
			" FROM" +
			"   IrAnnounce a" +
			" WHERE" +
			"   a.irAnnounceLanguage = :language" +
			"   AND a.irAnnounceDisplayed = :displayed" +
			"	AND (a.irAnnounceReleaseStartTime <= :currentDate OR a.irAnnounceReleaseStartTime IS NULL)" +
			"	AND a.irAnnounceDate < :irAnnounceDate")
	List<IrAnnounce> findDisplayableNextNewsByLanguage(String language, String displayed, Date currentDate, Date irAnnounceDate, Pageable pageable);
}
