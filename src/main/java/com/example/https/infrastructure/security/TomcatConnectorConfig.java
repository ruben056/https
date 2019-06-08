package com.example.https.infrastructure.security;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConnectorConfig {

	@Bean
	public ServletWebServerFactory servletContainer() {
	    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
	        @Override
	        protected void postProcessContext(Context context) {
	            SecurityConstraint securityConstraint = new SecurityConstraint();
//	            securityConstraint.setUserConstraint("NONE");
	            securityConstraint.setUserConstraint("CONFIDENTIAL");
	            SecurityCollection collection = new SecurityCollection();
	            collection.addPattern("/*");
	            securityConstraint.addCollection(collection);
	            context.addConstraint(securityConstraint);
	        }
	    };
	    tomcat.addAdditionalTomcatConnectors(httpConnector());
	    return tomcat;
	}
	
	/**
	 * this connector will not redirect, because of the setSecure(true), even with security constraint CONFIDENTIAL
	 * This allows for http and https to exist on same app/server, without https being redirected
	 * 
	 * @return
	 */
//    private Connector httpConnector(){
//    	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(true);
//        return connector;
//    }

    /**
     * By default the http connector is configured to with a redirectport 443. 
     * Wether the redirect is executed depends on security constraints configured...    
     * In this case this connector is useless because our ssl runs on 8443 not on 443 so it will
     * redirect to port that is not configurd...
     * 
     * If however userconstraints NONE is used iso confidential, no redirect will be attempted
     * and http will work
     */
  private Connector httpConnector() {
	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setPort(8080);
    connector.setSecure(false);
    return connector;
}
    
    /**
     * In this connector we actually set the correct ssl port and http calls will be redirected
     * to a working https connector. if security constraint is CONFIDENTIAL
     */
//    private Connector redirectConnector() {
//    	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
}
