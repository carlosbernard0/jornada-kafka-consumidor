package com.jornada.consumidor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jornada.consumidor.dto.MensagemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumidorService {

    @KafkaListener(groupId = "${kafka.group.id}",
    topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions ={"0","1"})},
    containerFactory = "listenerContainerFactory",
    clientIdPrefix = "primeiroTopico")
    public void consumir(
            @Payload String mensagem,
            @Header(KafkaHeaders.RECEIVED_KEY) String chave,
            @Header(KafkaHeaders.OFFSET) Long offset
    ) throws JsonProcessingException {
        //string em obj...
        MensagemDTO dto = new ObjectMapper().readValue(mensagem, MensagemDTO.class);
        log.info("Mensagem de SMS ou WHATSAPP: {} \n chave: {} \n offset: {}", dto, chave, offset);
    }

    @KafkaListener(groupId = "${kafka.group.id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions ={"2"})},
            containerFactory = "listenerContainerFactory",
            clientIdPrefix = "consumidorEmail")
    public void consumirEmail(
            @Payload String mensagem,
            @Header(KafkaHeaders.RECEIVED_KEY) String chave,
            @Header(KafkaHeaders.OFFSET) Long offset
    ) throws JsonProcessingException {
        //string em obj...
        MensagemDTO dto = new ObjectMapper().readValue(mensagem, MensagemDTO.class);
        log.info("Mensagem de EMAIL: {} \n chave: {} \n offset: {}", dto, chave, offset);
    }
}
