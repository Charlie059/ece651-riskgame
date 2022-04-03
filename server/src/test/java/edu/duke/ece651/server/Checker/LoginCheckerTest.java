package edu.duke.ece651.server.Checker;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class LoginCheckerTest {

    @Test
    public void test_LoginChecker_success(){
        AccountID accountID = new AccountID("pod128g");

        HashMap<GameID, Game> gameHashMap = new HashMap<>();
        HashMap<AccountID, Account> accountHashMap = new HashMap<>();

        Account account = new Account();
        account.setPassword("123");
        accountHashMap.put(accountID,account);

        AccountID recvAccount = new AccountID("pod128g");
        String recvPassword = "123";

        LoginChecker loginChecker = new LoginChecker(accountID, gameHashMap, accountHashMap, recvAccount,recvPassword);
        Assertions.assertEquals(true, loginChecker.doCheck());
    }

    @Test
    public void test_LoginChecker_failure(){
        AccountID accountID = new AccountID("pod128g");

        HashMap<GameID, Game> gameHashMap = new HashMap<>();
        HashMap<AccountID, Account> accountHashMap = new HashMap<>();

        Account account = new Account();
        account.setPassword("123");
        accountHashMap.put(accountID,account);

        AccountID recvAccount = new AccountID("pod128g");
        String recvPassword = "121";

        LoginChecker loginChecker = new LoginChecker(accountID, gameHashMap, accountHashMap, recvAccount,recvPassword);
        Assertions.assertEquals(false, loginChecker.doCheck());
    }

    @Test
    public void test_LoginChecker_failure_account_no_exist(){
        AccountID accountID = new AccountID("pod128g");

        HashMap<GameID, Game> gameHashMap = new HashMap<>();
        HashMap<AccountID, Account> accountHashMap = new HashMap<>();

        Account account = new Account();
        account.setPassword("123");
        accountHashMap.put(accountID,account);

        AccountID recvAccount = new AccountID("pod18g");
        String recvPassword = "123";

        LoginChecker loginChecker = new LoginChecker(accountID, gameHashMap, accountHashMap, recvAccount,recvPassword);
        Assertions.assertEquals(false, loginChecker.doCheck());
    }
}