package ru.codein.creative.api.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Класс APIService хранит в себе все экземпляры классов для работы с API
 **/
@Getter
@AllArgsConstructor
public class APIService {
    private final CreativePlayerDbAPI creativePlayerDbAPI;
    private final RankAPI rankAPI;
    private final TabAPI tabAPI;
    private final PermissionAPI permissionAPI;
}
