package edu.miu.cs590.SA.Ecommerce.service;

import edu.miu.cs590.SA.Ecommerce.domain.Account;
import edu.miu.cs590.SA.Ecommerce.domain.PaymentType;
import edu.miu.cs590.SA.Ecommerce.dto.AccountDTO;
import edu.miu.cs590.SA.Ecommerce.dto.AccountRegistrationDTO;
import edu.miu.cs590.SA.Ecommerce.dto.LoginDTO;
import edu.miu.cs590.SA.Ecommerce.repository.AccountRepository;
import edu.miu.cs590.SA.Ecommerce.repository.PaymentTypeRepository;
import edu.miu.cs590.SA.Ecommerce.util.JwtUtil;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService{

    @Value("${jwt.expiry}")
    private String expiry;
    @Value("${jwt.secret}")
    private String secret;

    private ModelMapper modelMapper;
    private PaymentTypeRepository paymentTypeRepository;
    private AuthenticationManager authenticationManager;
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(ModelMapper modelMapper, PaymentTypeRepository paymentTypeRepository,
                              AuthenticationManager authenticationManager, AccountRepository accountRepository){
        this.modelMapper = modelMapper;
        this.paymentTypeRepository = paymentTypeRepository;
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<AccountDTO> findAll() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOs = new ArrayList<>();
        accounts.forEach((account) -> {
            accountDTOs.add(modelMapper.map(account, AccountDTO.class));
        });
        return accountDTOs;
    }

    @Override
    public AccountDTO save(AccountRegistrationDTO accountBody) {
        String encodedPassword = bCryptPasswordEncoder.encode(accountBody.getPassword());

        Set<PaymentType> paymentTypes = new HashSet<>(Arrays.asList(
                paymentTypeRepository.getPaymentTypeByPaymentName("paypal").get(),
                paymentTypeRepository.getPaymentTypeByPaymentName("credit card").get(),
                paymentTypeRepository.getPaymentTypeByPaymentName("debit card").get()));

        Account account = new Account();
        account.setFirstName(accountBody.getFirstName());
        account.setLastName(accountBody.getLastName());
        account.setEmail(accountBody.getEmail());
        account.setPassword(encodedPassword);
        account.setUsername(accountBody.getUsername());
        account.setPaymentMethods(paymentTypes);
        account.setPreferredPaymentMethod(accountBody.getPreferredPaymentType());
        account.setShippingAddress(accountBody.getShippingAddress());
        accountRepository.save(account);

        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

        return accountDTO;
    }

    @Override
    public Account update(Long id, AccountRegistrationDTO accountBody) {
        return null;
    }

    @Override
    public ResponseEntity<?> authenticate(LoginDTO credentialsBody) {
        JSONObject responseObject = new JSONObject();
        if(!validateInputs(credentialsBody.getUsername())){
            responseObject.put("username","Username is required");
        }
        if(!validateInputs(credentialsBody.getPassword())){
            responseObject.put("password","Password is required");
        }
        if(!responseObject.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }else{
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(credentialsBody.getUsername(), credentialsBody.getPassword())
                );
            } catch (Exception ex) {
                System.out.println("Exception : "+ex);
                responseObject.put("credentials","Invalid credentials");
                return ResponseEntity.badRequest().body(responseObject);
            }
            Optional<Account> account = accountRepository.getUserByUsername(credentialsBody.getUsername());
            String token = JwtUtil.generateToken(String.valueOf(account.get().getId()), secret, expiry);
            responseObject.put("success",true);
            responseObject.put("token","Bearer " +token);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
    }

    @Override
    public void deleteById(Long id) {
        System.out.println("Account deleted");
    }

    public boolean validateInputs(String input){
        if(input == null || input == ""){
            return false;
        } else return true;
    }
}
