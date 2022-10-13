package uz.pdp.restservice.service;

import admin.receive.GatewayReceiveDto;
import admin.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.GatewayEntity;
import uz.pdp.restservice.model.GatewayMerchantEntity;
import uz.pdp.restservice.repository.GatewayMerchantRepository;
import uz.pdp.restservice.repository.GatewayRepository;
import uz.pdp.restservice.service.base.BaseService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GatewayService implements BaseService<GatewayReceiveDto, List<GatewayEntity>, GatewayEntity> {

    private final GatewayRepository gatewayRepository;
    private final GatewayMerchantRepository gatewayMerchantRepository;
    private final ModelMapper modelMapper;

    public GatewayService(GatewayRepository gatewayRepository, GatewayMerchantRepository gatewayMerchantRepository, ModelMapper modelMapper) {
        this.gatewayRepository = gatewayRepository;
        this.gatewayMerchantRepository = gatewayMerchantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<Object> add(GatewayReceiveDto gatewayReceiveDto) {
        List<GatewayEntity> gatewayList = gatewayRepository.findAll();
        for (GatewayEntity gatewayEntity : gatewayList) {
            if (gatewayReceiveDto.getSecretKey().equals(gatewayEntity.getSecretKey()))
                return new ApiResponse<>(400, "this secretKey already exists");
            if (gatewayReceiveDto.getName().equals(gatewayEntity.getName()))
                return new ApiResponse<>(400, "this gateway already exists");
        }
        GatewayEntity gatewayEntity = new GatewayEntity();
        gatewayEntity.setName(gatewayReceiveDto.getName());
        gatewayEntity.setSecretKey(gatewayReceiveDto.getSecretKey());
        gatewayRepository.save(gatewayEntity);
        return new ApiResponse<>(0, SUCCESS );
    }

    @Override
    public ApiResponse<List<GatewayEntity>> getList() {
        List<GatewayEntity> list = gatewayRepository.findAll();
        List<GatewayEntity> gatewaylist=new ArrayList<>();
        for (GatewayEntity gatewayEntity : list) {
            if (!gatewayEntity.isInActive()){
                gatewaylist.add(gatewayEntity);
            }
        }
        return new ApiResponse<>(0, SUCCESS, gatewaylist);
    }

    @Override
    public ApiResponse<Object> edit(long id, GatewayReceiveDto gatewayReceiveDto) {

        GatewayEntity gateway = gatewayRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("id not found exception"));
//        Optional<GatewayEntity> optionalGateway = gatewayRepository.findById(id);
//        if (optionalGateway.isEmpty())
//            return new ApiResponse<>(400,ID_EMPTY);

        List<GatewayEntity> gatewayList = gatewayRepository.findAllByIdNot(id);
        for (GatewayEntity gatewayEntity : gatewayList) {
            if (gatewayReceiveDto.getSecretKey().equals(gatewayEntity.getSecretKey()))
                return new ApiResponse<>(400, "this secretKey already exists");
            if (gatewayReceiveDto.getName().equals(gatewayEntity.getName()))
                return new ApiResponse<>(400, "this gateway already exists");
        }

        gateway.setName(gatewayReceiveDto.getName());
        gateway.setSecretKey(gatewayReceiveDto.getSecretKey());
        gatewayRepository.save(gateway);
        return new ApiResponse<>(400, SUCCESS);
    }

    @Override
    public GatewayEntity getById(long id) {
        return gatewayRepository.getReferenceById(id);
    }

    @Override
    public ApiResponse<Object> delete(long id) {
       try {
           GatewayEntity gatewayEntity = gatewayRepository.getReferenceById(id);
           gatewayEntity.setInActive(true);
           gatewayRepository.save(gatewayEntity);
           List<GatewayMerchantEntity> gatewayMerchantEntities =
                   gatewayMerchantRepository.findAllByGatewayEntityId(id);
           for (GatewayMerchantEntity gatewayMerchantEntity : gatewayMerchantEntities) {
               gatewayMerchantEntity.setInActive(true);
           }
           return new ApiResponse<>(0, SUCCESS);
       }catch (Exception e){
           return new ApiResponse<>(400,ERROR);
       }
    }

    @Override
    public ApiResponse<List<GatewayEntity>> getDisabledList() {
        List<GatewayEntity> list = gatewayRepository.findAll();
        List<GatewayEntity> gatewaylist=new ArrayList<>();
        for (GatewayEntity gatewayEntity : list) {
            if (gatewayEntity.isInActive()){
                gatewaylist.add(gatewayEntity);
            }
        }
        return new ApiResponse<>(0, SUCCESS, gatewaylist);
    }
}
