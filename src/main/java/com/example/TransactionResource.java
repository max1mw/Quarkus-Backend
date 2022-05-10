package com.example;


import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


@Path("/tasktest2")

public class TransactionResource {


//Получения списка всех транзакций
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<Transaction> transactions = Transaction.listAll();
        return Response.ok(transactions).build();
    }

//Добавление новой транзакции
    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Transaction transaction){
        Transaction.persist(transaction);
        if(transaction.isPersistent()){
            return Response.created(URI.create("/transactions"+ transaction.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    //изменение данных транзакции по ИД
    @PUT
    @Path("{TransactionToUpdate}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateTransaction(@PathParam("TransactionToUpdate") long transactionToUpdate,
                                      Transaction transaction1){

        Transaction transaction=Transaction.findById(transactionToUpdate);
        if(transaction==null){
            throw new WebApplicationException("Not exist",404);
        }

        transaction.idDep=transaction1.idDep;
        transaction.borrowdate=transaction1.borrowdate;
        transaction.returndate=transaction1.returndate;
        transaction.isreturned= transaction1.isreturned;
        return Response.ok(transaction).build();

    }




    //Удаление транзакции по ид
    @DELETE
    @Transactional
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id){
        boolean deleted = Transaction.deleteById(id);
        if(deleted){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

//Получение списка с данными и об книге
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll1(){
        List<Transaction> transactions = Transaction.list("Select a,b from Book a,Transaction b where b.bookId = a.bookId");
        return Response.ok(transactions).build();
    }









}
