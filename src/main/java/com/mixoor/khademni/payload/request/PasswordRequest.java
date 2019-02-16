package com.mixoor.khademni.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordRequest {

    @NotBlank
    @Size(min = 8, max = 20)
     private String oldPassword;
    @NotBlank
    @Size(min = 8, max = 20)
     private String newPassword;
    @NotBlank
    @Size(min = 8, max = 20)
     private String conPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConPassword() {
        return conPassword;
    }

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }
}
