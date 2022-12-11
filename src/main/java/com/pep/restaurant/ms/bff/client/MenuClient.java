package com.pep.restaurant.ms.bff.client;

import com.pep.restaurant.ms.bff.config.ApplicationProperties;
import com.pep.restaurant.ms.bff.domain.Menu;
import com.pep.restaurant.ms.bff.logging.Logger;
import com.pep.restaurant.ms.bff.logging.enumeration.LogTag;
import com.pep.restaurant.ms.bff.service.mapper.MenuMapper;
import com.pep.restaurant.ms.bff.web.api.model.MenuDTO;
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
public class MenuClient {

    private static final Logger LOGGER = new Logger(MenuClient.class);
    public static final String MENU_MENU_ID = "/api/menu/{menuId}";
    public static final String MENU = "/api/menu";
    private final RestTemplate restTemplate;
    private final MenuMapper menuMapper;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public MenuClient(final RestTemplate restTemplate,
                      final MenuMapper menuMapper,
                      final ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.menuMapper = menuMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Client to get Menu by id.
     * @param menuId menu id.
     * @return menu.
     */
    public Menu getMenu(final long menuId){
        final String url =  applicationProperties.getMsManager().getUrl().concat(MENU_MENU_ID);
        final String correlationId = UUID.randomUUID().toString();
        MenuDTO menuDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity requestGetMenuId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("menuId", String.valueOf(menuId));
        headers.set("Correlation-Id", correlationId);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.RETRIEVED),
                "Get Menu by id: " + menuId);

        try{
            final ResponseEntity<MenuDTO> responseMenu = restTemplate.exchange(url, HttpMethod.GET, requestGetMenuId,
                    new ParameterizedTypeReference<MenuDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseMenu.getStatusCode())){
                menuDTO = responseMenu.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.RETRIEVED),
                    "Menu id not found");
        }

        return menuMapper.mapMenuDTOToMenu(menuDTO);
    }

    /**
     * Client to create menu.
     * @param menu menu.
     * @return menu.
     */
    public Menu createMenu(final Menu menu){
        final String url =  applicationProperties.getMsManager().getUrl().concat(MENU);
        final String correlationId = UUID.randomUUID().toString();
        MenuDTO menuDTO = null;

        final MenuDTO requestMenuDTO = menuMapper.mapMenuToMenuDTO(menu);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity<MenuDTO> requestCreateMenuClient = new HttpEntity(requestMenuDTO ,headers);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.PERSISTED),
                "Create Menu: " + menu.toString());

        try{
            final ResponseEntity<MenuDTO> responseMenuCreated = restTemplate.exchange(url, HttpMethod.POST,
                    requestCreateMenuClient, MenuDTO.class);
            if(HttpStatus.OK.equals(responseMenuCreated.getStatusCode())){
                menuDTO = responseMenuCreated.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.PERSISTED)
                    , "Menu was not created!!");
        }

        return menuMapper.mapMenuDTOToMenu(menuDTO);
    }

    /**
     * Client to edit menu.
     * @param menuId menu id.
     * @param menu menu.
     * @return menu.
     */
    public Menu editMenu(final long menuId, final Menu menu){
        final String url =  applicationProperties.getMsManager().getUrl().concat(MENU_MENU_ID);
        final String correlationId = UUID.randomUUID().toString();
        MenuDTO menuDTO = null;

        final MenuDTO requestMenuDTO = menuMapper.mapMenuToMenuDTO(menu);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity<MenuDTO> requestEditMenuClient = new HttpEntity(requestMenuDTO ,headers);
        final Map<String, String> params = new HashMap<>();
        params.put("menuId", String.valueOf(menuId));

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.EDITED),
                "Edit Menu by id " + menuId);

        try{
            final ResponseEntity<MenuDTO> responseMenuEdited = restTemplate.exchange(url, HttpMethod.PUT,
                    requestEditMenuClient, MenuDTO.class, params);
            if(HttpStatus.OK.equals(responseMenuEdited.getStatusCode())){
                menuDTO = responseMenuEdited.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.EDITED)
                    , "Menu was not edited!!");
        }

        return menuMapper.mapMenuDTOToMenu(menuDTO);
    }

    /**
     * Client to delete menu.
     * @param menuId menu id.
     * @return menu deleted.
     */
    public Menu deleteMenu(final Long menuId) {
        final String url =  applicationProperties.getMsManager().getUrl().concat(MENU_MENU_ID);
        final String correlationId = UUID.randomUUID().toString();
        MenuDTO menuDTO = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetMenuId = new HttpEntity(headers);
        final Map<String, String> params = new HashMap<>();
        params.put("menuId", String.valueOf(menuId));

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.DELETED),
                "Delete Menu by id: " + menuId);

        try{
            final ResponseEntity<MenuDTO> responseMenu = restTemplate.exchange(url, HttpMethod.DELETE, requestGetMenuId,
                    new ParameterizedTypeReference<MenuDTO>() {
                    }, params);
            if(HttpStatus.OK.equals(responseMenu.getStatusCode())){
                menuDTO = responseMenu.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.DELETED),
                    "Menu id not found");
        }

        return menuMapper.mapMenuDTOToMenu(menuDTO);
    }

    /**
     * Client to get all menus.
     * @return menus list.
     */
    public List<Menu> getAllMenus() {
        final String url =  applicationProperties.getMsManager().getUrl().concat(MENU);
        final String correlationId = UUID.randomUUID().toString();
        List<MenuDTO> menuDTOList = null;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Correlation-Id", correlationId);

        final HttpEntity requestGetMenuId = new HttpEntity(headers);

        LOGGER.info(correlationId, Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.RETRIEVED),
                "Get All Menus");

        try{
            final ResponseEntity<List<MenuDTO>> responseMenu = restTemplate.exchange(url, HttpMethod.GET,
                    requestGetMenuId,
                    new ParameterizedTypeReference<List<MenuDTO>>() {
                    });
            if(HttpStatus.OK.equals(responseMenu.getStatusCode())){
                menuDTOList = responseMenu.getBody();
            }
        } catch (final RestClientException restClientException){
            LOGGER.info(Arrays.asList(LogTag.CLIENT, LogTag.MENUS, LogTag.RETRIEVED),
                    "Menu list not found");
        }

        return menuMapper.mapMenuDTOListToMenuList(menuDTOList);
    }
}
