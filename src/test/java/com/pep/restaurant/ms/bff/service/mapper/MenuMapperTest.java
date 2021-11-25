package com.pep.restaurant.ms.bff.service.mapper;

import com.pep.restaurant.ms.bff.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.domain.Menu;
import com.pep.restaurant.ms.bff.web.api.model.MenuDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
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
        Assert.assertEquals(menuGivenList.get(0).getId(), menuDTOResultList.get(0).getId().longValue());
        Assert.assertEquals(menuGivenList.get(0).getLanguage(), menuDTOResultList.get(0).getLanguage());

    }

    @Test
    public void passingAnMenuListNull_checkThatMenuDTOListIsNull() {
        Assert.assertNull(menuMapper.mapMenuListToMenuDTOList(null));
    }

    @Test
    public void passingAMenu_checkThatMenuDTOIsEquals() {
        //Given
        Menu menuGiven = applicationDataProvider.getMenu();

        //When
        MenuDTO menuDTOResult = menuMapper.mapMenuToMenuDTO(menuGiven);

        //Then
        Assert.assertEquals(menuGiven.getId(), menuDTOResult.getId().longValue());
        Assert.assertEquals(menuGiven.getLanguage(), menuDTOResult.getLanguage());
    }

    @Test
    public void passingAnMenuNull_checkThatMenuDTOIsNull() {
        Assert.assertNull(menuMapper.mapMenuToMenuDTO(null));
    }

    @Test
    public void passingAMenuDTO_checkThatMenuIsEquals() {
        //Given
        MenuDTO menuDTOGiven = applicationDataProvider.getMenuDTO();

        //When
        Menu menuResult = menuMapper.mapMenuDTOToMenu(menuDTOGiven);

        //Then
        Assert.assertEquals(menuDTOGiven.getId().longValue(), menuResult.getId());
        Assert.assertEquals(menuDTOGiven.getLanguage(), menuResult.getLanguage());
    }

    @Test
    public void passingAnMenuDTONull_checkThatMenuIsNull() {
        Assert.assertNull(menuMapper.mapMenuDTOToMenu(null));
    }

    @Test
    public void passingAMenuDTOList_checkThatMenuListIsEquals() {
        //Given
        List<MenuDTO> menuGivenListDTO = Collections.singletonList(applicationDataProvider.getMenuDTO());

        //When
        List<Menu> menuResultList = menuMapper.mapMenuDTOListToMenuList(menuGivenListDTO);

        //Then
        Assert.assertEquals(menuGivenListDTO.get(0).getId().longValue(), menuResultList.get(0).getId());
        Assert.assertEquals(menuGivenListDTO.get(0).getLanguage(), menuResultList.get(0).getLanguage());

    }

    @Test
    public void passingAnMenuDTOListNull_checkThatMenuListIsNull() {
        Assert.assertNull(menuMapper.mapMenuDTOListToMenuList(null));
    }

}
