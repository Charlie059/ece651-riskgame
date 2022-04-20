package edu.duke.ece651.client.Controller;

public interface Communication {

    /*
    when user click the territory button in map, it will call outstide controller's
    setTerrInfo to show related territory info in the view.
    * */
    void setTerrInfo(String terrName);
}
