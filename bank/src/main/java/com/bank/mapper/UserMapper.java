package com.bank.mapper;

import com.bank.entity.User;
import com.bank.model.AddressM;
import com.bank.model.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserInfo convertToAccountM(User User) {
        AddressM address = new AddressM();
        address.setStreet(User.getAddress().getStreet());
        address.setCity(User.getAddress().getCity());
        address.setState(User.getAddress().getState());
        address.setCountry(User.getAddress().getCountry());
        address.setPostalCode(User.getAddress().getPostalCode());


        UserInfo UserInfo = new UserInfo();

        UserInfo.setEmail(User.getEmail());
        UserInfo.setName(User.getName());

        UserInfo.setAddress(address);
        return UserInfo;
    }

}
