package com.dream.study01.service;

import com.dream.study01.domain.entity.RefreshToken;
import com.dream.study01.domain.entity.User;
import com.dream.study01.domain.repository.RefreshTokenRepository;
import com.dream.study01.domain.repository.UserRepository;
import com.dream.study01.dto.TokenDto;
import com.dream.study01.dto.TokenRequestDto;
import com.dream.study01.dto.UserRequestDto;
import com.dream.study01.dto.UserResponseDto;
import com.dream.study01.jwt.TokenProvider;
import com.dream.study01.service.shop.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CartService cartService;

    @Transactional
    public UserResponseDto signup(UserRequestDto userRequestDto){
        if(userRepository.existsByEmail(userRequestDto.getEmail())){
            throw new RuntimeException("이미 가입되있는 유저입니다");
        }
        User user = userRepository.save(userRequestDto.toUser(passwordEncoder));
        cartService.createCart(user);
        return UserResponseDto.of(user);
    }

    @Transactional
    public TokenDto login(UserRequestDto userRequestDto){
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
        if(!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(()-> new RuntimeException("로그아웃된 사용자입니다.."));

        if(!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())){
            log.info("===============================");
            log.info(tokenRequestDto.getRefreshToken());
            log.info(refreshToken.getValue());
            log.info("===============================");

            throw new RuntimeException("토큰의 유저 정보가 일치하지않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }

}
