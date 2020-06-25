package ru.vtb.neoflex.autotests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.dao.MySqlConnector;
import ru.neoflex.model.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import static ru.vtb.neoflex.autotests.TestBase.validRequestSetPrice;

public class ChangePriceTest {
    String changePriceURI = "http://localhost:8080/services/testimony/changePrice";

    public static Iterator<Object[]> dataRead() throws IOException {
        String requestFile = "src/test/resources/ChangePriceTest.json";
        return validRequestSetPrice(requestFile);
    }

    @MethodSource("dataRead")
    @ParameterizedTest
    public void checkCodeSuccessTest(RequestSetPrice requestSetPrice) {
        int actualStatusCode = RequestTestController.getRequestCodeSetPrice(changePriceURI, requestSetPrice);
        Assertions.assertEquals(200, actualStatusCode);
    }

    @MethodSource("dataRead")
    @ParameterizedTest
    public void checkFaultCodeSuccessTest(RequestSetPrice requestSetPrice) throws SQLException {
        ResponseSetPrice responseBodySetPrice = RequestTestController.getResponseBodySetPrice(changePriceURI, requestSetPrice);
        String resultCode = responseBodySetPrice.getResultCode();
        String resultText = responseBodySetPrice.getResultText();
        System.out.println(resultCode);
        System.out.println(resultText);
        Assertions.assertEquals("0", resultCode);
        Assertions.assertEquals("success", resultText);
        //проверка соответствия значений в БД и значений в запросе к микросервису
        ResultSet expectedRsult = MySqlConnector.selectAllFromPrices();
        System.out.println(expectedRsult.next());
        while (expectedRsult.next()) {
            double coldWater = expectedRsult.getInt("priceColdWater");
            double hotWater = expectedRsult.getInt("priceHotWater");
            double gas = expectedRsult.getInt("priceGas");
            double electricity = expectedRsult.getInt("priceElectricity");
            //Assertions.assertEquals(date, requestSetPrice.getDate());
            Assertions.assertEquals(coldWater, requestSetPrice.getPrice().getPriceColdWater());
            Assertions.assertEquals(hotWater, requestSetPrice.getPrice().getPriceHotWater());
            Assertions.assertEquals(gas, requestSetPrice.getPrice().getPriceGas());
            Assertions.assertEquals(electricity, requestSetPrice.getPrice().getPriceElectricity());
        }

    }
}
