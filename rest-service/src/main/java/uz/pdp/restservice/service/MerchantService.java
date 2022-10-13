package uz.pdp.restservice.service;

import admin.receive.MerchantReceiveDto;
import admin.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.AgentMerchantEntity;
import uz.pdp.restservice.model.GatewayMerchantEntity;
import uz.pdp.restservice.model.MerchantEntity;
import uz.pdp.restservice.repository.AgentMerchantRepository;
import uz.pdp.restservice.repository.GatewayMerchantRepository;
import uz.pdp.restservice.repository.MerchantRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<GatewayMerchantEntity> optionalGatewayMerchant = gatewayMerchantRepository.findById(merchantReceiveDto.getGatewayMerchantId());
        Optional<AgentMerchantEntity> optionalAgentMerchant = agentMerchantRepository.findById(merchantReceiveDto.getAgentMerchantId());
        for (MerchantEntity merchantEntity : merchantRepository.findAll()) {
            if (merchantEntity.getName().equals(merchantReceiveDto.getName()))
                return new ApiResponse<>(400,"this name is already exists");
        }
//        if (optionalGatewayMerchant.isEmpty())
//            return new ApiResponse<>(400, "gatewayMerchant not exists");
//        if (optionalAgentMerchant.isEmpty())
//            return new ApiResponse<>(400, "agentMerchant not exists");
        MerchantEntity merchantEntity = new MerchantEntity();
        merchantEntity.setName(merchantReceiveDto.getName());
        merchantEntity.setAgentMerchantEntity(optionalAgentMerchant.get());
        merchantEntity.setGatewayMerchantEntity(optionalGatewayMerchant.get());
        merchantRepository.save(merchantEntity);
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<MerchantEntity>> getList() {
        List<MerchantEntity> merchantEntities=new ArrayList<>();
        for (MerchantEntity merchantEntity : merchantRepository.findAll()) {
            if(!merchantEntity.isInActive())
                merchantEntities.add(merchantEntity);
        }
        return new ApiResponse<>(0, SUCCESS,merchantEntities );
    }

    @Override
    public ApiResponse<Object> edit(long id, MerchantReceiveDto merchantReceiveDto) {
        Optional<MerchantEntity> optionalMerchant = merchantRepository.findById(id);
        if (optionalMerchant.isEmpty())
            return new ApiResponse<>(400,ID_EMPTY);
        for (MerchantEntity merchantEntity : merchantRepository.findAllByIdNot(id)) {
            if (merchantEntity.getName().equals(merchantReceiveDto.getName()))
                return new ApiResponse<>(400,"this name is already exists");
        }
        MerchantEntity merchantEntity= optionalMerchant.get();
        merchantEntity.setName(merchantReceiveDto.getName());
        merchantEntity.setAgentMerchantEntity(
                agentMerchantRepository.getReferenceById(merchantReceiveDto.getAgentMerchantId()));
        merchantEntity.setGatewayMerchantEntity(
                gatewayMerchantRepository.getReferenceById(merchantReceiveDto.getGatewayMerchantId()));
        return new ApiResponse<>(0,SUCCESS);
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

    @Override
    public ApiResponse<List<MerchantEntity>> getDisabledList() {
        List<MerchantEntity> merchantEntities=new ArrayList<>();
        for (MerchantEntity merchantEntity : merchantRepository.findAll()) {
            if(merchantEntity.isInActive())
                merchantEntities.add(merchantEntity);
        }
        return new ApiResponse<>(0, SUCCESS,merchantEntities );
    }

    public void disabledMerchant(MerchantEntity merchant) {
        if (merchant.getAgentMerchantEntity().isInActive()
                && merchant.getGatewayMerchantEntity().isInActive()) {
            merchant.setInActive(true);
        }
    }
}
