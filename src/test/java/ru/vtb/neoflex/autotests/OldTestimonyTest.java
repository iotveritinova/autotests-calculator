package ru.vtb.neoflex.autotests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.model.CurrentTestimony;
import ru.neoflex.model.RequestSaveTestimony;
import ru.neoflex.model.ResponseOldTestimony;
import ru.neoflex.model.ResponseSaveTestimony;

public class OldTestimonyTest {
    @Test
    public void checkCodeSuccessTest() {
        String testimonyDate = "01-2020";
        String oldTestimonyURI = String.format("http://localhost:8080/services/testimony/get/old/testimony/%s", testimonyDate);
        int actualStatusCode = RequestTestController.getRequestCodeGetOldTestimony(oldTestimonyURI);
        Assertions.assertEquals(200, actualStatusCode);
        System.out.println("OldTestimonyTest : " + "statusCode : " + actualStatusCode);
    }

    @Test
    public void checkFaultCodeSuccessTest() {
        String testimonyDate = "02-2020";
        String oldTestimonyURI = String.format("http://localhost:8080/services/testimony/get/old/testimony/%s", testimonyDate);

        ResponseOldTestimony responseBodyGetOld = RequestTestController.getResponseBodyGetOld(oldTestimonyURI);
        String resultCode = responseBodyGetOld.getFaultcode().getResultCode();
        String resultText = responseBodyGetOld.getFaultcode().getResultText();
        System.out.println(resultCode);
        System.out.println(resultText);
        Assertions.assertEquals("0", resultCode);
        Assertions.assertEquals("success", resultText);

    }
}
