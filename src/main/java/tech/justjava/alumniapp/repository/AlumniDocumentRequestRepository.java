package tech.justjava.alumniApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.justjava.alumniApp.entity.AlumniDocumentRequest;

public interface AlumniDocumentRequestRepository extends JpaRepository<AlumniDocumentRequest, Long> {
    // Custom query methods can be added here if needed
}
