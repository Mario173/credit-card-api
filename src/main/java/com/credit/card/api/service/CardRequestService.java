package com.credit.card.api.service;

import com.credit.card.api.entity.NewCardRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Card Request Service
 */
@Slf4j
@Service
public class CardRequestService {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://api.something.com/v1/api/v1/card-request";

    /**
     * Constructor for CardRequestService
     *
     * @param restTemplate rest template
     */
    public CardRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Method used to send a card request
     *
     * @param newCardRequest card request to be sent
     * @return response entity
     */
    public ResponseEntity<String> sendCardRequest(NewCardRequest newCardRequest) {
        log.info("Sending card request: {}.", newCardRequest);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<NewCardRequest> requestEntity = new HttpEntity<NewCardRequest>(newCardRequest, headers);

            return restTemplate.exchange(BASE_URL, HttpMethod.POST, requestEntity, String.class);
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

}
