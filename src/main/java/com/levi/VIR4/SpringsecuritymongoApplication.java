package com.levi.VIR4;

import com.levi.VIR4.domain.Role;
import com.levi.VIR4.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringsecuritymongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecuritymongoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }

            Role jpgRole = roleRepository.findByRole("JPG");
            if (jpgRole == null) {
                Role newJpgRole = new Role();
                newJpgRole.setRole("JPG");
                roleRepository.save(newJpgRole);
            }

            Role pngRole = roleRepository.findByRole("PNG");
            if (pngRole == null) {
                Role newPngRole = new Role();
                newPngRole.setRole("PNG");
                roleRepository.save(newPngRole);
            }

            Role gifRole = roleRepository.findByRole("GIF");
            if (gifRole == null) {
                Role newGifRole = new Role();
                newGifRole.setRole("GIF");
                roleRepository.save(newGifRole);
            }
        };

    }

}
