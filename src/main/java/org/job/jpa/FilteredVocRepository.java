package org.job.jpa;

import org.job.writer.FilteredVOC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilteredVocRepository extends JpaRepository<FilteredVOC, Long> {
}
