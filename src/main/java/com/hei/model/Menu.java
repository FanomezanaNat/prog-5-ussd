package com.hei.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Menu {
  private final String message;

  public abstract void handleInput(String input);
}
