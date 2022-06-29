package bg.beesoft.beehive.model.user;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentUser {

  private Long id;
  private String name;
  private boolean loggedIn;

  public String getName() {
    return name;
  }

  public CurrentUser setName(String name) {
    this.name = name;
    return this;
  }

  public Long getId() {
    return id;
  }

  public CurrentUser setId(Long id) {
    this.id = id;
    return this;
  }

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public boolean isGuest() {
    return !isLoggedIn();
  }

  public CurrentUser setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
    return this;
  }

  public void clear() {
    loggedIn = false;
    name = null;
    id = null;
  }
}
