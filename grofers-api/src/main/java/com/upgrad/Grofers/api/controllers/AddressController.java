package com.upgrad.Grofers.api.controllers;

import com.upgrad.Grofers.api.*;
import com.upgrad.Grofers.api.util.Validators;
import com.upgrad.Grofers.service.business.AddressService;
import com.upgrad.Grofers.service.business.CustomerService;
import com.upgrad.Grofers.service.entity.AddressEntity;
import com.upgrad.Grofers.service.entity.CustomerEntity;
import com.upgrad.Grofers.service.exception.AddressNotFoundException;
import com.upgrad.Grofers.service.exception.AuthorizationFailedException;
import com.upgrad.Grofers.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public class AddressController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST,value = "/address", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> postAddress(@RequestHeader("authorization") final String accessToken, @RequestBody SaveAddressRequest saveAddressRequest) throws SaveAddressException, AuthorizationFailedException, UpdateCustomerException {

        String bearerToken = null;
        try {
            bearerToken = accessToken.split("Bearer ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            bearerToken = accessToken;
        }

        if(Validators.checkForEmptyEntityField(saveAddressRequest)) {
            throw new SaveAddressException("SAR-001", "No field can be empty");
        }

        AddressEntity newAddress = new AddressEntity();
        newAddress.setCity(saveAddressRequest.getCity());
        newAddress.setFlatBuilNo(saveAddressRequest.getFlatBuildingName());
        newAddress.setLocality(saveAddressRequest.getLocality());
        newAddress.setPincode(saveAddressRequest.getPincode());
        newAddress.setActive(1);
        newAddress.setUuid(UUID.randomUUID().toString());

        String stateUuid = saveAddressRequest.getState();

        AddressEntity savedAddress = addressService.saveAddress(newAddress, stateUuid, bearerToken);

        CustomerEntity customerEntity = customerService.getCustomerByToken(bearerToken);

        addressService.CustomerAddressEntity(customerEntity, savedAddress);
        return new ResponseEntity<SaveAddressResponse>(
                new SaveAddressResponse().id(savedAddress.getUuid()).
                        status("ADDRESS SUCCESSFULLY REGISTERED"),
                HttpStatus.CREATED);

    }

    @CrossOrigin
    @GetMapping(value="/address/customer",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<AddressListResponse> retrieveAllAddressForUser(
            @RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException{

        String bearerToken = null;
        try {
            bearerToken = accessToken.split("Bearer ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            bearerToken = accessToken;
        }

        CustomerEntity customerEntity = customerService.getCustomerByToken(bearerToken);
        AddressListResponse response = new AddressListResponse();
        for(AddressEntity addressEntity : customerEntity.getAddress()){
            response.addAddressesItem(new AddressList().
                    id(UUID.fromString(addressEntity.getUuid())).
                    city(addressEntity.getCity()).
                    flatBuildingName(addressEntity.getFlatBuilNo()).
                    locality(addressEntity.getLocality()).
                    city(addressEntity.getCity()).
                    pincode(addressEntity.getPincode()).
                    state(new AddressListState().
                            stateName(addressEntity.getState().getStateByUuid()).
                            id(UUID.fromString(addressEntity.getStateByUuid().getUuid()))));
        }

        return new ResponseEntity<AddressListResponse>(response,HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value="/address/{address_id}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<DeleteAddressResponse> deleteAddressById(
            @RequestHeader("authorization") final String accessToken,
            @PathVariable final String address_id) throws AddressNotFoundException,
            AuthorizationFailedException {

        String bearerToken = null;
        try {
            bearerToken = accessToken.split("Bearer ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            bearerToken = accessToken;
        }

        CustomerEntity customerEntity = customerService.getCustomerByToken(bearerToken);
        boolean match = false;
        AddressEntity addressEntity = (AddressEntity) addressService.getAllAddress(address_id);

        if(address_id.isEmpty())
            throw new AddressNotFoundException("ANF-005",
                    "Address id can not be empty");

        if(addressEntity==null)
            throw new AddressNotFoundException("ANF-003",
                    "No address by this id");

        if(!match)
            throw new AddressNotFoundException("ATHR-004",
                    "You are not authorized to view/update/delete any one else's address");

        AddressEntity deletedEntity = addressService.deleteAddress(address_id);

        return new ResponseEntity<DeleteAddressResponse>(
                new DeleteAddressResponse().id(UUID.fromString(deletedEntity.getUuid())).
                        status("ADDRESS DELETED SUCCESSFULLY"),
                HttpStatus.OK);    }
}
