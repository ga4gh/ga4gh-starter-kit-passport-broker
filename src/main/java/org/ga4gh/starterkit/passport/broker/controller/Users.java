package org.ga4gh.starterkit.passport.broker.controller;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.EntityNotFoundException;

import org.ga4gh.starterkit.passport.broker.model.PassportUser;
import org.ga4gh.starterkit.passport.broker.model.PassportVisaAssertion;
import org.ga4gh.starterkit.passport.broker.repository.PassportUserRepository;
import org.ga4gh.starterkit.passport.broker.utils.SerializeView;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/ga4gh/passport/v1/users")
public class Users {

    private final PassportUserRepository passportUserRepository;

    public Users(PassportUserRepository passportUserRepository) {
        this.passportUserRepository = passportUserRepository;
    }

    @GetMapping
    @JsonView(SerializeView.User.class)
    public List<PassportUser> getPassportUsers() {
        return passportUserRepository.findAll();
    }

    @GetMapping(path = "/{userId:.+}")
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser getPassportUser(
        @PathVariable(name = "userId") String userId
    ) {
        Optional<PassportUser> passportUser = passportUserRepository.findById(userId);
        return passportUser.orElse(null);
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
        return passportUserRepository.save(passportUser);
    }

    @PutMapping(path = "/{userId:.+}")
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser updatePassportUser(
        @PathVariable(name = "userId") String userId,
        @RequestBody PassportUser passportUser
    ) {
        PassportUser existingPassportUser = passportUserRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not Found"));
        if (!existingPassportUser.getId().equals(passportUser.getId())) {
            throw new IllegalArgumentException("User ID in path does not match user ID in request body");
        }

        setBidirectionalRelationship(passportUser);
        // set assertion time to now if no assertion time exists
        Long epoch = Long.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        for (PassportVisaAssertion assertion : passportUser.getPassportVisaAssertions()) {
            if (assertion.getAssertedAt() == null) {
                assertion.setAssertedAt(epoch);
            }
        }
        return passportUserRepository.save(passportUser);
    }

    @DeleteMapping(path = "/{userId:.+}")
    @JsonView(SerializeView.UserRelational.class)
    public PassportUser deletePassportUser(
        @PathVariable(name = "userId") String userId
    ) {
        passportUserRepository.deleteById(userId);
        return null;
    }

    private void setBidirectionalRelationship(PassportUser passportUser) {
        if (passportUser.getPassportVisaAssertions() != null) {
            passportUser.getPassportVisaAssertions().forEach(assertion -> assertion.setPassportUser(passportUser));
        }
    }
}
