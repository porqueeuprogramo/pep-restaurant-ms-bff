package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.MenuClient;
import com.pep.restaurant.ms.bff.domain.Menu;
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
public class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuClient menuClient;

    private ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingAnMenuId_checkIfGetMenuClientIsCalled() {
        //Given
        Menu menuGiven = applicationDataProvider.getMenu();

        //When
        when(menuClient.getMenu(0L)).thenReturn(menuGiven);
        Menu menu = menuService.getMenu(0L);

        //Then
        verify(menuClient, Mockito.times(1)).getMenu(0L);
    }

    @Test
    public void requestingAnMenu_checkIfCreateMenuClientIsCalled() {
        //Given
        Menu menuGiven = applicationDataProvider.getMenu();

        //When
        when(menuClient.createMenu(menuGiven)).thenReturn(menuGiven);
        Menu menu = menuService.createMenu(menuGiven);

        //Then
        verify(menuClient, Mockito.times(1)).createMenu(menuGiven);
    }

    @Test
    public void requestingAnMenuIdAndAnMenu_checkIfEditMenuClientIsCalled() {
        //Given
        Menu menuGiven = applicationDataProvider.getMenu();

        //When
        when(menuClient.editMenu(0L, menuGiven)).thenReturn(menuGiven);
        Menu menu = menuService.editMenu(0L, menuGiven);

        //Then
        verify(menuClient, Mockito.times(1)).editMenu(0L, menuGiven);
    }

    @Test
    public void requestingAnMenuId_checkIfDeleteMenuClientIsCalled() {
        //Given
        Menu menuGiven = applicationDataProvider.getMenu();

        //When
        when(menuClient.deleteMenu(0L)).thenReturn(menuGiven);
        Menu menu = menuService.deleteMenu(0L);

        //Then
        verify(menuClient, Mockito.times(1)).deleteMenu(0L);
    }

    @Test
    public void checkIfGetAllMenusClientIsCalled() {
        //Given
        Menu menuGiven = applicationDataProvider.getMenu();

        //When
        when(menuService.getAllMenus()).thenReturn(Collections.singletonList(menuGiven));
        List<Menu> menuList = menuService.getAllMenus();

        //Then
        verify(menuClient, Mockito.times(1)).getAllMenus();
    }
}
