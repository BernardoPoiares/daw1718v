package com.isel.daw.checklist.services;

import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.model.RequestsDTO.UserRequestDTO;
import com.isel.daw.checklist.repositories.UserRepository;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.utils.ValidatorObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;

@Component
public class UserService implements Service {
    public final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public ResponseEntity<?> getUser(String username){
        List<Users> res=userRepository.findAll();
        return new ResponseEntity<>(res.size(),
                HttpStatus.ACCEPTED
        );

    }

    public ResponseEntity<?> create(UserRequestDTO userdto){
        Users user=userRepository.findByUsername(userdto.getUsername());
        if(user!=null)
            return new ResponseEntity<>(null,
                    HttpStatus.BAD_REQUEST
            );
        Users newuser=new Users(userdto.getUsername(), userdto.getPassword(),Base64.getEncoder().encodeToString((userdto.getUsername()+":"+userdto.getPassword()).getBytes()));
        userRepository.save(newuser);
        return new ResponseEntity<>("User "+userdto.getUsername()+"created.",
                HttpStatus.ACCEPTED
        );
    }
/*
    //CHECK AGAIN!!!
    public ResponseEntity<?> login(String authorization){
        Users user=userRepository.findByUsername(null);
        ValidatorObj valobj=validateUser(user);
        if(valobj.error!=null)
            return new ResponseEntity<>(null,
                    HttpStatus.BAD_REQUEST
            );
        String encoding=Base64.getEncoder().encodeToString((user.getUserName()+":"+user.getPassword()).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authoritazion", "Basic "+encoding);
        return new ResponseEntity<>(null,
                headers,
                HttpStatus.ACCEPTED
        );

    }*/

        private ValidatorObj validateUser(Users user){
            return new ValidatorObj();
        }

}
