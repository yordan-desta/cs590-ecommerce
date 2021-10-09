package edu.miu.cs590.SA.Ecommerce.service;

import edu.miu.cs590.SA.Ecommerce.util.TokenExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OrderService {

    @Value("${base_url}")
    private String baseUrl;
    @Value("${order_prefix}")
    private String orderPrefix;

    RestTemplate restTemplate = new RestTemplate();


    public ResponseEntity<?> findAll() {
        final String token = TokenExtractor.extractToken();
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<Object> request =
                    new HttpEntity<>(null, headers);
            Object response
                    = restTemplate.exchange(baseUrl + orderPrefix, HttpMethod.GET,request,Object.class).getBody();
            return ResponseEntity.ok(response);
        }catch (Exception e){
            String[] errorMessage = e.getMessage().split("00 : ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage[1]);
        }
    }

    public Optional<Object> findById(Long id) {
        return null;
    }

    public Object create(Object orderBody) {
        final String token = TokenExtractor.extractToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> request =
                new HttpEntity<>(orderBody, headers);

        Object response
                = restTemplate.postForObject(baseUrl + orderPrefix, request,Object.class);
        return ResponseEntity.ok(response);
    }

    public Object update(Long id, Object orderBody) {
        return null;
    }

    public void deleteById(Long id) {
    }

}
