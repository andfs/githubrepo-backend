package com.br.gitrepos.gitrepo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_favorites")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Favorites {

    public Favorites(Long idRepository) {
        this.idRepository = idRepository;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_repository", unique = true)
    private Long idRepository;

    @ManyToMany(mappedBy = "favorites")
    @JsonBackReference
    @EqualsAndHashCode.Exclude private Set<User> users;
}