package org.poo.cb.Exchange;

import org.poo.cb.CentralApp;

public class USDToJPY implements ExchangeStrategy {
    @Override
    public float exchange(float amount) {
        return amount * CentralApp.getInstance().getExchangeRates().get("USDJPY");
    }
}
