package org.poo.cb.Exchange;

import org.poo.cb.CentralApp;

public class CADToJPY implements ExchangeStrategy {
    @Override
    public float exchange(float amount) {
        return amount * CentralApp.getInstance().getExchangeRates().get("CADJPY");
    }
}
