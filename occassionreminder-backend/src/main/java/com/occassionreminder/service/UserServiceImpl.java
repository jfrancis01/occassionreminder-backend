package com.occassionreminder.service;

import java.util.ArrayList;
import java.util.Optional;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.occassionreminder.constants.MyConstants;
import com.occassionreminder.model.AuthResponseData;
import com.occassionreminder.model.Occassion;
import com.occassionreminder.model.User;
import com.occassionreminder.repository.UserRepository;

import jakarta.ws.rs.core.Response;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepo;
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private Environment environment;
	private String base = null;
	private String realm = null;
	private String username = null;
	private String password = null;

	public UserServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	private Keycloak getAdminKeycloak() {
		this.base = environment.getProperty(MyConstants.KEYCLOAK_BASE);
		this.realm = environment.getProperty(MyConstants.KEYCLOAK_REALM);
		this.username = environment.getProperty(MyConstants.KEYCLOAK_SERVER_API_CLIENTID);
		this.password = environment.getProperty(MyConstants.KEYCLOAK_SERVICE_API_SECRET);
		return KeycloakBuilder.builder().serverUrl(base)
				.realm(realm)
				.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
				.clientId(username)
				.clientSecret(password).build();
	}

	@Override
	public ResponseEntity<String> registerUser(User user) {
		
		Keycloak keycloak = getAdminKeycloak();
		try {
            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
            System.out.println("Access Token for client: " + tokenResponse.getToken());
        } catch (Exception e) {
            System.out.println("Failed to retrieve client access token: " + e.getMessage());
        }
    
		// set user representation
		UserRepresentation newuser = new UserRepresentation();
		newuser.setUsername(user.getEmail());
		newuser.setEmail(user.getEmail());
		newuser.setFirstName(user.getFirstName());
		newuser.setLastName(user.getLastName());
		newuser.setEnabled(true);
		// Get realm
		RealmResource realmResource = keycloak.realm(realm);
		System.out.print(realmResource.toRepresentation().toString());
		UsersResource usersResource = realmResource.users();
		// create user
		Response response = usersResource.create(newuser);
		System.out.print(response.getStatus());
		String userID = CreatedResponseUtil.getCreatedId(response);
		if (userID == null || userID.isEmpty()) {
			return new ResponseEntity<>(MyConstants.ERROR_OCCURED, HttpStatus.METHOD_FAILURE);
		}
		// set credentials
		CredentialRepresentation passwordCred = new CredentialRepresentation();
		passwordCred.setTemporary(false);
		passwordCred.setType(CredentialRepresentation.PASSWORD);
		passwordCred.setValue(user.getPassword());
		UserResource userResource = usersResource.get(userID);
		userResource.resetPassword(passwordCred);

		return new ResponseEntity<>(MyConstants.REGISTRATION_COMPLETE, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> editUser(User user) {
		Optional<User> original = userRepo.findByUserID(user.getUserID());
		if (!original.isPresent()) {
			return new ResponseEntity<>(MyConstants.INVALID_USERID, HttpStatus.BAD_REQUEST);
		} else {
			userRepo.updateUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getUserID());
		}
		return new ResponseEntity<>(MyConstants.UPDATE_SUCCESSFUL, HttpStatus.OK);
	}

	@Override
	public String deleteUser(String userID) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public ResponseEntity<String> getUserProfile(String userID) {
		Optional<User> user = userRepo.findByUserID(userID);
		if (user.isPresent()) {
			try {
				return new ResponseEntity<>(mapper.writeValueAsString(new User(user.get().getUserID(),
						user.get().getFirstName(), user.get().getLastName(), user.get().getEmail())), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>(MyConstants.ERROR_OCCURED, HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>(MyConstants.INVALID_USERID, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ArrayList<Occassion> getOccassions(String userID) {
		return null;
	}

	@Override
	public ResponseEntity<String> login(Authentication authentication) {
		try {
			Optional<User> found = userRepo.findByEmail(authentication.getName());
			if (!found.isPresent()) {
				return new ResponseEntity<>(MyConstants.INVALID_USERNAME_PASSWORD, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(mapper.writeValueAsString(new AuthResponseData(found.get().getEmail(),
						found.get().getUserID(), true, found.get().getFirstName(), found.get().getLastName())),
						HttpStatus.OK);
			}
		} catch (Exception exception) {
			return new ResponseEntity<String>(MyConstants.INVALID_USERNAME_PASSWORD, HttpStatus.BAD_REQUEST);
		}

	}

}
