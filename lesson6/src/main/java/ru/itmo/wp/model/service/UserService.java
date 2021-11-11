package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @noinspection UnstableApiUsage
 */
public class UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private static final String PASSWORD_SALT = "177d4b5f2e4f4edafa7404533973c04c513ac619";

    public void validateRegistration(User user, String password, String confirmationPassword) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 12) {
            throw new ValidationException("Password can't be longer than 12 characters");
        }
        if (!password.equals(confirmationPassword)) {
            throw new ValidationException("Password and confirmation password must be the same");
        }


        if (Strings.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
        }
        if (!(user.getEmail().contains("@") && user.getEmail().split("@").length == 2)) {
            throw new ValidationException("Invalid email");
        }

    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateEnter(String loginOrEmail, String password) throws ValidationException {

        if (Strings.isNullOrEmpty(loginOrEmail)) {
            throw new ValidationException("Name must be not empty");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password must be not empty");
        }

        User user = userRepository.findByLoginAndPasswordSha(loginOrEmail, getPasswordSha(password));

        if (user == null) {
            user = userRepository.findByEmailAndPasswordSha(loginOrEmail, getPasswordSha(password));
        }

        if (user == null) {
            throw new ValidationException("Invalid login, email or password");
        }
    }

    public User findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
    }
    public User findByLoginOrEmailAndPassword(String login, String password) {
        return userRepository.findByLoginOrEmailAndPasswordSha(login, getPasswordSha(password));
    }

    public Long findCount() {
        return userRepository.findCount();
    }
    public User findByLogin(String login) {
        return  userRepository.findByLogin(login);
    }

    public User find(Long id) {
        return userRepository.find(id);
    }

}
