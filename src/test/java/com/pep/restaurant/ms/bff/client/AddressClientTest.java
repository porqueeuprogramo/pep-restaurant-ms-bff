package com.pep.restaurant.ms.bff.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Address;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.service.mapper.AddressMapper;
import com.pep.restaurant.ms.bff.web.api.model.AddressDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AddressClientTest {

    @InjectMocks
    private AddressClient addressClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private AddressMapper addressMapper;

    private ApplicationProperties.MsManager msManagerProperty = new ApplicationProperties.MsManager();

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingAAddressIdToGet_checkAddressResult() {

        //Given
        Address address = applicationDataProvider.getAddress();
        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(addressDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenReturn(responseEntity);
        when(addressMapper.mapAddressDTOToAddress(addressDTO)).thenReturn(address);
        Address addressResult = addressClient.getAddress(1L);

        //Then
        Assertions.assertEquals(address.getId(), addressResult.getId());
        Assertions.assertEquals(address.getPostalCode(), addressResult.getPostalCode());
        Assertions.assertEquals(address.getCountry(), addressResult.getCountry());
        Assertions.assertEquals(address.getName(), addressResult.getName());
        Assertions.assertEquals(address.getCity(), addressResult.getCity());

    }

    @Test
    public void requestingAnAddressIdToGet_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(AddressClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenThrow(RestClientException.class);
        Address addressResult = addressClient.getAddress(1L);

        //Then
        Assertions.assertEquals("[CLIENT, ADDRESSES, RETRIEVED] Address id not found",
                logsList.get(1).getMessage());
    }

    @Test
    public void requestingAnAddressToCreate_checkAddressResult() {

        //Given
        Address address = applicationDataProvider.getAddress();
        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(addressDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<AddressDTO>) any())).thenReturn(responseEntity);
        when(addressMapper.mapAddressDTOToAddress(addressDTO)).thenReturn(address);
        Address addressResult = addressClient.createAddress(address);

        //Then
        Assertions.assertEquals(address.getId(), addressResult.getId());
        Assertions.assertEquals(address.getPostalCode(), addressResult.getPostalCode());
        Assertions.assertEquals(address.getCountry(), addressResult.getCountry());
        Assertions.assertEquals(address.getName(), addressResult.getName());
        Assertions.assertEquals(address.getCity(), addressResult.getCity());
    }

    @Test
    public void requestingAnAddressToCreate_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(AddressClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Address address = applicationDataProvider.getAddress();
        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<AddressDTO>) any())).thenThrow(RestClientException.class);
        Address addressResult = addressClient.createAddress(address);

        //Then
        Assertions.assertEquals("[CLIENT, ADDRESSES, PERSISTED] Address was not created!!",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingAnAddressIdToEditAndAnAddressEdited_checkAddressResult() {

        //Given
        Address address = applicationDataProvider.getAddress();
        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(addressDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<AddressDTO>) any(), anyMap())).thenReturn(responseEntity);
        when(addressMapper.mapAddressDTOToAddress(addressDTO)).thenReturn(address);
        Address addressResult = addressClient.editAddress(1L, address);

        //Then
        Assertions.assertEquals(address.getId(), addressResult.getId());
        Assertions.assertEquals(address.getPostalCode(), addressResult.getPostalCode());
        Assertions.assertEquals(address.getCountry(), addressResult.getCountry());
        Assertions.assertEquals(address.getName(), addressResult.getName());
        Assertions.assertEquals(address.getCity(), addressResult.getCity());
    }
    

    @Test
    public void requestingAAddressIdToEditAndAAddressEdited_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(AddressClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Address address = applicationDataProvider.getAddress();
        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<AddressDTO>) any(), anyMap())).thenThrow(RestClientException.class);
        Address addressResult = addressClient.editAddress(1L, address);

        //Then
        Assertions.assertEquals("[CLIENT, ADDRESSES, EDITED] Address was not edited!!",
                logsList.get(1).getMessage());
    }

    @Test
    public void requestingAnAddressIdToDelete_checkAddressResult() {

        //Given
        Address address = applicationDataProvider.getAddress();
        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        ResponseEntity responseEntity = new ResponseEntity<>(addressDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(addressMapper.mapAddressDTOToAddress(addressDTO)).thenReturn(address);
        Address addressResult = addressClient.deleteAddress(1L);

        //Then
        Assertions.assertEquals(address.getId(), addressResult.getId());
        Assertions.assertEquals(address.getPostalCode(), addressResult.getPostalCode());
        Assertions.assertEquals(address.getCountry(), addressResult.getCountry());
        Assertions.assertEquals(address.getName(), addressResult.getName());
        Assertions.assertEquals(address.getCity(), addressResult.getCity());

    }

    @Test
    public void requestingAAddressIdToDelete_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(AddressClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Address addressResult = addressClient.deleteAddress(1L);

        //Then
        Assertions.assertEquals("[CLIENT, ADDRESSES, DELETED] Address id not found",
                logsList.get(1).getMessage());

    }

    @Test
    public void checkAddressesResultList() {

        //Given
        Address address = applicationDataProvider.getAddress();
        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(addressDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(responseEntity);

        when(addressMapper.mapAddressListDTOToAddressList(
                Collections.singletonList(addressDTO))).thenReturn(Collections.singletonList(address));

        List<Address> addressResult = addressClient.getAllAddresses();

        //Then
        Assertions.assertEquals(address.getId(), addressResult.get(0).getId());
        Assertions.assertEquals(address.getPostalCode(), addressResult.get(0).getPostalCode());
        Assertions.assertEquals(address.getCountry(), addressResult.get(0).getCountry());
        Assertions.assertEquals(address.getName(), addressResult.get(0).getName());
        Assertions.assertEquals(address.getCity(), addressResult.get(0).getCity());

    }

    @Test
    public void checkLogThrownWhenGettingTheAddressResultList() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(AddressClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        AddressDTO addressDTO = applicationDataProvider.getAddressDTO();

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);
        List<Address> addressResult = addressClient.getAllAddresses();

        //Then
        Assertions.assertEquals("[CLIENT, ADDRESSES, RETRIEVED] Address list not found",
                logsList.get(1).getMessage());

    }

}
