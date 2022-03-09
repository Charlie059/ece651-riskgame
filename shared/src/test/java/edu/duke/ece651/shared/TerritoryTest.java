package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TerritoryTest {
    @Test
    void testGetName() {
        Territory t = new Territory("123");
        assertTrue(t.getName().equals("123"));
    }

    @Test
    void test() {
        ArrayList<Territory> list = new ArrayList<Territory>();
        Territory t1 = new Territory("1");
        Territory t2 = new Territory("2");
        Territory t3 = new Territory("3");
        list.add(t1);
        list.add(t2);
        list.add(t3);

        System.out.println(t1.getName() + " " + t1.getOwner());
        System.out.println(t2.getName() + " " + t2.getOwner());
        System.out.println(t3.getName() + " " + t3.getOwner());

        for (int i = 0; i < 3; i++) {
            list.get(i).setOwner(i+1);
        }

        System.out.println(t1.getName() + " " + t1.getOwner());
        System.out.println(t2.getName() + " " + t2.getOwner());
        System.out.println(t3.getName() + " " + t3.getOwner());

    }
}
