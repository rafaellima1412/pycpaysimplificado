package com.example.pycpaysimplificado.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.pycpaysimplificado.domain.User;
import com.example.pycpaysimplificado.domain.transaction.Transaction;
import com.example.pycpaysimplificado.dtos.TransactionDTO;
import com.example.pycpaysimplificado.repositories.TransactionRepository;

@Service
public class TransactionService {
  @Autowired
  private UserService userService;
  @Autowired
  private TransactionRepository repository;
  @Autowired
  RestTemplate restTemplate;

  public void createTransaction(TransactionDTO transaction) throws Exception {
    User sender = this.userService.findUserByIdUser(transaction.senderId());
    User receiver = this.userService.findUserByIdUser(transaction.receiverId());
    userService.validadeTransaction(sender, transaction.value());
    boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
    if (!isAuthorized) {
      throw new Exception("transação não autorizada!");
    }
    Transaction newtransaction = new Transaction();
    newtransaction.setAmount(transaction.value());
    newtransaction.setSender(sender);
    newtransaction.setReceiver(receiver);
    newtransaction.setTimestampe(LocalDateTime.now());
    sender.setBalance(sender.getBalance().subtract(transaction.value()));
    receiver.setBalance(sender.getBalance().add(transaction.value()));

    this.repository.save(newtransaction);
    this.userService.saveUser(receiver);
    this.userService.saveUser(sender);

  }

  public boolean authorizeTransaction(User sender, BigDecimal value) {
    ResponseEntity<Map> authorizationResponse = restTemplate
        .getForEntity("https://run.mocky.io/v3/9b89b419-a2f7-4885-aa86-5ddcea24d520", Map.class);
    if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
      String message = (String) authorizationResponse.getBody().get("message");
      // se no corpo da message existe apalavra autorizado
      return "Autorizado".equalsIgnoreCase(message);
    } else {
      return false;
    }
  }
}
