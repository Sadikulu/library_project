package com.lib.exception;

public class ErrorMessage {

	public final static String RESOURCE_NOT_FOUND_MESSAGE="Resource with id %d not found";

	public final static String ROLE_NOT_FOUND_MESSAGE = "Role : %s not found";

	public final static String JWTTOKEN_ERROR_MESSAGE = "JWT Token Validation Error: %s";

	public final static String USER_NOT_FOUND_MESSAGE = "Email or password doesn't matched.";

	public final static String PRINCIPAL_FOUND_MESSAGE= "User not found";

	public final static String IMAGE_NOT_FOUND_MESSAGE = "ImageFile with id %s not found";

	public final static String IMAGE_USED_MESSAGE = "Image already used";

	public final static String BOOK_CONFLICT_EXCEPTION = "Book '%s' is already exist";
}
