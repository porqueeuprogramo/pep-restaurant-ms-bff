package com.pep.restaurant.ms.bff.service.mapper;

import com.pep.restaurant.ms.bff.domain.Menu;
import com.pep.restaurant.ms.bff.web.api.model.MenuDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {

    /**
     * Map menu list to menu list DTO.
     * @param menuList menu list.
     * @return Menu List DTO.
     */
    public List<MenuDTO> mapMenuListToMenuDTOList(final List<Menu> menuList) {
        return menuList != null
                ? menuList
                .stream()
                .map(this::mapMenuToMenuDTO)
                .collect(Collectors.toList())
                : null;
    }

    /**
     * Map menu DTO list to menu list.
     * @param menuDTOList menu DTO list.
     * @return Menu List.
     */
    public List<Menu> mapMenuDTOListToMenuList(final List<MenuDTO> menuDTOList) {
        return menuDTOList != null
                ? menuDTOList
                .stream()
                .map(this::mapMenuDTOToMenu)
                .collect(Collectors.toList())
                : null;
    }

    /**
     * Map Menu to Menu DTO.
     * @param menu menu.
     * @return MenuDTO.
     */
    public MenuDTO mapMenuToMenuDTO(final Menu menu) {
        return menu != null ?
                new MenuDTO()
                        .uid(menu.getUid())
                        .language(menu.getLanguage())
                : null;
    }

    /**
     * Map menu DTO to menu.
     * @param menuDTO menu DTO.
     * @return Menu.
     */
    public Menu mapMenuDTOToMenu(final MenuDTO menuDTO) {
        return menuDTO != null ?
                new Menu()
                        .uid(menuDTO.getUid())
                        .language(menuDTO.getLanguage())
                : null;
    }

}
