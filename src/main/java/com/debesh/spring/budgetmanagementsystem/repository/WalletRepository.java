package com.debesh.spring.budgetmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.debesh.spring.budgetmanagementsystem.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
	@Query("select w from Wallet w join w.user u where u.id=?1 order by w.priority")
	List<Wallet> findAllByUserIdOrderByPriority(Long userId);

	Optional<Wallet> findByAccNumber(String accNumber);
}