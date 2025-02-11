package su.sendandsolve.server.data.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PaginationConfig {

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer paginatorCustomizer() {
        return resolver -> {
            resolver.setMaxPageSize(100); // Максимум 100 элементов на странице
            resolver.setFallbackPageable(PageRequest.of(0, 25)); // Дефолтные 20 элементов
        };
    }
}
