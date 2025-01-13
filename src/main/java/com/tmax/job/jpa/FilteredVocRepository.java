package com.tmax.job.jpa;

import com.tmax.job.writer.FilteredVOC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilteredVocRepository extends JpaRepository<FilteredVOC, Long> {
}
