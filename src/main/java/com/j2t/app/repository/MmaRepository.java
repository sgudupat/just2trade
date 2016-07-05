package com.j2t.app.repository;

import com.j2t.app.domain.Mma;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mma entity.
 */
public interface MmaRepository extends JpaRepository<Mma,Long> {

}
