package org.insultapp.rest;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

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
	private static int _visits = 0;
	@Inject
	@InsultGeneratorQualifier(type = InsultGeneratorType.INMEMORY)
	private InsultGenerator insultGenerator;

	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String insult() {
		return "You requested an insult @"
				+ new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date())
				+ insultGenerator.insult();
	}

	@Path("/html")
	@GET
	@Produces(value = MediaType.TEXT_HTML)
	public String insultHtml() {
		String buildTime= getBuildTime();
		return "<html><body><h1>Insult " + ++_visits + "!</h1>"
				+ "You requested an insult @"
				+ new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date())
				+"<hr/><b>"
				+ insultGenerator.insult()
				+ "</b><br/>"
				+ "<i>" + buildTime + "</i>"
				+ "</body>";
	}

	@Path("/{name}")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String namedInsult(@PathParam("name") String name) {
		return insultGenerator.namedInsult(name);
	}
	
	private String getBuildTime() {
		String buildTime= "n/a";
	ClassLoader classLoader = this.getClass().getClassLoader();
	Properties prop = new Properties();
	try {
		InputStream stream= classLoader.getResourceAsStream("build.properties"); 
	    prop.load(stream);
	    buildTime = prop.getProperty("build.date");
	    String pomVersion = prop.getProperty("version");
	} catch (Exception e) {
	    e.printStackTrace();
	    buildTime= e.getMessage();
	}
	return buildTime;
	}
	}
