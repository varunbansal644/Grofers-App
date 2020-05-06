package com.upgrad.Grofers.service.business;



import com.upgrad.Grofers.service.entity.AddressEntity;
import com.upgrad.Grofers.service.entity.CustomerAddressEntity;
import com.upgrad.Grofers.service.entity.CustomerEntity;
import com.upgrad.Grofers.service.entity.StateEntity;
import com.upgrad.Grofers.service.exception.AddressNotFoundException;
import com.upgrad.Grofers.service.exception.AuthorizationFailedException;
import com.upgrad.Grofers.service.exception.SaveAddressException;

import java.util.List;

public interface AddressService {

    AddressEntity saveAddress(AddressEntity addressEntity, CustomerAddressEntity customerAddressEntity) throws
            SaveAddressException;
    AddressEntity getAddressByUUID(String addressId, CustomerEntity customerEntity) throws AuthorizationFailedException, AddressNotFoundException;
    AddressEntity deleteAddress(AddressEntity addressEntity);
    List<AddressEntity> getAllAddress(CustomerEntity customer);
    StateEntity getStateByUUID(String uuid) throws AddressNotFoundException;
}
