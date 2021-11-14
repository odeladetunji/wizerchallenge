package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(
        name = "Books"
)
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Books extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Status")
    private String status;

    @Column(name = "CategoryId")
    private Long categoryId;

    @Column(name = "IsFavourite")
    private Boolean isFavourite;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", categoryId=" + categoryId +
                ", isFavourite=" + isFavourite +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Books)) return false;
        if (!super.equals(o)) return false;
        Books books = (Books) o;
        return Objects.equals(getId(), books.getId()) && Objects.equals(getName(), books.getName()) && Objects.equals(getPrice(), books.getPrice()) && Objects.equals(getStatus(), books.getStatus()) && Objects.equals(getCategoryId(), books.getCategoryId()) && Objects.equals(isFavourite, books.isFavourite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getName(), getPrice(), getStatus(), getCategoryId(), isFavourite);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
