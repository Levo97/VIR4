/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.levi.VIR4.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/*
Ez a feljegyzés egy osztályt olyan objektumként jelöl meg, amelyet fenn akarunk tartani az adatbázisban.
Ez lehetővé teszi számunkra a használni kívánt gyűjtemény nevének kiválasztását is
 */
@Document(collection = "role")
public class Role {
    
    @Id //@Id jelöl egy mezőt egy modellosztályban elsődleges kulcsként:
    private String id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
        /*
    @Indexed ez az annotáció jelöli meg egyetlen mező indexelését.
    A direction adja meg az index rendezési sorrendjét, amely alapértelmezés szerint növekszik (itt csökken).
     */

    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
