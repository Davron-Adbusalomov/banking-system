package com.example.banking_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawDTO {
    private String sender;

    private String password;

    private Long amount;
}
