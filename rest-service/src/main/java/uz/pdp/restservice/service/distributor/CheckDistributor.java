package uz.pdp.restservice.service.distributor;

import org.springframework.stereotype.Service;
import uz.pdp.restservice.model.*;
import uz.pdp.restservice.model.receive.DefaultCheckReceiveDto;
import uz.pdp.restservice.model.response.BaseCheckAgentResponse;
import uz.pdp.restservice.repository.GatewayMerchantRepository;
import uz.pdp.restservice.repository.GatewayRepository;
import uz.pdp.restservice.repository.MerchantRepository;
import uz.pdp.restservice.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CheckDistributor {

    private final MerchantRepository merchantRepository;
    private final TransactionRepository transactionRepository;
    private final GatewayMerchantRepository gatewayMerchantRepository;
    private final GatewayRepository gatewayRepository;

    public CheckDistributor(MerchantRepository merchantRepository, TransactionRepository transactionRepository, GatewayMerchantRepository gatewayMerchantRepository, GatewayRepository gatewayRepository) {
        this.merchantRepository = merchantRepository;
        this.transactionRepository = transactionRepository;
        this.gatewayMerchantRepository = gatewayMerchantRepository;
        this.gatewayRepository = gatewayRepository;
    }

    public BaseCheckAgentResponse check(
            AgentEntity agentEntity,
            DefaultCheckReceiveDto defaultCheckReceiveDto
    ){
        TransactionError transactionError = null;
        MerchantEntity merchantEntity = null;
        GatewayMerchantEntity gatewayMerchantEntity = null;
        AgentMerchantEntity agentMerchantEntity = null;
        GatewayEntity gatewayEntity = null;
        TransactionEntity transactionEntity = null;


        if (!defaultCheckReceiveDto.isFull()){
            transactionError = TransactionError.PARAMETERS_INVALID;
        }

        if (transactionError == null) {
            merchantEntity = merchantRepository.getReferenceById(defaultCheckReceiveDto.getMerchantId());

            transactionEntity = new TransactionEntity(TransactionState.CREATED);
            transactionEntity.setTransactionAmount(defaultCheckReceiveDto.getAmount());
            transactionEntity.setMerchantEntity(merchantEntity);
            transactionRepository.save(transactionEntity);

            if (merchantEntity == null) {
                transactionError = TransactionError.MERCHANT_NOT_FOUND;
            }

            if (transactionError == null) {
                gatewayMerchantEntity = merchantEntity.getGatewayMerchantEntity();
                agentMerchantEntity = merchantEntity.getAgentMerchantEntity();
                gatewayEntity = gatewayMerchantEntity.getGatewayEntity();

                if (defaultCheckReceiveDto.getAmount().compareTo(BigDecimal.valueOf(agentMerchantEntity.getMaxAmount())) > 0) {
                    transactionError = TransactionError.MAX_AMOUNT;
                }

                if (defaultCheckReceiveDto.getAmount().compareTo(BigDecimal.valueOf(agentMerchantEntity.getMinAmount())) < 0) {
                    transactionError = TransactionError.MIN_AMOUNT;
                }

                if (gatewayEntity.isPaynet()){
                    if (getRandomNumber() < 5){
                        transactionEntity.setState(TransactionState.CHECK_ERROR);
                    }else {
                        transactionEntity.setState(TransactionState.CHECKED);
                    }
                }

                if (gatewayEntity.isPayme()){
                    if (getRandomNumber() < 5){
                        transactionEntity.setState(TransactionState.CHECK_ERROR);
                    }else {
                        transactionEntity.setState(TransactionState.CHECKED);
                    }
                }
            }
        }

        return null;
    }

    private int getRandomNumber(){
        return (int)(Math.random() * 10);
    }
}
