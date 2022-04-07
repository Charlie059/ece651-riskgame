package edu.duke.ece651.shared;

import edu.duke.ece651.shared.Wrapper.GameID;

public class GameIDCounter {
        private static GameIDCounter counter_obj = null;
        private static int next_counter;
        private static int current_id;

        /**
         * Private Constructor
         */
        public GameIDCounter(){
            next_counter = 1;
        }

        public static GameIDCounter getInstance(){
            if (counter_obj == null){
                synchronized(GameIDCounter.class){
                    if (counter_obj == null){
                        counter_obj = new GameIDCounter();
                    }
                }
            }
            current_id = next_counter;
            next_counter++;
            return counter_obj;
        }

        public GameID getCurrent_id() {
            return new GameID(current_id);
        }



}
