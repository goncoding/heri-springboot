package com.doosan.heritage.repository;

import com.doosan.heritage.model.MainVisual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainVisualRepository extends JpaRepository<MainVisual, Long> {

	List<MainVisual> findByMainVisualLanguageAndMainVisualDisplayedOrderByMainVisualDisplayOrderAsc(String language, String displayed);
}
