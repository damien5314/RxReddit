package rxreddit.api;

public class AuthorizationCallback {
  public String state;
  public String code;

  public AuthorizationCallback(String state, String code) {
    this.state = state;
    this.code = code;
  }
}
