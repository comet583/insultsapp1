package org.insultapp.rest;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
import javax.ws.rs.core.Response;

import org.insultapp.generators.InsultGenerator;
import org.insultapp.generators.InsultGeneratorQualifier;
import org.insultapp.generators.InsultGeneratorType;

@Path("/")
public class InsultResource {
	private static int _visits = 0;
	@Inject
	@InsultGeneratorQualifier(type = InsultGeneratorType.INMEMORY)
	private InsultGenerator insultGenerator;

	@Path("/health/check")
	@GET
	public Response healthCheck() {
		Date now= new Date();
		// toss an unavailable every 10 minutes
		if ( now.getMinutes() % 30 == 0 ) {
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
		}
		return Response.ok( 
				"OK at " + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(now),
				MediaType.TEXT_PLAIN).build();
	}

	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String insult() {
		return "You requested an insult @" + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date())
				+ insultGenerator.insult();
	}

	private static BufferedWriter _writer=null;
	private BufferedWriter getWriter() {
		if ( _writer == null ) {
			try {
				_writer= new BufferedWriter(new FileWriter("/mydata/lastvisit.txt"));
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		return _writer;
	}
	@Path("/html")
	@GET
	@Produces(value = MediaType.TEXT_HTML)
	public String insultHtml() {
		String buildTime = getBuildTime();
		String gitCommit= getGitProperties();
		String insult= insultGenerator.insult();
		_visits+= 1;
		recordInsult(_visits,insult);
		System.out.println("insult is " + insult);
		return "<html><body><h1>Insult " + _visits + "!</h1>" + "You requested an insult @"
				+ new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()) + "<hr/><br/><b>\""
				+ insult + "\"</b><br/><br/>" + "<i>last build: " + buildTime + "</i> "
				+ _gitCommitId + " : " + _gitCommitMessageShort
				+ "</body>";
	}

	private void recordInsult(int visit, String insult) {
		BufferedWriter writer= getWriter();
		if ( writer != null ) {
			try {
				writer.write(visit + ": " + insult + "\n");
				writer.flush();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	@Path("/{name}")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String namedInsult(@PathParam("name") String name) {
		return insultGenerator.namedInsult(name);
	}

	private String _buildTime = null;
	private String _pomVersion;

	private String getBuildTime() {
		if (_buildTime != null)
			return _buildTime;
		_buildTime = "n/a";
		ClassLoader classLoader = this.getClass().getClassLoader();
		Properties prop = new Properties();
		try {
			InputStream stream = classLoader.getResourceAsStream("build.properties");
			prop.load(stream);
			_buildTime = prop.getProperty("build.date");
			_pomVersion = prop.getProperty("version");
		} catch (Exception e) {
			e.printStackTrace();
			_buildTime = e.getMessage();
		}
		return _buildTime;
	}
	
	private String _gitCommitId= null;
	private String _gitCommitMessageShort= null;
	private String getGitProperties() {
		if (_gitCommitId != null)
			return _gitCommitId;
		_buildTime = "n/a";
		ClassLoader classLoader = this.getClass().getClassLoader();
		Properties prop = new Properties();
		try {
			InputStream stream = classLoader.getResourceAsStream("git.properties");
			prop.load(stream);
			_gitCommitId = prop.getProperty("git.commit.id");
			_gitCommitMessageShort = prop.getProperty("git.commit.message.short");
		} catch (Exception e) {
			e.printStackTrace();
			_gitCommitId = e.getMessage();
		}
		return _gitCommitId;
	}

}
