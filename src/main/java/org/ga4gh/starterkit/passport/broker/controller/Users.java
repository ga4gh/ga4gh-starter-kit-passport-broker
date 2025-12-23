package org.ga4gh.starterkit.passport.broker.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import jakarta.annotation.Resource;
import com.fasterxml.jackson.annotation.JsonView;
import org.ga4gh.starterkit.common.requesthandler.BasicCreateRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicDeleteRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicShowRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicUpdateRequestHandler;
import org.ga4gh.starterkit.passport.broker.model.PassportUser;
import org.ga4gh.starterkit.passport.broker.model.PassportVisaAssertion;
import org.ga4gh.starterkit.passport.broker.utils.SerializeView;
import org.ga4gh.starterkit.passport.broker.utils.hibernate.PassportBrokerHibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/ga4gh/passport/v1/users")
public class Users {

    @Autowired
    private PassportBrokerHibernateUtil hibernateUtil;

    @Resource(name = "showUserRequestHandler")
    private BasicShowRequestHandler<String, PassportUser> showUser;

    @Resource(name = "createUserRequestHandler")
    private BasicCreateRequestHandler<String, PassportUser> createUser;

    @Resource(name = "updateUserRequestHandler")
    private BasicUpdateRequestHandler<String, PassportUser> updateUser;

    @Resource(name = "deleteUserRequestHandler")
    private BasicDeleteRequestHandler<String, PassportUser> deleteUser;

    @GetMapping
    @JsonView(SerializeView.User.class)
    public List<PassportUser> getPassportUsers() {
        return hibernateUtil.getPassportUsers();
    }

    @GetMapping(path = "/{userId:.+}")
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser getPassportUser(
        @PathVariable(name = "userId") String userId
    ) {
        return showUser.prepare(userId).handleRequest();
    }

    @PostMapping
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser createPassportUser(
        @RequestBody PassportUser passportUser
    ) {
        setBidirectionalRelationship(passportUser);
        // set assertion time to now if no assertion time exists
        Long epoch = Long.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        for (PassportVisaAssertion assertion : passportUser.getPassportVisaAssertions()) {
            if (assertion.getAssertedAt() == null) {
                assertion.setAssertedAt(epoch);
            }
        }
        return createUser.prepare(passportUser).handleRequest();
    }

    @PutMapping(path = "/{userId:.+}")
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser updatePassportUser(
        @PathVariable(name = "userId") String userId,
        @RequestBody PassportUser passportUser
    ) {
        setBidirectionalRelationship(passportUser);
        // set assertion time to now if no assertion time exists
        Long epoch = Long.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        for (PassportVisaAssertion assertion : passportUser.getPassportVisaAssertions()) {
            if (assertion.getAssertedAt() == null) {
                assertion.setAssertedAt(epoch);
            }
        }
        return updateUser.prepare(userId, passportUser).handleRequest();
    }

    @DeleteMapping(path = "/{userId:.+}")
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser deletePassportUser(
        @PathVariable(name = "userId") String userId
    ) {
        return deleteUser.prepare(userId).handleRequest();
    }

    private void setBidirectionalRelationship(PassportUser passportUser) {
        if (passportUser.getPassportVisaAssertions() != null) {
            passportUser.getPassportVisaAssertions().forEach(assertion -> assertion.setPassportUser(passportUser));
        }
    }
}
