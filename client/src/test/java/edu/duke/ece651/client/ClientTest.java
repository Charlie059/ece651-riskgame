package edu.duke.ece651.client;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ClientTest {
//    Client c;
//
//    @Start
//    public void start(Stage window) throws Exception {
//        c = new Client();
//        c.setDebug(true);
//        c.start(window);
//    }
//
//    @Test
//    void test_all(FxRobot robot) {
//
//        // Test sign up
//        robot.clickOn("Sign up");
//        robot.clickOn("#userName");
//        robot.write("admin");
//        robot.clickOn("#passWord");
//        robot.write("12345");
//        robot.clickOn("Sign Up");
//        robot.clickOn("Back");
//
//        // Test Login
//        robot.clickOn("#userName");
//        robot.write("admin");
//        robot.clickOn("#passWord");
//        robot.write("12345");
//        robot.clickOn("Login");
//
//        // Test New Game
//        robot.clickOn("New Game");
//
//        FxAssert.verifyThat("#n_players", TextInputControlMatchers.hasText(""));
//        FxAssert.verifyThat("#gameID", TextInputControlMatchers.hasText(""));
//
//        robot.clickOn("#n_players");
//        robot.write("0");
//        FxAssert.verifyThat("#n_players", TextInputControlMatchers.hasText("0"));
//        robot.clickOn("#gameID");
//        robot.write("12345");
//        FxAssert.verifyThat("#gameID", TextInputControlMatchers.hasText("12345"));
//        robot.clickOn("Start");
//
//        robot.clickOn("#n_players");
//        robot.write("2");
//        FxAssert.verifyThat("#n_players", TextInputControlMatchers.hasText("02"));
//        robot.clickOn("Start");
//
//        // Test deploy
//        robot.clickOn("#territorySelect");
//        robot.clickOn("b1");
//        robot.clickOn("#levelSelect");
//        robot.clickOn("0");
//
//        robot.clickOn("#numberSelect");
//        robot.clickOn("9");
//
//        robot.clickOn("Done");
//        robot.clickOn("Commit");
//
//
//        // Test attack
//        robot.clickOn("Attack");
//
//        FxAssert.verifyThat("#attackList", ListViewMatchers.isEmpty());
//
//        robot.clickOn("#terrFrom");
//        robot.write("b1");
//        robot.clickOn("#terrTo");
//        robot.write("a1");
//        robot.clickOn("#selectLevel");
//        robot.write("0");
//        robot.clickOn("#selectNum");
//        robot.write("1");
//
//        robot.clickOn("Add");
//        robot.clickOn("Submit");
//
//        // Test Move
//
//        robot.clickOn("Move");
//        robot.clickOn("#terrFrom");
//        robot.write("b1");
//        robot.clickOn("#terrTo");
//        robot.write("b2");
//        robot.clickOn("#selectLevel");
//        robot.write("0");
//        robot.clickOn("#selectNum");
//        robot.write("1");
//
//        robot.clickOn("Add");
//        robot.clickOn("Submit");
//
//
//        // Test Upgrade Units
//        robot.clickOn("Upgrade Units");
//
//        FxAssert.verifyThat("#upgradeList", ListViewMatchers.isEmpty());
//
//        robot.clickOn("#terrFrom");
//        robot.write("b1");
//        robot.clickOn("#selectCurLevel");
//        robot.write("0");
//        robot.clickOn("#selectNum");
//        robot.write("1");
//        robot.clickOn("#selectUpgradeLevel");
//        robot.write("1");
//
//        robot.clickOn("Add");
//        robot.clickOn("Submit");
//
//        // Test Upgrade Tech
//        robot.clickOn("Upgrade Tech");
//
//        // View Map
//        robot.clickOn("View Map");
//        robot.clickOn("a1");
//        robot.clickOn("b1");
//
//        // Test switch game
//        robot.clickOn("Switch Game");
//        robot.clickOn("Back");
//
//
//    }
//
//    private void Enter_menu(FxRobot robot){
//        robot.clickOn("#userName");
//        robot.write("admin");
//        robot.clickOn("#passWord");
//        robot.write("12345");
//
//        robot.clickOn("Login");
//    }
//
//    private void Enter_deploy(FxRobot robot){
//        Enter_menu(robot);
//        robot.clickOn("New Game");
//        robot.clickOn("#n_players");
//        robot.write("2");
//        robot.clickOn("Start");
//    }
//
//    private void Enter_Main(FxRobot robot){
//        Enter_deploy(robot);
//
//        robot.clickOn("Commit");
//    }
//
//    @Test
//    void test_login(FxRobot robot){
////        FxAssert.verifyThat("#userName", TextInputControlMatchers.hasText(""));
////        FxAssert.verifyThat("#passWord", TextInputControlMatchers.hasText(""));
//
//        robot.clickOn("#userName");
//        robot.write("admin");
//        robot.clickOn("#passWord");
//        robot.write("12345");
//
////        FxAssert.verifyThat("#userName", TextInputControlMatchers.hasText("admin"));
////        FxAssert.verifyThat("#passWord", TextInputControlMatchers.hasText("12345"));
//
//        robot.clickOn("Login");
//    }
//
//    @Test
//    void test_signUp(FxRobot robot){
//        robot.clickOn("Sign up");
//        robot.clickOn("Back");
//        robot.clickOn("Sign up");
//
//        FxAssert.verifyThat("#userName", TextInputControlMatchers.hasText(""));
//        FxAssert.verifyThat("#passWord", TextInputControlMatchers.hasText(""));
//
//        robot.clickOn("#userName");
//        robot.write("admin");
//        FxAssert.verifyThat("#userName", TextInputControlMatchers.hasText("admin"));
//        robot.clickOn("#passWord");
//        robot.write("12345");
//        FxAssert.verifyThat("#passWord", TextInputControlMatchers.hasText("12345"));
//
//        robot.clickOn("Sign Up");
//
//    }
//
//    @Test
//    void test_menu(FxRobot robot){
//        // Test Login
//        robot.clickOn("#userName");
//        robot.write("admin");
//        robot.clickOn("#passWord");
//        robot.write("12345");
//        robot.clickOn("Login");
//
//        // Test Continues
//        robot.clickOn("New Game");
//        robot.clickOn("Back");
//        robot.clickOn("Continue");
//        robot.clickOn("Back");
//        robot.clickOn("Join");
//        robot.clickOn("Back");
//        robot.clickOn("Exit");
//    }
//
//    @Test
//    void test_newGame(FxRobot robot){
//        Enter_menu(robot);
//        robot.clickOn("New Game");
//
//        FxAssert.verifyThat("#n_players", TextInputControlMatchers.hasText(""));
//        FxAssert.verifyThat("#gameID", TextInputControlMatchers.hasText(""));
//
//        robot.clickOn("#n_players");
//        robot.write("0");
//        FxAssert.verifyThat("#n_players", TextInputControlMatchers.hasText("0"));
//        robot.clickOn("#gameID");
//        robot.write("12345");
//        FxAssert.verifyThat("#gameID", TextInputControlMatchers.hasText("12345"));
//        robot.clickOn("Start");
//
//        robot.clickOn("#n_players");
//        robot.write("2");
//        FxAssert.verifyThat("#n_players", TextInputControlMatchers.hasText("02"));
//        robot.clickOn("Start");
//    }
//
//    @Test
//    void test_continue(FxRobot robot){
//        Enter_menu(robot);
//        robot.clickOn("Continue");
//        robot.clickOn("Enter");
//    }
//
//    @Test
//    void test_join(FxRobot robot){
//        Enter_menu(robot);
//        robot.clickOn("Join");
//        robot.clickOn("Enter");
//    }
//
//    @Test
//    void test_deploy(FxRobot robot){
//        Enter_deploy(robot);
//
//        //repeat ..
//        robot.clickOn("#territorySelect");
//        robot.clickOn("b1");
//        robot.clickOn("#levelSelect");
//        robot.clickOn("0");
//
//        robot.clickOn("#numberSelect");
//        robot.clickOn("9");
//
//        robot.clickOn("Commit");
//        robot.clickOn("Done");
//
//
//    }
//
//    @Test
//    void test_MainGameViwe_Done(FxRobot robot){
//        Enter_Main(robot);
//        robot.clickOn("Done");
//    }
//
//    @Test
//    void test_MainGameViwe_Switch(FxRobot robot){
//        Enter_Main(robot);
//        robot.clickOn("Done");
//        robot.clickOn("Switch Game");
//    }
//
//    @Test
//    void test_MainGameViwe_Exit(FxRobot robot){
//        Enter_Main(robot);
//        robot.clickOn("Exit");
//    }
//
//    @Test
//    void test_view_Map(FxRobot robot){
//        Enter_deploy(robot);
//        robot.clickOn("View Map");
//        robot.clickOn("a1");
//        robot.clickOn("b1");
//    }
//
//    @Test
//    void test_Attack(FxRobot robot){
//        Enter_Main(robot);
//        robot.clickOn("Attack");
//
//        FxAssert.verifyThat("#attackList", ListViewMatchers.isEmpty());
//
//        robot.clickOn("#terrFrom");
//        robot.write("b1");
//        robot.clickOn("#terrTo");
//        robot.write("a1");
//        robot.clickOn("#selectLevel");
//        robot.write("0");
//        robot.clickOn("#selectNum");
//        robot.write("1");
//
//        robot.clickOn("Add");
//        robot.clickOn("Submit");
//    }
//
//    @Test
//    void test_Move(FxRobot robot){
//        Enter_Main(robot);
//        robot.clickOn("Move");
//
//        FxAssert.verifyThat("#moveList", ListViewMatchers.isEmpty());
//
//        robot.clickOn("#terrFrom");
//        robot.write("b1");
//        robot.clickOn("#terrTo");
//        robot.write("b0");
//        robot.clickOn("#selectLevel");
//        robot.write("0");
//        robot.clickOn("#selectNum");
//        robot.write("1");
//
//        robot.clickOn("Add");
//        robot.clickOn("Submit");
//
//    }
//
//    @Test
//    void test_UpgradeUnits(FxRobot robot){
//        Enter_Main(robot);
//        robot.clickOn("Upgrade Units");
//
//        FxAssert.verifyThat("#upgradeList", ListViewMatchers.isEmpty());
//
//        robot.clickOn("#terrFrom");
//        robot.write("b1");
//        robot.clickOn("#selectCurLevel");
//        robot.write("0");
//        robot.clickOn("#selectNum");
//        robot.write("1");
//        robot.clickOn("#selectUpgradeLevel");
//        robot.write("1");
//
//        robot.clickOn("Add");
//        robot.clickOn("Submit");
//
//    }
//
//    @Test
//    void test_UpgradeTech(FxRobot robot){
//        Enter_Main(robot);
//        robot.clickOn("Upgrade Tech");
//    }



}