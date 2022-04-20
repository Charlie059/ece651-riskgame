package edu.duke.ece651.client.Email;

import java.util.Random;

public class CodeGenerator {

    private static CodeGenerator codeGenerator = null;

    /**
     * Private Constructor
     */
    private CodeGenerator(){}

    public static CodeGenerator getInstance(){
        if (codeGenerator == null){
            synchronized(CodeGenerator.class){
                if (codeGenerator == null){
                    codeGenerator = new CodeGenerator();
                }
            }
        }
        return codeGenerator;
    }

    /**
     * Gen random code
     * @return random code
     */
    public String codeGen(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }


}

