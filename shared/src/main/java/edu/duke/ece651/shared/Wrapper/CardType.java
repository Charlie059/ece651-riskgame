package edu.duke.ece651.shared.Wrapper;


import java.util.List;

public class CardType {
    private final List<Integer> bombardment =  List.of(11,120); //(card_type_index, cost(points))
    private final List<Integer> sanction =  List.of(12,300);
    private final List<Integer> greatLeapForward =  List.of(13,300);
    private final List<Integer> dayBreaks =  List.of(14,200);
    private final List<Integer> GodBeWithYou =  List.of(15,150);
    private final List<Integer> specialSpyUpgrade =  List.of(16,120);
    private final List<Integer> unitDeploy = List.of(17,0);


    public List<Integer> getBombardment() {
        return bombardment;
    }

    public List<Integer> getSanction() {
        return sanction;
    }

    public List<Integer> getGreatLeapForward() {
        return greatLeapForward;
    }

    public List<Integer> getDayBreaks() {
        return dayBreaks;
    }

    public List<Integer> getGodBeWithYou() {
        return GodBeWithYou;
    }

    public List<Integer> getSpecialSpyUpgrade() {
        return specialSpyUpgrade;
    }

    public List<Integer> getUnitDeploy() {
        return unitDeploy;
    }


}
