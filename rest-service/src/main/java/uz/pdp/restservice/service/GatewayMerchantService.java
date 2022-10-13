package uz.pdp.restservice.service;

import admin.receive.GatewayMerchantReceiveDto;
import admin.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.GatewayEntity;
import uz.pdp.restservice.model.GatewayMerchantEntity;
import uz.pdp.restservice.model.MerchantEntity;
import uz.pdp.restservice.repository.GatewayMerchantRepository;
import uz.pdp.restservice.repository.GatewayRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GatewayMerchantService implements BaseService<GatewayMerchantReceiveDto, List<GatewayMerchantEntity>, GatewayMerchantEntity> {

    private final GatewayMerchantRepository gatewayMerchantRepository;
    private final ModelMapper modelMapper;
    private final GatewayRepository gatewayRepository;
    private final MerchantService merchantService;

    public GatewayMerchantService(GatewayMerchantRepository gatewayMerchantRepository, ModelMapper modelMapper, GatewayRepository gatewayRepository, MerchantService merchantService) {
        this.gatewayMerchantRepository = gatewayMerchantRepository;
        this.modelMapper = modelMapper;
        this.gatewayRepository = gatewayRepository;
        this.merchantService = merchantService;
    }

    @Override
    public ApiResponse<Object> add(GatewayMerchantReceiveDto gatewayMerchantReceiveDto) {
        Optional<GatewayEntity> optionalGateway = gatewayRepository.findById(gatewayMerchantReceiveDto.getGatewayId());
        if (optionalGateway.isEmpty()){
            return new ApiResponse<>(400,"gateway not exists");
        }
        GatewayMerchantEntity gatewayMerchantEntity=new GatewayMerchantEntity();
        gatewayMerchantEntity.setGatewayEntity(optionalGateway.get());
        gatewayMerchantEntity.setName(gatewayMerchantReceiveDto.getName());
        gatewayMerchantRepository.save(gatewayMerchantEntity);
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<GatewayMerchantEntity>> getList() {
        List<GatewayMerchantEntity> gatewayMerchantEntities=new ArrayList<>();
        for (GatewayMerchantEntity gatewayMerchantEntity : gatewayMerchantRepository.findAll()) {
            if (!gatewayMerchantEntity.isInActive())
                gatewayMerchantEntities.add(gatewayMerchantEntity);
        }

        return new ApiResponse<>(0, SUCCESS,gatewayMerchantEntities );
    }

    @Override
    public ApiResponse<Object> edit(long id, GatewayMerchantReceiveDto gatewayMerchantReceiveDto) {
        Optional<GatewayEntity> optionalGateway = gatewayRepository.findById(gatewayMerchantReceiveDto.getGatewayId());
        if (optionalGateway.isEmpty()){
            return new ApiResponse<>(400,"gateway not exists");
        }
        for (GatewayMerchantEntity gatewayMerchantEntity : gatewayMerchantRepository.findAllByIdNot(id)) {
            if (gatewayMerchantEntity.getName().equals(gatewayMerchantReceiveDto.getName()))
                return new ApiResponse<>(400,"this name already exists");
        }
        Optional<GatewayMerchantEntity> optionalGatewayMerchant = gatewayMerchantRepository.findById(id);
        if (optionalGatewayMerchant.isEmpty())
            return new ApiResponse<>(400,"id not found");
        GatewayMerchantEntity gatewayMerchantEntity=optionalGatewayMerchant.get();
        gatewayMerchantEntity.setGatewayEntity(optionalGateway.get());
        gatewayMerchantEntity.setName(gatewayMerchantReceiveDto.getName());
        gatewayMerchantRepository.save(gatewayMerchantEntity);
        return new ApiResponse<>(0, SUCCESS);

    }

    @Override
    public GatewayMerchantEntity getById(long id) {
        return gatewayMerchantRepository.getReferenceById(id);
    }

    @Override
    public ApiResponse<Object> delete(long id) {
        GatewayMerchantEntity gatewayMerchantEntity = gatewayMerchantRepository.getReferenceById(id);
        gatewayMerchantEntity.setInActive(true);
        gatewayMerchantRepository.save(gatewayMerchantEntity);
        MerchantEntity merchantEntity = gatewayMerchantEntity.getMerchantEntity();
        if (merchantEntity!=null){
            merchantService.disabledMerchant(merchantEntity);
        }
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<GatewayMerchantEntity>> getDisabledList() {
        List<GatewayMerchantEntity> gatewayMerchantEntities=new ArrayList<>();
        for (GatewayMerchantEntity gatewayMerchantEntity : gatewayMerchantRepository.findAll()) {
            if (gatewayMerchantEntity.isInActive())
                gatewayMerchantEntities.add(gatewayMerchantEntity);
        }

        return new ApiResponse<>(0, SUCCESS,gatewayMerchantEntities );
    }
}
