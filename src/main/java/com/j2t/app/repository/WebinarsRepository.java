package com.j2t.app.repository;

import com.j2t.app.domain.Webinars;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Webinars entity.
 */
public interface WebinarsRepository extends JpaRepository<Webinars,Long> {

}
