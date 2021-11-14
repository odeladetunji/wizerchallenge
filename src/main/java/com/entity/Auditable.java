package com.entity;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String last_modified_by;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_modified_date;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLast_modified_by() {
        return last_modified_by;
    }

    public void setLast_modified_by(String last_modified_by) {
        this.last_modified_by = last_modified_by;
    }

    public Date getLast_modified_date() {
        return last_modified_date;
    }

    public void setLast_modified_date(Date last_modified_date) {
        this.last_modified_date = last_modified_date;
    }

    @Override
    public String toString() {
        return "Auditable{" +
                "createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", last_modified_by='" + last_modified_by + '\'' +
                ", last_modified_date=" + last_modified_date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auditable)) return false;
        Auditable auditable = (Auditable) o;
        return Objects.equals(getCreatedDate(), auditable.getCreatedDate()) && Objects.equals(getCreatedBy(), auditable.getCreatedBy()) && Objects.equals(getLast_modified_by(), auditable.getLast_modified_by()) && Objects.equals(getLast_modified_date(), auditable.getLast_modified_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreatedDate(), getCreatedBy(), getLast_modified_by(), getLast_modified_date());
    }
}
