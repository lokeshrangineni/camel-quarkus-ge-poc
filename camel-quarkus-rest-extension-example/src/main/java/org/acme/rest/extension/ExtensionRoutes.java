/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme.rest.extension;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.acme.rest.json.Fruit;
import org.acme.rest.json.Legume;
import org.acme.rest.json.MyRouteTemplates;
import org.acme.rest.json.Routes;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * Camel route definitions.
 */
public class ExtensionRoutes extends RouteBuilder {
    private final Set<Fruit> fruitsExtension = Collections.synchronizedSet(new LinkedHashSet<>());
    private final Set<Legume> legumesExtension = Collections.synchronizedSet(new LinkedHashSet<>());

    public ExtensionRoutes() {

        /* Let's add some initial fruits */
        this.fruitsExtension.add(new Fruit("Apple_ext", "Winter fruit"));
        this.fruitsExtension.add(new Fruit("Pineapple_ext", "Tropical fruit"));

        /* Let's add some initial legumes */
        this.legumesExtension.add(new Legume("Carrot_ext", "Root vegetable, usually orange"));
        this.legumesExtension.add(new Legume("Zucchini_ext", "Summer squash"));
    }

    @Override
    public void configure() throws Exception {

        restConfiguration().bindingMode(RestBindingMode.json);

        CamelContext context = getContext();
        context.addRoutes(new Routes());
        context.addRoutes(new MyRouteTemplates());

        rest("/fruitsExt").get().to("direct:getFruitsExt")

                .post().type(Fruit.class).to("direct:addFruitExt");

        rest("/legumesExt").get().to("direct:getLegumesExt");

        from("direct:getFruitsExt").setBody().constant(fruitsExtension);

        from("direct:addFruitExt").process().body(Fruit.class, fruitsExtension::add).setBody()
                .constant(fruitsExtension);

        from("direct:getLegumesExt").setBody().constant(legumesExtension);

        templatedRoute("myTemplate").parameter("name", "two").parameter("greeting", "Hello from route template")
                .parameter("myPeriod", "5s");
    }
}
