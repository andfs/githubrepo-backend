package com.br.gitrepos.gitrepo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.br.gitrepos.gitrepo.model.dto.UserDTO;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class User {

    public User(UserDTO dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private  String username;

    @NotNull
    @Length(min = 6)
    @Column(name = "password", nullable = false)
    private  String password;

    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_users_favorites",  joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "favorite_id"))
    @EqualsAndHashCode.Exclude private Set<Favorites> favorites = new HashSet<>();
}