package com.occassionreminder.service;

import java.util.ArrayList;
import java.util.List;
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

import com.fasterxml.jackson.core.JsonProcessingException;
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
	
	/**
	 * The admin manage user needs to be created in the appropriate realm with the 
	 * following steps:
	 *  1. Create a client with standards flow, client_authentication, direct access grants 
	 *  	and service  accounts roles checked
	 *  2. In the service account roles tab make sure you add "manage-users"
	 * @return
	 */
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
            return new ResponseEntity<String>(MyConstants.ERROR_OCCURED, HttpStatus.BAD_REQUEST);
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
		UsersResource usersResource = realmResource.users();
		// create user
		try {
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
			return new ResponseEntity<>(mapper.writeValueAsString(MyConstants.REGISTRATION_COMPLETE),HttpStatus.OK);
		}
		
		catch(Exception createException) {
			return new ResponseEntity<>(createException.getMessage(), HttpStatus.METHOD_FAILURE);
		}
	}

	@Override
	public ResponseEntity<String> editUser(User user) {
		
		Keycloak keycloak = getAdminKeycloak();
		try {
            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
            System.out.println("Access Token for client: " + tokenResponse.getToken());
        } catch (Exception e) {
            System.out.println("Failed to retrieve client access token: " + e.getMessage());
            return new ResponseEntity<String>(MyConstants.ERROR_OCCURED, HttpStatus.BAD_REQUEST);
        }
		RealmResource realmResource = keycloak.realm(realm);
		UsersResource usersResource = realmResource.users();
		List<UserRepresentation> search = usersResource.search("id:"+user.getUserID(), 1, 1, false);
		if(search.size() > 1) {
			return new ResponseEntity<>(MyConstants.USERID_ID_NOT_UNIQUE, HttpStatus.FORBIDDEN);
		}
		if(search.size() < 1) {
			return new ResponseEntity<>(MyConstants.USER_ID_INVALID, HttpStatus.BAD_REQUEST);
		}
		
		UserRepresentation original = search.get(0);
		original.setFirstName(user.getFirstName());
		original.setLastName(user.getLastName());
		original.setEmail(user.getEmail());
		UserResource userResource = usersResource.get(user.getUserID());
		userResource.update(original);
		
		return new ResponseEntity<>(MyConstants.UPDATE_SUCCESSFUL, HttpStatus.OK);
	}

	@Override
	public String deleteUser(String userID) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public ResponseEntity<String> getUserProfile(String userID) {
		
		Keycloak keycloak = getAdminKeycloak();
		try {
            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
            System.out.println("Access Token for client: " + tokenResponse.getToken());
        } catch (Exception e) {
            System.out.println("Failed to retrieve client access token: " + e.getMessage());
            return new ResponseEntity<String>(MyConstants.ERROR_OCCURED, HttpStatus.BAD_REQUEST);
        }
		RealmResource realmResource = keycloak.realm(realm);
		UsersResource usersResource = realmResource.users();
		List<UserRepresentation> search = usersResource.search("id:"+userID, 1, 1, true);
		if(search.size() > 1) {
			return new ResponseEntity<>(MyConstants.USERID_ID_NOT_UNIQUE, HttpStatus.FORBIDDEN);
		}
		if(search.size() < 1) {
			return new ResponseEntity<>(MyConstants.USER_ID_INVALID, HttpStatus.BAD_REQUEST);
		}
		UserRepresentation original = search.get(0);
		try {
			return new ResponseEntity<>(mapper.writeValueAsString(new User(original.getId(),
					original.getFirstName(), original.getLastName(), original.getEmail())), HttpStatus.OK);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(MyConstants.ERROR_OCCURED, HttpStatus.BAD_REQUEST);
		}
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
