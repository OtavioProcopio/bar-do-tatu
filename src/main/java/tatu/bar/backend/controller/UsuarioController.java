package tatu.bar.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import tatu.bar.backend.dto.UsuarioDTO;
import tatu.bar.backend.entity.Usuario;
import tatu.bar.backend.service.UsuarioService;

@RestController
@Tag(name = "Usuarios", description = "Apis de Crud para Usuarios")
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    
    @PostMapping("/save")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping("/findByName/{nome}")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorNome(@PathVariable String name) {
        return ResponseEntity.ok(usuarioService.buscarPorNome(name));
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<List<Usuario>> buscarUsuarioPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }



    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable UUID id, @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }




}
