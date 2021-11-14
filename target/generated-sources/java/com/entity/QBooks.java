package com.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBooks is a Querydsl query type for Books
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBooks extends EntityPathBase<Books> {

    private static final long serialVersionUID = 577318924L;

    public static final QBooks books = new QBooks("books");

    public final QAuditable _super = new QAuditable(this);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isFavourite = createBoolean("isFavourite");

    //inherited
    public final StringPath last_modified_by = _super.last_modified_by;

    //inherited
    public final DateTimePath<java.util.Date> last_modified_date = _super.last_modified_date;

    public final StringPath name = createString("name");

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final StringPath status = createString("status");

    public QBooks(String variable) {
        super(Books.class, forVariable(variable));
    }

    public QBooks(Path<? extends Books> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBooks(PathMetadata metadata) {
        super(Books.class, metadata);
    }

}

