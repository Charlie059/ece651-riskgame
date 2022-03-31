package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Wrapper.AccountID;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LoginCheckerTest {

    @Test
    public void test_LoginChecker_success(){
        AccountID accountID = new AccountID();
        accountID.setaccountID("pod128g");

        HashMap<Integer, Game> gameHashMap = new HashMap<>();
        HashMap<String, Account> accountHashMap = new HashMap<>();

        Account account = new Account();
        account.setPassword("123");
        accountHashMap.put(accountID.getaccountID(),account);

        String recvAccount = "pod128g";
        String recvPassword = "123";

        LoginChecker loginChecker = new LoginChecker(accountID, gameHashMap, accountHashMap, recvAccount,recvPassword);
        assertEquals(true, loginChecker.doCheck());
    }

    @Test
    public void test_LoginChecker_failure(){
        AccountID accountID = new AccountID();
        accountID.setaccountID("pod128g");

        HashMap<Integer, Game> gameHashMap = new HashMap<>();
        HashMap<String, Account> accountHashMap = new HashMap<>();

        Account account = new Account();
        account.setPassword("123");
        accountHashMap.put(accountID.getaccountID(),account);

        String recvAccount = "pod128g";
        String recvPassword = "121";

        LoginChecker loginChecker = new LoginChecker(accountID, gameHashMap, accountHashMap, recvAccount,recvPassword);
        assertEquals(false, loginChecker.doCheck());
    }

    @Test
    public void test_LoginChecker_failure_account_no_exist(){
        AccountID accountID = new AccountID();
        accountID.setaccountID("pod128g");

        HashMap<Integer, Game> gameHashMap = new HashMap<>();
        HashMap<String, Account> accountHashMap = new HashMap<>();

        Account account = new Account();
        account.setPassword("123");
        accountHashMap.put(accountID.getaccountID(),account);

        String recvAccount = "pod12g";
        String recvPassword = "123";

        LoginChecker loginChecker = new LoginChecker(accountID, gameHashMap, accountHashMap, recvAccount,recvPassword);
        assertEquals(false, loginChecker.doCheck());
    }
}