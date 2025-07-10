package pe.edu.vallegrande.Santiago_Prada.controller;

import pe.edu.vallegrande.Santiago_Prada.model.RucConsultation;
import pe.edu.vallegrande.Santiago_Prada.repository.RucConsultationRepository;
import pe.edu.vallegrande.Santiago_Prada.service.RucApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ruc")
public class RucController {

    @Autowired
    private RucApiService rucApiService;

    @Autowired
    private RucConsultationRepository rucConsultationRepository;

    @GetMapping("/{ruc}")
    public Mono<RucConsultation> getRuc(@PathVariable String ruc) {
        return rucApiService.getRucInfo(ruc)
                .flatMap(rucConsultation -> rucConsultationRepository.save(rucConsultation))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "RUC not found")));
    }

    @PatchMapping("/delete/{id}")
    public Mono<RucConsultation> logicalDeleteRuc(@PathVariable String id) {
        return rucConsultationRepository.findById(id)
                .flatMap(rucConsultation -> {
                    rucConsultation.setDeleted(true);
                    return rucConsultationRepository.save(rucConsultation);
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "RUC consultation record not found")));
    }
} 