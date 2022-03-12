package com.paragon.visitorchek.security;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paragon.visitorchek.model.VisitRequest;
import com.paragon.visitorchek.usermanagement.ERole;
import com.paragon.visitorchek.usermanagement.Role;
import com.paragon.visitorchek.usermanagement.RoleRepository;
import com.paragon.visitorchek.usermanagement.SignupRequest;
import com.paragon.visitorchek.usermanagement.User;
import com.paragon.visitorchek.usermanagement.UserDetailsImpl;
import com.paragon.visitorchek.usermanagement.UserRepository;
import com.paragon.visitorchek.util.ImageUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags="Authentication", description="Manages Authentication operation")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;	
 
	final String baseUrl = "https://portal.talentchek.com/tc/app/mobileApp/mobilelogin";
	
	final String talentIdUrl = "https://portal.talentchek.com/tc/hrms/master/employeeAdminMaster/visitorIdFromMobileNo?mobileNo=";
	
	@ApiOperation(value = "Sign In")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		RestTemplate restTemplate = new RestTemplate();  
		JSONObject obj=new JSONObject();    
		  obj.put("username",loginRequest.getUsername());    
		  obj.put("password",loginRequest.getPassword());   
	    ResponseEntity<Object> result = restTemplate.postForEntity(baseUrl, obj, Object.class); 
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
	 

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles,ImageUtility.decompressImage(userDetails.getImage())));
	}
	
	@ApiOperation(value = "Get TalentId from mobilenumber")
	@GetMapping("/talentId")
	public JSONObject getTalentIdByMobile(@RequestParam("mobileNo") String mobileNo) {
		RestTemplate restTemplate = new RestTemplate();   
	    ResponseEntity<String> result = restTemplate.getForEntity(talentIdUrl+mobileNo,  String.class); 
	    JSONObject obj=new JSONObject();    
		  obj.put("talentId",result.getBody());        
	    return obj;
	}

	@ApiOperation(value = "Get user info by token")
	@GetMapping("/userbytoken")
	public Optional<User> getUserDetail(@RequestParam("token") String jwtToken) {
		Optional<User> userDetails = null;
		if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
			String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
			userDetails = userRepository.findByUsername(username);
			userDetails.get().setPassword(null);

		}
		return userDetails;
	}

	@ApiOperation(value = "Sign Up") 
	@RequestMapping(value = "/signup") 
	public ResponseEntity<?> registerUser(@RequestParam("image") MultipartFile file,
			@RequestParam("name")  String  name,@RequestParam("username")  String  username,@RequestParam("mobilenumber")  String  mobilenumber,
			@RequestParam("email")  String  email,@RequestParam("password")  String  password,@RequestParam("usertype")  String  usertype)   throws IOException  {
		 if (userRepository.existsByUsername(username)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(email)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		if (userRepository.existsByMobilenumber(mobilenumber)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Mobile Number is already in use!"));
		}
		// Create new user's account
	
		Set<String> strRoles =new HashSet<>();
		if(usertype.equals("Host"))
			strRoles.add(ERole.ROLE_ADMIN.toString());
		else strRoles.add(ERole.ROLE_USER.toString());
  
		Set<Role> roles = new HashSet<>();

		User user = new User();
		
		
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(encoder.encode(password));
		user.setMobilenumber(mobilenumber);
		user.setUsertype(usertype); 
		user.setImage(ImageUtility.compressImage(file.getBytes())); 

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		 

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
}
