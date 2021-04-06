package com.upgrade.campsite.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class Config {

    public static List<LocalDate> reservedDates = new ArrayList<>();
    private static final Object addLocker = new Object();
    private static final Object removeLocker = new Object();

    public static void addReservedDates(List<LocalDate> localDateList) {
        synchronized (addLocker) {
            reservedDates.addAll(localDateList);
        }
    }
    public static void removeReservedDates(List<LocalDate> localDateList) {
        synchronized (removeLocker) {
            reservedDates.removeAll(localDateList);
        }
    }

    public static Optional<List<LocalDate>> getReservedDates() {
        return Optional.of(reservedDates);
    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("sample application API")
                        .version("appVersion")
                        .description("appDesciption")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }


}
