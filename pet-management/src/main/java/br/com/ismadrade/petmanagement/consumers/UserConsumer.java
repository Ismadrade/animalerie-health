package br.com.ismadrade.petmanagement.consumers;

import br.com.ismadrade.petmanagement.dtos.UserEventDto;
import br.com.ismadrade.petmanagement.enums.ActionType;
import br.com.ismadrade.petmanagement.exceptions.CustomException;
import br.com.ismadrade.petmanagement.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class UserConsumer {

    @Autowired
    UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "${animalerie-health.broker.queue.userEventQueue.name}",
                    durable = "${animalerie-health.broker.exchange.userEventExchange}",
                    exclusive = "false",
                    autoDelete = "false",
                    arguments = {@Argument(name = "x-dead-letter-exchange", value = "${animalerie-health.broker.exchange.deadLetter}")}),
            exchange = @Exchange(value = "${animalerie-health.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT)
    ))
    public void listenUserEvent(@Payload UserEventDto userEventDto) throws Exception {
        var userModel = userEventDto.convertToUserModel();
        switch (ActionType.valueOf(userEventDto.getActionType())) {
            case CREATE:
            case UPDATE:
                userService.save(userModel);
                log.info("Message received! user saved with id: {}", userModel.getUserId());
                break;
            case DELETE:
                userService.delete(userEventDto.getUserId());
                log.warn("Message received! user deleted with id: {}", userModel.getUserId());
                break;
        }
    }


    @RabbitListener(queues = "${animalerie-health.broker.queue.deadLetter.name}")
    public void listenDeadLetterQueue(Message<UserEventDto> message){
        log.warn("Error receiving message queue {}", "${animalerie-health.broker.queue.userEventQueue.name}");
        log.error("Menssagem DLQ: {}", message.toString());
    }
}