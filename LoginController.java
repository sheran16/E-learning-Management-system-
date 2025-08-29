package controller;

import model.LoginModel;

public class LoginController {
    private LoginModel model;

    public LoginController() {
        model = new LoginModel();
    }

    public boolean validate(String user, String pass) {
        return model.validateLogin(user, pass);
    }
}
