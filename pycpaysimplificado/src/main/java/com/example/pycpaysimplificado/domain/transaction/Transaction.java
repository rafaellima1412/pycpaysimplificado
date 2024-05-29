package com.example.pycpaysimplificado.domain.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import com.example.pycpaysimplificado.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name= "transactions")
@Table(name= "transactions")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Transaction {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private BigDecimal amount;
@ManyToAny
@JoinColumn(name= "sender_id")
private User sender;
@JoinColumn(name= "receive_id")
private User receiver;
private LocalDateTime timestampe;
}