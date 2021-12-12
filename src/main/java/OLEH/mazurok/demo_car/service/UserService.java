package alla.verkhohliadova.demo_car.service;

import alla.verkhohliadova.demo_car.dto.request.UserRequest;
import alla.verkhohliadova.demo_car.dto.response.AuthenticationResponse;
import alla.verkhohliadova.demo_car.dto.response.UserResponse;
import alla.verkhohliadova.demo_car.entity.User;
import alla.verkhohliadova.demo_car.entity.UserRole;
import alla.verkhohliadova.demo_car.repository.UserRepository;
import alla.verkhohliadova.demo_car.security.JwtTokenTool;
import alla.verkhohliadova.demo_car.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenTool jwtTokenTool;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public Boolean register (UserRequest request){ //AuthenticationResponse register(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return false;
            //throw new BadCredentialsException("User with username " + request.getUsername() + " already exists");
        }
        else {
            User user = new User();
            user.setFirstname(request.getFirstname());
            user.setSurname(request.getSurname());
            user.setUsername(request.getUsername());
            user.setPassword(encoder.encode(request.getPassword()));
            user.setSex(request.getSex());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setUserRole(UserRole.ROLE_USER);

            userRepository.save(user);
            return true;
        }

        //return login(request);
    }

    public AuthenticationResponse login(UserRequest request) {
        String username = request.getUsername();
        User user = findByUsername(username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
        String token = jwtTokenTool.createToken(username, user.getUserRole());
        //System.out.println(user.getUsername() + ", " + user.getFirstname());
        user.setToken(token);
        userRepository.save(user);
        System.out.println(token);
        return new AuthenticationResponse(username, token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new JwtUser(user.getUsername(), user.getUserRole(), user.getPassword());
    }

    public User findByUsername(String username)  {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not exists"));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public void delete(Long id) throws IllegalArgumentException {
        userRepository.delete(findOne(id));
    }

    public User findOne(Long id) {
        return userRepository.findUserById(id);
                //.orElseThrow(() -> new IllegalArgumentException ("User with id " + id + " not exists"));
    }

    public UserRole findUserRoleByUsername(UserRequest userRequest){
        String username = userRequest.getUsername();
        User user = findByUsername(username);
        UserRole userRole = user.getUserRole();
        return userRole;
    }

    public String findTokenByUsername(String username){
        User user = findByUsername(username);
        String token = user.getToken();
        return token;
    }

    public User findByToken(String token)  {
        return userRepository.findByToken(token).orElseThrow(() -> new UsernameNotFoundException("User with token " + token + " not exists"));
    }

    public Long findUserIdByToken(String token){
        User user = findByToken(token);
        Long userId = user.getId();
        return userId;
    }

    public void update(Long id, UserRequest userRequest){
        User user = findOne(id);
        if(!userRequest.getPassword().isEmpty()){
            user.setPassword(encoder.encode(userRequest.getPassword()));
        }
        if(!userRequest.getEmail().isEmpty()){
            user.setEmail(userRequest.getEmail());
        }
        if(!userRequest.getPhone().isEmpty()){
            user.setPhone(userRequest.getPhone());
        }
        userRepository.save(user);
        //userRepository.save(userRequestToUserUpdate(findOne(id), userRequest));
    }

    /*public String findEmailByUsername(UserRequest userRequest){
        String username = userRequest.getUsername();
        User user = findByUsername(username);
        String email = user.getEmail();
        return email;
    }*/
}
