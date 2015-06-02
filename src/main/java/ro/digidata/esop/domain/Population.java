/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.digidata.esop.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author iulian.radulescu
 */
@Entity
public class Population {
    
    @Id
    private int id;
    
    private String name;
    
    @OneToMany(mappedBy="population", cascade = CascadeType.PERSIST)
    private List<PopulationStructure> attributes = new ArrayList<PopulationStructure>( );

    public int getId( ) {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName( ) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PopulationStructure> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<PopulationStructure> attributes) {
        this.attributes = attributes;
    }

}
