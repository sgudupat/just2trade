package com.j2t.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Partners.
 */
@Entity
@Table(name = "partners")
public class Partners implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "mobile")
    private String mobile;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "comments")
    private String comments;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Partners partners = (Partners) o;
        if(partners.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, partners.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Partners{" +
            "id=" + id +
            ", fullName='" + fullName + "'" +
            ", mobile='" + mobile + "'" +
            ", email='" + email + "'" +
            ", comments='" + comments + "'" +
            '}';
    }
    
    public String mailContent() {
        return fullName + " want to be partner.\n" +
            "Contact information is:\n" +
            "Mobile='" + mobile + "'\n" +
            "Email='" + email + "'";
    }
}
