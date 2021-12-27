package com.tqi.lending.service;

import com.tqi.lending.exception.ClientNotFoundException;
import com.tqi.lending.model.Client;
import com.tqi.lending.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Page<Client> findAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new ClientNotFoundException("Client not found");
        }
    }

    public Client createClient(Client client) {
        clientRepository.save(client);
        return client;
    }

    public void deleteClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            clientRepository.delete(client.get());
        } else {
            throw new ClientNotFoundException("Client not found");
        }
    }

    public void updateClient(Long id, Client newClient) {
        clientRepository.findById(id)
                .map(client -> {
                    client.setName(newClient.getName());
                    client.setEmail(newClient.getEmail());
                    client.setCpf(newClient.getCpf());
                    client.setRg(newClient.getRg());
                    client.setAddress(newClient.getAddress());
                    client.setIncome(newClient.getIncome());
                    Client updateClient = clientRepository.save(client);
                    return client;
                });
    }
}
