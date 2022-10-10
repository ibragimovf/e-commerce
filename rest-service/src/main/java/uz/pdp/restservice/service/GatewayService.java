package uz.pdp.restservice.service;

import admin.receive.GatewayReceiveDto;
import admin.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.GatewayEntity;
import uz.pdp.restservice.repository.GatewayRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.List;

@Service
public class GatewayService implements BaseService<GatewayReceiveDto, List<GatewayEntity>, GatewayEntity> {

    private final GatewayRepository gatewayRepository;
    private final ModelMapper modelMapper;

    public GatewayService(GatewayRepository gatewayRepository, ModelMapper modelMapper) {
        this.gatewayRepository = gatewayRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<Object> add(GatewayReceiveDto gatewayReceiveDto) {
//        GatewayEntity gatewayEntity = modelMapper.map(gatewayReceiveDto, GatewayEntity.class);
        GatewayEntity gatewayEntity= new GatewayEntity();
        gatewayEntity.setName(gatewayEntity.getName());
        gatewayRepository.save(gatewayEntity);
        return new ApiResponse<>(0, SUCCESS);
    }

    @Override
    public ApiResponse<List<GatewayEntity>> getList() {
        return new ApiResponse<>(0, SUCCESS, gatewayRepository.findAll());
    }

    @Override
    public GatewayEntity getById(long id) {
        return gatewayRepository.getReferenceById(id);
    }

    @Override
    public ApiResponse<Object> delete(long id) {
        GatewayEntity gatewayEntity = gatewayRepository.getReferenceById(id);
        gatewayEntity.setInActive(true);
        gatewayRepository.save(gatewayEntity);
        return new ApiResponse<>(0, SUCCESS);
    }
}
