package ru.amironnikov.order.service.impl;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import productService.ProductOuterClass;
import productService.ProductServiceGrpc;
import ru.amironnikov.order.service.ProductGrpcService;

import java.util.List;

@Service
public class ProductGrpcClientImpl implements ProductGrpcService {

    @GrpcClient("products-grpc-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productsStub;

    @Override
    public List<ProductOuterClass.Product> getProducts() {
        return productsStub.findAll(
                        ProductOuterClass.UsersParams.newBuilder().build())
                .getProductsList();
    }
}
