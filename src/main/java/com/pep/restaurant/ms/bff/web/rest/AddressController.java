package com.pep.restaurant.ms.bff.web.rest;

import com.pep.restaurant.ms.bff.service.AddressService;
import com.pep.restaurant.ms.bff.service.mapper.AddressMapper;
import com.pep.restaurant.ms.bff.web.api.AddressApi;
import com.pep.restaurant.ms.bff.web.api.model.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
public class AddressController implements AddressApi, ApiController {

    public static final String ADDRESS_ADDRESS_ID = "/address/{addressId}";
    public static final String ADDRESS = "/address";
    private final AddressService addressService;
    private final AddressMapper addressMapper;

    /**
     * Constructor for Address Controller.
     *
     * @param addressService Address Service.
     * @param addressMapper  Address Mapper.
     */
    public AddressController(final AddressService addressService, final AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    /**
     * Controller to get a address by id.
     *
     * @param addressId id of address to get.
     * @return AddressDTO with the provided id.
     */

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = ADDRESS_ADDRESS_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<AddressDTO> getAddress(final Long addressId) {
        return ResponseEntity.ok(addressMapper.mapAddressToAddressDTO(
                addressService.getAddress(addressId)));
    }

    /**
     * Controller to create a address.
     *
     * @param addressDTO addressDTO to create.
     * @return AddressDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(value = ADDRESS,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<AddressDTO> createAddress(@RequestBody final AddressDTO addressDTO) {
        return ResponseEntity.ok(addressMapper.mapAddressToAddressDTO(
                addressService.createAddress(addressMapper.mapAddressDTOToAddress(addressDTO))));
    }

    /**
     * Controller to edit a address.
     *
     * @param addressId address to be edited.
     * @param addressDTO addressDTO new data.
     * @return AddressDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = ADDRESS_ADDRESS_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<AddressDTO> editAddress(final Long addressId,
                                                        @RequestBody final AddressDTO addressDTO) {
        return ResponseEntity.ok(addressMapper.mapAddressToAddressDTO(
                addressService.editAddress(addressId,
                        addressMapper.mapAddressDTOToAddress(addressDTO))));

    }

    /**
     * Controller to delete address by id.
     * @param addressId address Id.
     * @return Address deleted.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(value = ADDRESS_ADDRESS_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<AddressDTO> deleteAddress(final Long addressId) {
        return ResponseEntity.ok(addressMapper.mapAddressToAddressDTO(
                addressService.deleteAddress(addressId)));
    }

    /**
     * Controller to Get all addresss.
     * @return Addresss List.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = ADDRESS,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressMapper.mapAddressListToAddressDTOList(
                addressService.getAllAddresss()));
    }
}
