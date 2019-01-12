package com.mixoor.khademni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users", uniqueConstraints = {

        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends DateAudit {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Role role;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_language",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    Set<Language> languages = new HashSet<Language>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Lob
    private String aboutMe;
    @Column(nullable = false)
    @JsonIgnore
    @Size(max = 100)
    private String password;
    @NaturalId
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
    private String path = "";
    @NotBlank
    @Size(max = 100)
    private String adresse;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;
    @Size(max = 15)
    private String phone_number;


    public User() {
    }

    public User(@NotBlank String name, @NotBlank String aboutMe, @Size(max = 100) String password, @NotBlank @Email @Size(max = 100) String email, String path, @NotBlank @Size(max = 100) String adresse, @NotBlank String city, @NotBlank String country, Date dob, @NotNull Gender gender, @Size(max = 15) String phone_number, Role role, Set<Language> languages) {
        this.name = name;
        this.aboutMe = aboutMe;
        this.password = password;
        this.email = email;
        this.path = path;
        this.adresse = adresse;
        this.city = city;
        this.country = country;
        this.dob = dob;
        this.gender = gender;
        this.phone_number = phone_number;
        this.role = role;
        this.languages = languages;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}



