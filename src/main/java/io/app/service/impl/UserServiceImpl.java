package io.app.service.impl;

import io.app.dto.AddressDto;
import io.app.dto.ApiResponse;
import io.app.dto.UserDto;
import io.app.exceptions.ResourceNotFoundException;
import io.app.models.Address;
import io.app.models.User;
import io.app.repository.UserRepository;
import io.app.service.JwtService;
import io.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    @Value("${files.profile-pic}")
    private String path;

    @Override
    public UserDto profile(String token) {
        String email=jwtService.extractUsername(token);
        User user=repository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User Details Not Availble!"));
        UserDto userDto=modelMapper.map(user,UserDto.class);
        return userDto;
    }

    @Override
    public ApiResponse updateAddress(String token,AddressDto addressDto) {
        String email=jwtService.extractUsername(token);
        User user=repository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User Details Not Availble!"));
        Address address=modelMapper.map(addressDto,Address.class);
        if(user.getAddress()==null) {
            address.setUser(user);
            user.setAddress(address);
        }else{
            user.getAddress().setCity(addressDto.getCity());
            user.getAddress().setStreet(addressDto.getStreet());
            user.getAddress().setState(addressDto.getState());
            user.getAddress().setPostalCode(addressDto.getPostalCode());
        }
        repository.save(user);
        return ApiResponse.builder()
                .msg("Address updated successfully")
                .status(true)
                .build();
    }

    @Override
    public ApiResponse updateMobileNumber(String token, String mobileNo) {
        String email=jwtService.extractUsername(token);
        User user=repository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User Details Not Availble!"));
        user.setMobile(mobileNo);
        repository.save(user);
        return new ApiResponse("Mobile Number Updated Successfully",true);
    }

    @Override
    public ApiResponse updateProfilePic(String token, MultipartFile file) {
       //Gettting user
        String email=jwtService.extractUsername(token);
        User user=repository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User Details Not Availble!"));

        // If root path is not available so create
        File folder=new File(this.path);
        if(!folder.exists()){
            folder.mkdir();
        }

        try{
            //Make the full path
            String extainsion=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fullPath=this.path+File.separator+user.getId()+extainsion;

            //Process of saving data
            byte[] image=file.getBytes();
            FileOutputStream fileOutputStream=new FileOutputStream(fullPath);
            fileOutputStream.write(image);
            fileOutputStream.flush();
            fileOutputStream.close();

            //update user path
            user.setProfileUrl(fullPath);
            repository.save(user);

            return new ApiResponse("Profile pic uploaded Successfully!",true);
        }catch (Exception exception){
            return new ApiResponse("Something went wrong!",false);
        }
    }

    @Override
    public ResponseEntity<byte[]> getProfilePic(String token) throws IOException {
        String email=jwtService.extractUsername(token);
        User user=repository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User Details Not Availble!"));

        if(user.getProfileUrl()==null){
            throw new ResourceNotFoundException("No such profile pic available!");
        }


        Path path1=Paths.get(this.path).resolve(user.getProfileUrl().substring(user.getProfileUrl().lastIndexOf("/"))).normalize();

        FileInputStream fileInputStream=new FileInputStream(user.getProfileUrl());
        byte[] image=new byte[fileInputStream.available()];
        image=fileInputStream.readAllBytes();
        fileInputStream.close();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path1));

        return new ResponseEntity<>(image,headers, HttpStatus.OK);
    }
}
