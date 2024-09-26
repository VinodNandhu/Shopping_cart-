package com.newproject.dreamshops.Service.User;

import com.newproject.dreamshops.DTO.OrderDto;
import com.newproject.dreamshops.DTO.UserDto;
import com.newproject.dreamshops.Entity.User;
import com.newproject.dreamshops.Repository.UserRepository;
import com.newproject.dreamshops.Service.Order.OrderServiceImpl;
import com.newproject.dreamshops.exceptions.ResourceNotFoundException;
import com.newproject.dreamshops.exceptions.UserAlreadyExistsException;
import com.newproject.dreamshops.request.CreateUserRequest;
import com.newproject.dreamshops.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final OrderServiceImpl orderService;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));  // Ensure password is encoded
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }


    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(existingUser.getFirstName());
            existingUser.setLastName(existingUser.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("user not found");
        });
    }

    @Override
    public UserDto mapUserToDto(User user) {
        // Convert basic fields
        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Add orders if they exist
        if (user.getOrder() != null && !user.getOrder().isEmpty()) {
            List<OrderDto> orderDtoList = user.getOrder().stream()
                    .map(orderService::convertToDto)
                    .collect(Collectors.toList());
            userDto.setOrders(orderDtoList);
        }

        return userDto;
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
