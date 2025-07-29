package com.major_project.e_commerce.security.user;

import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.model.User;
import com.major_project.e_commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.of(userRepository.findByEmail(email))
                .orElseThrow(() -> new ResourceNotFoundException("User with this email not found!!"));
        return new ShopUserDetails().buildShopUserDetails(user);
    }
}
