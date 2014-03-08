package org.apilytc.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RateDataOnDemand {

	private List<Rate> data;

	private Random rnd = new SecureRandom();

	@Autowired
	private RedisTemplate<String, String> template;

	public Rate getNewTransientRate(int index) {
		Rate obj = new Rate();
		obj.setKey(Rate.key(String.valueOf(index)));

		Map<String, String> value = new HashMap<String, String>();
		String currency = RandomStringUtils.randomAlphabetic(3);
		Integer currencyRate = rnd.nextInt();
		value.put(currency, String.valueOf(currencyRate));

		obj.setValue(value);

		return obj;
	}

	public Rate getRandomRate() {
		init();

		Rate obj = data.get(rnd.nextInt(data.size()));

		Rate rate = new Rate();
		rate.setKey(obj.getKey());
		HashOperations<String, String, String> opsForHash = template
				.opsForHash();
		rate.setValue(opsForHash.entries(obj.getKey()));

		return rate;
	}

	public void init() {
		int from = 0;
		int to = 10;

		HashOperations<String, String, String> opsForHash = template
				.opsForHash();

		data = new ArrayList<Rate>();

		// FIXME provide findAllImplementation
		for (int i = from; i < to; i++) {
			if (opsForHash.entries(String.valueOf(i)).size() == 0) {
				continue;
			}

			Rate rate = new Rate();
			rate.setKey(Rate.key(String.valueOf(i)));
			rate.setValue(opsForHash.entries(String.valueOf(i)));

			data.add(rate);
		}

		if (!data.isEmpty()) {
			return;
		}

		for (int i = from; i < to; i++) {
			Rate obj = getNewTransientRate(i);
			try {
				template.opsForHash().putAll(obj.getKey(), obj.getValue());
			} catch (final ConstraintViolationException e) {
				final StringBuilder msg = new StringBuilder();
				for (Iterator<ConstraintViolation<?>> iter = e
						.getConstraintViolations().iterator(); iter.hasNext();) {
					final ConstraintViolation<?> cv = iter.next();
					msg.append("[")
							.append(cv.getRootBean().getClass().getName())
							.append(".").append(cv.getPropertyPath())
							.append(": ").append(cv.getMessage())
							.append(" (invalid value = ")
							.append(cv.getInvalidValue()).append(")")
							.append("]");
				}
				throw new IllegalStateException(msg.toString(), e);
			}

			data.add(obj);
		}
	}

}