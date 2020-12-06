/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.levi.VIR4.domain;

import java.util.Set;

import com.levi.VIR4.repositories.UserRepository;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "user")
/*
Ez a feljegyzés egy osztályt olyan objektumként jelöl meg, amelyet fenn akarunk tartani az adatbázisban.
Ez lehetővé teszi számunkra a használni kívánt gyűjtemény nevének kiválasztását is
 */

public class User {

    @Id //@Id jelöl egy mezőt egy modellosztályban elsődleges kulcsként:
    private String id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    /*
    @Indexed ez az annotáció jelöli meg egyetlen mező indexelését.
    A direction adja meg az index rendezési sorrendjét, amely alapértelmezés szerint növekszik (itt csökken).
     */

    private String email;
    private String password;
    private String fullname;

    @DBRef
/*
A @DBRef a dokumentum referenciáját vagy mutatóját tárolja, ahelyett, hogy közvetlenül beágyazná a szülő dokumentumba.
Ez hasonló egy relációs adatbázisban található idegen kulcshoz.
 */
    private Set<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }






}
