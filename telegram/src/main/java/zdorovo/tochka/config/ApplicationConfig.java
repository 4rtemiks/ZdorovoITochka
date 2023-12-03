package zdorovo.tochka.config;

import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zdorovo.tochka.convertor.OrdinalToEnumConverterFactory;

public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new OrdinalToEnumConverterFactory());
    }


}
