package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.AddressClient;
import com.pep.restaurant.ms.bff.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressClient addressClient;

    @Autowired
    public AddressService(final AddressClient addressClient) {
        this.addressClient = addressClient;
    }

    /**
     * Call get address client.
     * @param addressId address id.
     * @return address.
     */
    public Address getAddress(final long addressId) {
        return addressClient.getAddress(addressId);
    }

    /**
     * Call create address client.
     * @param address address to create.
     * @return address.
     */
    public Address createAddress(final Address address) {
        return addressClient.createAddress(address);
    }

    /**
     * Call edit address client.
     * @param addressId address id to edit.
     * @param address address to be edited
     * @return address edited.
     */
    public Address editAddress(final Long addressId, final Address address) {
        return addressClient.editAddress(addressId, address);
    }

    /**
     * Call remove address.
     * @param addressId address id.
     * @return Address Deleted.
     */
    public Address deleteAddress(final Long addressId) {
        return addressClient.deleteAddress(addressId);
    }

    /**
     * Call Get all addresss.
     * @return Address List.
     */
    public List<Address> getAllAddresss() {
        return addressClient.getAllAddresses();
    }

}
