package org.apilytic.currency.persistence.domain;

import org.apilytic.currency.persistence.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Rollback
public class CurrencyIntegrationTest {

	@Autowired
	private CurrencyRepository repo;

	@Test
	public void save() {
		Set h = new HashSet<>(Arrays.asList("usd", "gbp"));
		Currency e = new Currency();
		e.setCodes(h);

		repo.save(e);

		Set title = repo.findById(e.getId()).get().getCodes();
		assertEquals(2, title.size());
	}

	@Test
	public void saveWithCustomId() {
		Set h = new HashSet<>(Arrays.asList("jpy", "eur"));

		Currency e = new Currency();
		e.setId("custom-id");
		e.setCodes(h);

		repo.save(e);

		Set title = repo.findById("custom-id").get().getCodes();
		assertEquals(2, title.size());
	}

	@Test
	public void findAll() {
		Iterable<Currency> all = repo.findAll();
		all.forEach(currency -> assertEquals(2, currency.getCodes().size()));
	}
}