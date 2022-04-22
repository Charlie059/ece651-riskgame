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

import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ClientTest {
    Client c;

    @Start
    public void start(Stage window) throws Exception {
        c = new Client();
        c.setDebug(true);
        c.start(window);
    }

    private void Enter_menu(FxRobot robot){
        robot.clickOn("#userName");
        robot.write("admin");
        robot.clickOn("#passWord");
        robot.write("12345");

        robot.clickOn("Login");
    }

    private void Enter_deploy(FxRobot robot){
        Enter_menu(robot);
        robot.clickOn("New Game");
        robot.clickOn("#n_players");
        robot.write("2");
        robot.clickOn("Start");
    }

    private void Enter_Main(FxRobot robot){
        Enter_menu(robot);
        robot.clickOn("Continue");
        robot.clickOn("Enter");
    }

    @Test
    void test_login(FxRobot robot){

        robot.clickOn("#userName");
        robot.write("admin");
        robot.clickOn("#passWord");
        robot.write("12345");

        robot.clickOn("Login");
    }

    @Test
    void test_signUp(FxRobot robot){
        robot.clickOn("Sign up");
        robot.clickOn("Back");
        robot.clickOn("Sign up");

        FxAssert.verifyThat("#userName", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#passWord", TextInputControlMatchers.hasText(""));

        robot.clickOn("#userName");
        robot.write("admin");
        FxAssert.verifyThat("#userName", TextInputControlMatchers.hasText("admin"));
        robot.clickOn("#passWord");
        robot.write("12345");
        FxAssert.verifyThat("#passWord", TextInputControlMatchers.hasText("12345"));

        robot.clickOn("Sign Up");

    }

    @Test
    void test_menu(FxRobot robot){
        // Test Login
        robot.clickOn("#userName");
        robot.write("admin");
        robot.clickOn("#passWord");
        robot.write("12345");
        robot.clickOn("Login");

        // Test Continues
        robot.clickOn("New Game");
        robot.clickOn("Back");
        robot.clickOn("Continue");
        robot.clickOn("Back");
        robot.clickOn("Join");
        robot.clickOn("Back");
        robot.clickOn("Exit");
    }

    @Test
    void test_newGame(FxRobot robot){
        Enter_menu(robot);
        robot.clickOn("New Game");

        FxAssert.verifyThat("#n_players", TextInputControlMatchers.hasText(""));

        robot.clickOn("#n_players");
        robot.write("0");
        FxAssert.verifyThat("#n_players", TextInputControlMatchers.hasText("0"));
        robot.clickOn("Start");

        robot.clickOn("#n_players");
        robot.write("2");
        FxAssert.verifyThat("#n_players", TextInputControlMatchers.hasText("02"));
        robot.clickOn("Start");
    }

    @Test
    void test_continue(FxRobot robot){
        Enter_menu(robot);
        robot.clickOn("Continue");
        robot.clickOn("Enter");
    }

    @Test
    void test_join(FxRobot robot){
        Enter_menu(robot);
        robot.clickOn("Join");
        robot.clickOn("Enter");
    }

    @Test
    void test_deploy(FxRobot robot){
        Enter_deploy(robot);

        //repeat ..
        robot.clickOn("#territorySelect");
        robot.clickOn("b1");
        robot.clickOn("#levelSelect");
        robot.clickOn("0");

        robot.clickOn("#numberSelect");
        robot.clickOn("9");

        robot.clickOn("Deploy");
        robot.clickOn("Commit");


    }

    @Test
    void test_MainGameViwe_Commit(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("Commit");
    }

    @Test
    void test_MainGameViwe_Switch(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("Switch Game");
    }

    @Test
    void test_MainGameViwe_Exit(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("Exit");
    }

    @Test
    void test_UpgradeTech(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("Upgrade Tech");
    }

    @Test
    void test_attack(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("a1");
        robot.clickOn("Attack");
    }

    @Test
    void test_move(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("Move");
    }

    @Test
    void test_moveSpy(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("#moveSpyButton");
    }

    @Test
    void test_deploySpy(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("#deploySpyButton");
    }

    @Test
    void test_upgradeUnits(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("#upgradeUnitButton");
    }

    @Test
    void test_cloak(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("#cloakButton");
    }

    @Test
    void test_Tool(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("#toolButton");
        robot.clickOn("Buying");
    }

//    @Test
//    void test_Tool_detail(FxRobot robot){
//        Enter_Main(robot);
//        robot.clickOn("#toolButton");
//        robot.clickOn("Detail");
//
//    }
//
//    @Test
//    void test_Tool_Use(FxRobot robot){
//        Enter_Main(robot);
//        robot.clickOn("#toolButton");
//        robot.clickOn("Buying");
//        robot.clickOn("Uses");
//    }

    @Test
    void test_Commit(FxRobot robot){
        Enter_Main(robot);
        robot.clickOn("#commitButton");
    }
}