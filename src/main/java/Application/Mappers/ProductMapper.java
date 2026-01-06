package Application.Mappers;

import Application.DTOs.Products.DTOModel;
import Application.DTOs.Products.DTOReturnNormalProduct;
import Application.DTOs.Products.DTOSaveProduct;
import Domain.Entities.Products.Model;
import Domain.Entities.Products.Product;

public class ProductMapper {

    Model modelCreator(DTOModel dtoModel) {
        Model model = new Model(
        dtoModel.name(),
        dtoModel.price(),
        dtoModel.quantity(),
        dtoModel.photos(),
        dtoModel.DiscountPercentage()
        );
        return model;
    }

    public Product toRegister(DTOSaveProduct dtoSaveProduct) {

        Product product = new Product(
            dtoSaveProduct.name(),
            dtoSaveProduct.description(),
            dtoSaveProduct.models().stream().map(this::modelCreator).toList(),
            dtoSaveProduct.category()
        );
        return product;
    }

    public DTOReturnNormalProduct toReturn(Product product){
        DTOReturnNormalProduct returnProduct = new DTOReturnNormalProduct(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getModels()
        );
        return returnProduct;
    }

}
