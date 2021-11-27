package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.AuthorRepository;
import hu.uni.eku.tzs.dao.BookRepository;
import hu.uni.eku.tzs.dao.OrderDetailRepository;
import hu.uni.eku.tzs.dao.entity.AuthorEntity;
import hu.uni.eku.tzs.dao.entity.BookEntity;
import hu.uni.eku.tzs.dao.entity.OrderDetailEntity;
import hu.uni.eku.tzs.dao.entity.OrderEntity;
import hu.uni.eku.tzs.dao.entity.ProductEntity;
import hu.uni.eku.tzs.dao.entity.ProductLineEntity;
import hu.uni.eku.tzs.model.Author;
import hu.uni.eku.tzs.model.Book;
import hu.uni.eku.tzs.model.Order;
import hu.uni.eku.tzs.model.OrderDetail;
import hu.uni.eku.tzs.service.exceptions.BookAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.BookNotFoundException;
import hu.uni.eku.tzs.service.exceptions.OrderDetailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailManagerImpl implements OrderDetailManager {

    private final OrderDetailRepository orderDetailRepository;

    private static OrderDetail convertOrderDetailEntity2Model(OrderDetailEntity orderDetailEntity) {
        return new OrderDetail(
            orderDetailEntity.getOrder().getOrderNumber(),
                orderDetailEntity.getProduct().getProductCode(),
                orderDetailEntity.getQuantityOrdered(),
                orderDetailEntity.getPriceEach(),
                orderDetailEntity.getOrderLineNumber()
        );
    }

    private static OrderDetailEntity convertOrderDetailModel2Entity(OrderDetail orderDetail) {
        return new OrderDetailEntity(
                //Hiba:
                //Ezeknek a mezőknek az értékeit honnan tudom meg?
                new OrderEntity(orderDetail.getOrderNumber(), null,null,null,null,null,null),
                new ProductEntity(orderDetail.getProductCode(), null,
                        new ProductLineEntity(orderDetail.getOrderLineNumber()))
        );
    }

    @Override
    public Order record(Order order) throws OrderDetailAlreadyExistsException {
        if (orderDetailRepository.findById(order.getOrderNumber()).isPresent()) {
            throw new OrderDetailAlreadyExistsException();
        }
        OrderDetailEntity orderDetailEntity = orderDetailRepository.save(
                OrderDetailEntity.builder()
                        .
                        .build()
        );
        return convertBookEntity2Model(bookEntity);
    }

    @Override
    public Book readByIsbn(String isbn) throws BookNotFoundException {
        Optional<BookEntity> entity = bookRepository.findById(isbn);
        if (entity.isEmpty()) {
            throw new BookNotFoundException(String.format("Cannot find book with ISBN %s", isbn));
        }

        return convertBookEntity2Model(entity.get());
    }

    @Override
    public Collection<Book> readAll() {
        return bookRepository.findAll().stream().map(OrderDetailManagerImpl::convertBookEntity2Model)
            .collect(Collectors.toList());
    }

    @Override
    public Book modify(Book book) {
        BookEntity entity = convertBookModel2Entity(book);
        return convertBookEntity2Model(bookRepository.save(entity));
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(convertBookModel2Entity(book));

    }

    private AuthorEntity readOrRecordAuthor(Author author) {
        if (authorRepository.findById(author.getId()).isPresent()) {
            return authorRepository.findById(author.getId()).get();
        }
        return authorRepository.save(
            AuthorEntity.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .nationality(author.getNationality())
                .build()
        );
    }

}
