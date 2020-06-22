package ru.vtb.neoflex.autotests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;

public class OldTestimonyTest {
    @Test
    public void checkCodeSuccessTest() {
        String testimonyDate = "01-2020";
        String oldTestimonyURI = String.format("http://localhost:8080/services/testimony/get/old/testimony/%s", testimonyDate);
        int actualStatusCode = RequestTestController.getRequestCodeGetOldTestimony(oldTestimonyURI);
        Assertions.assertEquals(200, actualStatusCode);
        System.out.println("OldTestimonyTest : "+"statusCode : " + actualStatusCode);


    }
}
