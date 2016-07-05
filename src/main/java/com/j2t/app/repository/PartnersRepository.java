package com.j2t.app.repository;

import com.j2t.app.domain.Partners;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Partners entity.
 */
public interface PartnersRepository extends JpaRepository<Partners,Long> {

}
