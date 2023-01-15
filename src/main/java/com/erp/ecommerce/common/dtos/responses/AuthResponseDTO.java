package com.erp.ecommerce.common.dtos.responses;

public record AuthResponseDTO(boolean valid, String accessToken, String refreshToken){}