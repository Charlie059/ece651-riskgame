package edu.duke.ece651.shared;

public class PlayerCounter {
    private static PlayerCounter counter_obj = null;
    private static int next_counter;
    private static int current_id;
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
        current_id = next_counter;
        next_counter++;
        return counter_obj;
    }

    public static int getCurrent_id() {
        return current_id;
    }
}
