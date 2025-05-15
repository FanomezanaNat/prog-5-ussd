package com.hei.service;

import com.hei.model.exception.InvalidAccountNumberException;
import com.hei.model.exception.InvalidAmountException;
import lombok.Getter;

@Getter
public class TransactionService {

  private double savingsBalance = 50_000;

  public void processPurchase(double amount, String description) {
    validatePositiveAmount(amount);
    System.out.printf("Achat: %s - montant: %.2f Ar%n", description, amount);
  }

  public void processTransfer(String accountNumber, double amount, String type) {
    if (accountNumber == null || accountNumber.trim().length() < 8) {
      throw new InvalidAccountNumberException("Numéro de compte invalide");
    }
    validatePositiveAmount(amount);
    System.out.printf("Transfert de %.2f Ar vers %s via %s%n", amount, accountNumber, type);
  }

  public boolean verifyPassword(String password) {
    return "1234".equals(password);
  }

    private void validatePositiveAmount(double amount) {
    if (amount <= 0) {
      throw new InvalidAmountException("Le montant doit être positif.");
    }
  }
}
