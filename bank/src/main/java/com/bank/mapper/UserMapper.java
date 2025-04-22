package com.bank.mapper;

import com.bank.entity.User;
import com.bank.model.AddressM;
import com.bank.model.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserInfo convertToAccountM(User user) {

        UserInfo UserInfo = new UserInfo();
        if(user != null) {
            AddressM address = new AddressM();
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setCountry(user.getAddress().getCountry());
            address.setPostalCode(user.getAddress().getPostalCode());



        UserInfo.setEmail(user.getEmail());
        UserInfo.setName(user.getName());
        UserInfo.setAddress(address);
        }
        return UserInfo;
    }

}
