package io.app.service;

import io.app.dto.AddressDto;
import io.app.dto.ApiResponse;
import io.app.dto.UserDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface UserService {
    public UserDto profile(String token);
    public ApiResponse updateAddress(String token,AddressDto addressDto);
    public ApiResponse updateMobileNumber(String token,String mobileNo);
    public ApiResponse updateProfilePic(String token, MultipartFile file) throws IOException;
    public ResponseEntity<byte[]> getProfilePic(String token) throws IOException;
}
