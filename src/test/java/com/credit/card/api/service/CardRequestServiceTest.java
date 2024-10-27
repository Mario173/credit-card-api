package com.credit.card.api.service;

import com.credit.card.api.AbstractUnitTest;
import com.credit.card.api.entity.NewCardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the class {@link CardRequestService}
 */
class CardRequestServiceTest extends AbstractUnitTest {

    @InjectMocks
    private CardRequestService cardRequestService;

    @Mock
    private RestTemplate restTemplate;

    private NewCardRequest newCardRequest;

    /**
     * Initializes the test data.
     */
    @BeforeEach
    void init() {
        newCardRequest = NewCardRequest.builder()
                .oib("12345678901")
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    /**
     * Test for {@link CardRequestService#sendCardRequest(NewCardRequest)}.
     * Method should send a card request and return a response entity.
     */
    @Test
    void testSendCardRequestWorksCorrectly() {
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Test"));

        ResponseEntity<String> response = cardRequestService.sendCardRequest(newCardRequest);

        verify(restTemplate, times(1))
                .exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));
        assertEquals(ResponseEntity.ok("Test"), response);
    }

    /**
     * Test for {@link CardRequestService#sendCardRequest(NewCardRequest)}.
     * Method should try to send a card request and catch a {@link HttpClientErrorException}.
     */
    @Test
    void testSendCardRequestWorksCorrectlyWhenClientErrorOccurs() {
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatusCode.valueOf(400));
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenThrow(httpClientErrorException);

        ResponseEntity<String> response = cardRequestService.sendCardRequest(newCardRequest);

        verify(restTemplate, times(1))
                .exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));
        assertEquals(ResponseEntity.badRequest().body(httpClientErrorException.getResponseBodyAsString()), response);
    }

    /**
     * Test for {@link CardRequestService#sendCardRequest(NewCardRequest)}.
     * Method should try to send a card request and catch a {@link RuntimeException}.
     */
    @Test
    void testSendCardRequestWorksCorrectlyWhenServerErrorOccurs() {
        RuntimeException exception = new RuntimeException("Test");
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenThrow(exception);

        ResponseEntity<String> response = cardRequestService.sendCardRequest(newCardRequest);

        verify(restTemplate, times(1))
                .exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));
        assertEquals(ResponseEntity.internalServerError().body("An error occurred: " + exception.getMessage()), response);
    }

}
