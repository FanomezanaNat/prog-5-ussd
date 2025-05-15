package com.hei.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TempData {
  private String number;
  private double amount;

  public void clear() {
    number = null;
    amount = 0;
  }
}
