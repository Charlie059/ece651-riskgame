package edu.duke.ece651.shared;

public class PlayerCounter {
    private static PlayerCounter counter_obj = null;
    private static int next_counter;
    private int current_id;
    private PlayerCounter(){
        next_counter = 1;
    }
    public static PlayerCounter getInstance(){
        if (counter_obj == null){
            synchronized(PlayerCounter.class){
                if (counter_obj == null){
                    counter_obj = new PlayerCounter();
                }
            }
        }
        return counter_obj;
    }

    public int getCurrent_id() {
        current_id = next_counter;
        next_counter++;
        return current_id;
    }
}
