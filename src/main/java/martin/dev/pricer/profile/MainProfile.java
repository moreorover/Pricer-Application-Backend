package martin.dev.pricer.profile;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainProfile {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
