package io.robusta.upload.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class WebServiceApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {

		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(WebService.class);
		s.add(DatabaseWebService.class);
		s.add(DropboxWebService.class);
		return s;
	}
}
