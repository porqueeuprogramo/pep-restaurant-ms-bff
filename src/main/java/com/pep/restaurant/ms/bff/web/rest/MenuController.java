package com.pep.restaurant.ms.bff.web.rest;

import com.pep.restaurant.ms.bff.service.MenuService;
import com.pep.restaurant.ms.bff.service.mapper.MenuMapper;
import com.pep.restaurant.ms.bff.web.api.MenuApi;
import com.pep.restaurant.ms.bff.web.api.model.MenuDTO;
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
public class MenuController implements MenuApi, ApiController {

    public static final String MENU_MENU_ID = "/menu/{menuId}";
    public static final String MENU = "/menu";
    private final MenuService menuService;
    private final MenuMapper menuMapper;

    /**
     * Constructor for Menu Controller.
     *
     * @param menuService Menu Service.
     * @param menuMapper  Menu Mapper.
     */
    public MenuController(final MenuService menuService, final MenuMapper menuMapper) {
        this.menuService = menuService;
        this.menuMapper = menuMapper;
    }

    /**
     * Controller to get a menu by id.
     *
     * @param menuId id of menu to get.
     * @return MenuDTO with the provided id.
     */

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = MENU_MENU_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<MenuDTO> getMenu(final Long menuId) {
        return ResponseEntity.ok(menuMapper.mapMenuToMenuDTO(
                menuService.getMenu(menuId)));
    }

    /**
     * Controller to create a menu.
     *
     * @param menuDTO menuDTO to create.
     * @return MenuDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(value = MENU,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<MenuDTO> createMenu(@RequestBody final MenuDTO menuDTO) {
        return ResponseEntity.ok(menuMapper.mapMenuToMenuDTO(
                menuService.createMenu(menuMapper.mapMenuDTOToMenu(menuDTO))));
    }

    /**
     * Controller to edit a menu.
     *
     * @param menuId menu to be edited.
     * @param menuDTO menuDTO new data.
     * @return MenuDTO created.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(value = MENU_MENU_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<MenuDTO> editMenu(final Long menuId,
                                                        @RequestBody final MenuDTO menuDTO) {
        return ResponseEntity.ok(menuMapper.mapMenuToMenuDTO(
                menuService.editMenu(menuId,
                        menuMapper.mapMenuDTOToMenu(menuDTO))));

    }

    /**
     * Controller to delete menu by id.
     * @param menuId menu Id.
     * @return Menu deleted.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(value = MENU_MENU_ID,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<MenuDTO> deleteMenu(final Long menuId) {
        return ResponseEntity.ok(menuMapper.mapMenuToMenuDTO(
                menuService.deleteMenu(menuId)));
    }

    /**
     * Controller to Get all menus.
     * @return Menus List.
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(value = MENU,
            produces = {"application/json"},
            consumes = {"application/json"})
    @Override
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        return ResponseEntity.ok(menuMapper.mapMenuListToMenuDTOList(
                menuService.getAllMenus()));
    }
}
