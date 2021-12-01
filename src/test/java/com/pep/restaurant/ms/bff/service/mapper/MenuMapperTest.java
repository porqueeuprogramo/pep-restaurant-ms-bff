package com.pep.restaurant.ms.bff.service.mapper;

import com.pep.restaurant.ms.bff.domain.Menu;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.web.api.model.MenuDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class MenuMapperTest {

    @InjectMocks
    MenuMapper menuMapper;

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void passingAMenuList_checkThatMenuDTOListIsEquals() {
        //Given
        List<Menu> menuGivenList = Collections.singletonList(applicationDataProvider.getMenu());

        //When
        List<MenuDTO> menuDTOResultList = menuMapper.mapMenuListToMenuDTOList(menuGivenList);

        //Then
        Assertions.assertEquals(menuGivenList.get(0).getId(), menuDTOResultList.get(0).getId().longValue());
        Assertions.assertEquals(menuGivenList.get(0).getLanguage(), menuDTOResultList.get(0).getLanguage());

    }

    @Test
    public void passingAnMenuListNull_checkThatMenuDTOListIsNull() {
        Assertions.assertNull(menuMapper.mapMenuListToMenuDTOList(null));
    }

    @Test
    public void passingAMenu_checkThatMenuDTOIsEquals() {
        //Given
        Menu menuGiven = applicationDataProvider.getMenu();

        //When
        MenuDTO menuDTOResult = menuMapper.mapMenuToMenuDTO(menuGiven);

        //Then
        Assertions.assertEquals(menuGiven.getId(), menuDTOResult.getId().longValue());
        Assertions.assertEquals(menuGiven.getLanguage(), menuDTOResult.getLanguage());
    }

    @Test
    public void passingAnMenuNull_checkThatMenuDTOIsNull() {
        Assertions.assertNull(menuMapper.mapMenuToMenuDTO(null));
    }

    @Test
    public void passingAMenuDTO_checkThatMenuIsEquals() {
        //Given
        MenuDTO menuDTOGiven = applicationDataProvider.getMenuDTO();

        //When
        Menu menuResult = menuMapper.mapMenuDTOToMenu(menuDTOGiven);

        //Then
        Assertions.assertEquals(menuDTOGiven.getId().longValue(), menuResult.getId());
        Assertions.assertEquals(menuDTOGiven.getLanguage(), menuResult.getLanguage());
    }

    @Test
    public void passingAnMenuDTONull_checkThatMenuIsNull() {
        Assertions.assertNull(menuMapper.mapMenuDTOToMenu(null));
    }

    @Test
    public void passingAMenuDTOList_checkThatMenuListIsEquals() {
        //Given
        List<MenuDTO> menuGivenListDTO = Collections.singletonList(applicationDataProvider.getMenuDTO());

        //When
        List<Menu> menuResultList = menuMapper.mapMenuDTOListToMenuList(menuGivenListDTO);

        //Then
        Assertions.assertEquals(menuGivenListDTO.get(0).getId().longValue(), menuResultList.get(0).getId());
        Assertions.assertEquals(menuGivenListDTO.get(0).getLanguage(), menuResultList.get(0).getLanguage());

    }

    @Test
    public void passingAnMenuDTOListNull_checkThatMenuListIsNull() {
        Assertions.assertNull(menuMapper.mapMenuDTOListToMenuList(null));
    }

}
