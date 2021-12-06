package com.pep.restaurant.ms.bff.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Menu;
import com.pep.restaurant.ms.bff.provider.ApplicationDataProvider;
import com.pep.restaurant.ms.bff.service.mapper.MenuMapper;
import com.pep.restaurant.ms.bff.web.api.model.MenuDTO;
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
public class MenuClientTest {

    @InjectMocks
    private MenuClient menuClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private MenuMapper menuMapper;

    private ApplicationProperties.MsManager msManagerProperty = new ApplicationProperties.MsManager();

    ApplicationDataProvider applicationDataProvider = new ApplicationDataProvider();

    @Test
    public void requestingAnMenuIdToGet_checkMenuResult() {

        //Given
        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(menuDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenReturn(responseEntity);
        when(menuMapper.mapMenuDTOToMenu(menuDTO)).thenReturn(menu);
        Menu menuResult = menuClient.getMenu(1L);

        //Then
        Assertions.assertEquals(menu.getId(), menuResult.getId());
        Assertions.assertEquals(menu.getLanguage(), menuResult.getLanguage());

    }

    @Test
    public void requestingAnMenuIdToGet_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(MenuClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(menuDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class),
                anyMap())).thenThrow(RestClientException.class);
        Menu menuResult = menuClient.getMenu(1L);

        //Then
        Assertions.assertEquals("[CLIENT, MENUS, RETRIEVED] Menu id not found",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingAnMenuToCreate_checkMenuResult() {

        //Given
        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(menuDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<MenuDTO>) any())).thenReturn(responseEntity);
        when(menuMapper.mapMenuDTOToMenu(menuDTO)).thenReturn(menu);
        Menu menuResult = menuClient.createMenu(menu);

        //Then
        Assertions.assertEquals(menu.getId(), menuResult.getId());
        Assertions.assertEquals(menu.getLanguage(), menuResult.getLanguage());

    }

    @Test
    public void requestingAnMenuToCreate_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(MenuClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(menuDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<MenuDTO>) any())).thenThrow(RestClientException.class);
        Menu menuResult = menuClient.createMenu(menu);

        //Then
        Assertions.assertEquals("[CLIENT, MENUS, PERSISTED] Menu was not created!!",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingAnMenuIdToEditAndAMenuEdited_checkMenuResult() {

        //Given
        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(menuDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<MenuDTO>) any(), anyMap())).thenReturn(responseEntity);
        when(menuMapper.mapMenuDTOToMenu(menuDTO)).thenReturn(menu);
        Menu menuResult = menuClient.editMenu(1L, menu);

        //Then
        Assertions.assertEquals(menu.getId(), menuResult.getId());
        Assertions.assertEquals(menu.getLanguage(), menuResult.getLanguage());

    }

    @Test
    public void requestingAnMenuIdToEditAndAMenuEdited_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(MenuClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(menuDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                (Class<MenuDTO>) any(), anyMap())).thenThrow(RestClientException.class);
        Menu menuResult = menuClient.editMenu(1L, menu);

        //Then
        Assertions.assertEquals("[CLIENT, MENUS, EDITED] Menu was not edited!!",
                logsList.get(1).getMessage());

    }

    @Test
    public void requestingAnMenuIdToDelete_checkMenuResult() {

        //Given
        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(menuDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenReturn(responseEntity);
        when(menuMapper.mapMenuDTOToMenu(menuDTO)).thenReturn(menu);
        Menu menuResult = menuClient.deleteMenu(1L);

        //Then
        Assertions.assertEquals(menu.getId(), menuResult.getId());
        Assertions.assertEquals(menu.getLanguage(), menuResult.getLanguage());

    }

    @Test
    public void requestingAnMenuIdToDelete_checkLogThrown() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(MenuClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(menuDTO, HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class), anyMap())).thenThrow(RestClientException.class);
        Menu menuResult = menuClient.deleteMenu(1L);

        //Then
        Assertions.assertEquals("[CLIENT, MENUS, DELETED] Menu id not found",
                logsList.get(1).getMessage());

    }

    @Test
    public void checkMenuResultList() {

        //Given
        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(menuDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(responseEntity);
        when(menuMapper.mapMenuDTOListToMenuList(
                Collections.singletonList(menuDTO))).thenReturn(Collections.singletonList(menu));
        List<Menu> menuResult = menuClient.getAllMenus();

        //Then
        Assertions.assertEquals(menu.getId(), menuResult.get(0).getId());
        Assertions.assertEquals(menu.getLanguage(), menuResult.get(0).getLanguage());

    }

    @Test
    public void checkLogThrownWhenGettingTheMenuResultList() {

        //Given
        Logger logger = (Logger) LoggerFactory.getLogger(MenuClient.class);

        // create and start a ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // add the appender to the logger
        logger.addAppender(listAppender);

        List<ILoggingEvent> logsList = listAppender.list;

        Menu menu = applicationDataProvider.getMenu();
        MenuDTO menuDTO = applicationDataProvider.getMenuDTO();
        ResponseEntity responseEntity = new ResponseEntity<>(Collections.singletonList(menuDTO), HttpStatus.OK);

        //When
        when(applicationProperties.getMsManager()).thenReturn(msManagerProperty);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);
        List<Menu> menuResult = menuClient.getAllMenus();

        //Then
        Assertions.assertEquals("[CLIENT, MENUS, RETRIEVED] Menu list not found",
                logsList.get(1).getMessage());

    }

}
