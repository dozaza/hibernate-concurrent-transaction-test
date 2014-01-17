create table READER
(
  READER_ID             bigint not null primary key AUTO_INCREMENT,
  NAME                  char(20)
) DEFAULT CHARACTER SET=utf8;

create table BOOK
(
  BOOK_ID               bigint not null primary key AUTO_INCREMENT,
  READER_ID             bigint,
  NAME                  char(20)
) DEFAULT CHARACTER SET=utf8;

alter table BOOK add constraint FK_BOOK_READER_ID foreign key (READER_ID)
      references READER (READER_ID) on delete restrict on update restrict;