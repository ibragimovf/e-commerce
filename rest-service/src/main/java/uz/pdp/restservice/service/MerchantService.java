package uz.pdp.restservice.service;

import admin.receive.MerchantReceiveDto;
import admin.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.MerchantEntity;
import uz.pdp.restservice.repository.AgentMerchantRepository;
import uz.pdp.restservice.repository.GatewayMerchantRepository;
import uz.pdp.restservice.repository.MerchantRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.List;

@Service
public class MerchantService implements BaseService<MerchantReceiveDto, List<MerchantEntity>, MerchantEntity> {
    private final MerchantRepository merchantRepository;
    private final GatewayMerchantRepository gatewayMerchantRepository;
    private final AgentMerchantRepository agentMerchantRepository;
    private final ModelMapper modelMapper;

    public MerchantService(MerchantRepository merchantRepository, GatewayMerchantRepository gatewayMerchantRepository, AgentMerchantRepository agentMerchantRepository, ModelMapper modelMapper) {
        this.merchantRepository = merchantRepository;
        this.gatewayMerchantRepository = gatewayMerchantRepository;
        this.agentMerchantRepository = agentMerchantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<Object> add(MerchantReceiveDto merchantReceiveDto) {
//        MerchantEntity merchantEntity = modelMapper.map(merchantReceiveDto, MerchantEntity.class);
        MerchantEntity merchantEntity=new MerchantEntity();
        merchantEntity.setName(merchantReceiveDto.getName());
        merchantEntity.setGatewayMerchantEntity(gatewayMerchantRepository.findById(merchantEntity.getId()).get());
        merchantEntity.setAgentMerchantEntity(agentMerchantRepository.findById(merchantReceiveDto.getAgentMerchantId()).get());
        merchantRepository.save(merchantEntity);
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<MerchantEntity>> getList() {
        return new ApiResponse<>(0, SUCCESS, merchantRepository.findAll());
    }

    @Override
    public MerchantEntity getById(long id) {
        return merchantRepository.getReferenceById(id);
    }


    @Override
    public ApiResponse<Object> delete(long id) {
        MerchantEntity merchantEntity = merchantRepository.getReferenceById(id);
        merchantEntity.setInActive(true);
        merchantRepository.save(merchantEntity);
        return new ApiResponse<>(0, SUCCESS);
    }
}
