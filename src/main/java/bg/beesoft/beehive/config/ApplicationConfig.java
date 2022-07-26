package bg.beesoft.beehive.config;

import bg.beesoft.beehive.model.dto.TaskAddDTO;
import bg.beesoft.beehive.model.entity.TaskEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<TaskAddDTO, TaskEntity>() {
            @Override
            protected void configure() {
                skip(destination.setId(null));
            }
        });
        return modelMapper;
    }

}
