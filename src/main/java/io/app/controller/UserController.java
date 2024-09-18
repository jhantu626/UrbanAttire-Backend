package io.app.controller;

import io.app.dto.AddressDto;
import io.app.dto.ApiResponse;
import io.app.dto.UserDto;
import io.app.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserServiceImpl service;


    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public UserDto profile(@RequestHeader("Authorization") String token){
        token=token.substring(7);
        return service.profile(token);
    }

    @PutMapping("/update-address")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateAddress(@RequestHeader("Authorization") String token,
                                     @RequestBody AddressDto addressDto){
        token=token.substring(7);
        return service.updateAddress(token,addressDto);
    }

    @PutMapping("update-number/{number}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateMobileNumber(@RequestHeader("Authorization") String token,
                                     @PathVariable String number){
        token=token.substring(7);
        number="+91 "+number;
        return service.updateMobileNumber(token,number);
    }

    @PutMapping("update-profile-pic")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse uploadProfilePic(@RequestHeader("Authorization") String token,
                                        @RequestParam("profile") MultipartFile multipartFile) throws IOException {
        token=token.substring(7);
        return service.updateProfilePic(token,multipartFile);
    }

    @GetMapping("/profile-pic")
    public ResponseEntity<byte[]> getProfilePic(@RequestHeader("Authorization") String token) throws IOException {
        token=token.substring(7);
        return service.getProfilePic(token);
    }



}










