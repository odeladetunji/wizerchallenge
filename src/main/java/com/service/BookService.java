package com.service;

import com.dto.BookDto;
import com.dto.CategoryDto;
import com.dto.FavouriteBooks;
import com.dto.Responsepojo;
import com.entity.Books;
import com.entity.Category;
import com.entity.QBooks;
import com.entity.QCategory;
import com.exception.ApiException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.repository.BooksRepository;
import com.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Responsepojo<Books> addABook(BookDto bookDto){

        Assert.notNull(bookDto.getPrice(), "Book price is required");
        Assert.notNull(bookDto.getName(), "Book name is required");
        Assert.hasText(bookDto.getName(), "Book name cannot be empty");

        QBooks qBooks = QBooks.books;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qBooks.name.equalsIgnoreCase(bookDto.getName()));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Books> jpaQuery = jpaQueryFactory.selectFrom(qBooks).where(predicate);

        if (!jpaQuery.fetch().isEmpty())
            throw new ApiException(String.format("Book with name (%s) already exits", bookDto.getName()));

        Books books = new Books();
        books.setName(bookDto.getName());
        books.setPrice(bookDto.getPrice());
        books.setStatus("ACTIVE");
        books.setCategoryId(bookDto.getCategoryId());
        books.setCreatedBy("System");
        books.setCreatedDate(new Date());
        books.setLast_modified_by("System");
        books.setLast_modified_date(new Date());
        books.setFavourite(false);

        Books aBook = booksRepository.save(books);

        Responsepojo<Books> responsepojo = new Responsepojo<>();
        responsepojo.setData(aBook);
        responsepojo.setMessage("Books Stored Successfully");

        return responsepojo;
    }

    @Transactional
    public Responsepojo<Books> editBook(BookDto bookDto){

        Assert.notNull(bookDto.getId(), "Id is required to edit a book");
        Assert.notNull(bookDto.getName(), "Book name is required");
        Assert.hasText(bookDto.getName(), "Book name cannot be empty");

        Optional<Books> optionalBooks = booksRepository.findById(bookDto.getId());
        optionalBooks.orElseThrow(() -> new ApiException(String.format("Book with id (%s) does not exist", bookDto.getId())));

        QBooks qBooks = QBooks.books;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qBooks.name.equalsIgnoreCase(bookDto.getName()));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Books> jpaQuery = jpaQueryFactory.selectFrom(qBooks).where(predicate);

        if (!jpaQuery.fetch().isEmpty()){
            Books abook = jpaQuery.fetchOne();
            assert abook != null;
            if (!abook.getId().equals(bookDto.getId()))
                throw new ApiException(String.format("A book with name (%s) already exits", bookDto.getName()));
        }

        Books books = optionalBooks.get();
        books.setName(bookDto.getName());
        books.setPrice(bookDto.getPrice());
        books.setStatus("ACTIVE");
        books.setCategoryId(bookDto.getCategoryId());
        books.setLast_modified_by("System");
        books.setLast_modified_date(new Date());
        books.setFavourite(false);

        Books aBook = booksRepository.save(books);

        Responsepojo<Books> responsepojo = new Responsepojo<>();
        responsepojo.setData(aBook);
        responsepojo.setMessage("Books Edited Successfully!");

        return responsepojo;
    }

    @Transactional
    public Responsepojo<Books> deleteBook(BookDto bookDto){

        Assert.notNull(bookDto.getId(), "Id is required to edit a book");
        Assert.notNull(bookDto.getName(), "Book name is required");
        Assert.hasText(bookDto.getName(), "Book name cannot be empty");

        Optional<Books> optionalBooks = booksRepository.findById(bookDto.getId());
        optionalBooks.orElseThrow(() -> new ApiException(String.format("Book with id (%s) does not exist", bookDto.getId())));

        QBooks qBooks = QBooks.books;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qBooks.name.equalsIgnoreCase(bookDto.getName()));
        predicate.and(qBooks.status.notEqualsIgnoreCase("INACTIVE"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Books> jpaQuery = jpaQueryFactory.selectFrom(qBooks).where(predicate);

        if (!jpaQuery.fetch().isEmpty()){
            Books abook = jpaQuery.fetchOne();
            assert abook != null;
            if (!abook.getId().equals(bookDto.getId()))
                throw new ApiException(String.format("A book with name (%s) already exits", bookDto.getName()));
        }

        Books books = optionalBooks.get();
        books.setStatus("INACTIVE");
        books.setLast_modified_by("System");
        books.setLast_modified_date(new Date());

        Books aBook = booksRepository.save(books);

        Responsepojo<Books> responsepojo = new Responsepojo<>();
        responsepojo.setData(null);
        responsepojo.setMessage("Books Deleted Successfully!");

        return responsepojo;
    }


    public Responsepojo<List<Books>> listBooks(){

        QBooks qBooks = QBooks.books;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qBooks.status.notEqualsIgnoreCase("INACTIVE"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Books> jpaQuery = jpaQueryFactory.selectFrom(qBooks)
                .where(predicate);

        Responsepojo<List<Books>> responsepojo = new Responsepojo<>();
        responsepojo.setData(jpaQuery.fetch());
        responsepojo.setMessage("List of books");

        return responsepojo;

    }

    @Transactional
    public Responsepojo<Category> addCategory(CategoryDto categoryDto){

        Assert.notNull(categoryDto.getName(), "Category Name can not be null");
        Assert.hasText(categoryDto.getName(), "Category Name cannot be empty");

        QCategory qCategory = QCategory.category;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qCategory.name.equalsIgnoreCase(categoryDto.getName()));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Category> jpaQuery = jpaQueryFactory.selectFrom(qCategory).where(predicate);

        if (!jpaQuery.fetch().isEmpty())
            throw new ApiException("This category already exists");

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setStatus("ACTIVE");
        category.setCreatedBy("System");
        category.setCreatedDate(new Date());
        category.setLast_modified_by("System");
        category.setLast_modified_date(new Date());

        Category categoryObj = categoryRepository.save(category);

        Responsepojo<Category> responsepojo = new Responsepojo<>();
        responsepojo.setData(categoryObj);
        responsepojo.setMessage("Category Saved Successfully!");

        return responsepojo;
//        Optional<Category> optionalCategory = categoryRepository.findById()
    }

    @Transactional
    public Responsepojo<Category> editCategory(CategoryDto categoryDto){

        Assert.notNull(categoryDto.getId(), "Category Id cannot be null");
        Assert.notNull(categoryDto.getName(), "Category Name can not be null");
        Assert.hasText(categoryDto.getName(), "Category Name cannot be empty");

        QCategory qCategory = QCategory.category;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qCategory.name.equalsIgnoreCase(categoryDto.getName()));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Category> jpaQuery = jpaQueryFactory.selectFrom(qCategory).where(predicate);

        if (!jpaQuery.fetch().isEmpty()) {
            if (jpaQuery.fetchOne().getId() != categoryDto.getId())
                throw new ApiException("Another record with this category name already exist");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getId());
        optionalCategory.orElseThrow(() -> new ApiException("A category with with this id (%s) does not exists"));

        Category category = optionalCategory.get();
        category.setName(categoryDto.getName());
        category.setStatus("ACTIVE");
        category.setLast_modified_by("System");
        category.setLast_modified_date(new Date());

        Category categoryObj = categoryRepository.save(category);

        Responsepojo<Category> responsepojo = new Responsepojo<>();
        responsepojo.setData(categoryObj);
        responsepojo.setMessage("Category Saved Successfully!");

        return responsepojo;

    }

    @Transactional
    public Responsepojo<Category> deleteCategory(CategoryDto categoryDto){

        Assert.notNull(categoryDto.getId(), "Category Id cannot be null");
        Assert.notNull(categoryDto.getName(), "Category Name can not be null");
        Assert.hasText(categoryDto.getName(), "Category Name cannot be empty");

        QCategory qCategory = QCategory.category;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qCategory.name.equalsIgnoreCase(categoryDto.getName()));
        predicate.and(qCategory.status.notEqualsIgnoreCase("INACTIVE"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Category> jpaQuery = jpaQueryFactory.selectFrom(qCategory).where(predicate);

        if (!jpaQuery.fetch().isEmpty()) {
            if (jpaQuery.fetchOne().getId() != categoryDto.getId())
                throw new ApiException("Another record with this category name already exist");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getId());
        optionalCategory.orElseThrow(() -> new ApiException("A category with with this id (%s) does not exists"));

        Category category = optionalCategory.get();
        category.setStatus("INACTIVE");
        category.setLast_modified_by("System");
        category.setLast_modified_date(new Date());

        Category categoryObj = categoryRepository.save(category);

        Responsepojo<Category> responsepojo = new Responsepojo<>();
        responsepojo.setData(null);
        responsepojo.setMessage("Category deleted Successfully!");

        return responsepojo;

    }

    public Responsepojo<List<Category>> listCategories(){

        QCategory qCategory = QCategory.category;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qCategory.status.notEqualsIgnoreCase("INACTIVE"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Category> jpaQuery = jpaQueryFactory.selectFrom(qCategory)
                .where(predicate);

        Responsepojo<List<Category>> responsepojo = new Responsepojo<>();
        responsepojo.setData(jpaQuery.fetch());
        responsepojo.setMessage("List of Categories");

        return responsepojo;

    }

    @Transactional
    public Responsepojo<List<Category>> createFavourites(FavouriteBooks favouriteBooks){

        Assert.notNull(favouriteBooks.getBookIdList(), "listOfId is required");

        QBooks qBooks = QBooks.books;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qBooks.id.in(favouriteBooks.getBookIdList().toArray(new Integer[0])));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        jpaQueryFactory.update(qBooks)
                .set(qBooks.isFavourite, true)
                .set(qBooks.last_modified_by, "System")
                .set(qBooks.last_modified_date, new Date())
                .where(predicate)
                .execute();

        Responsepojo<List<Category>> responsepojo = new Responsepojo<>();
        responsepojo.setData(null);
        responsepojo.setMessage("favourite books created successfully!");

        return responsepojo;

    }

    @Transactional
    public Responsepojo<List<Books>> listOfFavouriteBooks(){

        QBooks qBooks = QBooks.books;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qBooks.isFavourite.isTrue());
        predicate.and(qBooks.status.notEqualsIgnoreCase("INACTIVE"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Books> jpaQuery = jpaQueryFactory.selectFrom(qBooks)
                .where(predicate);

        Responsepojo<List<Books>> responsepojo = new Responsepojo<>();
        responsepojo.setData(jpaQuery.fetch());
        responsepojo.setMessage("List of Favourite Books");

        return responsepojo;

    }


}
