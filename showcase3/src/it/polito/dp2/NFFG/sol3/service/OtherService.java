/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.service;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author a.zanotti
 */
@Path("/other")
public class OtherService {
    
    //Resources - Nffgs
    @GET
    @ApiOperation(value = "get the list of the nffgs", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Produces(MediaType.APPLICATION_XML)
    @Path("/cachePolicy")
    public String getCachePolicy() {
        return "prova";
    }
    
}
