package ru.vtb.neoflex.autotests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.dao.MySqlConnector;
import ru.neoflex.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePriceTest {
    @Test
    public void checkCodeSuccessTest() {
        String changePriceURI = "http://localhost:8080/services/testimony/changePrice";
        RequestSetPrice requestSetPrice = new RequestSetPrice();
        Price price = new Price();

        price.setPriceColdWater(1050);
        price.setPriceHotWater(123);
        price.setPriceGas(124);
        price.setPriceElectricity(12412412);
        requestSetPrice.setPrice(price);

        int actualStatusCode = RequestTestController.getRequestCodeSetPrice(changePriceURI, requestSetPrice);
        Assertions.assertEquals(200, actualStatusCode);
        System.out.println("ChangePriceTest : " + "statusCode : " + actualStatusCode);
    }

    @Test
    public void checkFaultCodeSuccessTest() throws SQLException {
        String changePriceURI = "http://localhost:8080/services/testimony/changePrice";
        RequestSetPrice requestSetPrice = new RequestSetPrice();
        Price price = new Price();

        price.setPriceColdWater(1050);
        price.setPriceHotWater(123);
        price.setPriceGas(124);
        price.setPriceElectricity(12412412);
        requestSetPrice.setPrice(price);

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
