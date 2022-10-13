package uz.pdp.restservice.service;

import admin.receive.AgentReceiveDto;
import admin.response.ApiResponse;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.AgentEntity;
import uz.pdp.restservice.model.AgentMerchantEntity;
import uz.pdp.restservice.repository.AgentMerchantRepository;
import uz.pdp.restservice.repository.AgentRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentService implements BaseService<AgentReceiveDto, List<AgentEntity>, AgentEntity> {
    private final AgentRepository agentRepository;
    private final AgentMerchantRepository agentMerchantRepository;


    public AgentService(AgentRepository agentRepository, AgentMerchantRepository agentMerchantRepository) {
        this.agentRepository = agentRepository;
        this.agentMerchantRepository = agentMerchantRepository;
    }

    private List<AgentEntity> agentEntityList() {
        List<AgentEntity> agentEntities = agentRepository.findAll();
        return agentEntities;
    }

    @Override
    public ApiResponse<Object> add(AgentReceiveDto agentReceiveDto) {
        List<AgentEntity> agentEntities = agentEntityList();
        for (AgentEntity agentEntity : agentEntities) {
            if (agentEntity.getSecretKey().equals(agentReceiveDto.getSecretKey()))
                return new ApiResponse<>(400, "this secretKey is exists");
            if (agentEntity.getName().equals(agentReceiveDto.getName()))
                return new ApiResponse<>(400, "this agent is exists");
        }
        AgentEntity agentEntity = new AgentEntity();
        agentEntity.setName(agentReceiveDto.getName());
        agentEntity.setSecretKey(agentReceiveDto.getSecretKey());
        agentRepository.save(agentEntity);
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<AgentEntity>> getList() {
        List<AgentEntity> agentEntities = new ArrayList<>();
        for (AgentEntity agentEntity : agentEntityList()) {
            if (!agentEntity.isInActive())
                agentEntities.add(agentEntity);
        }
        return new ApiResponse<>(0, SUCCESS, agentEntities);
    }

    @Override
    public ApiResponse<Object> edit(long id, AgentReceiveDto agentReceiveDto) {
        Optional<AgentEntity> optionalAgent = agentRepository.findById(id);
        if (optionalAgent.isEmpty())
            return new ApiResponse<>(400, ID_EMPTY);

        List<AgentEntity> agentEntities = agentRepository.findAllByIdNot(id);
        for (AgentEntity agentEntity : agentEntities) {
            if (agentEntity.getSecretKey().equals(agentReceiveDto.getSecretKey()))
                return new ApiResponse<>(400, "this secretKey is exists");
            if (agentEntity.getName().equals(agentReceiveDto.getName()))
                return new ApiResponse<>(400, "this agent is exists");
        }
        AgentEntity agentEntity = optionalAgent.get();
        agentEntity.setName(agentReceiveDto.getName());
        agentEntity.setSecretKey(agentReceiveDto.getSecretKey());
        agentRepository.save(agentEntity);
        return new ApiResponse<>(0, SUCCESS);

    }

    @Override
    public AgentEntity getById(long id) {
        return agentRepository.getReferenceById(id);
    }

    @Override
    public ApiResponse<Object> delete(long id) {
        AgentEntity agentEntity = agentRepository.getReferenceById(id);
        agentEntity.setInActive(true);
        List<AgentMerchantEntity> agentMerchantEntities =
                agentMerchantRepository.findAllByAgentEntityId(id);
        for (AgentMerchantEntity agentMerchantEntity : agentMerchantEntities) {
            agentEntity.setInActive(true);
        }
        agentRepository.save(agentEntity);
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<AgentEntity>> getDisabledList() {
        List<AgentEntity> agentEntities = new ArrayList<>();
        for (AgentEntity agentEntity : agentEntityList()) {
            if (agentEntity.isInActive())
                agentEntities.add(agentEntity);
        }
        return new ApiResponse<>(0, SUCCESS, agentEntities);
    }
}
