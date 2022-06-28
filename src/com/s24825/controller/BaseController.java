package com.s24825.controller;

import com.s24825.Settings;
import com.s24825.view.ViewFactory;

public abstract class BaseController {

    protected ViewFactory viewFactory;
    private final String fxmlName;
    protected Settings settings;

    public BaseController(ViewFactory viewFactory, String fxmlName, Settings settings) {
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
        this.settings = settings;
    }

    public String getFxmlName() {
        return fxmlName;
    }

}
