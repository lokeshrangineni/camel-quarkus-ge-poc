package org.acme.rest.json;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // create a route template with the given name
        routeTemplate("myTemplate")
                // here we define the required input parameters (can have default values)
                .templateParameter("name").templateParameter("greeting").templateParameter("myPeriod", "3s")
                .from("timer:{{name}}?period={{myPeriod}}").setBody(simple("{{greeting}} ${body}")).log("${body}");
    }
}