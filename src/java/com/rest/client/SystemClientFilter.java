/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.client;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import java.util.ArrayList;

/**
 *
 * @author Atuan
 */
public class SystemClientFilter extends ClientFilter {
    
    @Override
    public ClientResponse handle(ClientRequest req) throws ClientHandlerException {
	req.getHeaders().put("Cookies", new ArrayList());
		
        ClientResponse resp = getNext().handle(req);
	return resp;
    }
    
}
