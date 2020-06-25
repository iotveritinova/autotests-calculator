package ru.vtb.neoflex.autotests;

import org.junit.jupiter.api.Assertions;
//аннотация тест по какой то прочине не отрабатывает в jupiter
//решено
//File->Settings->Build, Execution...->Build Tools->Gradle gradle jvm должно быть 11 версия
// +run test using должно использовать idea, а не gradle+ в build.gradle не хватало блока test {useJUnitPlatform()}
//import org.junit.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.dao.MySqlConnector;
import ru.neoflex.model.RequestSaveTestimony;
import ru.neoflex.model.ResponseSaveTestimony;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import static ru.vtb.neoflex.autotests.TestBase.validRequestSave;

public class SaveTestimonyTest {
    static String saveTestimonyURI = "http://localhost:8080/services/testimony/save";

    public static Iterator<Object[]> dataRead() throws IOException {
        String requestFile = "src/test/resources/SaveTestimonyTest.json";
        return validRequestSave(requestFile);
    }

    @MethodSource("dataRead")
    @ParameterizedTest
    public void checkCodeSuccessTest(RequestSaveTestimony requestSaveTestimony) {
        int actualStatusCode = RequestTestController.getRequestCodeSaveTestimony(saveTestimonyURI, requestSaveTestimony);
        Assertions.assertEquals(200, actualStatusCode);
    }

    @MethodSource("dataRead")
    @ParameterizedTest
    //@Test
    public void checkFaultCodeSuccessTest(RequestSaveTestimony requestSaveTestimony) throws SQLException {
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
