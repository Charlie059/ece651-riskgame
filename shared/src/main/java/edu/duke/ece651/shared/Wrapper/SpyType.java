package edu.duke.ece651.shared.Wrapper;

public class SpyType {
    private final Integer defaultType = 1;
    private final Integer rosenbergs = 2;
    private final Integer harrietTubman = 3;
    public Integer DefaultType(){
        return defaultType;
    }
    public Integer Rosenbergs(){
        return rosenbergs;
    }
    public Integer HarrietTubman(){
        return harrietTubman;
    }
}
