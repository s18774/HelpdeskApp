package pl.wroblewski.helpdeskapp.services;

import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.InvalidCredentialsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;

@Service
public class UserService {
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authUser(String login, String password) throws InvalidCredentialsException {
        User user = userRepository.findByLogin(login).orElseThrow(InvalidCredentialsException::new);
        //dalsze porowanie hasla itp
        //sprawdzanie do jakiego role należy login
    }
}
