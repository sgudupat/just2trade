package com.j2t.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Webinars.
 */
@Entity
@Table(name = "webinars")
public class Webinars implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Webinars webinars = (Webinars) o;
        if (webinars.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, webinars.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Webinar{" +
            "id=" + id +
            ", fullName='" + fullName + "'" +
            ", mobile='" + mobile + "'" +
            ", email='" + email + "'" +
            '}';
    }

    public String mailContent() {
        return fullName + " want to attend the Webinar.\n" +
            "Contact information is:\n" +
            "Mobile='" + mobile + "'\n" +
            "Email='" + email + "'";
    }
}
