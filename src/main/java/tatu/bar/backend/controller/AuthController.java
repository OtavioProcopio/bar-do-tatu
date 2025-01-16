package tatu.bar.backend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tatu.bar.backend.dto.LoginRequestDTO;
import tatu.bar.backend.dto.RegisterRequestDTO;
import tatu.bar.backend.dto.ResponseDTO;
import tatu.bar.backend.entity.Usuario;
import tatu.bar.backend.service.TokenService;
import tatu.bar.backend.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        
        try{
            Usuario user = usuarioService.buscarPorEmail(body.email());

            if (passwordEncoder.matches(body.password(), user.getPassword())){
                String token = this.tokenService.generateToken(user);
                return ResponseEntity.ok(new ResponseDTO(user.getEmail(), token));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login");
        }
    }

    
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        try{
            Usuario newUser = usuarioService.criarUsuario(body);
            String token = tokenService.generateToken(newUser);

            return ResponseEntity.ok(new ResponseDTO(newUser.getEmail(), token));
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
