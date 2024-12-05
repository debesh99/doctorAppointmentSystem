package com.debesh.spring.budgetmanagementsystem.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@NotNull(message = "Amount can't be null")
	private Double amount;

	@Size(max = 100)
	private String description;

	@Column(nullable = false)
	@Min(1)
	@Max(2)
	private int type; // 1. Income 2.Expense

	private LocalDateTime transactionDate;
	
	@ManyToOne
	@JoinColumn(name="walletId")
	@JsonIgnore
	private Wallet wallet;

	@PrePersist
	public void setTransactionDate() {
		this.transactionDate = LocalDateTime.now();
	}
}
// JsonIgnore : It won't show the field in rest