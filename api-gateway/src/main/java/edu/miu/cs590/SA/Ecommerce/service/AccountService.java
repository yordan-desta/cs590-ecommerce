package edu.miu.cs590.SA.Ecommerce.service;

import edu.miu.cs590.SA.Ecommerce.util.TokenExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class AccountService {

    @Value("${account.service.url}")
    private String baseUrl;

    @Value("${account.service.api.account}")
    private String accountPrefix;

    @Value("${account.service.api.login}")
    private String loginPrefix;

    @Value("${account.service.api.register}")
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
            log.error(e.getLocalizedMessage());
            //String[] errorMessage = e.getMessage().split("00 : ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    public ResponseEntity<?> save(Object accountBody) {
        final String address = baseUrl + accountPrefix + registerPrefix;

        log.info("Sending account: " + accountBody);
        log.info("destination url: " + address);


        try{
            HttpEntity<Object> request =
                    new HttpEntity<>(accountBody);
            Object response
                    = restTemplate.postForObject(address, request,Object.class);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            //String[] errorMessage = e.getMessage().split("00 : ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
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
            log.error(e.getLocalizedMessage());
            //String[] errorMessage = e.getMessage().split("00 : ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }

    public void deleteById(Long id) {
        System.out.println("Account deleted");
    }
}
