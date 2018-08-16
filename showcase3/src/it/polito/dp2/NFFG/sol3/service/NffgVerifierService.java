/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import it.polito.dp2.NFFG.sol3.service.util.NffgVerifierUtil;
import it.polito.dp2.NFFG.sol3.service.entities.NffgType;
import it.polito.dp2.NFFG.sol3.service.entities.NffgVerifierType;
import it.polito.dp2.NFFG.sol3.service.entities.NffgsType;
import it.polito.dp2.NFFG.sol3.service.entities.PoliciesType;
import it.polito.dp2.NFFG.sol3.service.entities.PolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.ReferenceByIDType;
import it.polito.dp2.NFFG.sol3.service.entities.VerificationResultsType;
import it.polito.dp2.NFFG.sol3.service.exception.AlreadyLoadedException;
import it.polito.dp2.NFFG.sol3.service.exception.ServiceException;
import it.polito.dp2.NFFG.sol3.service.exception.UnknownNameException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author a.zanotti
 */
@Path("/nffgVerifier")
@Api(value = "/nffgVerifier", description = "the nffgverifier")
public class NffgVerifierService {

    //Resources - All togheter
    @GET
    @ApiOperation(value = "get the nffgverifier", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Produces(MediaType.APPLICATION_XML)
    public NffgVerifierType getNffgVerifier() {

        NffgVerifierType nffgVerifier = NffgVerifierUtil.getNffgVerifier();
        return nffgVerifier;
    }

    //Resources - Nffgs
    @GET
    @ApiOperation(value = "get the list of the nffgs", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Produces(MediaType.APPLICATION_XML)
    @Path("/nffgs")
    public NffgsType getNffgs() {

        NffgsType nffgs = NffgVerifierUtil.getNffgs();
        return nffgs;
    }

    @POST
    @ApiOperation(value = "upload a list of nffgs", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/nffgs")
    public ReferenceByIDType uploadNffgs(NffgsType nffgs) throws ForbiddenException, InternalServerErrorException{
        try {
            ReferenceByIDType referencesByID = NffgVerifierUtil.uploadNffgs(nffgs);
            return referencesByID;
        } catch (AlreadyLoadedException ex) {
            throw new ForbiddenException("Forbidden because " + ex.getMessage());
        } catch (ServiceException ex) {
            throw new InternalServerErrorException("Upload failed because " + ex.getMessage());
        }
    }

    @DELETE
    @ApiOperation(value = "delete a list of nffgs", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/nffgs")
    public ReferenceByIDType deleteNffgs(
            @QueryParam("removeAssociatedPolicies") @DefaultValue("true") boolean removeAssociatedPolicies,
            ReferenceByIDType nffgs) {

        try {
            ReferenceByIDType deleteNffgs = NffgVerifierUtil.deleteNffgs(nffgs,removeAssociatedPolicies);
            return deleteNffgs;
        } catch (UnknownNameException ex) {
            throw new NotFoundException("Nffgs not found: " + ex.getMessage());
        } catch (ServiceException ex) {
            throw new InternalServerErrorException("Upload failed because " + ex.getMessage());
        }
        
    }

    @GET
    @ApiOperation(value = "get a single nffg", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Produces(MediaType.APPLICATION_XML)
    @Path("/nffgs/{id}")
    public NffgType getNffg(@PathParam("id") String id){

        try {
            NffgType nffg = NffgVerifierUtil.getNffg(id);
            return nffg;
        } catch (UnknownNameException ex) {
            throw new NotFoundException("Resource not found:" + ex.getMessage());
        }
    }

    //Resources - Policies
    @GET
    @ApiOperation(value = "get the policies", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Produces(MediaType.APPLICATION_XML)
    @Path("/policies")
    public PoliciesType getPolicies(@QueryParam("nffgName") String nffgName) {

        try {
            PoliciesType policies = NffgVerifierUtil.getPolicies(nffgName);
            return policies;
        } catch (UnknownNameException ex) {
            throw new NotFoundException("Resource not found:" + ex.getMessage());
        }
    }
    
    @GET
    @ApiOperation(value = "get the policies and the referenced nffgs", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Produces(MediaType.APPLICATION_XML)
    @Path("/policiesAndReferencedNffgs")
    public NffgVerifierType getPoliciesAndReferencedNffgs(@QueryParam("nffgName") String nffgName,
            @QueryParam("verificationTime") String verificationtime) {

        try {
            NffgVerifierType policiesAndNffgs = NffgVerifierUtil.getPoliciesAndNffgs(nffgName,verificationtime);
            return policiesAndNffgs;
        } catch (UnknownNameException ex) {
            throw new NotFoundException("Resource not found:" + ex.getMessage());
        } catch (DatatypeConfigurationException ex){
            throw new InternalServerErrorException("Internal server error " + ex.getMessage());
        }
    }

    @POST
    @ApiOperation(value = "upload a list of policies", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/policies")
    public ReferenceByIDType uploadPolicies(PoliciesType policies) {

        try {
            ReferenceByIDType referencesByID = NffgVerifierUtil.uploadPolicies(policies);
            return referencesByID;
        } catch(UnknownNameException ex){
            throw new ForbiddenException("Can not upload:" + ex.getMessage());
        }
    }

    @DELETE
    @ApiOperation(value = "delete a list of policies", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/policies")
    public ReferenceByIDType deletePolicies(ReferenceByIDType policies) {

        try {
            ReferenceByIDType deletePolicies = NffgVerifierUtil.deletePolicies(policies);
            return deletePolicies;
        } catch (UnknownNameException ex) {
            throw new NotFoundException("Policies not found: " + ex.getMessage());
        }
    }

    @GET
    @ApiOperation(value = "get a single policy", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Produces(MediaType.APPLICATION_XML)
    @Path("/policies/{id}")
    public PolicyType getPolicy(@PathParam("id") String id) {

        try {
            PolicyType policy = NffgVerifierUtil.getPolicy(id);
            return policy;
        } catch (UnknownNameException ex) {
            throw new NotFoundException("Resource not found:" + ex.getMessage());
        }
    }

    //Operations
    @POST
    @ApiOperation(value = "verify a list of stored policies", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/verifyStoredPolicies")
    public VerificationResultsType verifyStoredPolicies(ReferenceByIDType policies) {

        try {
            VerificationResultsType results = NffgVerifierUtil.verifyStoredPolicies(policies);
            return results;
        } catch (UnknownNameException ex) {
            throw new NotFoundException("Can not verify because " + ex.getMessage());
        } catch (ServiceException ex) {
            throw new InternalServerErrorException("Verification failed because " + ex.getMessage());
        }
    }

    @POST
    @ApiOperation(value = "verify a list of new policies", notes = "xml format")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/verifyNewPolicies")
    public VerificationResultsType verifyNewPolicies(PoliciesType policies){

        try {
            VerificationResultsType results = NffgVerifierUtil.verifyNewPolicies(policies);
            return results;
        } catch (UnknownNameException ex) {
            throw new NotFoundException("Can not verify because " + ex.getMessage());
        } catch (ServiceException ex) {
            throw new InternalServerErrorException("Verification failed because " + ex.getMessage());
        }
    }
}
