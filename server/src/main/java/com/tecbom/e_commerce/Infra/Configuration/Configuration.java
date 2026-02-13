package com.tecbom.e_commerce.Infra.Configuration;

import com.tecbom.e_commerce.Application.Mappers.ProductMapper;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Output.*;
import com.tecbom.e_commerce.Application.UseCases.Products.*;
import com.tecbom.e_commerce.Application.UseCases.User.*;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Adapters.CartRepositoryAdapter;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Adapters.ProductRepositoryAdapter;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Mappers.NoSQLMapper;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Repositories.MongoCartRepository;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Repositories.MongoProductRepository;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Adapters.MasterRepositoryAdapter;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Adapters.UserRepositoryAdapter;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Mappers.SQLMapper;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Repositories.JpaMasterRepository;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Repositories.JpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    UserRepository userRepository(JpaUserRepository jpaUserRepository, SQLMapper sqlMapper) {
        return new UserRepositoryAdapter(jpaUserRepository, sqlMapper);
    }
    @Bean
    MasterRepository masterRepository(JpaMasterRepository jpaMasterRepository, SQLMapper sqlMapper) {
        return new MasterRepositoryAdapter(jpaMasterRepository, sqlMapper);
    }
    @Bean
    ProductRepository productRepository(MongoTemplate mongoTemplate, MongoProductRepository mongoProductRepository, NoSQLMapper noSQLMapper) {
        return new ProductRepositoryAdapter(mongoTemplate, mongoProductRepository, noSQLMapper);
    }
    @Bean
    CartRepository cartRepository(MongoCartRepository mongoCartRepository, NoSQLMapper noSQLMapper) {
        return new CartRepositoryAdapter(mongoCartRepository, noSQLMapper);
    }
    @Bean
    public UserMapper userMapper(CepService cepService) {
        return new UserMapper(cepService);
    }
    @Bean
    public ProductMapper productMapper(){
        return new ProductMapper();
    }
    @Bean
    AddCartItemUseCase addCartItemUseCase(CartRepository cartRepository) {
        return new AddCartItemUseCase(cartRepository);
    }
    @Bean
    AddMasterUseCase addMasterUseCase(MasterRepository masterRepository, UserMapper userMapper) {
        return new AddMasterUseCase(masterRepository, userMapper);
    }
    @Bean
    ConfirmEmailValidationTokenUseCase confirmEmailValidationTokenUseCase(UserRepository userRepository) {
        return new ConfirmEmailValidationTokenUseCase(userRepository);
    }
    @Bean
    ConfirmPasswordTokenUseCase confirmPasswordTokenUseCase(UserRepository userRepository) {
        return new ConfirmPasswordTokenUseCase(userRepository);
    }
    @Bean
    DeleteMasterUseCase deleteMasterUseCase(MasterRepository masterRepository) {
        return new DeleteMasterUseCase(masterRepository);
    }
    @Bean
    MasterOnUseCase masterOnUseCase(MasterRepository masterRepository) {
        return new MasterOnUseCase(masterRepository);
    }
    @Bean
    DeleteUserUseCase deleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }
    @Bean
    DissmissAdminUseCase dissmissAdminUseCase(UserRepository userRepository) {
        return new DissmissAdminUseCase(userRepository);
    }
    @Bean
    GetCartUseCase getCartUseCase(CartRepository cartRepository) {
        return new GetCartUseCase(cartRepository);
    }
    @Bean
    HireUserUseCase hireUserUseCase(UserRepository userRepository) {
        return new HireUserUseCase(userRepository);
    }
    @Bean
    ReactivateUserUseCase reactivateUserUseCase(UserRepository userRepository) {
        return new ReactivateUserUseCase(userRepository);
    }
    @Bean
    RemoveCartItemUseCase removeCartItemUseCase(CartRepository cartRepository) {
        return new RemoveCartItemUseCase(cartRepository);
    }
    @Bean
    SaveAdminUseCase saveAdminUseCase(UserRepository userRepository, CartRepository cartRepository, UserMapper userMapper, EmailService emailService) {
        return new SaveAdminUseCase(userRepository, userMapper, emailService, cartRepository);
    }
    @Bean
    SaveUserUseCase saveUserUseCase(UserRepository userRepository, CartRepository cartRepository, UserMapper userMapper, EmailService emailService) {
        return new SaveUserUseCase(userRepository, cartRepository, userMapper, emailService);
    }
    @Bean
    SearchAdminUseCase searchAdminUseCase(UserRepository userRepository, UserMapper userMapper) {
        return new SearchAdminUseCase(userRepository, userMapper);
    }
    @Bean
    SearchUserUseCase searchUserUseCase(UserRepository userRepository, UserMapper userMapper) {
        return new SearchUserUseCase(userRepository, userMapper);
    }
    @Bean
    UpdatePasswordUserUseCase updatePasswordUserUseCase(UserRepository userRepository, EmailService emailService) {
        return new UpdatePasswordUserUseCase(userRepository, emailService);
    }
    @Bean
    UpdateUserUseCase updateUserUseCase(UserRepository userRepository, UserMapper userMapper) {
        return new UpdateUserUseCase(userRepository, userMapper);
    }
    @Bean
    AdminSearchUseCase adminSearchUseCase(ProductRepository productRepository, ProductMapper productMapper) {
        return new AdminSearchUseCase(productRepository, productMapper);
    }
    @Bean
    ApplyDiscountUseCase applyDiscountUseCase(ProductRepository productRepository) {
        return new ApplyDiscountUseCase(productRepository);
    }
    @Bean
    DeleteModelUseCase deleteModelUseCase(ProductRepository productRepository) {
        return new DeleteModelUseCase(productRepository);
    }
    @Bean
    DeleteProductUseCase deleteProductUseCase(ProductRepository productRepository) {
        return new DeleteProductUseCase(productRepository);
    }
    @Bean
    NewModelUseCase newModelUseCase(ProductRepository productRepository) {
        return new NewModelUseCase(productRepository);
    }
    @Bean
    NewReviewUseCase newReviewUseCase(ProductRepository productRepository) {
        return new NewReviewUseCase(productRepository);
    }
    @Bean
    NormalSearchUseCase normalSearchUseCase(ProductRepository productRepository, ProductMapper productMapper) {
        return new NormalSearchUseCase(productRepository, productMapper);
    }
    @Bean
    RecordPurchaseUseCase recordPurchaseUseCase(ProductRepository productRepository) {
        return new RecordPurchaseUseCase(productRepository);
    }
    @Bean
    SaveProductUseCase saveProductUseCase(ProductRepository productRepository, ProductMapper productMapper) {
        return new SaveProductUseCase(productRepository, productMapper);
    }
    @Bean
    UpdateProductUseCase updateProductUseCase(ProductRepository productRepository) {
        return new UpdateProductUseCase(productRepository);
    }
}
