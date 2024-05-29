package com.example.pycpaysimplificado.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pycpaysimplificado.domain.User;
import com.example.pycpaysimplificado.domain.UserType;
import com.example.pycpaysimplificado.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository repository;

  public void validadeTransaction(User sender, BigDecimal amount) throws Exception {
    if (sender.getUserType() == UserType.MERCHANT) {
      throw new Exception("Usuario lojista não achado!");
    }

  }

  public User findUserByIdUser(Long id) throws Exception {
    return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuario não achado!"));
  }

  public void saveUser(User user) {
    this.repository.save(user);
  }
}
