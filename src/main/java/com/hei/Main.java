package com.hei;

import com.hei.model.UssdState;
import com.hei.service.MenuService;
import com.hei.service.TransactionService;

public class Main {
  public static void main(String[] args) {

    UssdState ussdState = new UssdState();
    TransactionService service = new TransactionService();
    MenuService menu = new MenuService(ussdState, service);

    menu.start();
  }
}
