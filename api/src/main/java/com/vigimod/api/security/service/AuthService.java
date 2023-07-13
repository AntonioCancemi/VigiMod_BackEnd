package com.vigimod.api.security.service;

import com.vigimod.api.security.payload.LoginDto;
import com.vigimod.api.security.payload.RegisterDto;

public interface AuthService {

	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);

}
