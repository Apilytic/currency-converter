// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.apilytc.currency.ingestion.vapor.model;

import org.apilytc.currency.ingestion.vapor.model.CurrencyTable;
import org.apilytc.currency.ingestion.vapor.model.ISO4217Bean;

privileged aspect ISO4217Bean_Roo_JavaBean {
    
    public CurrencyTable ISO4217Bean.getCurrencyTable() {
        return this.currencyTable;
    }
    
    public void ISO4217Bean.setCurrencyTable(CurrencyTable currencyTable) {
        this.currencyTable = currencyTable;
    }
    
}
