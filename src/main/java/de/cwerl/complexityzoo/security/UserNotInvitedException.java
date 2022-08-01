package de.cwerl.complexityzoo.security;

public final class UserNotInvitedException extends RuntimeException {
    public UserNotInvitedException() {
        super();
    }

    public UserNotInvitedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotInvitedException(final String message) {
        super(message);
    }
    
    public UserNotInvitedException(final Throwable cause) {
        super(cause);
    }
}
