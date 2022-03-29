package com.example.jpa_bookmanager.service;

import com.example.jpa_bookmanager.domain.Author;
import com.example.jpa_bookmanager.domain.Book;
import com.example.jpa_bookmanager.repository.AuthorRepository;
import com.example.jpa_bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

//    @Transactional
    public void putBookAndAuthor() throws Exception {
        Book book = new Book();
        book.setName("Start JPA");

        bookRepository.save(book);

        Author author = new Author();
        author.setName("martin");

        authorRepository.save(author);

        throw new Exception("cant commit to db cause error");
        //RuntimeException(unchecked exception)과 달리 checkedE는 DB반영이 일어난다!
    }
}
