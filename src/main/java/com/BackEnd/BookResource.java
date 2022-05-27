package com.BackEnd;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


@Path("/tasktest1/test1")

public class BookResource {



//Афишировать Список всех книг

    @GET
    @Produces(MediaType.APPLICATION_JSON)
  public Response getAll(){
List<Book> books = Book.listAll();
return Response.ok(books).build();
  }

    // Добавление новой книги (ДБ)
    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Book book){
        Book.persist(book);
        if(book.isPersistent()){
            return Response.created(URI.create("/books"+book.bookId)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    //Изменение данных книги по ид @PUT JSON отправка
//         {
//        "bookId":"524",
//            "title":"Segodnya",
//            "price":"233",
//            "nbPages":"42",
//            "description":"Tesrare"
//            }
    @PUT
    @Path("{BookToUpdate}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateBook(@PathParam("BookToUpdate") int BookIDToUpdate,
                                      Book book1){
        Book book=Book.findById(BookIDToUpdate);
        if(book==null){
            throw new WebApplicationException("Not exist",404);
        }
        book.title=book1.title;
        book.price=book1.price;
        book.nbPages= book1.nbPages;
        book.Description=book1.Description;
        return Response.ok(book).build();

    }

    //Изменение данных книги по ид @PATCH
    //http://localhost:8080/tasktest1/updatepatchbook/1?title=test2&price=100&nbPages=23&Description=Lasttest
    //Test zapros
    @PATCH
    @Path("updatepatchbook/{IDBookToUpdate}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response UpdateBookTitle(@PathParam("IDBookToUpdate") int intbooktoUpdate,
                                    @QueryParam("title")String updateTitle,
                                    @QueryParam("price")double updatePrice,
                                    @QueryParam("nbPages")int updateNbPages,
                                    @QueryParam("Description")String updateDescription
    ){
        Book book= Book.findById(intbooktoUpdate);
        book.title=updateTitle;
        book.price=updatePrice;
        book.nbPages=updateNbPages;
        book.Description=updateDescription;
        return Response.ok(book).build();
    }


//Удаление книги по ИД
    @DELETE
    @Transactional
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") int id) {
        boolean deleted = Book.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }




//Поиск книги по названию
    @GET
    @Path("title/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByTitle1(@PathParam("title") String title){
        return  Book.find("title",title)
                .singleResultOptional()
                .map(Book -> Response.ok(Book).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }

    //Изменение название книги по Ид
    @PUT
    @Path("updatetitle/{BookToUpdate}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response UpdateBookTitle(@PathParam("BookToUpdate") long transactionToUpdate,
                                      @QueryParam("title")String updateTransaction){
        Book book= Book.findById(transactionToUpdate);
        book.title=updateTransaction;
        return Response.ok(book).build();
    }






//Поиск книги по ИД
   @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id){
       //Book book = Book.findById(id);
        return  Book.findByIdOptional(id)
                .map(Book -> Response.ok(Book).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }



//Поиск книги по названию
    @GET
    @Path("title/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByTitle(@PathParam("title") String title){
        return  Book.find("title",title)
                .singleResultOptional()
                .map(Book -> Response.ok(Book).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }

    //Поиск книги по цене
  @GET
  @Path("price/{price}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getByPrice(@PathParam("price") double price){
      List<Book> books = Book.list("select m from Book m where m.price = ?1 order by id "+
                "DESC",price);
      return Response.ok(books).build();

  }




}