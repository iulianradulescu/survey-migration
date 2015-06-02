/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.digidata.esop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author iulian.radulescu
 */
@Entity
@Table(name="OLD_SAMPLE", schema="ESOP_MIGRATION")
public class SSample {
    
    @Id
    private int id;
    
    private int status;
    
    @Column(name="ONLINE_EDIT")
    private String onlineEdit;
    
    @Column(name="MAXIMAL_LIST_ID")
    private long maximalListId;
    
    @ManyToOne
    //@JoinColumn(name="SURVEY_ID")
    private Survey survey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOnlineEdit() {
        return onlineEdit;
    }

    public void setOnlineEdit(String onlineEdit) {
        this.onlineEdit = onlineEdit;
    }

    public long getMaximalListId() {
        return maximalListId;
    }

    public void setMaximalListId(long maximalListId) {
        this.maximalListId = maximalListId;
    }
}
