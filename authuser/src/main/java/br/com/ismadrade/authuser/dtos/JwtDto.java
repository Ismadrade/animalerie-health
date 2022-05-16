package br.com.ismadrade.authuser.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.NonNull;


@Getter
@Setter
@RequiredArgsConstructor
public class JwtDto {

    @NonNull
    private String token;
    private String type = "Bearer";
}
