package com.pep.restaurant.ms.bff.client;

import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Address;
import com.pep.restaurant.ms.bff.logging.Logger;
import com.pep.restaurant.ms.bff.logging.enumeration.LogTag;
import com.pep.restaurant.ms.bff.service.mapper.AddressMapper;
import com.pep.restaurant.ms.bff.web.api.model.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class AddressClient {

    private static final Logger LOGGER = new Logger(AddressClient.class);
    public static final String ADDRESS_ADDRESS_ID = "/api/address/{addressId}";
    public static final String ADDRESS = "/api/address";
    private final RestTemplate restTemplate;
    private final AddressMapper addressMapper;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public AddressClient(final RestTemplate restTemplate,
                         final AddressMapper addressMapper,
                         final ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.addressMapper = addressMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Client to get Address by id.
     * @param addressId address id.
     * @return address.
     */
    public Address getAddress(final long addressId){
        final String url =  applicationProperties.getMsManager().getUrl().concat(ADDRESS_ADDRESS_ID);
        final String correlationId = UUID.randomUUID().toString();
        AddressDTO addressDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetAddressId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("addressId", String.valueOf(addressId));

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.RETRIEVED),
                "Get Address by id: " + addressId);

        try{
            final ResponseEntity<AddressDTO> responseAddress = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetAddressId,
                    new ParameterizedTypeReference<AddressDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseAddress.getStatusCode())){
                addressDTO = responseAddress.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.RETRIEVED),
                    "Address id not found");
        }

        return addressMapper.mapAddressDTOToAddress(addressDTO);
    }

    /**
     * Client to create address.
     * @param address address.
     * @return address.
     */
    public Address createAddress(final Address address){
        final String url =  applicationProperties.getMsManager().getUrl().concat(ADDRESS);
        final String correlationId = UUID.randomUUID().toString();
        AddressDTO addressDTO = null;

        final AddressDTO requestAddressDTO = addressMapper.mapAddressToAddressDTO(address);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity<AddressDTO> requestCreateAddressClient = new HttpEntity(requestAddressDTO ,headers);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.PERSISTED),
                "Create Address: " + address.toString());

        try{
            final ResponseEntity<AddressDTO> responseAddressCreated = restTemplate.exchange(url, HttpMethod.POST,
                    requestCreateAddressClient, AddressDTO.class);
            if(HttpStatus.OK.equals(responseAddressCreated.getStatusCode())){
                addressDTO = responseAddressCreated.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.PERSISTED)
                    , "Address was not created!!");
        }

        return addressMapper.mapAddressDTOToAddress(addressDTO);
    }

    /**
     * Client to edit address.
     * @param addressId address id.
     * @param address address.
     * @return address.
     */
    public Address editAddress(final long addressId, final Address address){
        final String url =  applicationProperties.getMsManager().getUrl().concat(ADDRESS_ADDRESS_ID);
        final String correlationId = UUID.randomUUID().toString();
        AddressDTO addressDTO = null;

        final AddressDTO requestAddressDTO = addressMapper.mapAddressToAddressDTO(address);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity<AddressDTO> requestEditAddressClient = new HttpEntity(requestAddressDTO ,headers);
        final Map<String, String> params = new HashMap<>();
        params.put("addressId", String.valueOf(addressId));

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.EDITED),
                "Edit Address by id " + addressId);

        try{
            final ResponseEntity<AddressDTO> responseAddressEdited = restTemplate.exchange(url, HttpMethod.PUT,
                    requestEditAddressClient, AddressDTO.class, params);
            if(HttpStatus.OK.equals(responseAddressEdited.getStatusCode())){
                addressDTO = responseAddressEdited.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.EDITED)
                    , "Address was not edited!!");
        }

        return addressMapper.mapAddressDTOToAddress(addressDTO);
    }

    /**
     * Client to delete address.
     * @param addressId address id.
     * @return address deleted.
     */
    public Address deleteAddress(final Long addressId) {
        final String url =  applicationProperties.getMsManager().getUrl().concat(ADDRESS_ADDRESS_ID);
        final String correlationId = UUID.randomUUID().toString();
        AddressDTO addressDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetAddressId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("addressId", String.valueOf(addressId));

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.DELETED),
                "Delete Address by id: " + addressId);

        try{
            final ResponseEntity<AddressDTO> responseAddress = restTemplate.exchange(url, HttpMethod.DELETE,
                    requestGetAddressId,
                    new ParameterizedTypeReference<AddressDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseAddress.getStatusCode())){
                addressDTO = responseAddress.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.DELETED),
                    "Address id not found");
        }

        return addressMapper.mapAddressDTOToAddress(addressDTO);
    }

    /**
     * Client to get all addresss.
     * @return addresss list.
     */
    public List<Address> getAllAddresses() {
        final String url =  applicationProperties.getMsManager().getUrl().concat(ADDRESS);
        final String correlationId = UUID.randomUUID().toString();
        List<AddressDTO> addressDTOList = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetAddressId = new HttpEntity(headers);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.RETRIEVED),
                "Get All Addresss list");

        try{
            final ResponseEntity<List<AddressDTO>> responseAddress = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetAddressId,
                    new ParameterizedTypeReference<List<AddressDTO>>() {
                    });
            if(HttpStatus.OK.equals(responseAddress.getStatusCode())){
                addressDTOList = responseAddress.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.ADDRESSES, LogTag.RETRIEVED),
                    "Address list not found");
        }

        return addressMapper.mapAddressListDTOToAddressList(addressDTOList);
    }
}
