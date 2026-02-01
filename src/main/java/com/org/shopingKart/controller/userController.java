package com.org.shopingKart.controller;

import com.org.shopingKart.Service.userService;
import com.org.shopingKart.entity.Users;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private userService userService;

    @GetMapping("/health")
    public String healthChk() {
        return "Health is ok";
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable("userId") ObjectId userId) {
        Users usr = userService.getById(userId);
        return usr == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(usr);
    }

    @PostMapping
    public ResponseEntity<Users> saveUser(@RequestBody Users user) {
        Users usr = userService.save(user);
        return usr == null ? ResponseEntity.noContent().build() : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{userId}")
    public ResponseEntity<Users> updateUser(@RequestBody Users users, @PathVariable("userId") ObjectId userId) {
        Users usr = userService.update(users, userId);
        return users == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(usr);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Boolean> removeUser(@PathVariable("userId") ObjectId userId) {
        Boolean isRemoved = userService.delete(userId);
        return isRemoved ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }


    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Users user = userService.login(email, password);
        if (user == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        // Store user in session
        session.setAttribute("userId", user.getId().toString());
        session.setAttribute("userRole", user.getRole());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete cookie
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }


}
