package com.upgrad.Grofers.service.business;

import com.upgrad.Grofers.service.dao.AddressDao;
import com.upgrad.Grofers.service.entity.AddressEntity;
import com.upgrad.Grofers.service.entity.CustomerAddressEntity;
import com.upgrad.Grofers.service.entity.CustomerEntity;
import com.upgrad.Grofers.service.entity.StateEntity;
import com.upgrad.Grofers.service.exception.AddressNotFoundException;
import com.upgrad.Grofers.service.exception.AuthorizationFailedException;
import com.upgrad.Grofers.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;

	@Override @Transactional(propagation = Propagation.REQUIRED) public AddressEntity saveAddress(AddressEntity addressEntity, CustomerAddressEntity customerAddressEntity) throws SaveAddressException {
		return addressEntity;
	}


	@Override public AddressEntity getAddressByUUID(String addressId, CustomerEntity customerEntity) throws AuthorizationFailedException, AddressNotFoundException {
		return AddressDao.getAddress();
	}


	@Transactional(propagation = Propagation.REQUIRED) public CustomerAddressEntity saveCustomerAddress(CustomerAddressEntity customerAddressEntity) {
		return customerAddressEntity;
	}


	@Override @Transactional(propagation = Propagation.REQUIRED) public AddressEntity deleteAddress(String addressEntity) {
		return deleteAddress(addressEntity.getUuid());
	}

	@Override public List<AddressEntity> getAllAddress(CustomerEntity customer) {
		return CustomerEntity.getAddress();
	}

	@Override public StateEntity getStateByUUID(String uuid) throws AddressNotFoundException {
		return null;
	}


}
