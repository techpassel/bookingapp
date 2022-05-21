package com.tp.backend.dto;

import com.tp.backend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String name;
    private String email;
    private String country;
    private String city;
    private String img;
    private String phone;
    private UserType userType;
    private String token;
    /*
        Here we are implementing "Builder design pattern".
        To understand it better first understand a situation and find the need of this design pattern.
        Consider we have a class which have 5 fields like name, email, age, city and phone. Among these 5 fields only
        name and email is mandatory and rest other are not mandatory. So in such scenario some user will provide only
        mandatory field data and some will provide all fields data while some will provide all mandatory filed data and
        few optional field data. So to create object of this class with provided data we will have to create different
        constructors with all possible combination of fields which will be a cumbersome task. So in such scenario
        builder pattern is a rescue.
        The builder pattern, as the name implies, is an alternative way to construct complex objects. It is a design
        pattern that allows for the step-by-step creation of complex objects using the correct sequence of actions. In
        this design pattern first we create an instance of the Builder class by passing the mandatory fields to its
        constructor. Then, we set the values for the optional fields by calling the setter-like methods of the Builder
        class. Once we set all the fields, we call the "build" method on the Builder instance. This method creates the
        Product(i.e. original object) by passing the previously set values to the Product’s constructor, and it
        eventually returns a new Product instance.
        (#Note :- However it is recommended but not mandatory that we create an instance of the Builder class by
        passing all the mandatory fields(i.e. required fields) to its constructor. We can create instance of the Builder
        class by using it's no argument constructor also. In that case we have to set the values for all the required as
         well as optional fields by calling the setter-like methods of the Builder class. We are doing same here.)
     */
    public LoginResponseDto(LoginResponseBuilder loginResponseBuilder) {
    }

    public static class LoginResponseBuilder
    {
        private Long id;
        private String name;
        private String email;
        private String country;
        private String city;
        private String img;
        private String phone;
        private UserType userType;
        private String token;

        public LoginResponseBuilder id(Long id){
            this.id = id;
            return this;
        }
        public LoginResponseBuilder name(String name){
            this.name = name;
            return this;
        }
        public LoginResponseBuilder email(String email){
            this.email = email;
            return this;
        }
        public LoginResponseBuilder country(String country){
            this.country = country;
            return this;
        }
        public LoginResponseBuilder city(String city){
            this.city = city;
            return this;
        }
        public LoginResponseBuilder img(String img){
            this.img = img;
            return this;
        }
        public LoginResponseBuilder phone(String phone){
            this.phone = phone;
            return this;
        }
        public LoginResponseBuilder userType(UserType userType){
            this.userType = userType;
            return this;
        }
        public LoginResponseBuilder token(String token){
            this.token = token;
            return this;
        }

        public LoginResponseDto build(){
            LoginResponseDto loginResponseDto = new LoginResponseDto(this);
            return loginResponseDto;
        }
    }
}
