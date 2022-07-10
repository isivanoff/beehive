package bg.beesoft.beehive.model.view;

public class ApiaryView {
    private Long id;
    private String name;

    public ApiaryView() {
    }

    public Long getId() {
        return id;
    }

    public ApiaryView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApiaryView setName(String name) {
        this.name = name;
        return this;
    }
}
