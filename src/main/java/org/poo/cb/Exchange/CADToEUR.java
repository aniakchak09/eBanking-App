package org.poo.cb.Exchange;

import org.poo.cb.CentralApp;

public class CADToEUR implements ExchangeStrategy{
    public float exchange(float amount) {
        return amount * CentralApp.getInstance().getExchangeRates().get("CADEUR");
    }
}
