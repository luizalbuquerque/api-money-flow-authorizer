package br.com.internetbanking.controller;


import br.com.internetbanking.dto.ClientDto;
import br.com.internetbanking.repository.ClientRepository;
import br.com.internetbanking.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController{

    private final ClientService userService;

    private final ClientRepository userRepository;

    public ClientController(ClientService userService, ClientRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // Criando um verbo http post para cadastro de um novo client como sugerido documentação.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody ClientDto clientDto) {
        userService.createUser(clientDto);
    }

    // Criando um verbo http post para cadastro de um novo client como sugerido documentação.

}
