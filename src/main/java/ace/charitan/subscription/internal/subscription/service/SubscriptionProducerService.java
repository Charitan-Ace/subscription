package ace.charitan.subscription.internal.subscription.service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;

import ace.charitan.common.dto.email.subscription.EmailNewProjectSubscription.EmailNewProjectSubscriptionRequestDto;
import ace.charitan.common.dto.geography.GetCountryByIsoCode.GetCountryByIsoCodeRequestDto;
import ace.charitan.common.dto.geography.GetCountryByIsoCode.GetCountryByIsoCodeResponseDto;
import ace.charitan.common.dto.notification.subscription.NotificationNewProjectSubscription.NotificationNewProjectSubscriptionRequestDto;

@Component
class SubscriptionProducerService {

    @Autowired
    private ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private void send(SubscriptionProducerTopic topic, Serializable data) {
        try {

            kafkaTemplate.send(topic.getTopic(), data);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private Object sendAndReceive(
            SubscriptionProducerTopic topic, Serializable data) {
        try {

            ProducerRecord<String, Object> record = new ProducerRecord<>(topic.getTopic(), data);
            RequestReplyFuture<String, Object, Object> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
            // SendResult<String, Object> sendResult = replyFuture.getSendFuture().get(10,
            // TimeUnit.SECONDS);
            ConsumerRecord<String, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
            return consumerRecord.value();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    GetCountryByIsoCodeResponseDto sendAndReceive(GetCountryByIsoCodeRequestDto dto) {
        GetCountryByIsoCodeResponseDto responseDto = (GetCountryByIsoCodeResponseDto) sendAndReceive(
                SubscriptionProducerTopic.SUBSCRIPTION_GEOGRAPHY_GET_COUNTRY_BY_ISO_CODE, dto);
        return responseDto;
    }

    void send(EmailNewProjectSubscriptionRequestDto requestDto) {
        send(SubscriptionProducerTopic.SUBSCRIPTION_EMAIL_NEW_PROJECT, requestDto);
    }

    void send(NotificationNewProjectSubscriptionRequestDto requestDto) {
        send(SubscriptionProducerTopic.SUBSCRIPTION_NOTIFICATION_NEW_PROJECT, requestDto);
    }

}
