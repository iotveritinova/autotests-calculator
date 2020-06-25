package ru.vtb.neoflex.autotests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//аннотация тест по какой то прочине не отрабатывает в jupiter
//решено
//File->Settings->Build, Execution...->Build Tools->Gradle gradle jvm должно быть 11 версия
// +run test using должно использовать idea, а не gradle+ в build.gradle не хватало блока test {useJUnitPlatform()}
//import org.junit.Test;

import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.dao.MySqlConnector;
import ru.neoflex.model.CurrentTestimony;
import ru.neoflex.model.RequestSaveTestimony;
import ru.neoflex.model.ResponseSaveTestimony;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    @Test
    public void checkFaultCodeSuccessTest() throws SQLException {
        String saveTestimonyURI = "http://localhost:8080/services/testimony/save";
        RequestSaveTestimony requestSaveTestimony = new RequestSaveTestimony();
        CurrentTestimony currentTestimony = new CurrentTestimony();

        requestSaveTestimony.setDate("02-2020");
        currentTestimony.setColdWater(30);
        currentTestimony.setHotWater(40);
        currentTestimony.setGas(50);
        currentTestimony.setElectricity(60);
        requestSaveTestimony.setCurrentTestimony(currentTestimony);

        ResponseSaveTestimony responseSaveTestimony = RequestTestController.getResponseBodySave(saveTestimonyURI, requestSaveTestimony);
        String resultCode = responseSaveTestimony.getFaultcode().getResultCode();
        String resultText = responseSaveTestimony.getFaultcode().getResultText();
        System.out.println(resultCode);
        System.out.println(resultText);
        Assertions.assertEquals("0", resultCode);
        Assertions.assertEquals("success", resultText);
        //проверка соответствия значений в БД и значений в запросе к микросервису
        ResultSet expectedRsult = MySqlConnector.selectAllFromBilling(requestSaveTestimony.getDate());
        System.out.println(expectedRsult.next());
        while (expectedRsult.next()) {
            String date = expectedRsult.getString("currentmonth");
            double coldWater = expectedRsult.getInt("coldWater");
            double hotWater = expectedRsult.getInt("hotWater");
            double gas = expectedRsult.getInt("gas");
            double electricity = expectedRsult.getInt("electricity");
            Assertions.assertEquals(date, requestSaveTestimony.getDate());
            Assertions.assertEquals(coldWater, requestSaveTestimony.getCurrentTestimony().getColdWater());
            Assertions.assertEquals(hotWater, requestSaveTestimony.getCurrentTestimony().getHotWater());
            Assertions.assertEquals(gas, requestSaveTestimony.getCurrentTestimony().getGas());
            Assertions.assertEquals(electricity, requestSaveTestimony.getCurrentTestimony().getElectricity());
        }

    }
}
