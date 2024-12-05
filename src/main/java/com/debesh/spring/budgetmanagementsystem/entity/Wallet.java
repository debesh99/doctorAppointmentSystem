package com.debesh.spring.budgetmanagementsystem.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(nullable = false)
	@Size(min = 3, max = 25)
	private String accName;

	@Column(nullable = false)
	@Size(min = 10, max = 25)
	private String accNumber;

	@Size(max = 100)
	private String description;

	@Column(nullable = false)
	@Min(1)
	@Max(3)
	private Integer priority; // 1. high 2. Medium 3.Low
//	We will display our bank accounts priority wise

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "wallet", orphanRemoval = true)
	private List<Transaction> transactions;

	private double currentBalance;

//	Current balance should start from 0.0
	@PrePersist
	public void setBalance() {
		this.currentBalance = 0.0;
	}
}
//cascadeType.Refresh - field will be refreshed each time a new transaction is added
//fetch type means when we fetch the wallet, all transaction should be fetched
//mapped by - mapped to wallet object of Transaction
//orphanRemoval - When wallet is deleted all transaction linked to wallet should also be deleted.