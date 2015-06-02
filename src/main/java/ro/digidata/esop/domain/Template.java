/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.digidata.esop.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author iulian.radulescu
 */
@Entity
public class Template {
  
    @Id
    private int id;
    
    private String mnemonic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
    
    
}
