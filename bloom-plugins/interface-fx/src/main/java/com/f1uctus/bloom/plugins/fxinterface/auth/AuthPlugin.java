package com.f1uctus.bloom.plugins.fxinterface.auth;

import com.f1uctus.bloom.plugins.coreinterface.BasePlugin;
import javafx.scene.Node;
import org.pf4j.ExtensionPoint;

public interface AuthPlugin extends ExtensionPoint, BasePlugin {
    Node buildAuthorizationView();
}
