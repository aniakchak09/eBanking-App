package org.poo.cb.Exchange;

import org.poo.cb.CentralApp;

public class EURToJPY implements ExchangeStrategy {
    @Override
    public float exchange(float amount) {
        return amount * CentralApp.getInstance().getExchangeRates().get("EURJPY");
    }
}
