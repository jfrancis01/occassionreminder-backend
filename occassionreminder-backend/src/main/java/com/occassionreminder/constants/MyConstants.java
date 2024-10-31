package com.occassionreminder.constants;

public final class MyConstants {
	
	private MyConstants() {
		
	}
	
	public static enum OccassionType{
		Birthday, Confirmation, Wedding, Anniversary, Graduation;
	}
	
	public static enum Offset{
		dayoff{
			public String toString() {
				return "day off";
			}
		},
		daybefore{
			public String toString() {
				return "day before";
			}
		},
		weekbefore{
			public String toString() {
				return "week before";
			}
		}
	}
	
	public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
	public static final String ERROR_OCCURED = "An error occured";
	public static final String INVALID_USERNAME_PASSWORD = "Invalid username or password";
	public static final String INVALID_USERNAME = "Invalid username or password";
	public static final String INVALID_USERID = "Invalid User ID";
	public static final String LOGIN_SUCCESSFUL = "Login successful";
	public static final String UPDATE_SUCCESSFUL = "Updaet Successful";
	public static final String JWT_SECRET_KEY = "JWT_SECRET_KEY";
	public static final String DEFUALT_SECRET_KEY = "nNYrCCg0Tqu02VRvoySj9FbvjKSp8W8fnNYrCCg0Tqu02VRvoySj9FbvjKSp8W8f";
	public static final String JWT_HEADER = "Authorization";
	public static final String KEYCLOACK_REGISTER_USER_ENDPOINT = "/admin/realms/OccassionReminder-dev/users";
}
