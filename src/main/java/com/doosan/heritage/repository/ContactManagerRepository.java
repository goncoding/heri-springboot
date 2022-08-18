package com.doosan.heritage.repository;

import com.doosan.heritage.model.ContactCategory;
import com.doosan.heritage.model.ContactManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactManagerRepository extends JpaRepository<ContactManager, Long> {

	List<ContactManager> findByContactCategory(ContactCategory contactCategory);
}
