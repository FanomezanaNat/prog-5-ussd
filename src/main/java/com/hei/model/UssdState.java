package com.hei.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UssdState {
  private List<String> path = new ArrayList<String>();
  private TempData tempData = new TempData();

  public String getCurrentPath() {
    return path.isEmpty() ? "" : String.join("-", path);
  }
}
