package org.insultapp.generators;

import java.util.List;

import javax.inject.Inject;

import org.insultapp.utils.RandomNumber;

@InsultGeneratorQualifier(type = InsultGeneratorType.INMEMORY)
public class InmemoryInsultGenerator implements InsultGenerator {

	@Inject
	@InsultQualifier(type = Type.FIRST_ADJ)
	private List<String> firstAdjs;

	@Inject
	@InsultQualifier(type = Type.SECOND_ADJ)
	private List<String> secondAdjs;

	@Inject
	@InsultQualifier(type = Type.NOUN)
	private List<String> nouns;

	@Override
	public String insult() {
		return "Thou " + generateResult() + "!";
	}

	@Override
	public String namedInsult(String name) {
		return name + ", thou " + generateResult() + "!";
	}

	private String generateResult() {
		int number = RandomNumber.randInt(0, firstAdjs.size() - 1);
		return firstAdjs.get(number) + " " + secondAdjs.get(number) + " "
				+ nouns.get(number);
	}
}
