package edu.duke.ece651.server;

import java.util.ArrayList;

public class ConnectionCallback {

  private ArrayList<String> callback;

  public ConnectionCallback() {
    this.callback = new ArrayList<String>();
  }

  void receiveString(String s) {
    System.out.println("ConnectionCallback()");
    this.callback.add(s);
  }

  ArrayList<String> getCallBack() {
    return this.callback;
  }
}
