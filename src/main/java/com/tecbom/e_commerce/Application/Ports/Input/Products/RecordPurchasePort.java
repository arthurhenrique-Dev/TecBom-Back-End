package com.tecbom.e_commerce.Application.Ports.Input.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTORecordPurchase;

public interface RecordPurchasePort {
    
    void RecordPurchase(DTORecordPurchase dtoRecordPurchase);
}
