package org.apilytic.currency;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

public aspect Spring_Integration_Annotation {

	declare @type: org.apilytic.currency..*IntegrationTest :@RunWith(SpringRunner.class);
	declare @type: org.apilytic.currency..*IntegrationTest :@ContextConfiguration(locations =
			"classpath*:/META-INF/spring/applicationContext*.xml");

}