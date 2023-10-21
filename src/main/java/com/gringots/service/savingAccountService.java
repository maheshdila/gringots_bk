package com.gringots.service;

import com.gringots.model.request.CustomerRequestDto;

public interface savingAccountService {
    boolean createSavingAccount(CustomerRequestDto customerRequestDto);
}
