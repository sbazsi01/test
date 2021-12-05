package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.PaymentRepository;
import hu.uni.eku.tzs.dao.entity.PaymentEntity;
import hu.uni.eku.tzs.model.Payment;
import hu.uni.eku.tzs.service.exceptions.PaymentAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentManagerImpl implements PaymentManager {

    private final PaymentRepository paymentRepository;

    private static Payment convertPaymentEntity2Model(PaymentEntity paymentEntity) {

        return new Payment(
                CustomerManagerImpl.convertCustomerEntity2Model(paymentEntity.getCustomerNumber()),
                paymentEntity.getCheckNumber(),
                paymentEntity.getPaymentDate(),
                paymentEntity.getAmount()
        );

    }

    private static PaymentEntity convertPaymentModel2Entity(Payment payment) {

        return PaymentEntity.builder()
                .customerNumber(CustomerManagerImpl.convertCustomerModel2Entity(payment.getCustomerNumber()))
                .checkNumber(payment.getCheckNumber())
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .build();

    }

    @Override
    public Payment record(Payment payment) throws PaymentAlreadyExistsException {

        if (paymentRepository.findById(payment.getCheckNumber()).isPresent()) {
            throw new PaymentAlreadyExistsException();
        }
        PaymentEntity paymentEntity = paymentRepository.save(
                convertPaymentModel2Entity(payment)
        );
        return convertPaymentEntity2Model(paymentEntity);

    }

    @Override
    public Payment readByCheckNumber(String checkNumber) throws PaymentNotFoundException {

        Optional<PaymentEntity> entity = paymentRepository.findById(checkNumber);
        if (entity.isEmpty()) {
            throw new PaymentNotFoundException("Cannot find payments with check number " + checkNumber);
        }
        return convertPaymentEntity2Model(entity.get());
    }

    @Override
    public Collection<Payment> readAll() {

        return paymentRepository.findAll().stream().map(PaymentManagerImpl::convertPaymentEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Payment modify(Payment payment) {

        PaymentEntity entity = convertPaymentModel2Entity(payment);
        return convertPaymentEntity2Model(paymentRepository.save(entity));
    }

    @Override
    public void delete(Payment payment) throws PaymentNotFoundException {

        paymentRepository.delete(convertPaymentModel2Entity(payment));
    }
}
