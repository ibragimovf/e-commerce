package uz.pdp.restservice.service;

import admin.receive.GatewayMerchantReceiveDto;
import admin.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.GatewayEntity;
import uz.pdp.restservice.model.GatewayMerchantEntity;
import uz.pdp.restservice.repository.GatewayMerchantRepository;
import uz.pdp.restservice.repository.GatewayRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.List;
import java.util.Optional;

@Service
public class GatewayMerchantService implements BaseService<GatewayMerchantReceiveDto, List<GatewayMerchantEntity>, GatewayMerchantEntity> {

    private final GatewayMerchantRepository gatewayMerchantRepository;
    private final ModelMapper modelMapper;
    private final GatewayRepository gatewayRepository;

    public GatewayMerchantService(GatewayMerchantRepository gatewayMerchantRepository, ModelMapper modelMapper, GatewayRepository gatewayRepository) {
        this.gatewayMerchantRepository = gatewayMerchantRepository;
        this.modelMapper = modelMapper;
        this.gatewayRepository = gatewayRepository;
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
        return new ApiResponse<>(0, SUCCESS, gatewayMerchantRepository.findAll());
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
        return new ApiResponse<>(0, SUCCESS);
    }
}
