package uz.pdp.restservice.service;

import admin.receive.AgentReceiveDto;
import admin.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.AgentEntity;
import uz.pdp.restservice.repository.AgentRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.List;

@Service
public class AgentService implements BaseService<AgentReceiveDto, List<AgentEntity>, AgentEntity> {
    private final AgentRepository agentRepository;
//    private final ModelMapper modelMapper;

    public AgentService(AgentRepository agentRepository, ModelMapper modelMapper) {
        this.agentRepository = agentRepository;
//        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<Object> add(AgentReceiveDto agentReceiveDto) {
        AgentEntity agentEntity =new AgentEntity();
        agentEntity.setName(agentEntity.getName());
        agentRepository.save(agentEntity);
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<AgentEntity>> getList() {
        return new ApiResponse<>(0, SUCCESS, agentRepository.findAll());
    }

    @Override
    public AgentEntity getById(long id) {
        return agentRepository.getReferenceById(id);
    }

    @Override
    public ApiResponse<Object> delete(long id) {
        AgentEntity agentEntity = agentRepository.getReferenceById(id);
        agentEntity.setInActive(true);
        agentRepository.save(agentEntity);
        return new ApiResponse<>(0, SUCCESS);
    }
}
