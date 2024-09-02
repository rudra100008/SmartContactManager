package com.smartcontactmanager.Entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    @NotBlank(message = "Name is required!!")
    @Size(min = 3,max = 16,message = "minimum character 3 and maximum character 16!!!")
    private String username;
    @Column(unique = true)
    @NotBlank(message = "Email is required!!")
    private String email;
    @Size(min = 3,max = 16,message = "Minimum character 3 and Maximum character 16!!!")
    private String password;
    private String position;
    private boolean enable;
    private String imageUrl;
    @Column(length = 500)
    private String about;
    //one user can many contacts
    //cascade all contact to one user
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private List<Contact> contact =new ArrayList<Contact>();

    public User(){
        super();
    }
    public User(int id, String username, String email, String password, String position, boolean enable,
            String imageUrl, String about,List<Contact> contact) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.position = position;
        this.enable = enable;
        this.imageUrl = imageUrl;
        this.about = about;
        this.contact=contact;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }
    public List<Contact> getContacts() {
        return contact;
    }
    public void setContacts(List<Contact> contacts) {
        this.contact = contacts;
    }
    @Override
    public String toString() {
        return "EndUser [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
                + ", position=" + position + ", enable=" + enable + ", imageUrl=" + imageUrl + ", about=" + about + "]";
    }

}
