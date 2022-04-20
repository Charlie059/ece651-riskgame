package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.Checker.Checker;
import edu.duke.ece651.client.Checker.PasswordChecker;
import edu.duke.ece651.client.Checker.EmailAddressChecker;
import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.client.Email.CodeGenerator;
import edu.duke.ece651.client.Email.EmailSender;
import edu.duke.ece651.shared.IO.ClientActions.SignUpAction;
import edu.duke.ece651.shared.IO.ServerResponse.ClientPlayerPacket;
import edu.duke.ece651.shared.IO.ServerResponse.RSPLoginSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.RSPSignupSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.io.IOException;
import java.util.HashMap;

public class SignupModel extends Model{
    private HashMap<String, String> userCode = new HashMap<>(); // username, code

    private static SignupModel signupModel;
    private SignupModel() {}

    public synchronized static SignupModel getInstance() {
        if (signupModel == null) {signupModel = new SignupModel();}
        return signupModel;
    }

    public boolean signUp(String userName, String passWord, Boolean debugMode){
        if(debugMode){
            return true;
        }

        // Use a local checker to check if password satisfy the following criteria:
        // Password must be a combination of 8 or more digits containing uppercase letters,
        // lowercase letters, numbers, special symbols (characters other than letters, numbers,
        // underscores)
        PasswordChecker passwordChecker = new PasswordChecker();
        String[] password = new String[1];
        password[0] = passWord;

        // If password is not secure enough
        if(!passwordChecker.doCheck(password)) return false;

        // Create a new LoginAction
        SignUpAction signUpAction = new SignUpAction(new AccountID(userName),passWord);
        // Send to Server to validate
        try {
            ClientSocket.getInstance().sendObject(signUpAction);
            Response response = (Response) ClientSocket.getInstance().recvObject();
            return response.getClass() == RSPSignupSuccess.class;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return false;
        }
    }

    public boolean signUp(String userName, String passWord, String userInCode, Boolean debugMode){
        if(debugMode){
            return true;
        }

        // Use a local checker to check if password satisfy the following criteria:
        // Password must be a combination of 8 or more digits containing uppercase letters,
        // lowercase letters, numbers, special symbols (characters other than letters, numbers,
        // underscores)
        PasswordChecker passwordChecker = new PasswordChecker();
        String[] password = new String[1];
        password[0] = passWord;

        // If password is not secure enough
        if(!passwordChecker.doCheck(password)) return false;


        // if user in the hashmap
        if(this.userCode.containsKey(userName)){
            // verify the userinput code
            if(userInCode.equals(this.userCode.get(userName))){
                // Create a new LoginAction
                SignUpAction signUpAction = new SignUpAction(new AccountID(userName),passWord);
                // Send to Server to validate
                try {
                    ClientSocket.getInstance().sendObject(signUpAction);
                    Response response = (Response) ClientSocket.getInstance().recvObject();
                    return response.getClass() == RSPSignupSuccess.class;
                } catch (IOException | ClassNotFoundException | ClassCastException e) {
                    return false;
                }
            }
            else {
                return false;
            }

        }
        else{ // Send a verify code

            // Check user userEmail
            EmailAddressChecker emailAddressChecker = new EmailAddressChecker();
            String[] userInputEmail = new String[1];
            userInputEmail[0] = userName;

            // If that is an email address
            if(emailAddressChecker.doCheck(userInputEmail)){
                // gen a code and save code
                String genCode = CodeGenerator.getInstance().codeGen();
                this.userCode.put(userName, genCode);

                // Send an email to user
                EmailSender sender = new EmailSender();
                sender.sendEmail(userName, genCode);
                return false;
            }
            else{
                return false;
            }
        }

    }

    /**
     * Debug use: get code
     * @param userEmail
     * @return code
     */
    public String getCode(String userEmail){
        return this.userCode.get(userEmail);
    }
}
