package edu.duke.ece651.client.Email;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    void codeGen() {
        assertEquals(CodeGenerator.getInstance().codeGen().length(),6);
    }
}