package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.AddressClient;
import com.pep.restaurant.ms.bff.domain.Address;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressClient addressClient;

    private ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingAnAddressId_checkIfGetAddressClientIsCalled() {
        //Given
        Address addressGiven = applicationDataProvider.getAddress();

        //When
        when(addressClient.getAddress(0L)).thenReturn(addressGiven);
        Address address = addressService.getAddress(0L);

        //Then
        verify(addressClient, Mockito.times(1)).getAddress(0L);
    }

    @Test
    public void requestingAnAddress_checkIfCreateAddressClientIsCalled() {
        //Given
        Address addressGiven = applicationDataProvider.getAddress();

        //When
        when(addressClient.createAddress(addressGiven)).thenReturn(addressGiven);
        Address address = addressService.createAddress(addressGiven);

        //Then
        verify(addressClient, Mockito.times(1)).createAddress(addressGiven);
    }

    @Test
    public void requestingAnAddressIdAndAnAddress_checkIfEditAddressClientIsCalled() {
        //Given
        Address addressGiven = applicationDataProvider.getAddress();

        //When
        when(addressClient.editAddress(0L, addressGiven)).thenReturn(addressGiven);
        Address address = addressService.editAddress(0L, addressGiven);

        //Then
        verify(addressClient, Mockito.times(1)).editAddress(0L, addressGiven);
    }

    @Test
    public void requestingAnAddressId_checkIfDeleteAddressClientIsCalled() {
        //Given
        Address addressGiven = applicationDataProvider.getAddress();

        //When
        when(addressClient.deleteAddress(0L)).thenReturn(addressGiven);
        Address address = addressService.deleteAddress(0L);

        //Then
        verify(addressClient, Mockito.times(1)).deleteAddress(0L);
    }

    @Test
    public void checkIfGetAllAddresssClientIsCalled() {
        //Given
        Address addressGiven = applicationDataProvider.getAddress();

        //When
        when(addressService.getAllAddresss()).thenReturn(Collections.singletonList(addressGiven));
        List<Address> addressList = addressService.getAllAddresss();

        //Then
        verify(addressClient, Mockito.times(1)).getAllAddresses();
    }
}
