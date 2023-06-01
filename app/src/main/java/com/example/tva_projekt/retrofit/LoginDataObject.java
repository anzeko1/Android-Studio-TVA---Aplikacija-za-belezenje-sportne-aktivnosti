package com.example.tva_projekt.retrofit;

public class LoginDataObject {

        public String userName;
        public String password;
        public String response;
        public String idUser;



        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public String getResponse() {
            return response;
        }
        public void setResponse(String response) {
            this.response = response;
        }
        public String getIdUser() {
            return idUser;
        }
        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public LoginDataObject(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
}
