package com.onlinebook.onlineBookStore.Services.Implementaion;

import com.onlinebook.onlineBookStore.DTO.BookDTO;
import com.onlinebook.onlineBookStore.Entity.Book;
import com.onlinebook.onlineBookStore.ExceptionHandeling.CustomExceptionHandel;
import com.onlinebook.onlineBookStore.Mapper.BookMapper;
import com.onlinebook.onlineBookStore.Repository.BookRepository;
import com.onlinebook.onlineBookStore.Repository.ReviewRepository;
import com.onlinebook.onlineBookStore.Services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;


    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public BookDTO createBook(BookDTO dto){
//        Book book = new Book();
//        book.setTitle(dto.getTitle());
//        book.setAuthor(dto.getAuthor());
//        book.setPrice(dto.getPrice());
//        book.setPublishedDate(dto.getPublicationDate());
//        book.setCategory(dto.getCategory());
//        book.setCoverImage(dto.getCoverImage());

        if(bookRepository.existsByTitleIgnoreCaseContaining(dto.getTitle())){
            throw new CustomExceptionHandel(
                    "Book name already exist " + dto.getTitle(), HttpStatus.CONFLICT.value()
            );
        }

        Book book = BookMapper.toEntity(dto);
        Book saveBook = bookRepository.save(book);
        return BookMapper.toDto(saveBook);
    }

    @Override
    public BookDTO getBookById(int id){
      Book book = bookRepository.findById(id)
              .orElseThrow(()-> new CustomExceptionHandel("Error " + id, HttpStatus.NOT_FOUND.value()));
      return BookMapper.toDto(book);

    }

    @Override
    public BookDTO updateBook(int id, BookDTO dto){
        Book book = bookRepository.findById(id)
                .orElseThrow(()->new CustomExceptionHandel("Book not found with id : "+id, HttpStatus.NOT_FOUND.value()));

        book.setTitle((dto.getTitle()));
        book.setAuthor((dto.getAuthor()));
        book.setPrice(dto.getPrice());
        book.setPublishedDate(dto.getPublicationDate());
        book.setCategory(dto.getCategory());
        book.setCoverImage(dto.getCoverImage());

        Book updateBook = bookRepository.save(book);
        return BookMapper.toDto(updateBook);

    }
    @Override
    public BookDTO deleteBook(int id){
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new CustomExceptionHandel("Book not found" + id, HttpStatus.NOT_FOUND.value()));
        bookRepository.delete(book);
        return BookMapper.toDto(book);
    }

//    @Override
//    public List<BookDTO> getBooksByCategory(String category){
//        List<Book> book = bookRepository.findBookByCategory(category);
//        if(book.isEmpty()){
//            throw new CustomExceptionHandel("Book Not Found for Category: " + category);
//            }
//        return book.stream()
//                .map(BookMapper::toDto)
//                .collect(Collectors.toList());
//
//    }

    @Override
    public List<BookDTO> searchBook(String category, String title, String author){

        List<Book> book ;

        boolean hasCategory = category!=null && !category.isEmpty();
        boolean hasTitle = title!=null && !title.isEmpty();
        boolean hasAuthor = author!=null && !author.isEmpty();

        if(hasCategory && hasTitle && hasAuthor){
            book = bookRepository.findByCategoryIgnoreCaseContainingAndTitleIgnoreCaseContainingAndAuthorIgnoreCaseContaining(category,title,author);
        } else if (hasCategory && hasTitle) {
            book = bookRepository.findByCategoryIgnoreCaseContainingAndTitleIgnoreCaseContaining(category, title);
        } else if (hasTitle && hasAuthor) {
            book = bookRepository.findByTitleIgnoreCaseContainingAndAuthorIgnoreCaseContaining(title,author);
        } else if (hasCategory) {
            book = bookRepository.findByCategoryIgnoreCaseContaining(category);
        } else if (hasTitle) {
            book = bookRepository.findByTitleIgnoreCaseContaining(title);
        } else if (hasAuthor) {
            book = bookRepository.findByAuthorIgnoreCaseContaining(author);
        }else {
            throw new CustomExceptionHandel("Provided search criteria: Category, Title or Author ", HttpStatus.NOT_FOUND.value());
        }
        if(book.isEmpty()){
            throw  new CustomExceptionHandel("No books matching found", HttpStatus.BAD_REQUEST.value());
        }

        return book.stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());

    }
}
