package com.j2t.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Partners entity.
 */
public class PartnersDTO implements Serializable {

    private Long id;

    private String fullName;


    private String mobile;


    private String email;


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

        PartnersDTO partnersDTO = (PartnersDTO) o;

        if ( ! Objects.equals(id, partnersDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PartnersDTO{" +
            "id=" + id +
            ", fullName='" + fullName + "'" +
            ", mobile='" + mobile + "'" +
            ", email='" + email + "'" +
            ", comments='" + comments + "'" +
            '}';
    }
}
