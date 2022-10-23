package masjuan.ioc.chefdeluxe.utils;

public class ValidateLogin {

    public ValidateLogin() {
    }

    public boolean usernameEmpty(String username) {
        //String infoError = "Usuari buit";
        return username.isEmpty();
    }

    public boolean passwordEmpty(String password) {
        //String infoError = "Password buit";
        return password.isEmpty();
    }


}
