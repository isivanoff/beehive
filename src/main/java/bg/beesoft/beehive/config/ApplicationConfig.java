package bg.beesoft.beehive.config;

import bg.beesoft.beehive.model.dto.UserRegisterDTO;
import bg.beesoft.beehive.model.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        return modelMapper;
    }

}
