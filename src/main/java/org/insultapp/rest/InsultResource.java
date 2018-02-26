package org.insultapp.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.insultapp.generators.InsultGenerator;
import org.insultapp.generators.InsultGeneratorQualifier;
import org.insultapp.generators.InsultGeneratorType;

@Path("/")
public class InsultResource {

	@Inject
	@InsultGeneratorQualifier(type = InsultGeneratorType.INMEMORY)
	private InsultGenerator insultGenerator;

	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String insult() {
		return insultGenerator.insult();
	}

	@Path("/{name}")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String namedInsult(@PathParam("name") String name) {
		return insultGenerator.namedInsult(name);
	}
}
