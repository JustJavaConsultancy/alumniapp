package tech.justjava.alumniapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.justjava.alumniapp.entity.DocumentRequest;

import java.util.List;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
    List<DocumentRequest> findByAlumniId(String alumniId);
}
