package com.example.cs308fx.controllers;

import com.example.cs308fx.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.example.cs308fx.UserModel;
import com.example.cs308fx.Manager;

public class ModuleController {

    private Module currentModule;

    @FXML
    private Label moduleName;

    public void setCurrentModule(Module module) {
        this.currentModule = module;
        setLabels();
    }

    private void setLabels() {
        moduleName.setText("Module: " + currentModule.getModuleName());
    }


}
