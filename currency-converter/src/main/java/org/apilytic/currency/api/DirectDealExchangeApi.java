package org.apilytic.currency.api;

import java.util.List;
import java.util.stream.Collectors;

import org.apilytic.currency.api.calc.ExchangeCalculator;
import org.apilytic.currency.api.model.CurrencyRate;
import org.apilytic.currency.api.model.ExchangeCurrency;
import org.apilytic.currency.ingestion.rate.provider.ExchangeRate;
import org.apilytic.currency.ingestion.rate.provider.FinancialProvider;
import org.apilytic.currency.ingestion.rate.query.QueryRateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Query currency exchange query directly from query provider.
 * This is slow operation.
 *
 * <p>Supported exchange query providers:</p>
 * <ul>
 * <li>Yahoo finance</li>
 * <li>Google currency calculator</li>
 * </ul>
 *
 * Created by g on 7/11/14.
 */
@Service
public class DirectDealExchangeApi implements CurrencyExchangeApi {

    @Autowired
    private FinancialProvider financialProvider;

    @Autowired
    private QueryRateBuilder queryRateBuilder;

    @Autowired
    private RateFormat rateFormat;

    @Override
    public ExchangeCurrency exchangeSingleCurrency(CurrencyRate currencyRate) {
        financialProvider.setExchangeQuery(queryRateBuilder.createQueryRate(currencyRate.getFromCurrency(),
                currencyRate.getToCurrency()));
        List<? extends ExchangeRate> providedRates = financialProvider
                .provideRate();

        String rawRatio = providedRates.get(0).rate();

        ExchangeCalculator exchangeCalc = new ExchangeCalculator();
        return exchangeCalc.calc(currencyRate, rawRatio);
    }

    @Override
    public List<ExchangeCurrency> exchangeMultipleCurrencies(List<CurrencyRate> currencyRates) {
        return currencyRates.stream().map(this::exchangeSingleCurrency).collect(Collectors.toList());
    }
}
