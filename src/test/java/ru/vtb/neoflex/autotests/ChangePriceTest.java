package ru.vtb.neoflex.autotests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.model.*;

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
    public void checkFaultCodeSuccessTest() {
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

    }
}
