package com.rest.core;

import com.sun.jersey.api.core.PackagesResourceConfig;
import javax.ws.rs.ApplicationPath;

/**
 *
 * @author Zbyszko
 */
@ApplicationPath("/rest")
public class RestApp extends PackagesResourceConfig {
    public RestApp(){
        super("com.rest.service");
    }
}