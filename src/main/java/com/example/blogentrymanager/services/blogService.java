package com.example.blogentrymanager.services;

import com.example.blogentrymanager.DataLayer.FakeDB;
import com.example.blogentrymanager.grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@EnableTransactionManagement
@Service
@GrpcService
public class blogService extends BlogEntryServiceGrpc.BlogEntryServiceImplBase {

    public FakeDB _fakeDB = FakeDB.getInstance();


    @Override
    public void getBlogEntries(GetBlogEntriesRequest request, StreamObserver<GetBlogEntriesResponse> responseObserver) {
        log.info("getBlogEntries request: {}", request);
        GetBlogEntriesResponse.Builder response = GetBlogEntriesResponse.newBuilder();

        response.addAllEntries(_fakeDB.getEntries());

        log.info("getBlogEntries response: {}", response.build());

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void getBlogEntryById(GetBlogEntryByIdRequest request, StreamObserver<GetBlogEntryByIdResponse> responseObserver) {
        log.info("getBlogEntryById request: {}", request);

        GetBlogEntryByIdResponse.Builder response = GetBlogEntryByIdResponse.newBuilder();
        String reqId = request.getId();
        int  id= Integer.parseInt(reqId);
        BlogEntry blogEntry = _fakeDB.getEntryById(id);

        if(blogEntry==null)
        {
            log.info("BlogEntry {} not found!", request.getId());

            // We do not give any hints to the client if the supermarket does not belong to him
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(request.getId()).asRuntimeException());
            return;
        }

        response.setEntry(
                blogEntry
        );

        log.info("getBlogEntryById response: {}", response.build());

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }


}



/*
    @Override
    public void getLayoutById(GetLayoutByIdRequest request,
                              StreamObserver<GetLayoutByIdResponse> responseObserver) {
        log.info("getLayoutById request: {}", request);

        GetLayoutByIdResponse.Builder response = GetLayoutByIdResponse.newBuilder();

        com.neoception.floww.layouter.supermarket.Supermarket supermarket = supermarketRepository
                .findByTenantIdAndIdEquals(request.getTenantId(),UUID.fromString(request.getId()));

        if(supermarket==null)
        {
            log.info("Supermarket {} not found!", request.getId());

            // We do not give any hints to the client if the supermarket does not belong to him
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(request.getId()).asRuntimeException());
            return;
        }

        response.setLayout(supermarket.getLayout());

        log.info("getLayoutById response: {}", response.build());

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }


}*/
