package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerCounterTest {

    @Test
    void playerCounterTest() {
        for (int i = 0; i < 5; i++){
            PlayerCounter p = null;
            //get current player id
            int curr_id= p.getInstance().getCurrent_id();
            System.out.println(curr_id);
            assertEquals(curr_id,i+1);
        }
    }
}