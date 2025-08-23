package ru.amironnikov.dictionaries.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import productService.ProductOuterClass;
import productService.ProductServiceGrpc;
import ru.amironnikov.common.Product;
import ru.amironnikov.dictionaries.service.ProductCache;

import java.util.List;

@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(ProductGrpcService.class);

    private final ProductCache productCache;

    public ProductGrpcService(ProductCache productCache) {
        this.productCache = productCache;
    }

    @Override
    public void findAll(
            ProductOuterClass.UsersParams request,
            StreamObserver<ProductOuterClass.ProductsResponse> responseObserver) {

        logger.debug("<Get> product list requested");

        List<Product> products = productCache.getProducts();

        var resultBuilder = ProductOuterClass.ProductsResponse.newBuilder();
        resultBuilder.addAllProducts(products.stream()
                .map(product -> {
                    var productBuilder = ProductOuterClass.Product.newBuilder();
                    return productBuilder.setId(product.id().toString())
                            .setName(product.name())
                            .setDescription(product.description())
                            .setCost(product.cost())
                            .setWeight(product.weight())
                            .setGuarantee(product.guarantee())
                            .build();
                }).toList());

        responseObserver.onNext(resultBuilder.build());
        responseObserver.onCompleted();

        logger.debug("Product list return <success>");
    }
}
