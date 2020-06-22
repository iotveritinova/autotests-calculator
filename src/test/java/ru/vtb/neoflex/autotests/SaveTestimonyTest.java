package ru.vtb.neoflex.autotests;

import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//аннотация тест по какой то прочине не отрабатывает в jupiter
import org.junit.Test;

import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.model.CurrentTestimony;
import ru.neoflex.model.RequestSaveTestimony;

public class SaveTestimonyTest {
    @Test
    public void checkCodeSuccessTest() {
        String saveTestimonyURI = "http://localhost:8080/services/testimony/save";
        RequestSaveTestimony requestSaveTestimony = new RequestSaveTestimony();
        CurrentTestimony currentTestimony = new CurrentTestimony();

        requestSaveTestimony.setDate("02-2020");
        currentTestimony.setColdWater(30);
        currentTestimony.setHotWater(40);
        currentTestimony.setGas(50);
        currentTestimony.setElectricity(60);
        requestSaveTestimony.setCurrentTestimony(currentTestimony);

        int actualStatusCode = RequestTestController.getRequestCodeSaveTestimony(saveTestimonyURI, requestSaveTestimony);
        Assertions.assertEquals(200, actualStatusCode);
        System.out.println("SaveTestimonyTest : "+"statusCode : " + actualStatusCode);


    }
}
