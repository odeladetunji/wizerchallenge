package com.api;

import com.dto.BookDto;
import com.dto.CategoryDto;
import com.dto.FavouriteBooks;
import com.dto.Responsepojo;
import com.entity.Books;
import com.entity.Category;
import com.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksApi {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/addBook")
    public Responsepojo<Books> addABook(@RequestBody BookDto bookDto) {
        return bookService.addABook(bookDto);
    }

    @PutMapping(value = "/editBook")
    public Responsepojo<Books> editBook(@RequestBody BookDto bookDto) {
        return bookService.editBook(bookDto);
    }

    @PutMapping(value = "/deleteBook")
    public Responsepojo<Books> deleteBook(@RequestBody BookDto bookDto) {
        return bookService.deleteBook(bookDto);
    }

    @GetMapping(value = "/booklist")
    public Responsepojo<List<Books>> listBooks() {
        return bookService.listBooks();
    }

    @PostMapping("/addCategory")
    public Responsepojo<Category> addCategory(@RequestBody CategoryDto categoryDto) {
        return bookService.addCategory(categoryDto);
    }

    @PutMapping(value = "/editCategory")
    public Responsepojo<Category> editCategory(@RequestBody CategoryDto categoryDto) {
        return bookService.editCategory(categoryDto);
    }

    @PutMapping(value = "/deleteCategory")
    public Responsepojo<Category> deleteCategory(@RequestBody CategoryDto categoryDto) {
        return bookService.deleteCategory(categoryDto);
    }

    @GetMapping(value = "/listCategories")
    public Responsepojo<List<Category>> listCategories() {
        return bookService.listCategories();
    }

    @PostMapping(value = "/createFovourite")
    public Responsepojo<List<Category>> createFavourites(@RequestBody FavouriteBooks favouriteBooks) {
        return bookService.createFavourites(favouriteBooks);
    }

    @GetMapping(value = "/listOfFavouriteBooks")
    public Responsepojo<List<Books>> listOfFavouriteBooks(){
        return bookService.listOfFavouriteBooks();
    }

}
