// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.apilytic.currency.persistence.domain;

import java.math.BigInteger;
import org.apilytic.currency.persistence.domain.Currency;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

privileged aspect Currency_Roo_Mongo_Entity {
    
    declare @type: Currency: @Persistent;
    
    @Id
    private BigInteger Currency.id;
    
    public BigInteger Currency.getId() {
        return this.id;
    }
    
    public void Currency.setId(BigInteger id) {
        this.id = id;
    }
    
}