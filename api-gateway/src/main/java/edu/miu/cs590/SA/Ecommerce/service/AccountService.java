package edu.miu.cs590.SA.Ecommerce.service;

import edu.miu.cs590.SA.Ecommerce.util.TokenExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AccountService {

    @Value("${base_url}")
    private String baseUrl;
    @Value("${account_prefix}")
    private String accountPrefix;
    @Value("${login_prefix}")
    private String loginPrefix;
    @Value("${register_prefix}")
    private String registerPrefix;

    RestTemplate restTemplate = new RestTemplate();

    public Optional<Object> findById(Long id) {
        return Optional.empty();
    }

    public ResponseEntity<?> findAll() {
        final String token = TokenExtractor.extractToken();
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<Object> request =
                    new HttpEntity<>(null, headers);
            Object response
                    = restTemplate.exchange(baseUrl+accountPrefix , HttpMethod.GET,request,Object.class).getBody();
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
    }

    public ResponseEntity<?> save(Object accountBody) {
        try{
            HttpEntity<Object> request =
                    new HttpEntity<>(accountBody);
            Object response
                    = restTemplate.postForObject(baseUrl + accountPrefix+registerPrefix, request,Object.class);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            String[] errorMessage = e.getMessage().split("00 : ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage[1]);
        }
    }

    public Object update(Long id, Object accountBody) {
        return null;
    }

    public ResponseEntity<?> authenticate(Object credentialsBody) {
        HttpEntity<Object> request =
                new HttpEntity<>(credentialsBody);
        try{
            Object response
                    = restTemplate.postForObject(baseUrl+accountPrefix + loginPrefix, request,Object.class);

            return ResponseEntity.ok(response);
        }catch (Exception e){
            String[] errorMessage = e.getMessage().split("00 : ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage[1]);
        }

    }

    public void deleteById(Long id) {
        System.out.println("Account deleted");
    }
}
