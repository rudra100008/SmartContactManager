package com.smartcontactmanager.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int cid;
    private String conctactname;
    private String work;
    private String email;
    private String phonenumber;
    private String image;
    @ManyToOne
    private User user;
    @Column(length = 1000)
    private String description;
    public Contact(int cid, String conctactname, String work, String email, String phonenumber, String image,
            String description) {
        this.cid = cid;
        this.conctactname = conctactname;
        this.work = work;
        this.email = email;
        this.phonenumber = phonenumber;
        this.image = image;
        this.description = description;
    }
    public Contact() {
    }
    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getConctactname() {
        return conctactname;
    }
    public void setConctactname(String conctactname) {
        this.conctactname = conctactname;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public User getUser()
    {
        return this.user;
    }
    public void setUser(User user)
    {
        this.user=user;
    }
    @Override
    public String toString() {
        return "Contact [cid=" + cid + ", conctactname=" + conctactname + ", work=" + work + ", email=" + email
                + ", phonenumber=" + phonenumber + ", image=" + image + ", description=" + description + "]";
    }
}
