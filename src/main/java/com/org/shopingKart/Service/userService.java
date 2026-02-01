package com.org.shopingKart.Service;

import com.org.shopingKart.entity.Users;
import com.org.shopingKart.repository.userRepo;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class userService {

    @Autowired
    private userRepo userRepo;

    public List<Users> getAll() {
        return userRepo.findAll();
    }

    public Users getById(ObjectId id) {
        return userRepo.findById(id).orElse(null);
    }

    public Users save(Users users) {
        users.setRegDate(Instant.now());
        return userRepo.save(users);
    }

    public Users update(Users updatedUser, ObjectId id) {
        Users existingUser = userRepo.findById(id).orElse(null);
        if (existingUser == null) return null;

        existingUser.setName(updatedUser.getName());
        existingUser.setMobileNumber(updatedUser.getMobileNumber());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());


        if (updatedUser.getAddress() != null)
            existingUser.setAddress(updatedUser.getAddress());

        if (updatedUser.getCity() != null)
            existingUser.setCity(updatedUser.getCity());

        if (updatedUser.getState() != null)
            existingUser.setState(updatedUser.getState());

        if (updatedUser.getPincode() != null)
            existingUser.setPincode(updatedUser.getPincode());

        if (updatedUser.getProfileImage() != null)
            existingUser.setProfileImage(updatedUser.getProfileImage());

        if (updatedUser.getRole() != null)
            existingUser.setRole(updatedUser.getRole());


        return userRepo.save(existingUser);
    }

    public Boolean delete(ObjectId id) {
        if (!userRepo.existsById(id)) return false;
        userRepo.deleteById(id);
        return true;
    }

    public Boolean existByemail(String email) {
        return userRepo.findByEmail(email) != null;
    }

    public Users login(String email, String password) {
        if (existByemail(email)) {
            Users users = userRepo.findByEmail(email);
            if (users.getPassword().equals(password)) {
                return users;
            }
        }
        return null;
    }


}
