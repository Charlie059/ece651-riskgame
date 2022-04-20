package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.IO.ClientActions.*;

public interface ActionVisitor {

    void visit(AttackAction attackAction);

    void visit(CommitAction commitAction);

    void visit(DeployAction deployAction);

    void visit(JoinAction joinAction);

    void visit(LoginAction loginAction);

    void visit(LogoutAction logoutAction);

    void visit(MoveAction moveAction);

    void visit(NewGameAction newGameAction);

    void visit(SignUpAction signUpAction);

    void visit(SwitchGameAction switchGameAction);

    void visit(UpgradeTechAction upgradeTechAction);

    void visit(UpgradeUnitsAction updateUnitsAction);

    void visit(ChooseJoinGameAction chooseJoinGameAction);

    void visit(ChooseSwitchGameAction chooseSwitchGameAction);

    void visit(SpyDeployAction spyDeployAction);

    void visit(SpyMoveAction spyMoveAction);

    void visit(SpyUpgradeAction spyUpgradeAction);

    void visit(CloakTerritoryAction cloakTerritoryAction);

    void visit(CardBuyAction cardBuyAction);

}
