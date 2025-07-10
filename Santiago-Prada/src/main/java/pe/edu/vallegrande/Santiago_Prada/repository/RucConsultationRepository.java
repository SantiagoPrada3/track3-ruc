package pe.edu.vallegrande.Santiago_Prada.repository;

import pe.edu.vallegrande.Santiago_Prada.model.RucConsultation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface RucConsultationRepository extends ReactiveMongoRepository<RucConsultation, String> {
} 