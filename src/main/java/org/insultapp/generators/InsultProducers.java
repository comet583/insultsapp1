package org.insultapp.generators;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class InsultProducers {

	@ApplicationScoped
	@Produces
	@InsultQualifier(type = Type.FIRST_ADJ)
	public List<String> firstAdjs() {
		return Arrays.asList("artless", "bawdy", "beslubbering", "bootless", "churlish");
	}

	@ApplicationScoped
	@Produces
	@InsultQualifier(type = Type.SECOND_ADJ)
	public List<String> secondAdjs() {
		return Arrays.asList("base-court", "bat-fowling", "beef-witted", "beetle-headed", "boil-brained");
	}

	@ApplicationScoped
	@Produces
	@InsultQualifier(type = Type.NOUN)
	public List<String> nouns() {
		return Arrays.asList("apple-john", "baggage", "barnacle", "bladder", "boar-pig");
	}
	
}
