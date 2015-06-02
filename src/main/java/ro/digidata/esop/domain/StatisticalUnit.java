/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name = "STATISTICAL_UNIT")
@NamedQueries({
    @NamedQuery(name = "StatisticalUnit.getUnitByCodeAndPopulation", query = "select s from StatisticalUnit s where code=:code and population=:population"),
    @NamedQuery(name = "StatisticalUnit.removeForPopulation", query = "delete  from StatisticalUnit where population=:population"),
     @NamedQuery(name = "StatisticalUnit.findByBaseCodeAndNameAndPopulation", query = "select s from StatisticalUnit s where upper(s.name)=upper(:name) and s.code like :baseCode and population=:population")
})
public class StatisticalUnit {

    @Id
    @SequenceGenerator(name = "SQSU", sequenceName = "SQ_STATISTICAL_UNIT", allocationSize = 1)
    @GeneratedValue(generator = "SQSU")
    private Long id;

    private String code;

    private String name;
    
    private int county;
    
    @Column(name="IS_ACTIVE")
    private int active = 1;

    @Column(name = "POPULATION_ID")
    private int population;
    
    @Column(name= "PARENT_ID")
    private Long parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setCounty(int county) {
        this.county = county;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public int getCounty() {
        return county;
    }

    public Long getParent() {
        return parent;
    }
}
