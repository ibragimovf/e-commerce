package uz.pdp.restservice.service;

import admin.receive.AgentMerchantReceiveDto;
import admin.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.AgentMerchantEntity;
import uz.pdp.restservice.repository.AgentMerchantRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.List;

@Service
public class AgentMerchantService implements BaseService<AgentMerchantReceiveDto, List<AgentMerchantEntity>, AgentMerchantEntity> {

    private final AgentMerchantRepository agentMerchantRepository;
    private final ModelMapper modelMapper;

    public AgentMerchantService(AgentMerchantRepository agentMerchantRepository, ModelMapper modelMapper) {
        this.agentMerchantRepository = agentMerchantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<Object> add(AgentMerchantReceiveDto agentMerchantReceiveDto) {
//        AgentMerchantEntity agentMerchantEntity = modelMapper.map(agentMerchantReceiveDto, AgentMerchantEntity.class);
        AgentMerchantEntity agentMerchantEntity=new AgentMerchantEntity();
        agentMerchantEntity.setName(agentMerchantReceiveDto.getName());
        agentMerchantEntity.setMinAmount(agentMerchantReceiveDto.getMinSum());
        agentMerchantEntity.setMaxAmount(agentMerchantReceiveDto.getMaxSum());
        agentMerchantRepository.save(agentMerchantEntity);
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<AgentMerchantEntity>> getList() {
        return new ApiResponse<>(0, SUCCESS, agentMerchantRepository.findAll());
    }

    @Override
    public AgentMerchantEntity getById(long id) {
        return agentMerchantRepository.getReferenceById(id);
    }

    @Override
    public ApiResponse<Object> delete(long id) {
        AgentMerchantEntity agentMerchantEntity = agentMerchantRepository.getReferenceById(id);
        agentMerchantEntity.setInActive(true);
        agentMerchantRepository.save(agentMerchantEntity);
        return new ApiResponse<>(0, SUCCESS);
    }
}
