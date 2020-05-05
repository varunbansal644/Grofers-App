package com.upgrad.Grofers.service.business;


import com.upgrad.Grofers.service.dao.CustomerDao;
import com.upgrad.Grofers.service.entity.CustomerAuthEntity;
import com.upgrad.Grofers.service.entity.CustomerEntity;
import com.upgrad.Grofers.service.exception.AuthenticationFailedException;
import com.upgrad.Grofers.service.exception.AuthorizationFailedException;
import com.upgrad.Grofers.service.exception.SignUpRestrictedException;
import com.upgrad.Grofers.service.exception.UpdateCustomerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    /**
     * The method implements the business logic for saving customer details endpoint.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) throws SignUpRestrictedException {
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(customerEntity.getEmail());
        return customerEntity;
    }

    /**
     * The method implements the business logic for signin endpoint.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticate(String contactNumber, String password) throws AuthenticationFailedException {
        boolean isDataAlreadyExists = false;
        CustomerAuthEntity customerAuthEntity = null;
        CustomerEntity customerEntity = customerDao.getCustomerByContactNumber(contactNumber);
        if (customerEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        }
        // encrypt password
        final String encryptedPassword = cryptographyProvider.encrypt(password, customerEntity.getSalt());

        // send phone and encrypted password to customerDao
        customerEntity = customerDao.authenticateUser(contactNumber, encryptedPassword);

        if (customerEntity != null) {// if userName and password match
            String uuid = customerEntity.getUuid();
            // Geneate authention token
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            CustomerAuthEntity = customerDao.getCustomerAuthEntityTokenByUuID(customerEntity.getUuid());
            if (CustomerAuthEntity != null) {
                isDataAlreadyExists = true;
            } else {
                CustomerAuthEntity = new CustomerAuthEntity();
            }
            CustomerAuthEntity.setUser(customerEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            CustomerAuthEntity
                    .setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt));
            CustomerAuthEntity.setLoginAt(now);
            CustomerAuthEntity.setExpiresAt(expiresAt);
            CustomerAuthEntity.setUuid(customerEntity.getUuid());
            CustomerAuthEntity.setLogoutAt(null);
            if (isDataAlreadyExists)
                customerDao.createAuthToken(CustomerAuthEntity);
            else
                customerDao.updateLoginInfo(CustomerAuthEntity);
        } else {
            throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
        }
        return CustomerAuthEntity;
    }

    /**
     * The method implements the business logic for logout endpoint.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity logout(String access_token) throws AuthorizationFailedException {

        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthByAccesstoken(access_token);
        authorization(access_token);
        customerAuthEntity.setLogoutAt(ZonedDateTime.now());
        return customerDao.updateCustomerAuth(customerAuthEntity);
    }

    /**
     * The method implements the business logic for updating customer password endpoint.
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String updateCustomerPassword(String oldPassword, String newPassword, String customerEntity) throws UpdateCustomerException {
        final String encryptedOldPassword = PasswordCryptographyProvider.encrypt(oldPassword, customerEntity.getSalt());
        return customerEntity;
    }


    /**
     * The method implements the business logic for checking authorization of any customer.
     */
    @Override
    public void authorization(String access_token) throws AuthorizationFailedException {

        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthByAccesstoken(access_token);

    }

    /**
     * The method implements the business logic for getting customer details by access token.
     */
    @Override
    public CustomerEntity getCustomer(String access_token) throws AuthorizationFailedException {

        authorization(access_token);
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthByAccesstoken(access_token);
        return customerAuthEntity.getCustomer();
    }
}
