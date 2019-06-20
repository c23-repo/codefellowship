package com.repocris.wk4.labs.codefellowship;

import javassist.compiler.ast.StringL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    String username;
    String password;
    String firstName;
    String lastName;
    String dateOfBirth;
    String bio;


    // DB relationship annotation
    @OneToMany(mappedBy = "creator")
    List<Post> posts;

    @ManyToMany
    Set<ApplicationUser> friends;

    public ApplicationUser(){}

    public ApplicationUser(String username, String password, String firstName, String lastName,
                           String dateOfBirth, String bio){

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() { return id; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Set<ApplicationUser> getFriends() {
        return friends;
    }
}
