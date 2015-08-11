/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Survey.findById", query = "select s from Survey s where s.id=:id")
})
public class Survey {

    @Id
    private long id;

    private int status;

    @Column(name = "SURVEY_SCRIPT")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String script;

    //@OneToMany(mappedBy = "survey")
    //private Collection<Instance> instances;
    @ManyToOne
    @JoinColumn(name = "TEMPLATE_ID")
    private Template template;

    public Template getTemplate() {
        return template;
    }
    
    public String getScript( ) {
        return this.script;
    }
}
