package com.example.merbabu_store_book.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String province;
    private String postal_code;

    // Getter & Setter
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getProvince() { return province; }
    public String getPostal_code() { return postal_code; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setProvince(String province) { this.province = province; }
    public void setPostal_code(String postal_code) { this.postal_code = postal_code; }
}
