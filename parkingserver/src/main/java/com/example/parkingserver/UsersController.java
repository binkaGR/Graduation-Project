/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.parkingserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author binka
 */
@Controller // This means that this class is a Controller
@RequestMapping(path = "/users") // This means URL's start with /demo (after Application path)
public class UsersController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private User_InformationRepository user_InformationRepository;

  @GetMapping(path = "/getAccess/username={username}&password={password}")
  public @ResponseBody String getAccess(@PathVariable String username, @PathVariable String password) {
    String Access = null;
    int numberOfUser = 0;
    // var users = userRepository.findAll();
    for (int i = 0; i < userRepository.count(); i++) {
      if (userRepository.findAll().get(i).GetUsername().equals(username) == true
          && userRepository.findAll().get(i).GetPassword().equals(password) == true) {
        Access = userRepository.findAll().get(i).GetAccess();
        break;
      }
      numberOfUser++;
    }
    if (numberOfUser == userRepository.count()) {
      Access = "Access denied ";
    }
    return Access;
  }

  @GetMapping(path = "/GetUsers/username={username}&password={password}")
  public @ResponseBody List<UserJson> getAllParkingSpot(@PathVariable String username, @PathVariable String password) {
    int companyId = 0;
    List<UserJson> users = new ArrayList();
    UserJson user = new UserJson();
    for (int i = 0; i < userRepository.count(); i++) {
      if (userRepository.findAll().get(i).GetUsername().equals(username) == true
          && userRepository.findAll().get(i).GetPassword().equals(password) == true) {
        companyId = user_InformationRepository.findAll().get(i).GetCompanyId();
        break;
      }
    }
    for (int i = 0; i < user_InformationRepository.count(); i++) {
      if (user_InformationRepository.findAll().get(i).GetCompanyId() == companyId) {
        user.Id = userRepository.findAll().get(i).GetId();
        user.Username = userRepository.findAll().get(i).GetUsername();
        user.Password = userRepository.findAll().get(i).GetPassword();
        user.Access = userRepository.findAll().get(i).GetAccess();
        users.add(user);
        user = new UserJson();

      }
    }
    return users;
  }

  @GetMapping(path = "/GetUserInformation/username={username}&password={password}")
  public @ResponseBody UserInformationJson GetUserInfo(@PathVariable String username,
      @PathVariable String password) {
    UserInformationJson userInformationJson = new UserInformationJson();
    for (int i = 0; i < userRepository.count(); i++) {
      if (userRepository.findAll().get(i).GetUsername().equals(username) == true
          && userRepository.findAll().get(i).GetPassword().equals(password) == true) {
        userInformationJson.FirstName = user_InformationRepository.findAll().get(i).GetFirstname();
        userInformationJson.LastName = user_InformationRepository.findAll().get(i).GetLastname();
        userInformationJson.EmailAddress = user_InformationRepository.findAll().get(i).GetEmail();
        break;
      }
    }
    return userInformationJson;
  }

  @GetMapping(path = "/CreateUserCompany/{username}/{password}/{NewUsername}/{newPassowrd}/{access}/{FirstName}/{Lastname}/{Email}")
  public @ResponseBody String CreateUserCompany(@PathVariable String username, @PathVariable String password,
      @PathVariable String NewUsername, @PathVariable String newPassowrd, @PathVariable String access,
      @PathVariable String FirstName,
      @PathVariable String Lastname, @PathVariable String Email) {
    String CreateUserStatus = "User isn't create!";
    Users newUser = new Users();
    User_information newUserInformation = new User_information();
    int numberOfUser = 1;
    int companyId = 0;
    for (int i = 0; i < userRepository.count(); i++) {
      if (userRepository.findAll().get(i).GetUsername().equals(username) == true
          && userRepository.findAll().get(i).GetPassword().equals(password) == true) {
        companyId = user_InformationRepository.findAll().get(i).GetCompanyId();

      }
      numberOfUser++;
    }
    Long _numberOfUser = Long.valueOf(numberOfUser + 1);
    newUser.SetId(_numberOfUser);
    newUser.SetUseraname(NewUsername);
    newUser.SetPassword(newPassowrd);
    newUser.SetAccess(access);
    newUserInformation.SetUserId(_numberOfUser);
    newUserInformation.SetFirstname(FirstName);
    newUserInformation.SetLastname(Lastname);
    newUserInformation.SetEmail(Email);
    newUserInformation.SetCompanyId(companyId);
    boolean cheakForSameUser = false;
    for (int i = 0; i < userRepository.count(); i++) {
      if (userRepository.findAll().get(i).GetUsername().equals(NewUsername)) {
        cheakForSameUser = true;
        break;
      }
    }
    if (cheakForSameUser == true) {
      CreateUserStatus = "User isn't create!";
    } else {
      CreateUserStatus = "User is create!";
      userRepository.save(newUser);
      user_InformationRepository.save(newUserInformation);
    }
    return CreateUserStatus;
  }

  @GetMapping(path = "/CreateUserMobile/{Username}/{Passowrd}/{FirstName}/{Lastname}/{Email}")
  public @ResponseBody String CreateUserMobile(@PathVariable String Username, @PathVariable String Passowrd,
      @PathVariable String FirstName,
      @PathVariable String Lastname, @PathVariable String Email) {
    String CreateUserStatus = "User isn't create!";
    Users newUser = new Users();
    User_information newUserInformation = new User_information();
    int numberOfUser = 1;
    for (int i = 0; i < userRepository.count(); i++) {
      numberOfUser++;
    }
    Long _numberOfUser = Long.valueOf(numberOfUser + 1);
    newUser.SetId(_numberOfUser);
    newUser.SetUseraname(Username);
    newUser.SetPassword(Passowrd);
    newUser.SetAccess("client");
    newUserInformation.SetUserId(_numberOfUser);
    newUserInformation.SetFirstname(FirstName);
    newUserInformation.SetLastname(Lastname);
    newUserInformation.SetEmail(Email);
    newUserInformation.SetCompanyId(0);
    boolean cheakForSameUser = false;
    for (int i = 0; i < userRepository.count(); i++) {
      if (userRepository.findAll().get(i).GetUsername().equals(Username)) {
        cheakForSameUser = true;
        break;
      }
    }
    if (cheakForSameUser == true) {
      CreateUserStatus = "User isn't create!";
    } else {
      CreateUserStatus = "User is create!";
      userRepository.save(newUser);
      user_InformationRepository.save(newUserInformation);
    }
    return CreateUserStatus;
  }

  @DeleteMapping(path = "/DeleteUsers/{indexdeleteUser}")
  public @ResponseBody String DeleteUsers(@PathVariable int indexdeleteUser) {

    String DeleteUsersRespond = "User is not delete!";
    if (userRepository.findById(indexdeleteUser).isPresent() == true
        && user_InformationRepository.findById(indexdeleteUser).isPresent()) {

      user_InformationRepository.deleteById(indexdeleteUser);
      userRepository.deleteById(indexdeleteUser);
      DeleteUsersRespond = "User in remove!";
    } else {
      DeleteUsersRespond = "User is not delete!";
    }
    return DeleteUsersRespond;
  }

  @PutMapping(path = "UpdateUserInformation/{id}/{Password}/{Access}/{Firstname}/{Lastname}/{Email}")
  public @ResponseBody String UpdateUserInformation(@PathVariable int id, @PathVariable String Password,
      @PathVariable String Access, @PathVariable String Firstname,
      @PathVariable String Lastname, @PathVariable String Email) {
    String UpdateUserRespond = "User isn't update";
    if (userRepository.findById(id).isPresent() == true
        && user_InformationRepository.findById(id).isPresent()) {
      Optional<Users> existingUser = userRepository.findById(id);
      Optional<User_information> existingUserInfor = user_InformationRepository.findById(id);
      Users updateUser = existingUser.get();
      updateUser.SetPassword(Password);
      updateUser.SetAccess(Access);
      User_information updateUserInformation = existingUserInfor.get();
      updateUserInformation.SetFirstname(Firstname);
      updateUserInformation.SetLastname(Lastname);
      updateUserInformation.SetEmail(Email);
      userRepository.save(updateUser);
      user_InformationRepository.save(updateUserInformation);
      UpdateUserRespond = "User is update!";
    } else {
      UpdateUserRespond = "User isn't update!";
    }
    return UpdateUserRespond;
  }

  @PutMapping(path = "UpdateUserInformationClient/{Username}/{Password}/{PasswordNew}/{Firstname}/{Lastname}/{Email}")
  public @ResponseBody String UpdateUserInformationClient(@PathVariable String Username, @PathVariable String Password,
      @PathVariable String PasswordNew,
      @PathVariable String Firstname, @PathVariable String Lastname, @PathVariable String Email) {
    String UpdateUserRespond = "User isn't update";
    for (int i = 0; i < userRepository.count(); i++) {
      if (userRepository.findAll().get(i).GetUsername().equals(Username)
          && userRepository.findAll().get(i).GetPassword().equals(Password)) {
        int id = userRepository.findAll().get(i).GetId().intValue();
        Optional<Users> existingUser = userRepository.findById(id);
        Optional<User_information> existingUserInfor = user_InformationRepository.findById(id);
        Users updateUser = existingUser.get();
        updateUser.SetPassword(PasswordNew);
        User_information updateUserInformation = existingUserInfor.get();
        updateUserInformation.SetFirstname(Firstname);
        updateUserInformation.SetLastname(Lastname);
        updateUserInformation.SetEmail(Email);
        userRepository.save(updateUser);
        user_InformationRepository.save(updateUserInformation);
        UpdateUserRespond = "User is update!";
      } else {
        UpdateUserRespond = "User isn't update!";
      }
    }
    return UpdateUserRespond;
  }

}
