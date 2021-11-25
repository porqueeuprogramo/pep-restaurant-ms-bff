package com.pep.restaurant.ms.bff.service;

import com.pep.restaurant.ms.bff.client.MenuClient;
import com.pep.restaurant.ms.bff.domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuClient menuClient;

    @Autowired
    public MenuService(final MenuClient menuClient) {
        this.menuClient = menuClient;
    }

    /**
     * Call get menu client.
     * @param menuId menu id.
     * @return menu.
     */
    public Menu getMenu(final long menuId) {
        return menuClient.getMenu(menuId);
    }

    /**
     * Call create menu client.
     * @param menu menu to create.
     * @return menu.
     */
    public Menu createMenu(final Menu menu) {
        return menuClient.createMenu(menu);
    }

    /**
     * Call edit menu client.
     * @param menuId menu id to edit.
     * @param menu menu to be edited
     * @return menu edited.
     */
    public Menu editMenu(final Long menuId, final Menu menu) {
        return menuClient.editMenu(menuId, menu);
    }

    /**
     * Call remove menu.
     * @param menuId menu id.
     * @return Menu Deleted.
     */
    public Menu deleteMenu(final Long menuId) {
        return menuClient.deleteMenu(menuId);
    }

    /**
     * Call Get all menus.
     * @return Menu List.
     */
    public List<Menu> getAllMenus() {
        return menuClient.getAllMenus();
    }
}
