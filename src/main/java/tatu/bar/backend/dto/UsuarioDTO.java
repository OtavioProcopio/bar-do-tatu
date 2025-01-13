package tatu.bar.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class UsuarioDTO {

    private String name;

    private String email;

    private String password;
}
