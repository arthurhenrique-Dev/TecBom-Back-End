package com.tecbom.e_commerce.Infra.Adapters.Input.Controllers;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOReturnUser;
import com.tecbom.e_commerce.Application.DTOs.Users.DTOSearchUser;
import com.tecbom.e_commerce.Application.UseCases.User.*;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TecBom/admin_dashboard/")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final DeleteMasterUseCase deleteMasterUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final DissmissAdminUseCase dissmissAdminUseCase;
    private final HireUserUseCase hireUserUseCase;
    private final ReactivateUserUseCase reactivateUserUseCase;
    private final SearchUserUseCase searchUserUseCase;
    private final SearchAdminUseCase searchAdminUseCase;

    public AdminController(DeleteMasterUseCase deleteMasterUseCase, DeleteUserUseCase deleteUserUseCase, DissmissAdminUseCase dissmissAdminUseCase, HireUserUseCase hireUserUseCase, ReactivateUserUseCase reactivateUserUseCase, SearchUserUseCase searchUserUseCase, SearchAdminUseCase searchAdminUseCase) {
        this.deleteMasterUseCase = deleteMasterUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.dissmissAdminUseCase = dissmissAdminUseCase;
        this.hireUserUseCase = hireUserUseCase;
        this.reactivateUserUseCase = reactivateUserUseCase;
        this.searchUserUseCase = searchUserUseCase;
        this.searchAdminUseCase = searchAdminUseCase;
    }

    @DeleteMapping("/master")
    public ResponseEntity deleteMaster(@RequestBody @Valid Cpf cpf) {
        deleteMasterUseCase.deleteMasterUser(cpf);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/stats")
    public ResponseEntity deleteUser(@RequestBody @Valid Cpf cpf) {
        deleteUserUseCase.deleteUserByCpf(cpf);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/master")
    public ResponseEntity dissmissAdmin(@RequestBody @Valid Cpf cpf) {
        dissmissAdminUseCase.dissmissAdmin(cpf);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user")
    public ResponseEntity hireUser(@RequestBody @Valid Cpf cpf) {
        hireUserUseCase.hireUser(cpf);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/stats")
    public ResponseEntity reactivateUser(@RequestBody @Valid Cpf cpf) {
        reactivateUserUseCase.reactivateUser(cpf);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public List<DTOReturnUser> searchUser(@PathVariable Cpf cpf, @PathVariable Name name, @PathVariable EmailVO email, @PathVariable PhoneNumber phoneNumber, @RequestParam(defaultValue = "0") Natural pages, @RequestParam(defaultValue = "20") Natural size) {
        return searchUserUseCase.searchUsers(cpf, name, email, phoneNumber, pages, size);
    }

    @GetMapping("/admin")
    public List<DTOReturnUser> searchAdmin(@PathVariable Cpf cpf, @PathVariable Name name, @PathVariable EmailVO email, @PathVariable PhoneNumber phoneNumber, @RequestParam(defaultValue = "0") Natural pages, @RequestParam(defaultValue = "20") Natural size){
        return searchAdminUseCase.searchAdmins(cpf, name, email, phoneNumber, pages, size);
    }


}
