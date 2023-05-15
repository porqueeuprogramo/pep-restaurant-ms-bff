package com.pep.restaurant.ms.bff.service.mapper;

import com.pep.restaurant.ms.bff.domain.Address;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.AddressDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class AddressMapperTest {

    @InjectMocks
    AddressMapper addressMapper;

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void passingAAddressList_checkThatAddressDTOListIsEquals() {
        //Given
        List<Address> addressGivenList = Collections.singletonList(applicationDataProvider.getAddress());

        //When
        List<AddressDTO> addressDTOResultList = addressMapper.mapAddressListToAddressDTOList(addressGivenList);

        //Then
        Assertions.assertEquals(addressGivenList.get(0).getName()
                , addressDTOResultList.get(0).getName());
        Assertions.assertEquals(addressGivenList.get(0).getCountry()
                , addressDTOResultList.get(0).getCountry());
        Assertions.assertEquals(addressGivenList.get(0).getCity()
                , addressDTOResultList.get(0).getCity());
        Assertions.assertEquals(addressGivenList.get(0).getPostalCode()
                , addressDTOResultList.get(0).getPostalCode());

    }

    @Test
    public void passingAnAddressListNull_checkThatAddressDTOListIsNull() {
        Assertions.assertNull(addressMapper.mapAddressListToAddressDTOList(null));
    }

    @Test
    public void passingAAddress_checkThatAddressDTOIsEquals() {
        //Given
        Address addressGiven = applicationDataProvider.getAddress();

        //When
        AddressDTO addressDTOResult = addressMapper.mapAddressToAddressDTO(addressGiven);

        //Then
        Assertions.assertEquals(addressGiven.getName()
                , addressDTOResult.getName());
        Assertions.assertEquals(addressGiven.getCountry()
                , addressDTOResult.getCountry());
        Assertions.assertEquals(addressGiven.getCity()
                , addressDTOResult.getCity());
        Assertions.assertEquals(addressGiven.getPostalCode()
                , addressDTOResult.getPostalCode());
    }

    @Test
    public void passingAnAddressNull_checkThatAddressDTOIsNull() {
        Assertions.assertNull(addressMapper.mapAddressToAddressDTO(null));
    }

    @Test
    public void passingAAddressDTO_checkThatAddressIsEquals() {
        //Given
        AddressDTO addressDTOGiven = applicationDataProvider.getAddressDTO();

        //When
        Address addressResult = addressMapper.mapAddressDTOToAddress(addressDTOGiven);

        //Then
        Assertions.assertEquals(addressDTOGiven.getName()
                , addressResult.getName());
        Assertions.assertEquals(addressDTOGiven.getCountry()
                , addressResult.getCountry());
        Assertions.assertEquals(addressDTOGiven.getCity()
                , addressResult.getCity());
        Assertions.assertEquals(addressDTOGiven.getPostalCode()
                , addressResult.getPostalCode());
    }

    @Test
    public void passingAnAddressDTONull_checkThatAddressIsNull() {
        Assertions.assertNull(addressMapper.mapAddressDTOToAddress(null));
    }

    @Test
    public void passingAAddressDTOList_checkThatAddressListIsEquals() {
        //Given
        List<AddressDTO> addressGivenListDTO = Collections.singletonList(applicationDataProvider.getAddressDTO());

        //When
        List<Address> addressResultList = addressMapper.mapAddressListDTOToAddressList(addressGivenListDTO);

        //Then
        Assertions.assertEquals(addressGivenListDTO.get(0).getName()
                , addressResultList.get(0).getName());
        Assertions.assertEquals(addressGivenListDTO.get(0).getCountry()
                , addressResultList.get(0).getCountry());
        Assertions.assertEquals(addressGivenListDTO.get(0).getCity()
                , addressResultList.get(0).getCity());
        Assertions.assertEquals(addressGivenListDTO.get(0).getPostalCode()
                , addressResultList.get(0).getPostalCode());

    }

    @Test
    public void passingAnAddressDTOListNull_checkThatAddressListIsNull() {
        Assertions.assertNull(addressMapper.mapAddressListDTOToAddressList(null));
    }

}
