package edu.duke.ece651.shared.IO;

import java.io.Serializable;

/**
 * This class is used to test serialize object message
 */
public class MockClass implements Serializable {
    private int a;

    public MockClass(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }
}
