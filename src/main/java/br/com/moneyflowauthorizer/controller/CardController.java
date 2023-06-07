package br.com.moneyflowauthorizer.controller;

import br.com.moneyflowauthorizer.dto.ClientDto;
import br.com.moneyflowauthorizer.entity.ClientEntity;
import br.com.moneyflowauthorizer.exeption.BusinessException;
import br.com.moneyflowauthorizer.repository.ClientRepository;
import br.com.moneyflowauthorizer.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController{

    private final ClientService clientService;

    private final ClientRepository clientRepository;

    public ClientController(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> save(@RequestBody ClientDto clientDto) {
        try {
            clientService.createClient(clientDto);
            return ResponseEntity.ok("Cliente criado com sucesso");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao criar cliente: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientEntity>> list() {
        List<ClientEntity> clients = clientRepository.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return clientService.findUserById(id);
            //return ResponseEntity.ok(clientService.findUserById(id));  -> Apresenta headers, boddy, status code..
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> update(@RequestBody ClientDto form, @PathVariable("id") Long id) {
        return clientService.updateCustumerById(form, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        try {
            return clientService.deleteById(id);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Erro ao deletar cliente: " + e.getMessage());
        }
    }

}
