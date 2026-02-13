package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Adapters;

import com.tecbom.e_commerce.Application.Ports.Output.MasterRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.DatabaseFailedException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Mappers.SQLMapper;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Models.MasterEntity;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Repositories.JpaMasterRepository;
import com.tecbom.e_commerce.Infra.Security.Service.CryptographyService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MasterRepositoryAdapter implements MasterRepository {

    private final JpaMasterRepository frameworkRepository;
    private final SQLMapper mapper;

    public MasterRepositoryAdapter(JpaMasterRepository frameworkRepository, SQLMapper mapper) {
        this.frameworkRepository = frameworkRepository;
        this.mapper = mapper;
    }

    @Override
    public void saveMaster(Master master) {
        try {
            MasterEntity masterEntity = mapper.masterToEntity(master);
            frameworkRepository.save(masterEntity);
        } catch (Exception e) {
            throw new DatabaseFailedException();
        }

    }

    @Override
    public Optional<Master> findMasterByCpf(Cpf cpf) {
        try {
            String cpfCrypt = CryptographyService.encrypt(cpf.toString());
            return frameworkRepository.findMasterByCpf(cpfCrypt)
                    .map(mapper::masterToDomain);
        } catch (Exception e) {
            throw new DatabaseFailedException();
        }
    }
}
