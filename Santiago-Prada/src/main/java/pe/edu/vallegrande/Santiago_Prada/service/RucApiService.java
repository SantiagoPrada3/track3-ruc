package pe.edu.vallegrande.Santiago_Prada.service;

import pe.edu.vallegrande.Santiago_Prada.model.RucConsultation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class RucApiService {

    private final WebClient webClient;

    @Value("${ruc.api.token}")
    private String apiToken;

    public RucApiService(WebClient.Builder webClientBuilder, @Value("${ruc.api.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<RucConsultation> getRucInfo(String ruc) {
        return webClient.get()
                .uri("/{ruc}?token={token}", ruc, apiToken)
                .retrieve()
                .bodyToMono(RucConsultation.class)
                .map(rucConsultation -> {
                    rucConsultation.setTimestamp(LocalDateTime.now());
                    rucConsultation.setDeleted(false); // Ensure it's not marked as deleted on creation
                    return rucConsultation;
                });
    }
} 