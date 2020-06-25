package ru.vtb.neoflex.autotests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.dao.MySqlConnector;
import ru.neoflex.model.ResponseOldTestimony;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void checkFaultCodeSuccessTest() throws SQLException {
        String testimonyDate = "02-2020";
        String oldTestimonyURI = String.format("http://localhost:8080/services/testimony/get/old/testimony/%s", testimonyDate);

        ResponseOldTestimony responseBodyGetOld = RequestTestController.getResponseBodyGetOld(oldTestimonyURI);
        String resultCode = responseBodyGetOld.getFaultcode().getResultCode();
        String resultText = responseBodyGetOld.getFaultcode().getResultText();
        System.out.println(resultCode);
        System.out.println(resultText);
        Assertions.assertEquals("0", resultCode);
        Assertions.assertEquals("success", resultText);
        //проверка соответствия значений в БД и значений в запросе к микросервису
        ResultSet expectedRsult = MySqlConnector.selectAllFromBilling(testimonyDate);
        System.out.println(expectedRsult.next());
        while (expectedRsult.next()) {
            String date = expectedRsult.getString("currentmonth");
            double coldWater = expectedRsult.getInt("coldWater");
            double hotWater = expectedRsult.getInt("hotWater");
            double gas = expectedRsult.getInt("gas");
            double electricity = expectedRsult.getInt("electricity");
            Assertions.assertEquals(date, responseBodyGetOld.getDate());
            Assertions.assertEquals(coldWater, responseBodyGetOld.getConsumed().getColdWater());
            Assertions.assertEquals(hotWater, responseBodyGetOld.getConsumed().getHotWater());
            Assertions.assertEquals(gas, responseBodyGetOld.getConsumed().getGas());
            Assertions.assertEquals(electricity, responseBodyGetOld.getConsumed().getElectricity());
        }

    }
}
