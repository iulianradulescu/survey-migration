/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.digidata.esop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name="REPORTING_UNIT")
public class ReportingUnit {
    
    @Id
    private long id;
    
    private char newsletter;
    
    private String email;
    
    @Column(name="AUTHORIZATION_KEY")
    private String authorizationKey;
    
    private String username;

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthorizationKey(String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }
    
    
}
