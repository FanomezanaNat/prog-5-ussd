package com.hei.service;

import com.hei.model.Menu;
import com.hei.model.UssdState;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuService {
  private final Map<String, Menu> menus = new HashMap<>();
  private final UssdState state;
  private final TransactionService transactionService;
  private final Scanner scanner = new Scanner(System.in);

  public MenuService(UssdState state, TransactionService transactionService) {
    this.state = state;
    this.transactionService = transactionService;
    initMenus();
  }

  private void initMenus() {
    menus.put(
        "",
        new Menu(
            """
            Menu Principal:
            1. Acheter crédit
            2. Transférer argent
            3. Mvola
            4. Retrait argent
            5. Voir solde
            6. Quitter""") {
          @Override
          public void handleInput(String input) {
            switch (input) {
              case "1":
              case "2":
              case "3":
              case "4":
                state.getPath().add(input);
                navigate();
                break;
              case "5":
                showBalance();
                break;
              case "6":
                System.out.println("Merci d'avoir utilisé le service.");
                System.exit(0);
                break;
              default:
                invalidInput();
            }
          }
        });

    menus.put(
        "1",
        new Menu(
            """
            Acheter crédit:
            1. Crédit Standard
            2. Crédit VIP
            3. Forfaits
            0. Retour""") {
          @Override
          public void handleInput(String input) {
            switch (input) {
              case "0":
                goBack();
                break;
              case "1":
              case "2":
              case "3":
                state.getPath().add(input);
                navigate();
                break;
              default:
                invalidInput();
            }
          }
        });

    menus.put(
        "1-1",
        new Menu(
            """
            Crédit Standard:
            1. 1000 Ar
            2. 2000 Ar
            3. 5000 Ar
            0. Retour""") {
          @Override
          public void handleInput(String input) {
            switch (input) {
              case "0":
                goBack();
                break;
              case "1":
                handlePurchase(1000, "Crédit Standard 1000 Ar");
                break;
              case "2":
                handlePurchase(2000, "Crédit Standard 2000 Ar");
                break;
              case "3":
                handlePurchase(5000, "Crédit Standard 5000 Ar");
                break;
              default:
                invalidInput();
            }
          }
        });

    menus.put(
        "1-2",
        new Menu(
            "Crédit VIP:\n" + "1. 10000 Ar\n" + "2. 20000 Ar\n" + "3. 50000 Ar\n" + "0. Retour") {
          @Override
          public void handleInput(String input) {
            switch (input) {
              case "0":
                goBack();
                break;
              case "1":
                handlePurchase(10000, "Crédit VIP 10000 Ar");
                break;
              case "2":
                handlePurchase(20000, "Crédit VIP 20000 Ar");
                break;
              case "3":
                handlePurchase(50000, "Crédit VIP 50000 Ar");
                break;
              default:
                invalidInput();
            }
          }
        });

    menus.put(
        "1-3",
        new Menu(
            """
            Forfaits:
            1. Internet - 1000 Ar
            2. Appels - 1500 Ar
            3. SMS - 500 Ar
            0. Retour""") {
          @Override
          public void handleInput(String input) {
            switch (input) {
              case "0":
                goBack();
                break;
              case "1":
                handlePurchase(1000, "Forfait Internet");
                break;
              case "2":
                handlePurchase(1500, "Forfait Appels");
                break;
              case "3":
                handlePurchase(500, "Forfait SMS");
                break;
              default:
                invalidInput();
            }
          }
        });

    menus.put(
        "2",
        new Menu(
            """
            Transférer argent:
            1. Mvola
            2. Autre opérateur
            3. Banque
            0. Retour""") {
          @Override
          public void handleInput(String input) {
            switch (input) {
              case "0":
                goBack();
                break;
              case "1":
              case "2":
              case "3":
                state.getPath().add(input);
                askRecipientNumber(input);
                break;
              default:
                invalidInput();
            }
          }
        });
  }

  public void start() {
    navigate();
  }

  public void navigate() {
    String path = state.getCurrentPath();
    Menu menu = menus.get(path);
    if (menu == null) {
      System.out.println("Menu introuvable. Retour au menu principal.");
      state.getPath().clear();
      navigate();
      return;
    }
    System.out.println(menu.getMessage());
    System.out.print("Votre choix: ");
    String input = scanner.nextLine();
    menu.handleInput(input);
  }

  private void goBack() {
    if (!state.getPath().isEmpty()) {
      state.getPath().remove(state.getPath().size() - 1);
    }
    navigate();
  }

  private void invalidInput() {
    System.out.println("Choix invalide, veuillez réessayer.");
    navigate();
  }

  private void handlePurchase(double amount, String description) {
    transactionService.processPurchase(amount, description);
    System.out.println("Appuyez sur Entrée pour revenir au menu principal...");
    scanner.nextLine();
    state.getPath().clear();
    navigate();
  }

  private void askRecipientNumber(String transferType) {
    System.out.println("Entrez le numéro du destinataire:");
    String number = scanner.nextLine();
    if (number.length() < 8) {
      System.out.println("Numéro invalide.");
      askRecipientNumber(transferType);
      return;
    }
    state.getTempData().setNumber(number);
    askTransferAmount(transferType);
  }

  private void askTransferAmount(String transferType) {
    System.out.println("Entrez le montant à transférer:");
    String amountStr = scanner.nextLine();
    double amount;
    try {
      amount = Double.parseDouble(amountStr);
      if (amount <= 0) throw new NumberFormatException();
    } catch (NumberFormatException e) {
      System.out.println("Montant invalide.");
      askTransferAmount(transferType);
      return;
    }
    state.getTempData().setAmount(amount);
    confirmTransfer(transferType);
  }

  private void confirmTransfer(String transferType) {
    String number = state.getTempData().getNumber();
    double amount = state.getTempData().getAmount();
    System.out.println(
        "Confirmer le transfert de "
            + amount
            + " Ar vers "
            + number
            + " via "
            + mapTransferType(transferType)
            + " ? (1=Oui, 0=Non)");
    String choice = scanner.nextLine();
    if ("1".equals(choice)) {
      askPassword(transferType);
    } else {
      System.out.println("Transfert annulé.");
      state.getPath().clear();
      navigate();
    }
  }

  private void askPassword(String transferType) {
    System.out.println("Entrez votre mot de passe:");
    String password = scanner.nextLine();
    if (transactionService.verifyPassword(password)) {
      transactionService.processTransfer(
          state.getTempData().getNumber(),
          state.getTempData().getAmount(),
          mapTransferType(transferType));
      state.getTempData().clear();
      System.out.println("Appuyez sur Entrée pour revenir au menu principal...");
      scanner.nextLine();
      state.getPath().clear();
      navigate();
    } else {
      System.out.println("Mot de passe incorrect.");
      askPassword(transferType);
    }
  }

  private String mapTransferType(String input) {
    return switch (input) {
      case "1" -> "Mvola";
      case "2" -> "Autre opérateur";
      case "3" -> "Banque";
      default -> "Inconnu";
    };
  }

  private void showBalance() {
    double balance = transactionService.getSavingsBalance();
    System.out.println("Votre solde actuel est : " + balance + " Ar");
    System.out.println("Appuyez sur Entrée pour revenir au menu principal...");
    scanner.nextLine();
    state.getPath().clear();
    navigate();
  }
}
