package org.ga4gh.starterkit.passport.broker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ga4gh.starterkit.passport.broker.model.PassportUser;

@Repository
public interface PassportUserRepository extends JpaRepository<PassportUser, String> {

}
