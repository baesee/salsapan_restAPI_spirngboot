package com.billlog.rest.salsapan.service;

import com.billlog.rest.salsapan.advice.exception.CUserNotFoundException;
import com.billlog.rest.salsapan.mapper.UserMapper;
import com.billlog.rest.salsapan.model.SalsaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    private final ResponseService responseService;

    public CustomUserDetailService(ResponseService responseService) {
        this.responseService = responseService;
    }

    @Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {

        SalsaUser user = userMapper.findByUsername(user_id);

        //Optional<SalsaUser> user = userMapper.findByUsername(user_id);

        if("".equals(user.getUser_idx())){
        //if("".equals(user.get().getUser_idx())){
            throw new CUserNotFoundException();
        }else{
            return user;
//            return (UserDetails) responseService.getSingleResult(userMapper.findByUsername(user_id));
        }
    }
}
