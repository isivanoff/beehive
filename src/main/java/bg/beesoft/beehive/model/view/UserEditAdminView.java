package bg.beesoft.beehive.model.view;

public class UserEditAdminView {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String imageUrl;

    public UserEditAdminView() {
    }

    public Long getId() {
        return id;
    }

    public UserEditAdminView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEditAdminView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditAdminView setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditAdminView setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserEditAdminView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
