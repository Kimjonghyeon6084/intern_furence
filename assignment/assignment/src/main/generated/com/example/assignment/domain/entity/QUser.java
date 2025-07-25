package com.example.assignment.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -882854532L;

    public static final QUser user = new QUser("user");

    public final StringPath desc = createString("desc");

    public final StringPath id = createString("id");

    public final StringPath level = createString("level");

    public final StringPath name = createString("name");

    public final StringPath pwd = createString("pwd");

    public final DateTimePath<java.sql.Timestamp> regDate = createDateTime("regDate", java.sql.Timestamp.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

