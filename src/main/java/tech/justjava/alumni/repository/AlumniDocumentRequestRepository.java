package tech.justjava.alumni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.justjava.alumni.entity.AlumniDocumentRequest;

import java.util.List;

public interface AlumniDocumentRequestRepository extends JpaRepository<AlumniDocumentRequest, Long> {

    List<AlumniDocumentRequest> findByAlumniId(String alumniId);

    List<AlumniDocumentRequest> findByProcessInstanceId(String processInstanceId);

    List<AlumniDocumentRequest> findByDocumentType(String documentType);

    List<AlumniDocumentRequest> findByPaymentStatus(String paymentStatus);

    List<AlumniDocumentRequest> findByApprovalStatus(String approvalStatus);
}
