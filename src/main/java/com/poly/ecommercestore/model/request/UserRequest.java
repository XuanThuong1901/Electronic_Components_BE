package com.poly.ecommercestore.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequest {
    private String name;
    private String email;
    private Integer role;
    private Date birthday;
    private boolean gender;
    private String identityCard;
    private String telephone;
    private String address;
//    private MultipartFile avatar;
    private String password;

//    public UserRequest(String email, String password, String name, String address, String telephone, int position) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.address = address;
//        this.telephone = telephone;
//        this.position = position;
//    }


    public UserRequest() {
    }

    public UserRequest(String name, String email, Integer role, Date birthday, Boolean gender, String identityCard, String telephone, String address, String password) {
        this.name = (name != null) ? name : "N/A";
        this.email = (email != null) ? email : "N/A" ;
        this.role = (role != null) ? role : 1;
        this.birthday = (birthday != null) ? birthday : null;
        this.gender = (gender != null) ? gender : false;
        this.identityCard = (identityCard != null) ? identityCard : "N/A";
        this.telephone = (telephone != null) ? telephone : "N/A";
        this.address = (address != null) ? address : "N/A";
//        this.avatar = (avatar != null) ? avatar : null ;
        this.password = (password != null) ? password : "N/A";
    }
}
