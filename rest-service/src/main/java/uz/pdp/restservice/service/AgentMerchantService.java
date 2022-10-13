package uz.pdp.restservice.service;

import admin.receive.AgentMerchantReceiveDto;
import admin.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.AgentEntity;
import uz.pdp.restservice.model.AgentMerchantEntity;
import uz.pdp.restservice.model.GatewayEntity;
import uz.pdp.restservice.model.MerchantEntity;
import uz.pdp.restservice.repository.AgentMerchantRepository;
import uz.pdp.restservice.repository.AgentRepository;
import uz.pdp.restservice.repository.MerchantRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.List;
import java.util.Optional;

@Service
public class AgentMerchantService implements BaseService<AgentMerchantReceiveDto, List<AgentMerchantEntity>, AgentMerchantEntity> {

    private final AgentMerchantRepository agentMerchantRepository;
    private final AgentRepository agentRepository;
    private final MerchantRepository merchantRepository;
    private final MerchantService merchantService;
    private final ModelMapper modelMapper;

    public AgentMerchantService(AgentMerchantRepository agentMerchantRepository, AgentRepository agentRepository, MerchantRepository merchantRepository, MerchantService merchantService, ModelMapper modelMapper) {
        this.agentMerchantRepository = agentMerchantRepository;
        this.agentRepository = agentRepository;
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;

        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<Object> add(AgentMerchantReceiveDto agentMerchantReceiveDto) {
        Optional<AgentEntity> agent = agentRepository.findById(agentMerchantReceiveDto.getAgentId());
        if (agent.isEmpty()){
            return new ApiResponse<>(400,"agent not exists");
        }
//        AgentMerchantEntity agentMerchantEntity = modelMapper.map(agentMerchantReceiveDto, AgentMerchantEntity.class);
        AgentMerchantEntity agentMerchantEntity=new AgentMerchantEntity();
        agentMerchantEntity.setAgentEntity(agent.get());
        agentMerchantEntity.setCommission(agentMerchantReceiveDto.getCommission());
        agentMerchantEntity.setName(agentMerchantReceiveDto.getName());
        agentMerchantEntity.setMinAmount(agentMerchantReceiveDto.getMinSum());
        agentMerchantEntity.setMaxAmount(agentMerchantReceiveDto.getMaxSum());
        agentMerchantRepository.save(agentMerchantEntity);
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<AgentMerchantEntity>> getList() {
        List<AgentMerchantEntity> agentMerchantEntities = agentMerchantRepository.findAll();
        for (AgentMerchantEntity agentMerchantEntity : agentMerchantEntities) {
            if (!agentMerchantEntity.isInActive())
                agentMerchantEntities.add(agentMerchantEntity);
        }
        return new ApiResponse<>(0, SUCCESS, agentMerchantEntities);
    }

    @Override
    public ApiResponse<Object> edit(long id, AgentMerchantReceiveDto agentMerchantReceiveDto) {
        Optional<AgentMerchantEntity> optionalAgentMerchant = agentMerchantRepository.findById(id);
        if (optionalAgentMerchant.isEmpty())
            return new ApiResponse<>(400, ID_EMPTY);
        List<AgentMerchantEntity> agentMerchantEntities = agentMerchantRepository.findAllByIdNot(id);
        for (AgentMerchantEntity agentMerchantEntity : agentMerchantEntities) {
            if (agentMerchantEntity.getName().equals(agentMerchantReceiveDto.getName())){
                return new ApiResponse<>(400,"agentMerchant name already exists");
            }
        }
        Optional<AgentEntity> optionalAgent = agentRepository.findById(agentMerchantReceiveDto.getAgentId());
        if (optionalAgent.isEmpty())
            return new ApiResponse<>(400, "agent not found");
        AgentMerchantEntity agentMerchantEntity= optionalAgentMerchant.get();
        agentMerchantEntity.setAgentEntity(optionalAgent.get());
        agentMerchantEntity.setCommission(agentMerchantReceiveDto.getCommission());
        agentMerchantEntity.setName(agentMerchantReceiveDto.getName());
        agentMerchantEntity.setMinAmount(agentMerchantReceiveDto.getMinSum());
        agentMerchantEntity.setMaxAmount(agentMerchantReceiveDto.getMaxSum());
        agentMerchantRepository.save(agentMerchantEntity);
        return new ApiResponse<>(0,SUCCESS);
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
        MerchantEntity merchantEntity = agentMerchantEntity.getMerchantEntity();
        if (merchantEntity!=null){
            merchantService.disabledMerchant(merchantEntity);
        }
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<AgentMerchantEntity>> getDisabledList() {
        List<AgentMerchantEntity> agentMerchantEntities = agentMerchantRepository.findAll();
        for (AgentMerchantEntity agentMerchantEntity : agentMerchantEntities) {
            if (agentMerchantEntity.isInActive())
                agentMerchantEntities.add(agentMerchantEntity);
        }
        return new ApiResponse<>(0, SUCCESS, agentMerchantEntities);
    }
}
