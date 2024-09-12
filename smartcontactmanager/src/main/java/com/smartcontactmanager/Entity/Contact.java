package com.smartcontactmanager.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int cid;
    @NotBlank(message = " Contact Name is required!!")
    private String contactname;
    private String work;
    @NotBlank(message = "Email is required!!")
    @Email
    private String email;
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",message = "Enter correct format")
    @Size(min = 10,max = 10,message = "Phone number must have 10 digit")
    private String phonenumber;
    private String image;
    @ManyToOne
    private User user;
    @Column(length = 1000)
    private String description;
    public Contact(int cid, String contactname, String work, String email, String phonenumber, String image,
            String description) {
        this.cid = cid;
        this.contactname = contactname;
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
    public String getContactname() {
        return contactname;
    }
    public void setContactname(String contactname) {
        this.contactname = contactname;
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
        return "Contact [cid=" + cid + ", conctactname=" + contactname + ", work=" + work + ", email=" + email
                + ", phonenumber=" + phonenumber + ", image=" + image + ", description=" + description + "]";
    }
}
