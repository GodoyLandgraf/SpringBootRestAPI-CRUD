package br.com.springboot.CursoSpringBoot.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.CursoSpringBoot.model.Usuario;
import br.com.springboot.CursoSpringBoot.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	/* Injeção de dependencia */
	@Autowired 
	private UsuarioRepository usuarioRepository;
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuarioRepository.save(usuario); /*grava no banco de dados*/
    	return "Ola mundo: " + nome;
    }
    
    @GetMapping(value = "listatodos")  /*Primeiro método API*/
    @ResponseBody /* retorna os dados para o corpo da resposta */
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /*retorna a lista em JSON*/
    	
    }
    
    
    @PostMapping(value = "salvar") /* Mapeia URL*/
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ /* recebe os dados para salvar */
    	
    	Usuario user = usuarioRepository.save(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    @DeleteMapping(value = "delete") /* Mapeia URL*/
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity<String> delete(@RequestParam Long iduser){ /* recebe os dados para salvar */
    	
    	usuarioRepository.deleteById(iduser);
    	return new ResponseEntity<String>("User Deletado com sucesso", HttpStatus.OK);
    }
    
    @PutMapping(value = "atualizar") /* Mapeia URL*/
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ /* recebe os dados para salvar */
    	if(usuario.getId()==null) {
        	return new ResponseEntity<String>("Id não foi informado!", HttpStatus.OK);
    	}
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscaruserid") /* Mapeia URL*/
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser){ /* recebe os dados para salvar */
    	Usuario usuario = usuarioRepository.findById(iduser).get();
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarPorNome") /* Mapeia URL*/
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name){ /* recebe os dados para salvar */
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
    
}
