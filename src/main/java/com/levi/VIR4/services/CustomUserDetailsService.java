/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.levi.VIR4.services;

import com.levi.VIR4.domain.Role;
import com.levi.VIR4.domain.User;
import com.levi.VIR4.repositories.RoleRepository;
import com.levi.VIR4.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public  void deleteRights(String email){
       User user=  userRepository.findByEmail(email);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
         userRepository.save(user);
    }

    public void setRight(User user, String role){
        Role userRole = roleRepository.findByRole(role);

        if (user.getRoles()!=null){
        Set<Role> a = user.getRoles();
        a.add(userRole);
        user.setRoles(a);}
        else{
            user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        }
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("nem található a user");
        }
    }



    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}

