package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSignInMaster;

public interface AddMasterPort {

    void saveMaster(DTOSignInMaster master);
}
