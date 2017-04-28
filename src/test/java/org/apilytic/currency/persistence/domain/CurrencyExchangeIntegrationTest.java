package org.apilytic.currency.persistence.domain;

import org.apilytic.currency.persistence.repository.CurrencyExchangeRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by g on 4/13/17.
 */
@Rollback
public class CurrencyExchangeIntegrationTest {

	@Autowired
	private CurrencyExchangeRepository repo;

	@Test
	public void save() {
		Set<String> h = new HashSet<String>(Arrays.asList("usd", "gbp"));
		CurrencyExchange e = new CurrencyExchange();
		e.setTitles(h);

		repo.save(e);

		Set<String> title = repo.findOne(e.getId()).getTitles();
		assertEquals(2, title.stream().count());
	}

	@Test
	public void saveWithCustomId() {
		Set<String> h = new HashSet<String>(Arrays.asList("jpy", "eur"));

		CurrencyExchange e = new CurrencyExchange();
		e.setId("custom-id");
		e.setTitles(h);

		repo.save(e);

		Set<String> title = repo.findOne("custom-id").getTitles();
		assertEquals(2, title.stream().count());
	}
}