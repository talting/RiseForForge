package com.alan.clients.utility.i18n.settings;

import lombok.Getter;

@Getter
public class I18nButtonSetting extends I18nSetting {
    private final String name;

    public I18nButtonSetting(String toolTip, String name) {
        super(toolTip);
        this.name = name;
    }
}
