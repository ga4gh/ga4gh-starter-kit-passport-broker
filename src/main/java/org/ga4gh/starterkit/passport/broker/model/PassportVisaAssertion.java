package org.ga4gh.starterkit.passport.broker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.passport.broker.utils.SerializeView;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "passport_visa_assertion")
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassportVisaAssertion implements HibernateEntity<Long> {

    /* Non-relational entity attributes */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "status")
    @JsonView(SerializeView.Always.class)
    private String status;

    @Column(name = "asserted_at")
    @JsonView(SerializeView.Always.class)
    private Long assertedAt;

    /* Relational entity attributes */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonView(SerializeView.VisaRelational.class)
    private PassportUser passportUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visa_id")
    @JsonView(SerializeView.UserRelational.class)
    private PassportVisa passportVisa;

    /* Constructors */

    public PassportVisaAssertion() {

    }

    public PassportVisaAssertion(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public void loadRelations() {

    }

    @Override
    public boolean equals(Object that) {
        return (this == that) || ((that instanceof PassportVisaAssertion) && this.getId().equals(((PassportVisaAssertion) that).getId()));
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
